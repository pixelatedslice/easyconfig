package com.pixelatedslice.easyconfig.api.comments;

import org.jspecify.annotations.NonNull;

public interface BuilderWithComments {
    @NonNull BuilderWithComments comments(@NonNull String @NonNull ... comments);

    @NonNull BuilderWithComments addComment(@NonNull String comment);
}
