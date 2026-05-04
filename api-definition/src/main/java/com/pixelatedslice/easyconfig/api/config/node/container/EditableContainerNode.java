package com.pixelatedslice.easyconfig.api.config.node.container;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.editable.EditableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

public interface EditableContainerNode extends EditableVariant {
    @NonNull EditableContainerNode addNodes(@NonNull Node @NonNull ... nodes);

    @NonNull EditableContainerNode setNodes(@NonNull Collection<? extends @NonNull Node> nodes);

    @NonNull EditableContainerNode removeNodes(@NonNull Node @NonNull ... nodes);

    @NonNull EditableContainerNode removeNodes(@NonNull String @NonNull ... keys);

    @NonNull EditableContainerNode clearNodes();

    @NonNull EditableContainerNode addValueNodes(@NonNull ValueNode<?> @NonNull ... nodes);

    @NonNull EditableContainerNode setValueNodes(@NonNull Collection<? extends @NonNull ValueNode<?>> nodes);

    @NonNull EditableContainerNode removeValueNodes(@NonNull ValueNode<?> @NonNull ... nodes);

    @NonNull EditableContainerNode removeValueNodes(@NonNull String @NonNull ... keys);

    @NonNull EditableContainerNode clearValueNodes();

    @NonNull EditableContainerNode addContainerNodes(@NonNull ContainerNode @NonNull ... nodes);

    @NonNull EditableContainerNode setContainerNodes(@NonNull Collection<? extends @NonNull ContainerNode> nodes);

    @NonNull EditableContainerNode removeContainerNodes(@NonNull ContainerNode @NonNull ... nodes);

    @NonNull EditableContainerNode removeContainerNodes(@NonNull String @NonNull ... keys);

    @NonNull EditableContainerNode clearContainerNodes();
}
