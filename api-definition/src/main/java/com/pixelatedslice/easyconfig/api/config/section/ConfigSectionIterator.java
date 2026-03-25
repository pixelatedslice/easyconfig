package com.pixelatedslice.easyconfig.api.config.section;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jspecify.annotations.NonNull;

import java.util.*;

/**
 * An iterator interface for traversing through {@link ConfigSection} elements in a hierarchical configuration
 * structure.
 */
public interface ConfigSectionIterator extends Iterator<ConfigSection> {
    /**
     * Finds a specific {@link ConfigSection} within a nested hierarchy of configuration sections
     * using the provided hierarchical keys.
     *
     * @param nestedSections the list of nested {@link ConfigSection} instances to search through; must not be null
     * @param providedKeys   the array of keys representing the hierarchical path to locate the desired
     *                       {@link ConfigSection};
     *                       must not be null and must not be empty
     * @return an {@link Optional} containing the found {@link ConfigSection} if a matching section exists,
     * or an empty {@link Optional} if no matching section is found
     * @throws NullPointerException if {@code nestedSections} or {@code providedKeys} is null
     */
    static @NonNull Optional<@NonNull ConfigSection> findSection(
            @NonNull List<@NonNull ConfigSection> nestedSections,
            @NonNull String... providedKeys
    ) {
        Objects.requireNonNull(nestedSections);
        Objects.requireNonNull(providedKeys);

        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        List<String> keys = List.of(providedKeys);
        List<ConfigSection> currentNestedSections = nestedSections;

        int last = keys.size() - 1;
        for (int i = 0; i <= last; i++) {
            String wanted = keys.get(i);

            ConfigSection next = null;
            for (ConfigSection section : currentNestedSections) {
                if (section.key().equals(wanted)) {
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

            currentNestedSections = next.nestedSections();
        }

        return Optional.empty();
    }

    /**
     * Searches for a {@link ConfigSection} within a hierarchy of nested sections using a hierarchical key.
     * The key may contain dot-separated components representing the path to the desired section.
     *
     * @param nestedSections the list of top-level {@link ConfigSection} objects to search within; cannot be null
     * @param key            the hierarchical key representing the path to the desired {@link ConfigSection};
     *                       must match the pattern defined in {@code EasyConfig.KEY_REGEX}; cannot be null
     * @return an {@link Optional} containing the found {@link ConfigSection} if it exists, or an empty
     * {@link Optional} if no matching section is found
     * @throws NullPointerException   if {@code nestedSections} or {@code key} is null
     * @throws BrokenNodeKeyException if the hierarchical key does not match the expected pattern defined in {@code
     *                                EasyConfig.KEY_REGEX}
     */
    @SuppressWarnings("DuplicatedCode")
    static @NonNull Optional<@NonNull ConfigSection> findSectionButInTheBukkitAPIStyle(
            @NonNull List<@NonNull ConfigSection> nestedSections,
            @NonNull String key
    ) {
        Objects.requireNonNull(nestedSections);
        Objects.requireNonNull(key);

        List<String> keys;
        if (!EasyConfig.KEY_REGEX.matcher(key).matches()) {
            throw new BrokenNodeKeyException(key, EasyConfig.KEY_REGEX.pattern());
        }
        keys = List.of(key.split("\\."));

        return findSection(nestedSections, keys.toArray(new String[0]));
    }

    /**
     * Determines if there are more elements to iterate over in the collection or structure.
     *
     * @return {@code true} if there are additional elements to iterate over, {@code false} otherwise
     */
    @Override
    boolean hasNext();

    /**
     * Advances the iterator to the next {@link ConfigSection} in the hierarchy.
     * This method retrieves the next available configuration section if one exists.
     *
     * @return the next {@link ConfigSection} in the hierarchy.
     * @throws NoSuchElementException if the next element is null
     */
    @Override
    @NonNull
    ConfigSection next();
}
