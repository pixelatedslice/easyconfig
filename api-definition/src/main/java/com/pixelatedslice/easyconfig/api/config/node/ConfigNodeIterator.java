package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.api.config.section.WithNestedConfigSection;
import com.pixelatedslice.easyconfig.api.utils.type_token.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConfigNodeIterator implements Iterator<ConfigNode<?>> {
    private final @NonNull Queue<@NonNull ConfigSection> sectionQueue = new LinkedList<>();
    private final @NonNull Queue<@NonNull ConfigNode<?>> nodeQueue = new LinkedList<>();

    public ConfigNodeIterator(@NonNull ConfigSection rootContainer) {
        Objects.requireNonNull(rootContainer);
        this.enqueueSection(rootContainer);
    }

    @SuppressWarnings("unchecked")
    public static <T, C extends WithConfigNodeChildren & WithNestedConfigSection>
    @NonNull Optional<@NonNull ConfigNode<T>> find(
            @NonNull C rootContainer,
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
            for (var node : rootContainer.nodes()) {
                if (!node.key().equals(nodeKey)) {
                    continue;
                }
                if (!TypeTokenUtils.matches(node.typeToken(), typeToken)) {
                    throw new IllegalStateException(String.format(
                            "The node (%s) is not of the expected typeToken", nodeKey
                    ));
                }
                return Optional.of((ConfigNode<T>) node);
            }
            return Optional.empty();
        }
        String[] parentKeys = Arrays.copyOf(providedKeys, providedKeys.length - 1);
        var nodeKey = providedKeys[providedKeys.length - 1];
        var sectionOptional = ConfigSectionIterator.findSection(rootContainer.sections(), parentKeys);
        return sectionOptional.flatMap((@NonNull WithConfigNodeChildren section) -> section.node(typeToken, nodeKey));
    }

    public static <C extends WithConfigNodeChildren & WithNestedConfigSection>
    @NonNull Optional<@NonNull TypeToken<?>> findTypeToken(
            @NonNull C rootContainer,
            @NonNull String @NonNull ... providedKeys
    ) {
        Objects.requireNonNull(rootContainer);
        Objects.requireNonNull(providedKeys);

        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        if (providedKeys.length == 1) {
            var nodeKey = providedKeys[0];
            for (var node : rootContainer.nodes()) {
                if (!node.key().equals(nodeKey)) {
                    continue;
                }
                return Optional.of(node.typeToken());
            }
            return Optional.empty();
        }

        String[] parentKeys = Arrays.copyOf(providedKeys, providedKeys.length - 1);
        var nodeKey = providedKeys[providedKeys.length - 1];
        var sectionOptional = ConfigSectionIterator.findSection(rootContainer.sections(), parentKeys);
        return sectionOptional.flatMap((@NonNull WithConfigNodeChildren section) -> section.nodeTypeToken(nodeKey));
    }

    private void enqueueSection(@NonNull ConfigSection container) {
        this.sectionQueue.add(container);
    }


    private void fillNodeQueueFromCurrentLevel() {
        while (this.nodeQueue.isEmpty() && !this.sectionQueue.isEmpty()) {
            ConfigSection current = this.sectionQueue.poll();

            this.nodeQueue.addAll(current.nodes());

            for (ConfigSection nested : current.sections()) {
                this.enqueueSection(nested);
            }
        }
    }


    @Override
    public boolean hasNext() {
        if (this.nodeQueue.isEmpty()) {
            this.fillNodeQueueFromCurrentLevel();
        }
        return !this.nodeQueue.isEmpty();
    }


    @Override
    public @Nullable ConfigNode<?> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        var node = this.nodeQueue.poll();

        if (this.nodeQueue.isEmpty()) {
            this.fillNodeQueueFromCurrentLevel();
        }

        return node;
    }
}
