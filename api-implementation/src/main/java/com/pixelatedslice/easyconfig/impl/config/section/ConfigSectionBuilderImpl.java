package com.pixelatedslice.easyconfig.impl.config.section;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptorBuilder;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.descriptor.section.ConfigSectionDescriptorBuilderImpl;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@AutoService(ConfigSectionBuilder.class)
public class ConfigSectionBuilderImpl implements ConfigSectionBuilder {
    private final ConfigSectionDescriptorBuilder descriptorBuilder = new ConfigSectionDescriptorBuilderImpl();
    private final List<ConfigNode<?>> childNodes = new ArrayList<>();
    private final List<ConfigSection> nestedSections = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();
    private ConfigSection parent;

    @Override
    public @NonNull ConfigSectionBuilder key(@NonNull String key) {
        this.descriptorBuilder.key(key);
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder parent(@NonNull ConfigSection parent) {
        this.parent = parent;
        this.descriptorBuilder.parent(parent.descriptor());
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
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
        this.node(builder.build());

        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken) {
        this.node(new ConfigNodeBuilderImpl<T>().key(key).typeToken(typeToken).build());
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken);
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull T value,
            @NonNull TypeToken<T> typeToken) {
        this.node(new ConfigNodeBuilderImpl<T>().key(key).value(value).typeToken(typeToken).build());
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType) {
        var typeToken = TypeToken.of(valueWithSimpleType.getClass());
        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
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
        this.node(builder.build());
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.typeToken(typeToken);
        this.node(builder.build());
        return this;
    }

    @Override
    public <T> @NonNull ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        this.node(builder.build());
        return this;
    }

    @Override
    public @NonNull <T> ConfigSectionBuilder node(@NonNull ConfigNode<T> node) {
        this.childNodes.add(node);
        this.descriptorBuilder.addNode(node.descriptor());
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder comments(@NonNull String @NonNull ... comments) {
        Objects.requireNonNull(comments);
        this.descriptorBuilder.comments(comments);
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
        this.section(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        Objects.requireNonNull(nestedSectionBuilder);
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        this.section(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigSectionBuilder section(@NonNull ConfigSection nestedSection) {
        Objects.requireNonNull(nestedSection);
        this.nestedSections.add(nestedSection);
        this.descriptorBuilder.addSection(nestedSection.descriptor());
        return this;
    }

    @Override
    public @NonNull ConfigSection build() {
        return new ConfigSectionImpl(
                Objects.requireNonNull(this.descriptorBuilder.build()),
                this.parent,
                Objects.requireNonNull(this.childNodes),
                Objects.requireNonNull(this.nestedSections)
        );
    }
}
