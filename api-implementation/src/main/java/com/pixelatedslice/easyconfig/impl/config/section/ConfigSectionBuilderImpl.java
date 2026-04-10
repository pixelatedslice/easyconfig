package com.pixelatedslice.easyconfig.impl.config.section;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.api.utils.type_token.TypeTokenUtils;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeBuilderImpl;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@AutoService(ConfigSectionBuilder.class)
public class ConfigSectionBuilderImpl implements ConfigSectionBuilder {
    private final List<ConfigNodeBuilder<?>> childNodes = new ArrayList<>();
    private final List<ConfigSectionBuilder> nestedSections = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();
    private String key;
    private ConfigSection parent;

    public ConfigSectionBuilderImpl(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
    }

    public ConfigSectionBuilderImpl() {
    }

    @Override
    public @NonNull ConfigSectionBuilder key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder parent(@NonNull ConfigSection parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder) {
        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken, nodeBuilder);
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        builder.typeToken(typeToken);
        this.node(builder);

        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken) {
        this.node(new ConfigNodeBuilderImpl<T>().key(key).typeToken(typeToken));
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType) {
        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken);
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull T value,
            @NonNull TypeToken<T> typeToken) {
        this.node(new ConfigNodeBuilderImpl<T>().key(key).value(value).typeToken(typeToken));
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType) {
        var typeToken = TypeToken.of(valueWithSimpleType.getClass());
        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }
        return this.node(key, typeToken);
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        this.node(builder);
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.typeToken(typeToken);
        this.node(builder);
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        this.node(builder);
        return this;
    }

    private void node(@NonNull ConfigNodeBuilder<?> nodeBuilder) {
        this.childNodes.add(nodeBuilder);
    }

    @Override
    public @NonNull ConfigSectionBuilder comments(@NonNull String @NonNull ... comments) {
        Objects.requireNonNull(comments);
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder addComment(@NonNull String comment) {
        Objects.requireNonNull(comment);
        this.comments.add(comment);
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(nestedSectionBuilder);
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        builder.key(key);
        this.section(builder);
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        Objects.requireNonNull(nestedSectionBuilder);
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        this.section(builder);
        return this;
    }

    private void section(@NonNull ConfigSectionBuilder nestedSectionBuilder) {
        Objects.requireNonNull(nestedSectionBuilder);
        this.nestedSections.add(nestedSectionBuilder);
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
