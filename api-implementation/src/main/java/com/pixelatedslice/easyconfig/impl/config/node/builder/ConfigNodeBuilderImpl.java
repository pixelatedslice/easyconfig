package com.pixelatedslice.easyconfig.impl.config.node.builder;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@AutoService(ConfigNodeBuilder.class)
public class ConfigNodeBuilderImpl<T>
        extends AbstractBaseConfigNodeBuilder<T, ConfigNode<T>, ConfigNodeBuilder.TypeStep<T>>
        implements ConfigNodeBuilder.Handler<T> {
    private @NonNull String key = "";
    private @Nullable T value;

    @Override
    public ConfigNodeBuilder.@NonNull TypeStep<T> key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public ConfigNodeBuilder.@NonNull ValueStep<T> value(@Nullable T value) {
        this.value = value;
        return this;
    }

    @Override
    public ConfigNodeBuilder.@NonNull ParentStep<T> type(@NonNull Class<T> simpleType) {
        return ConfigNodeBuilder.Handler.super.type(simpleType);
    }

    @Override
    public ConfigNodeBuilder.@NonNull ParentStep<T> type(@NonNull TypeToken<T> typeToken) {
        return (ConfigNodeBuilder.ParentStep<T>) super.type(typeToken);
    }

    @Override
    public ConfigNodeBuilder.@NonNull ValueStep<T> parent(@NonNull ConfigSection parent) {
        return (ConfigNodeBuilder.ValueStep<T>) super.parent(parent);
    }

    @Override
    public ConfigNodeBuilder.@NonNull ValueStep<T> defaultValue(@Nullable T defaultValue) {
        super.defaultValue(defaultValue);
        return this;
    }

    @Override
    public @NonNull ConfigNode<T> build() {
        return new ConfigNodeImpl<>(this.key,
                this.typeToken,
                this.parent,
                this.value,
                this.defaultValue,
                this.validator,
                this.comments
        );
    }
}
