package com.checkaboy.descriptor;

import com.checkaboy.descriptor.typifier.EFieldType;

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
    private final boolean writable;

    private final Function<O, V> getter;
    private final BiConsumer<O, V> setter;

    public FieldDescriptor(EFieldType fieldType,
                           Class<V> type,
                           String name,
                           boolean writable,
                           Function<O, V> getter,
                           BiConsumer<O, V> setter) {
        this.fieldType = fieldType;
        this.type = type;
        this.name = name;
        this.writable = writable;
        this.getter = getter;
        this.setter = setter;
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
    public boolean isWritable() {
        return writable;
    }

    @Override
    public void set(O object, V value) {
        if (!writable)
            throw new UnsupportedOperationException("Field " + name + " is read-only");
        setter.accept(object, value);
    }

    @Override
    public V get(O object) {
        return getter.apply(object);
    }

}
