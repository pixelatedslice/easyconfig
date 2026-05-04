package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.exception.NodeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.*;

public sealed interface NodeIterator<N extends Node> extends Iterator<N>
        permits NodeIterator.Container, NodeIterator.Env, NodeIterator.Value {
    non-sealed interface Container extends NodeIterator<ContainerNode> {
        static @NonNull Optional<@NonNull ContainerNode> find(
                @NonNull Collection<? extends @NonNull Node> nestedNodes,
                @NonNull String @NonNull ... providedKeys
        ) {
            Objects.requireNonNull(nestedNodes);
            Objects.requireNonNull(providedKeys);

            if (providedKeys.length == 0) {
                return Optional.empty();
            }

            List<String> keys = List.of(providedKeys);
            var currentNestedNodes = nestedNodes;
            int last = keys.size() - 1;
            for (int i = 0; i <= last; i++) {
                String wanted = keys.get(i);
                ContainerNode next = null;
                for (Node node : currentNestedNodes) {
                    if (!(node instanceof ContainerNode containerNode)) {
                        continue;
                    }

                    if (containerNode.key().equals(wanted)) {
                        next = containerNode;
                        break;
                    }
                }
                if (next == null) {
                    return Optional.empty();
                }
                if (i == last) {
                    return Optional.of(next);
                }
                currentNestedNodes = next.children();
            }
            return Optional.empty();
        }
    }

    non-sealed interface Value extends NodeIterator<ValueNode<?>> {
        static <T> @NonNull Optional<@NonNull ValueNode<T>> find(
                @NonNull ContainerNode rootContainer,
                @NonNull TypeToken<@NonNull T> typeToken, @NonNull String @NonNull ... providedKeys
        ) {
            Objects.requireNonNull(rootContainer);
            Objects.requireNonNull(typeToken);
            Objects.requireNonNull(providedKeys);

            if (providedKeys.length == 0) {
                return Optional.empty();
            }

            if (providedKeys.length == 1) {
                var nodeKey = providedKeys[0];
                for (var node : rootContainer.children()) {
                    if (!(node instanceof ValueNode<?> valueNode)) {
                        continue;
                    }

                    if (!valueNode.key().equals(nodeKey)) {
                        continue;
                    }

                    if (!TypeTokenUtils.matches(valueNode.typeToken(), typeToken)) {
                        throw NodeException.NODE_HAS_WRONG_VALUE_TYPE(
                                nodeKey,
                                valueNode.typeToken(),
                                typeToken.toString()
                        );
                    }

                    @SuppressWarnings("unchecked") var casted = (ValueNode<T>) valueNode;
                    return Optional.of(casted);
                }
                return Optional.empty();
            }
            String[] parentKeys = Arrays.copyOf(providedKeys, providedKeys.length - 1);
            var nodeKey = providedKeys[providedKeys.length - 1];
            var sectionOptional = Container.find(rootContainer.children(), parentKeys);
            return sectionOptional.flatMap((@NonNull ContainerNode section) -> section.valueNode(typeToken, nodeKey));
        }
    }

    non-sealed interface Env extends NodeIterator<EnvNode<?>> {
        static <T> @NonNull Optional<@NonNull EnvNode<T>> find(
                @NonNull ContainerNode rootContainer,
                @NonNull TypeToken<@NonNull T> typeToken, @NonNull String @NonNull ... providedKeys
        ) {
            var opt = Value.find(rootContainer, typeToken, providedKeys);

            if (opt.isEmpty()) {
                return Optional.empty();
            }

            var found = opt.get();
            if (!(found instanceof EnvNode<?>)) {
                throw NodeException.DID_NOT_EXPECT_NODE_TYPE(found.key(), NodeType.ENV_NODE, found.nodeType());
            }
            return Optional.of((EnvNode<T>) found);
        }
    }
}
