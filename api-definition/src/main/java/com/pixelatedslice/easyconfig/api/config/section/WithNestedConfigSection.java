package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface WithNestedConfigSection {
    @NonNull List<@NonNull ConfigSection> sections();

    default @NonNull Optional<@NonNull ConfigSection> section(@NonNull String... providedKeys) {
        return ConfigSectionIterator.findSection(this.sections(), providedKeys);
    }

    default @NonNull Optional<@NonNull ConfigSection> nestedSectionButInTheBukkitAPIStyle(@NonNull String key) {
        return ConfigSectionIterator.findSectionButInTheBukkitAPIStyle(this.sections(), key);
    }

    WithNestedConfigSection addSection(@NonNull ConfigSection section);

    WithNestedConfigSection removeSection(@NonNull String key);

    @NonNull ConfigSectionIterator sectionIterator();
}