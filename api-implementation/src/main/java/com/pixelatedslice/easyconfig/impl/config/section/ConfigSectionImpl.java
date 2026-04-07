package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.WithDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.impl.descriptor.section.ConfigSectionDescriptorImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConfigSectionImpl implements ConfigSection {
    private final @NonNull ConfigSectionDescriptor descriptor;
    private final @NonNull List<@NonNull ConfigNode<?>> nodes;
    private final @NonNull List<@NonNull ConfigSection> sections;
    private final @Nullable ConfigSection parent;

    public ConfigSectionImpl(
            @NonNull ConfigSectionDescriptor descriptor,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull ConfigNode<?>> nodes,
            @NonNull List<@NonNull ConfigSection> sections
    ) {
        this.descriptor = descriptor;
        this.parent = parent;
        this.nodes = nodes;
        this.sections = sections;
    }

    public static ConfigSection newRootSection() {
        return new ConfigSectionImpl(ConfigSectionDescriptorImpl.newRootSectionDescriptor(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        return this.nodes;
    }

    @Override
    public @NonNull ConfigSection addNodes(@NonNull ConfigNode<?> @NonNull ... children) {
        for (var child : children) {
            this.nodes.add(child);
            this.descriptor.addNodes(child.descriptor());
        }

        return this;
    }

    @Override
    public @NonNull ConfigSection removeNodes(@NonNull String @NonNull ... keys) {
        for (var key : keys) {
            this.nodes.removeIf(((@NonNull ConfigNode<?> node) -> node.descriptor().key().equals(key)));
            this.descriptor.removeNodes(key);
        }
        return this;
    }

    @Override
    public @NonNull ConfigSection clearNodes() {
        this.nodes.clear();
        return this;
    }

    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        return this.sections;
    }

    @Override
    public @NonNull ConfigSection addSections(@NonNull ConfigSection @NonNull ... sections) {
        for (var section : sections) {
            this.sections.add(section);
            this.descriptor.addSections(section.descriptor());
        }
        return this;
    }

    @Override
    public @NonNull ConfigSection removeSections(@NonNull String @NonNull ... keys) {
        for (var key : keys) {
            this.sections.removeIf((WithDescriptor<ConfigSectionDescriptor> section) -> section
                    .descriptor()
                    .key()
                    .equals(key)
            );
            this.descriptor.removeSections(key);
        }
        return this;
    }

    @Override
    public @NonNull ConfigSection clearSections() {
        this.sections.clear();
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptor descriptor() {
        return this.descriptor;
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigSection that)
                && this.descriptor.equals(that.descriptor())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.descriptor, this.parent);
    }
}
