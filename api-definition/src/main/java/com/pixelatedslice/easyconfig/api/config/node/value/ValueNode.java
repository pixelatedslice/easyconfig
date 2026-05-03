package com.pixelatedslice.easyconfig.api.config.node.value;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public non-sealed interface ValueNode<T> extends Node {
    @NonNull Optional<@NonNull T> value();

    @NonNull Optional<@NonNull T> defaultValue();

    default @NonNull Optional<@NonNull T> valueOrDefault() {
        return this.value().or(this::defaultValue);
    }

    @NonNull Validator<T> validator();

    @NonNull TypeToken<T> typeToken();
}
