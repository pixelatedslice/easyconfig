package com.pixelatedslice.easyconfig.api.comments;

import org.jspecify.annotations.NonNull;

import java.util.Collection;

public interface Commentable {
    @NonNull Collection<String> comments();

    void addComment(@NonNull String comment);

    void removeComment(@NonNull String comment);

    void removeComment(int index);

    void clearComments();
}