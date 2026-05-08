package com.pixelatedslice.easyconfig.api.config.node.container;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.editable.EditableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.List;

public interface EditableContainerNode extends EditableVariant {
    default @NonNull EditableContainerNode addNodes(@NonNull Node @NonNull ... nodes) {
        return this.addNodes(List.of(nodes));
    }

    @NonNull EditableContainerNode addNodes(@NonNull Collection<? extends @NonNull Node> nodes);

    @NonNull EditableContainerNode setNodes(@NonNull Collection<? extends @NonNull Node> nodes);

    default @NonNull EditableContainerNode removeNodes(@NonNull Node @NonNull ... nodes) {
        return this.removeNodes(List.of(nodes));
    }

    @NonNull EditableContainerNode removeNodes(@NonNull Collection<? extends @NonNull Node> nodes);

    @NonNull EditableContainerNode removeNodes(@NonNull String @NonNull ... keys);

    @NonNull EditableContainerNode clearNodes();
}
