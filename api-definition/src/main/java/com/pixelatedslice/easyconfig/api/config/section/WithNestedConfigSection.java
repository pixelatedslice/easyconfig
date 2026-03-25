package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Represents an entity that contains a collection of child configuration nodes.
 * This interface provides methods to manage and interact with the child nodes
 * within a hierarchical configuration structure.
 */
public interface WithNestedConfigSection {

    /**
     * Retrieves the list of nested configuration sections contained within this instance.
     * Each section represents a child node in the hierarchical configuration structure.
     *
     * @return a non-null list of non-null {@link ConfigSection} instances representing the nested sections
     * @throws NullPointerException if the method implementation tries to return a null list
     */
    @NonNull List<@NonNull ConfigSection> nestedSections();

    /**
     * Retrieves a nested configuration section based on the provided keys.
     * The method searches through the nested sections associated with this entity
     * and attempts to return a matching configuration section if found.
     *
     * @param providedKeys the sequence of keys used to locate the desired nested configuration section;
     *                     must not be null, and all elements in the sequence must also be non-null
     * @return an {@link Optional} containing the matching {@link ConfigSection} if found,
     * or an empty {@link Optional} if no matching section exists
     * @throws NullPointerException if the {@code providedKeys} array or any of its elements is null
     */
    default @NonNull Optional<@NonNull ConfigSection> nestedSection(@NonNull String... providedKeys) {
        return ConfigSectionIterator.findSection(this.nestedSections(), providedKeys);
    }

    /**
     * Searches for a nested configuration section within this entity's collection of child
     * sections using a key, following a Bukkit API-style lookup.
     *
     * @param key the non-null key used to identify the desired nested configuration section
     * @return an {@link Optional} containing the found {@link ConfigSection} if one exists
     * matching the given key, or an empty {@link Optional} if no match is found
     * @throws NullPointerException if the provided {@code key} is null
     */
    default @NonNull Optional<@NonNull ConfigSection> nestedSectionButInTheBukkitAPIStyle(@NonNull String key) {
        return ConfigSectionIterator.findSectionButInTheBukkitAPIStyle(this.nestedSections(), key);
    }

    /**
     * Adds a nested configuration section to this entity's collection of child sections.
     *
     * @param section the non-null configuration section to be added as a nested section
     * @throws NullPointerException if the provided {@code section} is null
     */
    void addNestedSection(@NonNull ConfigSection section);

    /**
     * Removes a nested configuration section identified by the specified key.
     *
     * @param key the non-null key of the nested section to be removed
     * @return {@code true} if the nested section was successfully removed, {@code false} if no section
     * with the given key was found or removal was not possible
     * @throws NullPointerException if the {@code key} is null
     */
    void removeNestedSection(@NonNull String key);

    /**
     * Provides an iterator to traverse through the nested configuration sections associated
     * with this entity. The iterator ensures that all returned configuration sections are
     * non-null, reflecting the hierarchical structure of the configuration.
     *
     * @return a non-null {@link Iterator} containing non-null {@link ConfigSection} objects
     * @throws NullPointerException if the method is called on a null context or if any
     *                              required non-null parameter is null
     */
    @NonNull ConfigSectionIterator sectionIterator();
}
