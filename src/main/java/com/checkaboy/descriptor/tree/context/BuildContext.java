package com.checkaboy.descriptor.tree.context;

import com.checkaboy.descriptor.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Taras Shaptala
 */
public class BuildContext {

    private final Map<Class<?>, ClassNode> visited = new HashMap<>();

    public boolean isVisited(Class<?> type) {
        return visited.containsKey(type);
    }

    public void put(Class<?> type, ClassNode node) {
        visited.put(type, node);
    }

    public ClassNode get(Class<?> type) {
        return visited.get(type);
    }

}
