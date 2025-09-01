package com.checkaboy.descriptor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Taras Shaptala
 */
public class FieldDescriptor<O, V>
        implements IFieldDescriptor<O, V> {

    private final EFieldType fieldType;
    private final Class<V> type;
    private final String name;
    private final Function<O, V> getter;
    private final BiConsumer<O, V> setter;

    public FieldDescriptor(Class<V> type, String name, Function<O, V> getter, BiConsumer<O, V> setter) {
        this.fieldType = FieldTypifier.getInstance().fieldType(type);
        this.type = type;
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    @SuppressWarnings("unchecked")
    public FieldDescriptor(Class<O> parent, Class<V> type, String name) throws NoSuchFieldException, IllegalAccessException {
        this.fieldType = FieldTypifier.getInstance().fieldType(type);
        this.type = type;
        this.name = name;

        VarHandle varHandle = MethodHandles
                .privateLookupIn(parent, MethodHandles.lookup())
                .findVarHandle(parent, name, type);

        this.getter = (obj) -> (V) varHandle.get(obj);
        this.setter = (obj, val) -> varHandle.set(obj, (V) val);
    }

    @Override
    public EFieldType getFieldType() {
        return fieldType;
    }

    @Override
    public Class<V> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void set(O object, V value) {
        setter.accept(object, value);
    }

    @Override
    public V get(O object) {
        return getter.apply(object);
    }

}
