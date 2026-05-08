package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@FunctionalInterface
public interface ValueNodeBuilderTypeStep<T> extends BuilderStep {
    @NonNull ValueNodeBuilderValueStep<@NonNull T> type(@NonNull TypeToken<@NonNull T> typeToken);

    default @NonNull ValueNodeBuilderValueStep<@NonNull T> type(@NonNull Class<@NonNull T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.type(typeToken);
    }
}