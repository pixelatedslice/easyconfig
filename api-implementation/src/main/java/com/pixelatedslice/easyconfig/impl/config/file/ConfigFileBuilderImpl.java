package com.pixelatedslice.easyconfig.impl.config.file;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@AutoService(ConfigFileBuilder.class)
public class ConfigFileBuilderImpl implements ConfigFileBuilder {
    private final List<ConfigNode<?>> childNodes = new ArrayList<>();
    private final List<ConfigSection> nestedSections = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();
    private Path filePathWithoutExtension;
    private Class<? extends FileFormat> fileFormatClass;

    @Override
    public @NonNull ConfigFileBuilder filePath(@NonNull Path filePathWithoutExtension) {
        this.filePathWithoutExtension = filePathWithoutExtension;
        return this;
    }

    @Override
    public @NonNull ConfigFileBuilder fileFormat(@NonNull Class<? extends FileFormat> fileFormatClass) {
        this.fileFormatClass = fileFormatClass;
        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken, nodeBuilder);
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        builder.typeToken(typeToken);
        this.childNodes.add(builder.build());

        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken) {
        this.childNodes.add(new ConfigNodeImpl<>(key, null, typeToken, null, this.comments));
        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull Class<T> simpleType) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(key, typeToken);
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken) {
        this.childNodes.add(new ConfigNodeImpl<>(key, value, typeToken, null, this.comments));
        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key, @NonNull T valueWithSimpleType) {
        var typeToken = TypeToken.of(valueWithSimpleType.getClass());
        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }
        return this.node(key, typeToken);
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.key(key);
        this.childNodes.add(builder.build());

        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        builder.typeToken(typeToken);
        this.childNodes.add(builder.build());
        return this;
    }

    @Override
    public <T> @NonNull ConfigFileBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        var builder = new ConfigNodeBuilderImpl<T>();
        nodeBuilder.accept(builder);
        this.childNodes.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigFileBuilder comments(@NonNull String @NonNull ... comments) {
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public @NonNull ConfigFileBuilder section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        builder.key(key);
        this.nestedSections.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigFileBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var builder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(builder);
        this.nestedSections.add(builder.build());
        return this;
    }

    @Override
    public @NonNull ConfigFile build() {
        return new ConfigFileImpl(
                Objects.requireNonNull(this.filePathWithoutExtension),
                Objects.requireNonNull(this.fileFormatClass),
                Objects.requireNonNull(this.childNodes),
                Objects.requireNonNull(this.nestedSections),
                Objects.requireNonNull(this.comments)
        );
    }
}
