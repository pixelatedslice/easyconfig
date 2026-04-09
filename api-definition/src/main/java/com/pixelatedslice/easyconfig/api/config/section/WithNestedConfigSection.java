package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

@FunctionalInterface
public interface WithNestedConfigSection<S extends ConfigSection> {
    default @NonNull Optional<@NonNull S> section(@NonNull String @NonNull ... providedKeys) {
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        return ((providedKeys.length == 1) && providedKeys[0].contains("."))
                ? ConfigSectionIterator.findButInTheBukkitAPIStyle(this.sections(), providedKeys[0])
                : ConfigSectionIterator.findSection(this.sections(), providedKeys);
    }

    @NonNull Collection<@NonNull S> sections();

    @NonNull
    default ConfigSectionIterator sectionIterator() {
        return ServiceLoader.load(ConfigSectionIterator.class).findFirst().orElseThrow();
    }

}