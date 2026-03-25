package com.pixelatedslice.easyconfig.impl.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeImpl;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ConfigSectionBuilderImpl implements ConfigSectionBuilder {
    private final List<ConfigNode<?>> childNodes = new ArrayList<>();
    private final List<ConfigSection> nestedSections = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();
    private String key;
    private ConfigSection parent;

    @Override
    public ConfigSectionBuilder key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public ConfigSectionBuilder parent(@NonNull ConfigSection parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken, nodeBuilder);
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        builder.typeToken(typeToken);
        this.childNodes.add(builder.build());

        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken) {
        this.childNodes.add(new ConfigNodeImpl<>(key, null, typeToken, null, this.comments));
        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken);
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken) {
        this.childNodes.add(new ConfigNodeImpl<>(key, value, typeToken, null, this.comments));
        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType) {
        var typeToken = TypeToken.of(valueWithSimpleType.getClass());
        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }
        return this.node(key, typeToken);
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        this.childNodes.add(builder.build());

        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.typeToken(typeToken);
        this.childNodes.add(builder.build());
        return this;
    }

    @Override
    public <T> ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        this.childNodes.add(builder.build());
        return this;
    }

    @Override
    public ConfigSectionBuilder comment(@NonNull String... comment) {
        Collections.addAll(this.comments, comment);
        return this;
    }

    @Override
    public ConfigSectionBuilder nestedSection(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        builder.key(key);
        this.nestedSections.add(builder.build());
        return this;
    }

    @Override
    public ConfigSectionBuilder nestedSection(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        this.nestedSections.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigSection build() {
        return new ConfigSectionImpl(this.key, this.parent, this.childNodes, this.nestedSections, this.comments, true);
    }
}
