package com.pixelatedslice.easyconfig.api.comments;

import org.jspecify.annotations.NonNull;

import java.util.Collection;

@FunctionalInterface
public interface Commentable {
    @NonNull Collection<@NonNull String> comments();
}