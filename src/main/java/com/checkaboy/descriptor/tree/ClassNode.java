package com.checkaboy.descriptor.tree;

import com.checkaboy.descriptor.IFieldDescriptor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Taras Shaptala
 */
public class ClassNode<O> {

    private final Class<O> type;
    private final List<IFieldDescriptor<O, ?>> fields;
    private final List<ClassNode<?>> children;
    private final ClassNode<?> parent;

    public ClassNode(Class<O> type,
                     List<IFieldDescriptor<O, ?>> fields,
                     List<ClassNode<?>> children,
                     ClassNode<?> parent) {
        this.type = type;
        this.fields = fields;
        this.children = children;
        this.parent = parent;
    }

    public Class<O> getType() {
        return type;
    }

    public List<IFieldDescriptor<O, ?>> getFields() {
        return fields;
    }

    public List<ClassNode<?>> getChildren() {
        return children;
    }

    public ClassNode<?> getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Optional<IFieldDescriptor<O, ?>> findField(String name) {
        return fields.stream()
                .filter(f -> f.getName().equals(name))
                .findFirst();
    }

    public Stream<ClassNode<?>> stream() {
        return Stream.concat(
                Stream.of(this),
                children.stream().flatMap(ClassNode::stream)
        );
    }

    public List<IFieldDescriptor<?, ?>> findFieldsByType(Class<?> searchType) {
        return stream()
                .flatMap(n -> n.getFields().stream())
                .filter(fd -> fd.getType().equals(searchType))
                .collect(Collectors.toList());
    }

    public String path() {
        if (parent == null) return type.getSimpleName();
        return parent.path() + "." + type.getSimpleName();
    }

    public String pathOf(IFieldDescriptor<O, ?> field) {
        return path() + "." + field.getName();
    }

    @Override
    public String toString() {
        return "ClassNode{" +
                "type=" + type.getSimpleName() +
                ", fields=" + fields.size() +
                ", children=" + children.size() +
                '}';
    }

}
