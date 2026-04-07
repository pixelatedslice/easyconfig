package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

public interface WithNestedConfigSection {
    default @NonNull Optional<@NonNull ConfigSection> section(@NonNull String @NonNull ... providedKeys) {
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        return ((providedKeys.length == 1) && providedKeys[0].contains("."))
                ? ConfigSectionIterator.findSectionButInTheBukkitAPIStyle(this.sections(), providedKeys[0])
                : ConfigSectionIterator.findSection(this.sections(), providedKeys);
    }

    @NonNull Collection<@NonNull ConfigSection> sections();

    void addSections(@NonNull ConfigSection @NonNull ... sections);

    void removeSections(@NonNull String @NonNull ... keys);

    void clearSections();

    default @NonNull ConfigSectionIterator sectionIterator() {
        return ServiceLoader.load(ConfigSectionIterator.class).findFirst().orElseThrow();
    }
}