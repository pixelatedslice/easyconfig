package com.pixelatedslice.easyconfig.impl.descriptor.section;

import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConfigSectionDescriptorImpl implements ConfigSectionDescriptor {
    private final @NonNull String key;
    private final @NonNull List<@NonNull ConfigSectionDescriptor> sections;
    private final @NonNull List<@NonNull ConfigNodeDescriptor<?>> nodes;
    private final @NonNull List<@NonNull String> comments;
    private final @Nullable ConfigSectionDescriptor parent;

    public ConfigSectionDescriptorImpl(
            @NonNull String key,
            @Nullable ConfigSectionDescriptor parent,
            @NonNull List<ConfigSectionDescriptor> sections,
            @NonNull List<ConfigNodeDescriptor<?>> nodes,
            @NonNull List<String> comments
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(parent);
        Objects.requireNonNull(sections);
        Objects.requireNonNull(nodes);
        Objects.requireNonNull(comments);

        this.key = key;
        this.parent = parent;
        this.sections = sections;
        this.nodes = nodes;
        this.comments = comments;
    }

    public static ConfigSectionDescriptor newRootSectionDescriptor() {
        return new ConfigSectionDescriptorImpl("root", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public @NonNull List<@NonNull ConfigSectionDescriptor> sections() {
        return Collections.unmodifiableList(this.sections);
    }

    @Override
    public void addSections(@NonNull ConfigSectionDescriptor @NonNull ... sectionDescriptors) {
        Objects.requireNonNull(sectionDescriptors);
        Collections.addAll(this.sections, sectionDescriptors);
    }

    @Override
    public void removeSections(@NonNull String @NonNull ... keys) {
        Objects.requireNonNull(keys);
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
        Objects.requireNonNull(nodeDescriptors);
        Collections.addAll(this.nodes, nodeDescriptors);
    }

    @Override
    public void removeNodes(@NonNull String @NonNull ... keys) {
        Objects.requireNonNull(keys);
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
        Objects.requireNonNull(comment);
        this.comments.add(comment);
    }

    @Override
    public void removeComment(@NonNull String comment) {
        Objects.requireNonNull(comment);
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
    public @NonNull Optional<@NonNull ConfigSectionDescriptor> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigSectionDescriptor that)
                && this.key.equals(that.key())
                && Objects.equals(this.parent, that.parent().orElse(null))
                && (this.nodes.size() == that.nodes().size())
                && (this.sections.size() == that.sections().size())
                && (this.comments.size() == that.comments().size())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.parent);
    }
}
