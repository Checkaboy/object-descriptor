package com.checkaboy.descriptor;

/**
 * @author Taras Shaptala
 */
public interface IFieldDescriptor<O, V> {

    EFieldType getFieldType();

    Class<V> getType();

    String getName();

    void set(O object, V value);

    V get(O object);

}
