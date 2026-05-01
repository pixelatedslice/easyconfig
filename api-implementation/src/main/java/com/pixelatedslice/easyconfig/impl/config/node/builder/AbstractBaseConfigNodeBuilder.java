package com.pixelatedslice.easyconfig.impl.config.node.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.BaseConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBaseConfigNodeBuilder<T, C extends ConfigNode<T>, N extends BuilderStep>
        implements BaseConfigNodeBuilder.Handler<T, C> {
    protected final @NonNull List<String> comments = new ArrayList<>();
    protected final @NonNull String environmentVariable = "";
    protected @NonNull String key = "";
    protected @NonNull TypeToken<T> typeToken = new TypeToken<>() {
    };
    protected @NonNull Validator<T> validator = Validator.empty();
    protected @Nullable ConfigSection parent;
    protected @Nullable T defaultValue;

    @Override
    public @NonNull ParentStep<T, C> type(@NonNull TypeToken<T> typeToken) {
        this.typeToken = typeToken;
        return this;
    }

    @Override
    public @NonNull ValueStep<T, C> parent(@NonNull ConfigSection parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public @NonNull ValueStep<T, C> defaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public @NonNull CommentStep<T, C> validator(@NonNull Validator<T> validator) {
        this.validator = validator;
        return this;
    }


    @Override
    public @NonNull CommentStep<T, C> comments(@NonNull String @NonNull ... comments) {
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public @NonNull CommentStep<T, C> addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }
}
