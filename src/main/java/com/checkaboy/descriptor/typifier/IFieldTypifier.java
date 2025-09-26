package com.checkaboy.descriptor.typifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Taras Shaptala
 */
public interface IFieldTypifier {

    List<Class<?>> getPrimitives();

    List<Class<? extends Collection>> getCollections();

    List<Class<? extends Map>> getMaps();

    List<Class<?>> getSpecials();

    <V> EFieldType fieldType(Class<V> type);

}
