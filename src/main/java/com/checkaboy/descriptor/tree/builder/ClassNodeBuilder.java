package com.checkaboy.descriptor.tree.builder;

import com.checkaboy.descriptor.FieldDescriptor;
import com.checkaboy.descriptor.IFieldDescriptor;
import com.checkaboy.descriptor.tree.ClassNode;
import com.checkaboy.descriptor.tree.context.BuildContext;
import com.checkaboy.descriptor.typifier.EFieldType;
import com.checkaboy.descriptor.typifier.IFieldTypifier;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Shaptala
 */
public class ClassNodeBuilder<O> {

    private final IFieldTypifier typifier;

    public ClassNodeBuilder(IFieldTypifier typifier) {
        this.typifier = typifier;
    }

    public ClassNode<O> build(Class<O> type) {
        return build(type, new BuildContext(), null);
    }

    @SuppressWarnings("unchecked")
    private ClassNode<O> build(Class<O> type, BuildContext ctx, ClassNode<?> parent) {
        if (ctx.isVisited(type)) {
            return (ClassNode<O>) ctx.get(type); // безопасно, типизировано
        }

        List<IFieldDescriptor<O, ?>> descriptors = new ArrayList<>();
        List<ClassNode<?>> children = new ArrayList<>();

        ClassNode<O> currentNode = new ClassNode<>(type, descriptors, children, parent);
        ctx.put(type, currentNode);

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            EFieldType eFieldType = typifier.fieldType(fieldType);
            boolean writable = !Modifier.isFinal(field.getModifiers());

            descriptors.add(createDescriptor(field, eFieldType, writable));
            if (eFieldType == EFieldType.OBJECT) {
                children.add(build((Class<O>) fieldType, ctx, currentNode));
            }
        }

        return currentNode;
    }

    private <V> IFieldDescriptor<O, V> createDescriptor(Field field, EFieldType eFieldType, boolean writable) {
        try {
            VarHandle varHandle = MethodHandles.privateLookupIn(field.getDeclaringClass(), MethodHandles.lookup())
                    .findVarHandle(field.getDeclaringClass(), field.getName(), field.getType());

            @SuppressWarnings("unchecked")
            Class<V> type = (Class<V>) field.getType();

            return new FieldDescriptor<>(
                    eFieldType,
                    type,
                    field.getName(),
                    writable,
                    (obj) -> {
                        try {
                            return (V) varHandle.get(obj);
                        } catch (Throwable t) {
                            throw new RuntimeException(t);
                        }
                    },
                    (obj, val) -> {
                        try {
                            varHandle.set(obj, val);
                        } catch (Throwable t) {
                            throw new RuntimeException(t);
                        }
                    }
            );

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field, e);
        }
    }

}
