package com.pixelatedslice.easyconfig.api.comments;

import org.jspecify.annotations.NonNull;

public interface MutableAndCommentable {
    void addComments(@NonNull String @NonNull ... comments);

    void removeComments(@NonNull String @NonNull ... comments);

    void clearComments();
}
