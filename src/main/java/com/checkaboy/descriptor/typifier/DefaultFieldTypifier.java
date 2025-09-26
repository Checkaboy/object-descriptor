package com.checkaboy.descriptor.typifier;

import java.util.*;
import java.util.concurrent.*;

/**
 * Default set of settings for automatic type detection.
 * Can be reconfigured or extended by the user
 *
 * @author Taras Shaptala
 */
public class DefaultFieldTypifier
        implements IFieldTypifier {

//    private static volatile DefaultFieldTypifier instance;

//    public static DefaultFieldTypifier getInstance() {
//        if (instance == null) {
//            synchronized (DefaultFieldTypifier.class) {
//                if (instance == null) {
//                    instance = new DefaultFieldTypifier();
//                }
//            }
//        }
//        return instance;
//    }

    // Default JDK primitives
    private final List<Class<?>> primitives;
    // Default collections realization
    private final List<Class<? extends Collection>> collections;
    // Default maps realizations
    private final List<Class<? extends Map>> maps;
    // User-specific data structures
    private final List<Class<?>> specials;

    private DefaultFieldTypifier() {
        primitives = new ArrayList<>(19);
        // primitives
        primitives.add(byte.class);
        primitives.add(short.class);
        primitives.add(int.class);
        primitives.add(long.class);
        primitives.add(float.class);
        primitives.add(double.class);
        primitives.add(char.class);
        primitives.add(boolean.class);
        // wrapped
        primitives.add(Byte.class);
        primitives.add(Short.class);
        primitives.add(Integer.class);
        primitives.add(Long.class);
        primitives.add(Float.class);
        primitives.add(Double.class);
        primitives.add(Character.class);
        primitives.add(Boolean.class);
        // append
        primitives.add(String.class);
        primitives.add(Enum.class);
        primitives.add(Void.class);

        collections = new ArrayList<>(24);
        // collections
        collections.add(AbstractCollection.class);
        collections.add(AbstractList.class);
        collections.add(ArrayList.class);
        collections.add(Vector.class);
        collections.add(Stack.class);
        collections.add(AbstractSequentialList.class);
        collections.add(LinkedList.class);
        collections.add(AbstractSet.class);
        collections.add(HashSet.class);
        collections.add(LinkedHashSet.class);
        collections.add(TreeSet.class);
        collections.add(EnumSet.class);
        collections.add(AbstractQueue.class);
        collections.add(PriorityQueue.class);
        collections.add(ConcurrentLinkedQueue.class);
        collections.add(ConcurrentLinkedDeque.class);
        collections.add(DelayQueue.class);
        collections.add(LinkedBlockingQueue.class);
        collections.add(LinkedBlockingDeque.class);
        collections.add(PriorityBlockingQueue.class);
        collections.add(SynchronousQueue.class);
        collections.add(CopyOnWriteArrayList.class);
        collections.add(CopyOnWriteArraySet.class);
        collections.add(ConcurrentSkipListSet.class);

        maps = new ArrayList<>(11);
        // maps
        maps.add(AbstractMap.class);
        maps.add(HashMap.class);
        maps.add(LinkedHashMap.class);
        maps.add(TreeMap.class);
        maps.add(WeakHashMap.class);
        maps.add(IdentityHashMap.class);
        maps.add(EnumMap.class);
        maps.add(Hashtable.class);
        maps.add(Properties.class);
        maps.add(ConcurrentHashMap.class);
        maps.add(ConcurrentSkipListMap.class);

        specials = new ArrayList<>(0);
    }

    @Override
    public List<Class<?>> getPrimitives() {
        return primitives;
    }

    @Override
    public List<Class<? extends Collection>> getCollections() {
        return collections;
    }

    @Override
    public List<Class<? extends Map>> getMaps() {
        return maps;
    }

    @Override
    public List<Class<?>> getSpecials() {
        return specials;
    }

    @Override
    public <V> EFieldType fieldType(Class<V> type) {
        if (primitives.contains(type)) return EFieldType.PRIMITIVE;
        else if (collections.contains(type)) return EFieldType.COLLECTION;
        else if (maps.contains(type)) return EFieldType.MAP;
        else if (specials.contains(type)) return EFieldType.SPECIAL;
        else return EFieldType.OBJECT;
    }

}
