package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeIteratorImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConfigSectionImpl implements ConfigSection {
    /**
     * The unique identifier associated with this configuration section.
     * It is used to distinguish and reference this section within the configuration hierarchy.
     */
    private final String key;
    /**
     * Represents the collection of child configuration nodes associated with this configuration section.
     * These child nodes form a hierarchy within the configuration structure, where each node may contain
     * a key, an optional value, and potentially additional child nodes.
     * <p>
     * The child nodes are stored as a list of {@link ConfigNode} instances, allowing for ordered iteration
     * and management of the nodes within the section. This list enables adding, removing, and retrieving
     * nodes based on their hierarchical relationships.
     */
    private final List<ConfigNode<?>> childNodes = new ArrayList<>();
    /**
     * A list of nested configuration sections contained within this configuration section.
     * Each nested section represents a hierarchical level below the current section,
     * allowing for structured configuration data organization.
     * <p>
     * This field is immutable and cannot be reassigned after initialization.
     */
    private final List<ConfigSection> nestedSections = new ArrayList<>();
    /**
     * A collection of comments associated with the configuration section.
     * This list is mutable and allows adding, removing, and clearing comments.
     */
    private final List<String> comments;
    /**
     * Represents the parent configuration section of the current section in the hierarchy.
     * The parent section defines the immediate higher-level context for this configuration
     * section. It can be null if this section does not have a parent.
     */
    private ConfigSection parent;

    /**
     * Creates a new instance of {@code ConfigSectionImpl}.
     *
     * @param key            the non-null key associated with this configuration section
     * @param parent         the parent section of this configuration, or null if this section has no parent
     * @param childNodes     the non-null list of child configuration nodes
     * @param nestedSections the non-null list of nested configuration sections within this section
     * @param comments       the non-null list of comments associated with this configuration section
     * @throws NullPointerException if {@code key}, {@code childNodes}, {@code nestedSections}, or {@code comments}
     *                              is null
     */
    public ConfigSectionImpl(@NonNull String key, @Nullable ConfigSection parent,
            @NonNull Collection<@NonNull ConfigNode<?>> childNodes,
            @NonNull Collection<@NonNull ConfigSection> nestedSections, @NonNull List<@NonNull String> comments) {
        this.key = key;
        this.parent = parent;
        this.childNodes.addAll(childNodes);
        this.nestedSections.addAll(nestedSections);
        this.comments = comments;
    }

    protected ConfigSectionImpl(@NonNull String key, @Nullable ConfigSection parent,
            @NonNull Iterable<@NonNull ConfigNode<?>> childNodes,
            @NonNull Collection<@NonNull ConfigSection> nestedSections, @NonNull List<@NonNull String> comments,
            boolean fromBuilder) {
        this.key = key;
        this.parent = parent;

        for (ConfigNode<?> node : childNodes) {
            node.setParent(this);
            this.childNodes.add(node);
        }

        this.nestedSections.addAll(nestedSections);
        this.comments = comments;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the key associated with this configuration section.
     */
    @Override
    public @NonNull String key() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the parent section of this configuration section, if any.
     */
    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method sets the parent section of this configuration section.
     */
    @Override
    public void setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an unmodifiable list of child nodes associated with this
     * configuration section.
     */
    @Override
    public @NonNull List<@NonNull ConfigNode<?>> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds a child node to the list of child nodes associated with this
     * configuration section.
     */
    @Override
    public void addChildNode(@NonNull ConfigNode<?> child) {
        this.childNodes.add(child);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a child node from the list of child nodes associated with
     * this configuration section.
     */
    @Override
    public void removeChildNode(@NonNull String key) {
        this.childNodes.removeIf((ConfigNode<?> node) -> node.key().equals(key));
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an iterator over the child nodes of this configuration
     * section.
     */
    @Override
    public @NonNull ConfigNodeIterator nodeIterator() {
        return new ConfigNodeIteratorImpl(this);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an unmodifiable list of nested sections within this
     * configuration section.
     */
    @Override
    public @NonNull List<@NonNull ConfigSection> nestedSections() {
        return Collections.unmodifiableList(this.nestedSections);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds a nested section to the list of nested sections within this
     * configuration section.
     */
    @Override
    public void addNestedSection(@NonNull ConfigSection section) {
        this.nestedSections.add(section);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a nested section from the list of nested sections within
     * this configuration section.
     */
    @Override
    public void removeNestedSection(@NonNull String key) {
        this.nestedSections.removeIf((ConfigSection section) -> section.key().equals(key));
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an iterator over the nested sections within this
     * configuration section.
     */
    @Override
    public @NonNull ConfigSectionIterator sectionIterator() {
        return new ConfigSectionIteratorImpl(this);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an unmodifiable collection of comments associated with this
     * configuration section.
     */
    @Override
    public @NonNull Collection<String> comments() {
        return Collections.unmodifiableCollection(this.comments);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds a comment to the list of comments associated with this
     * configuration section.
     */
    @Override
    public void addComment(@NonNull String comment) {
        this.comments.add(comment);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a comment from the list of comments associated with this
     * configuration section.
     */
    @Override
    public void removeComment(@NonNull String comment) {
        this.comments.remove(comment);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a comment at a specific index from the list of comments
     * associated with this configuration section.
     */
    @Override
    public void removeComment(int index) {
        this.comments.remove(index);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method clears all comments associated with this configuration section.
     */
    @Override
    public void clearComments() {
        this.comments.clear();
    }
}
