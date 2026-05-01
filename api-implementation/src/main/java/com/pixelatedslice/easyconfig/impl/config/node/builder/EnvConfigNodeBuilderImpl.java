package com.pixelatedslice.easyconfig.impl.config.node.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.EnvConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.EnvConfigNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.EnvConfigNodeImpl;
import org.jspecify.annotations.NonNull;

public class EnvConfigNodeBuilderImpl<T>
        extends AbstractBaseConfigNodeBuilder<T, EnvConfigNode<T>, EnvConfigNodeBuilder.TypeStep<T>>
        implements EnvConfigNodeBuilder.Handler<T> {
    private @NonNull String key = "";
    private @NonNull String environmentVariable = "";

    @Override
    public EnvConfigNodeBuilder.@NonNull TypeStep<T> key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public ParentStep<T, EnvConfigNode<T>> environmentVariable(@NonNull String environmentVariable) {
        this.environmentVariable = environmentVariable;
        return this;
    }

    @Override
    public @NonNull EnvironmentVariableStep<T> type(@NonNull TypeToken<T> typeToken) {
        return (EnvironmentVariableStep<T>) super.type(typeToken);
    }

    @Override
    public @NonNull EnvConfigNode<T> build() {
        return new EnvConfigNodeImpl<>(this.key,
                this.environmentVariable,
                this.typeToken,
                this.parent,
                this.defaultValue,
                this.validator,
                this.comments
        );
    }
}