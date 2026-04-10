package com.pixelatedslice.easyconfig.api.config.section;

import com.pixelatedslice.easyconfig.api.comments.MutableAndCommentable;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.mutability.immutable.WithImmutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.mutable.MutableVariant;
import org.jspecify.annotations.NonNull;

public interface MutableConfigSection extends MutableVariant, WithImmutableVariant<ConfigSection>,
        MutableAndCommentable {
    void addNodes(@NonNull ConfigNode<?> @NonNull ... nodes);

    void removeNodes(@NonNull ConfigNode<?> @NonNull ... nodes);

    void removeNodes(@NonNull String @NonNull ... keys);

    void clearNodes();

    void addSections(@NonNull ConfigSection @NonNull ... sections);

    void removeSections(@NonNull ConfigSection @NonNull ... sections);

    void removeSections(@NonNull String @NonNull ... keys);

    void clearSections();
}