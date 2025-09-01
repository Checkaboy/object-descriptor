package com.checkaboy.descriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Taras Shaptala
 */
public class ObjectDescriptor<O>
        extends HashMap<String, IFieldDescriptor<O, ?>> {

    public void putAll(Collection<? extends IFieldDescriptor<O, ?>> collection) {
        Map<String, IFieldDescriptor<O, ?>> m = collection.stream().collect(Collectors.toMap(
                IFieldDescriptor::getName,
                fd -> fd
        ));
        super.putAll(m);
    }

    public IFieldDescriptor<O, ?> put(IFieldDescriptor<O, ?> value) {
        return this.put(value.getName(), value);
    }

    @SuppressWarnings("unchecked")
    private <V> FieldDescriptor<O, V> getCastedFieldDescriptor(String key) {
        return (FieldDescriptor<O, V>) this.get(key);
    }

    public <V> void valueSet(String name, O object, V value) {
        FieldDescriptor<O, V> fieldDescriptor = getCastedFieldDescriptor(name);
        if (fieldDescriptor != null) fieldDescriptor.set(object, value);
    }

    public <V> V valueGet(String name, O object) {
        FieldDescriptor<O, V> fieldDescriptor = getCastedFieldDescriptor(name);
        return fieldDescriptor.get(object);
    }

}
