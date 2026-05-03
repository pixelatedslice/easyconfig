package com.pixelatedslice.easyconfig.api.config.node.container;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.editable.EditableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

public interface EditableContainerNode extends EditableVariant {
    void addNodes(@NonNull Node @NonNull ... nodes);

    void setNodes(@NonNull Collection<? extends @NonNull Node> nodes);

    void removeNodes(@NonNull Node @NonNull ... nodes);

    void removeNodes(@NonNull String @NonNull ... keys);

    void clearNodes();

    void addValueNodes(@NonNull ValueNode<?> @NonNull ... nodes);

    void setValueNodes(@NonNull Collection<? extends @NonNull ValueNode<?>> nodes);

    void removeValueNodes(@NonNull ValueNode<?> @NonNull ... nodes);

    void removeValueNodes(@NonNull String @NonNull ... keys);

    void clearValueNodes();

    void addContainerNodes(@NonNull ContainerNode @NonNull ... nodes);

    void setContainerNodes(@NonNull Collection<? extends @NonNull ContainerNode> nodes);

    void removeContainerNodes(@NonNull ContainerNode @NonNull ... nodes);

    void removeContainerNodes(@NonNull String @NonNull ... keys);

    void clearContainerNodes();
}
