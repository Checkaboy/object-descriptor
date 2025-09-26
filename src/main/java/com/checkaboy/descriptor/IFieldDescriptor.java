package com.checkaboy.descriptor;

import com.checkaboy.descriptor.typifier.EFieldType;

/**
 * @author Taras Shaptala
 */
public interface IFieldDescriptor<O, V> {

    EFieldType getFieldType();

    Class<V> getType();

    String getName();

    boolean isWritable();

    void set(O object, V value);

    V get(O object);

}
