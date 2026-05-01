package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.api.utils.type_token.TypeTokenUtils;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@FunctionalInterface
public interface BaseConfigNodeBuilder<T, C extends ConfigNode<T>> {
    @NonNull TypeStep<T, C> key(@NonNull String key);

    @FunctionalInterface
    interface TypeStep<T, C extends ConfigNode<T>> extends BuilderStep {
        @NonNull ParentStep<T, C> type(@NonNull TypeToken<T> typeToken);

        @NonNull
        default ParentStep<T, C> type(@NonNull Class<T> simpleType) {
            Objects.requireNonNull(simpleType);

            var typeToken = TypeToken.of(simpleType);

            if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
                throw new ComplexInsteadOfSimpleTypeUsedException();
            }

            return this.type(typeToken);
        }
    }

    interface ParentStep<T, C extends ConfigNode<T>> extends BuilderStep, ValueStep<T, C> {
        @NonNull ValueStep<T, C> parent(@NonNull ConfigSection parent);
    }

    interface ValueStep<T, C extends ConfigNode<T>> extends BuilderStep, ValidatorStep<T, C> {
        @NonNull ValueStep<T, C> defaultValue(@Nullable T defaultValue);
    }

    interface ValidatorStep<T, C extends ConfigNode<T>> extends BuilderStep, CommentStep<T, C> {
        @NonNull CommentStep<T, C> validator(Validator<T> validator);
    }

    interface CommentStep<T, C extends ConfigNode<T>>
            extends BuilderStep, com.pixelatedslice.easyconfig.api.builder.config.CommentStep<CommentStep<T, C>>,
            FinalStep<T, C> {
    }

    @FunctionalInterface
    interface FinalStep<T, C extends ConfigNode<T>> extends BuilderStep {
        @NonNull C build();
    }

    interface Handler<T, C extends ConfigNode<T>> extends BaseConfigNodeBuilder<T, C>,
            TypeStep<T, C>, ParentStep<T, C>, ValueStep<T, C>,
            ValidatorStep<T, C>, CommentStep<T, C>, FinalStep<T, C> {
    }
}
