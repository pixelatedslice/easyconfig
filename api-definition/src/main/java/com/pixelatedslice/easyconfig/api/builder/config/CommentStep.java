package com.pixelatedslice.easyconfig.api.builder.config;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import org.jspecify.annotations.NonNull;

public interface CommentStep<Self extends CommentStep<Self>> extends BuilderStep {
    @NonNull Self comments(@NonNull String @NonNull ... comments);

    @NonNull Self addComment(@NonNull String comment);
}
