package com.pixelatedslice.easyconfig.api.config.section;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jspecify.annotations.NonNull;

import java.util.*;

public interface ConfigSectionIterator extends Iterator<ConfigSection> {
    static <S extends ConfigSection> @NonNull Optional<@NonNull S> findSection(
            @NonNull Collection<? extends @NonNull S> nestedSections,
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
            S next = null;
            for (S section : currentNestedSections) {
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
            currentNestedSections = (Collection<? extends S>) next.sections();
        }
        return Optional.empty();
    }

    @SuppressWarnings("DuplicatedCode")
    static <S extends ConfigSection> @NonNull Optional<@NonNull S> findButInTheBukkitAPIStyle(
            @NonNull Collection<? extends @NonNull S> nestedSections, @NonNull String key) {
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
