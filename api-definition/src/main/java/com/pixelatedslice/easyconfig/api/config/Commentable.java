package com.pixelatedslice.easyconfig.api.config;

import org.jspecify.annotations.NonNull;

import java.util.Collection;

public interface Commentable {
    @NonNull Collection<String> comments();

    Commentable addComment(@NonNull String comment);

    Commentable removeComment(@NonNull String comment);

    Commentable removeComment(int index);

    Commentable clearComments();
}