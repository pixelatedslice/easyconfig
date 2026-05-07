package com.pixelatedslice.easyconfig.impl.config.node.value.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.value.ValueNodeImpl;
import org.jspecify.annotations.NonNull;

public class ValueNodeChildBuilder<T, Parent extends InternalNodeBuilder<?>> extends AbstractValueNodeBuilder<ValueNodeChildBuilder<T, Parent>, T> implements NodeBuilder.ValueFinalStep.Child<T, Parent> {

    private final Parent parent;

    public ValueNodeChildBuilder(@NonNull TypeToken<T> token, @NonNull String key, Parent parent) {
        super(token, key);
        this.parent = parent;
    }

    @Override
    public @NonNull AbstractNode build() {
        return new ValueNodeImpl<>(this);
    }

    @Override
    public @NonNull Parent complete() {
        this.parent.appendChild(this);
        return this.parent;
    }
}
