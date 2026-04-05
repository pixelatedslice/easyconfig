package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.WithNestedConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.WithDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ConfigSectionImpl implements ConfigSection {
    private final @NonNull ConfigSectionDescriptor descriptor;
    private final @NonNull List<@NonNull ConfigNode<?>> nodes;
    private final @NonNull List<@NonNull ConfigSection> sections;
    private @Nullable ConfigSection parent;

    ConfigSectionImpl(
            @NonNull ConfigSectionDescriptor descriptor,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull ConfigNode<?>> nodes,
            @NonNull List<@NonNull ConfigSection> sections
    ) {
        this.descriptor = descriptor;
        this.nodes = nodes;
        this.sections = sections;
    }

    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        return this.nodes;
    }

    @Override
    public @NonNull WithConfigNodeChildren addNode(@NonNull ConfigNode<?> child) {
        this.nodes.add(child);
        return this;
    }

    @Override
    public @NonNull WithConfigNodeChildren removeNode(@NonNull String key) {
        this.nodes.removeIf((WithDescriptor<ConfigNodeDescriptor<?>> node) -> node.descriptor().key().equals(key));
        return this;
    }

    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        return this.sections;
    }

    @Override
    public @NonNull WithNestedConfigSection addSection(@NonNull ConfigSection section) {
        this.sections.add(section);
        return this;
    }

    @Override
    public @NonNull WithNestedConfigSection removeSection(@NonNull String key) {
        this.sections.removeIf((WithDescriptor<ConfigSectionDescriptor> section) -> section
                .descriptor()
                .key()
                .equals(key)
        );
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
    public void setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
        this.descriptor.setParent((parent != null) ? parent.descriptor() : null);
    }
}
