package com.pixelatedslice.easyconfig.impl.comments;

import com.pixelatedslice.easyconfig.api.comments.MutableAndCommentable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public abstract class AbstractMutableAndCommentable implements MutableAndCommentable {
    protected final Collection<Consumer<Collection<String>>> commentUpdates = new ArrayList<>();


    @Override
    public void addComments(@NonNull String @NonNull ... comments) {
        this.commentUpdates.add((Collection<String> list) -> {
            Collections.addAll(list, comments);
        });
    }

    @Override
    public void removeComments(@NonNull String @NonNull ... comments) {
        this.commentUpdates.add((Collection<String> list) -> {
            for (String comment : comments) {
                list.remove(comment);
            }
        });
    }

    @Override
    public void clearComments() {
        this.commentUpdates.add(Collection::clear);
    }
}
