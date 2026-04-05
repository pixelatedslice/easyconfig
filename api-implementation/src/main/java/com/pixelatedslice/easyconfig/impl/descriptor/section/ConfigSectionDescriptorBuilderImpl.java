package com.pixelatedslice.easyconfig.impl.descriptor.section;

import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptorBuilder;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptorBuilder;
import com.pixelatedslice.easyconfig.impl.descriptor.node.ConfigNodeDescriptorBuilderImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ConfigSectionDescriptorBuilderImpl implements ConfigSectionDescriptorBuilder {
    private final @NonNull List<@NonNull String> comments = new ArrayList<>();
    private String key;
    private @Nullable ConfigSectionDescriptor parent;
    private @NonNull List<@NonNull ConfigSectionDescriptor> sections = new ArrayList<>();
    private @NonNull List<@NonNull ConfigNodeDescriptor<?>> nodes = new ArrayList<>();

    @Override
    public @NonNull ConfigSectionDescriptorBuilder key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder parent(@Nullable ConfigSectionDescriptor parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder sections(@NonNull ConfigSectionDescriptor @NonNull ... sections) {
        this.sections = List.of(sections);
        return this;
    }


    @Override
    public @NonNull ConfigSectionDescriptorBuilder addSection(@NonNull ConfigSectionDescriptor sectionDescriptor) {
        this.sections.add(sectionDescriptor);
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder addSection(
            @NonNull Consumer<? super @NonNull ConfigSectionDescriptorBuilder> sectionBuilder) {
        var builder = new ConfigSectionDescriptorBuilderImpl();
        sectionBuilder.accept(builder);
        this.sections.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder nodes(@NonNull ConfigNodeDescriptor<?> @NonNull ... nodes) {
        this.nodes = List.of(nodes);
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder addNode(@NonNull ConfigNodeDescriptor<?> nodeDescriptor) {
        this.nodes.add(nodeDescriptor);
        return this;
    }

    @Override
    public @NonNull <T> ConfigSectionDescriptorBuilder addNode(
            @NonNull Consumer<? super @NonNull ConfigNodeDescriptorBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeDescriptorBuilderImpl<T>();
        nodeBuilder.accept(builder);
        this.nodes.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder comments(@NonNull String @NonNull ... comment) {
        Collections.addAll(this.comments, comment);
        return this;
    }

    @Override
    public @NonNull ConfigSectionDescriptorBuilder addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }


    @Override
    public @NonNull ConfigSectionDescriptor build() {
        return new ConfigSectionDescriptorImpl(
                Objects.requireNonNull(this.key),
                this.parent,
                Objects.requireNonNull(this.sections),
                Objects.requireNonNull(this.nodes),
                Objects.requireNonNull(this.comments)
        );
    }
}
