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

    private final String key;

    private final List<ConfigNode<?>> childNodes = new ArrayList<>();

    private final List<ConfigSection> nestedSections = new ArrayList<>();

    private final List<String> comments;

    private ConfigSection parent;


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


    @Override
    public @NonNull String key() {
        return this.key;
    }


    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }


    @Override
    public ConfigSection setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
        return this;
    }


    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        return Collections.unmodifiableList(this.childNodes);
    }


    @Override
    public ConfigSection addNode(@NonNull ConfigNode<?> child) {
        this.childNodes.add(child);
        return this;
    }


    @Override
    public ConfigSection removeNode(@NonNull String key) {
        this.childNodes.removeIf((ConfigNode<?> node) -> node.key().equals(key));
        return this;
    }


    @Override
    public @NonNull ConfigNodeIterator nodeIterator() {
        return new ConfigNodeIteratorImpl(this);
    }


    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        return Collections.unmodifiableList(this.nestedSections);
    }


    @Override
    public ConfigSection addSection(@NonNull ConfigSection section) {
        this.nestedSections.add(section);
        return this;
    }


    @Override
    public ConfigSection removeSection(@NonNull String key) {
        this.nestedSections.removeIf((ConfigSection section) -> section.key().equals(key));
        return this;
    }


    @Override
    public @NonNull ConfigSectionIterator sectionIterator() {
        return new ConfigSectionIteratorImpl(this);
    }


    @Override
    public @NonNull Collection<String> comments() {
        return Collections.unmodifiableCollection(this.comments);
    }


    @Override
    public ConfigSection addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }


    @Override
    public ConfigSection removeComment(@NonNull String comment) {
        this.comments.remove(comment);
        return this;
    }


    @Override
    public ConfigSection removeComment(int index) {
        this.comments.remove(index);
        return this;
    }


    @Override
    public ConfigSection clearComments() {
        this.comments.clear();
        return this;
    }
}
