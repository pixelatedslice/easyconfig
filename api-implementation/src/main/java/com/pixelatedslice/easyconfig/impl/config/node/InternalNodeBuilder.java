package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.errorprone.annotations.CheckReturnValue;
import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;

public interface InternalNodeBuilder<Self> extends NodeBuilder {

    @CheckReturnValue
    @NonNull
    Self parent(@Nullable AbstractNode node);

    @Nullable AbstractNode parent();

    @CheckReturnValue
    @NonNull
    Self config(@Nullable Config config);

    @Nullable
    Config config();

    @Nullable
    String key();

    @NonNull
    Collection<InternalNodeBuilder<?>> children();

    void appendChild(@NonNull InternalNodeBuilder<?> builder);

    @NonNull
    AbstractNode build();

    default void buildChildren(AbstractNode built) {
        @SuppressWarnings("ResultOfMethodCallIgnored") var immediateChildren = this
                .children()
                .stream()
                .peek(builder -> builder.parent(built))
                .collect(Collectors.toMap(t -> t, InternalNodeBuilder::build));
        for (var child : immediateChildren.values()) {
            built.internalAppendChild(child);
        }
        for (var entry : immediateChildren.entrySet()) {
            entry.getKey().buildChildren(entry.getValue());
        }

    }

}
