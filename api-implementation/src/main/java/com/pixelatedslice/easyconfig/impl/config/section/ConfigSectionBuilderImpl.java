package com.pixelatedslice.easyconfig.impl.config.section;

import com.google.auto.service.AutoService;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.BaseConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.EnvConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.builder.ConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.node.builder.EnvConfigNodeBuilderImpl;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("DuplicatedCode")
@AutoService(ConfigSectionBuilder.class)
public class ConfigSectionBuilderImpl implements ConfigSectionBuilder.Handler {
    protected final Collection<BaseConfigNodeBuilder.Handler<?, ?>> childNodes = new ArrayList<>();
    protected final Collection<ConfigSectionBuilder.Handler> nestedSections = new ArrayList<>();
    protected final List<String> comments = new ArrayList<>();
    private String key;
    private ConfigSection parent;

    public ConfigSectionBuilderImpl(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);

        this.parent = parent;
    }

    public ConfigSectionBuilderImpl() {
    }

    @Override
    public @NonNull ParentStep key(@NonNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
        return this;
    }

    @Override
    public ConfigSectionBuilder.@NonNull CommentStep comments(@NonNull String @NonNull ... comments) {
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public ConfigSectionBuilder.@NonNull CommentStep addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }

    @Override
    public <T> ConfigSectionBuilder.@NonNull ChildNodeStep node(
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        return this.addNodeBuilder(() -> new ConfigNodeBuilderImpl<T>(), nodeBuilder);
    }

    @Override
    public @NonNull <T> ChildNodeStep node(ConfigNodeBuilder.@NonNull Handler<T> nodeBuilder) {
        this.childNodes.add(nodeBuilder);
        return this;
    }

    @Override
    public @NonNull <T> ConfigNodeBuilder<T> node() {
        return new ConfigNodeBuilderImpl<>();
    }

    @Override
    public <T> ConfigSectionBuilder.@NonNull ChildNodeStep env(
            @NonNull Consumer<? super EnvConfigNodeBuilder<T>> nodeBuilder) {
        return this.addNodeBuilder(EnvConfigNodeBuilderImpl<T>::new, nodeBuilder);
    }

    @Override
    public @NonNull <T> ChildNodeStep env(EnvConfigNodeBuilder.@NonNull Handler<T> nodeBuilder) {
        this.childNodes.add(nodeBuilder);
        return this;
    }

    @Override
    public @NonNull <T> EnvConfigNodeBuilder<T> env() {
        return new EnvConfigNodeBuilderImpl<>();
    }

    private <T, H extends BaseConfigNodeBuilder.Handler<T, ? extends ConfigNode<T>>>
    ConfigSectionBuilder.@NonNull ChildNodeStep addNodeBuilder(
            @NonNull Supplier<H> constructor,
            @NonNull Consumer<? super H> builder
    ) {
        var builderImpl = constructor.get();
        builder.accept(builderImpl);
        this.childNodes.add(builderImpl);
        return this;
    }

    @Override
    public ConfigSectionBuilder.@NonNull NestedSectionStep parent(@NonNull ConfigSection parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public @NonNull NestedSectionStep section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var sectionBuilder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(sectionBuilder);
        this.nestedSections.add(sectionBuilder);
        return this;
    }

    @Override
    public @NonNull NestedSectionStep section(ConfigSectionBuilder.@NonNull Handler nestedSectionBuilder) {
        this.nestedSections.add(nestedSectionBuilder);
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder section() {
        return new ConfigSectionBuilderImpl();
    }

    @Override
    public @NonNull ConfigSection build() {
        var section = new ConfigSectionImpl(
                Objects.requireNonNull(this.key),
                this.parent,
                new ArrayList<>(),
                new ArrayList<>(),
                Objects.requireNonNull(this.comments)
        );

        try (var mutable = section.mutable()) {
            for (var childNode : this.childNodes) {
                childNode.parent(section);
                mutable.addNodes(childNode.build());
            }

            for (var nestedSection : this.nestedSections) {
                nestedSection.parent(section);
                mutable.addSections(nestedSection.build());
            }
            return section;
        }
    }
}
