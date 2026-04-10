package com.pixelatedslice.easyconfig.impl.comments;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.comments.Commentable;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class AbstractCommentable implements Commentable {
    private final String key;
    private final List<String> comments;

    private final @NonNull BlockingQueue<@NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdateQueue =
            new LinkedBlockingQueue<>();

    protected AbstractCommentable(@NonNull String key, @NonNull List<@NonNull String> comments) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(comments);

        this.key = key;
        this.comments = comments;
    }

    @Override
    public @NonNull List<@NonNull String> comments() {
        return Collections.unmodifiableList(this.comments);
    }

    protected void pushChangesToQueue(
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdates
    ) throws InterruptedException {
        for (var commentUpdate : commentUpdates) {
            this.commentUpdateQueue.put(commentUpdate);
        }
    }

    private void processCommentUpdateQueue() {
        while (true) {
            try {
                var consumer = this.commentUpdateQueue.take();
                synchronized (this.commentUpdateQueue) {
                    consumer.accept(this.comments);

                    Collection<Consumer<Collection<String>>> drained =
                            new ArrayList<>(EasyConfig.QUEUE_DRAINED_INITIAL_CAPACITY);
                    this.commentUpdateQueue.drainTo(drained);

                    for (var drainedConsumer : drained) {
                        drainedConsumer.accept(this.comments);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("The comment update queue processing for section %s interrupted!%n", this.key);
                break;
            }
        }
    }
}
