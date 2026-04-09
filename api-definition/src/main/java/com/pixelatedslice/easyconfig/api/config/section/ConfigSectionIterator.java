package com.pixelatedslice.easyconfig.api.config.section;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jspecify.annotations.NonNull;

import java.util.*;

public interface ConfigSectionIterator extends Iterator<ConfigSection> {
    static @NonNull Optional<@NonNull ConfigSection> findSection(
            @NonNull Collection<? extends @NonNull ConfigSection> nestedSections,
            @NonNull String... providedKeys
    ) {
        Objects.requireNonNull(nestedSections);
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }
        List<String> keys = List.of(providedKeys);
        var currentNestedSections = nestedSections;
        int last = keys.size() - 1;
        for (int i = 0; i <= last; i++) {
            String wanted = keys.get(i);
            ConfigSection next = null;
            for (ConfigSection section : currentNestedSections) {
                if (section.descriptor().key().equals(wanted)) {
                    next = section;
                    break;
                }
            }
            if (next == null) {
                return Optional.empty();
            }
            if (i == last) {
                return Optional.of(next);
            }
            currentNestedSections = next.sections();
        }
        return Optional.empty();
    }

    @SuppressWarnings("DuplicatedCode")
    static @NonNull Optional<@NonNull ConfigSection> findButInTheBukkitAPIStyle(
            @NonNull Collection<? extends @NonNull ConfigSection> nestedSections, @NonNull String key) {
        Objects.requireNonNull(nestedSections);
        Objects.requireNonNull(key);
        List<String> keys;
        if (!EasyConfig.KEY_REGEX.matcher(key).matches()) {
            throw new BrokenNodeKeyException(key, EasyConfig.KEY_REGEX.pattern());
        }
        keys = List.of(key.split("\\."));
        return findSection(nestedSections, keys.toArray(String[]::new));
    }
}
