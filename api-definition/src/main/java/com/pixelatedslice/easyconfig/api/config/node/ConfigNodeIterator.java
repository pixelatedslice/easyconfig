package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public interface ConfigNodeIterator extends Iterator<ConfigNode<?>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull Optional<@NonNull ConfigNode<T>> findNode(@NonNull ConfigSection rootSection,
            @NonNull TypeToken<@NonNull T> typeToken, @NonNull String... providedKeys) {
        Objects.requireNonNull(rootSection);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }
        if (providedKeys.length == 1) {
            var nodeKey = providedKeys[0];
            for (var node : rootSection.nodes()) {
                if (!node.key().equals(nodeKey)) {
                    continue;
                }
                if (!node.typeToken().equals(typeToken)) {
                    throw new IllegalStateException(String.format("The node (%s) is not of the expected typeToken",
                            nodeKey));
                }
                return Optional.of((ConfigNode<T>) node);
            }
            return Optional.empty();
        }
        String[] parentKeys = Arrays.copyOf(providedKeys, providedKeys.length - 1);
        var nodeKey = providedKeys[providedKeys.length - 1];
        var sectionOptional = ConfigSectionIterator.findSection(rootSection.sections(), parentKeys);
        return sectionOptional.flatMap((@NonNull ConfigSection section) -> section.node(typeToken, nodeKey));
    }

    @SuppressWarnings("DuplicatedCode")
    static <T> @NonNull Optional<@NonNull ConfigNode<T>> findNodeButInTheBukkitAPIStyle(
            @NonNull ConfigSection rootSection, @NonNull TypeToken<@NonNull T> typeToken, @NonNull String key) {
        Objects.requireNonNull(rootSection);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(key);
        List<String> keys;
        if (!EasyConfig.KEY_REGEX.matcher(key).matches()) {
            throw new BrokenNodeKeyException(key, EasyConfig.KEY_REGEX.pattern());
        }
        keys = List.of(key.split("\\."));

        return findNode(rootSection, typeToken, keys.toArray(new String[0]));
    }


    @Override
    boolean hasNext();


    @Override
    @Nullable ConfigNode<?> next();
}
