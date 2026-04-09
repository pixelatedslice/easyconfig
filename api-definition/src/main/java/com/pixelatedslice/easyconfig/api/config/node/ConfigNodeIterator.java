package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.api.config.section.WithNestedConfigSection;
import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jspecify.annotations.NonNull;

import java.util.*;

public interface ConfigNodeIterator extends Iterator<ConfigNode<?>> {
    @SuppressWarnings("unchecked")
    static <T, C extends WithConfigNodeChildren & WithNestedConfigSection>
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
                var descriptor = node.descriptor();
                if (!descriptor.key().equals(nodeKey)) {
                    continue;
                }
                if (!descriptor.typeToken().orElseThrow().equals(typeToken)) {
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

    @SuppressWarnings("DuplicatedCode")
    static <T, C extends WithConfigNodeChildren & WithNestedConfigSection>
    @NonNull Optional<@NonNull ConfigNode<T>> findButInTheBukkitAPIStyle(
            @NonNull C rootContainer, @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String key) {
        Objects.requireNonNull(rootContainer);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(key);
        List<String> keys;
        if (!EasyConfig.KEY_REGEX.matcher(key).matches()) {
            throw new BrokenNodeKeyException(key, EasyConfig.KEY_REGEX.pattern());
        }
        keys = List.of(key.split("\\."));

        return find(rootContainer, typeToken, keys.toArray(String[]::new));
    }
}
