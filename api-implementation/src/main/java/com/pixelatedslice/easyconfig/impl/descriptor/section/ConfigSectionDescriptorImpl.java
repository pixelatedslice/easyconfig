package com.pixelatedslice.easyconfig.impl.descriptor.section;

import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ConfigSectionDescriptorImpl implements ConfigSectionDescriptor {
    private final @NonNull String key;
    private final @NonNull List<@NonNull ConfigSectionDescriptor> sections;
    private final @NonNull List<@NonNull ConfigNodeDescriptor<?>> nodes;
    private final @NonNull List<@NonNull String> comments;
    private @Nullable ConfigSectionDescriptor parent;

    ConfigSectionDescriptorImpl(
            @NonNull String key,
            @Nullable ConfigSectionDescriptor parent,
            @NonNull List<ConfigSectionDescriptor> sections,
            @NonNull List<ConfigNodeDescriptor<?>> nodes,
            @NonNull List<String> comments
    ) {
        this.key = key;
        this.parent = parent;
        this.sections = sections;
        this.nodes = nodes;
        this.comments = comments;
    }

    @Override
    public @NonNull List<@NonNull ConfigSectionDescriptor> sections() {
        return Collections.unmodifiableList(this.sections);
    }

    @Override
    public void addSections(@NonNull ConfigSectionDescriptor @NonNull ... sectionDescriptors) {
        Collections.addAll(this.sections, sectionDescriptors);
    }

    @Override
    public void removeSections(@NonNull String @NonNull ... keys) {
        for (String key : keys) {
            this.sections.removeIf((ConfigSectionDescriptor descriptor) -> descriptor.key().equals(key));
        }
    }

    @Override
    public @NonNull List<@NonNull ConfigNodeDescriptor<?>> nodes() {
        return Collections.unmodifiableList(this.nodes);
    }

    @Override
    public void addNodes(@NonNull ConfigNodeDescriptor<?> @NonNull ... nodeDescriptors) {
        Collections.addAll(this.nodes, nodeDescriptors);
    }

    @Override
    public void removeNodes(@NonNull String @NonNull ... keys) {
        for (String key : keys) {
            this.nodes.removeIf((ConfigNodeDescriptor<?> descriptor) -> descriptor.key().equals(key));
        }
    }

    @Override
    public @NonNull List<String> comments() {
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public void addComment(@NonNull String comment) {
        this.comments.add(comment);
    }

    @Override
    public void removeComment(@NonNull String comment) {
        this.comments.remove(comment);
    }

    @Override
    public void removeComment(int index) {
        this.comments.remove(index);
    }

    @Override
    public void clearComments() {
        this.comments.clear();
    }

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public void setKey(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSectionDescriptor> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public void setParent(@Nullable ConfigSectionDescriptor parent) {
        this.parent = parent;
    }
}
