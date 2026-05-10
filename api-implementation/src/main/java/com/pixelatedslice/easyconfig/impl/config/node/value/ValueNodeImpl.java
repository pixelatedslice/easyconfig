package com.pixelatedslice.easyconfig.impl.config.node.value;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.value.EditableValueNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.AbstractValueNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.ValueNodeOriginalBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ValueNodeImpl<T> extends AbstractNode implements ValueNode<T> {

    private final @NonNull TypeToken<T> token;
    private final @NonNull Validator<T> validator;
    private final @Nullable Serializer<T> serializer;
    private final @Nullable T defaultValue;
    private final @Nullable T value;

    public ValueNodeImpl(AbstractValueNodeBuilder<?, T> builder) {
        super(builder);
        this.defaultValue = builder.defaultValue();
        this.token = Objects.requireNonNull(builder.type());
        this.validator = Objects.requireNonNullElseGet(builder.validator(), Validator::empty);
        this.serializer = builder.serializer();
        this.value = builder.value();
    }

    @Override
    public @NonNull Optional<T> value() {
        return Optional.ofNullable(this.value).or(this::defaultValue);
    }

    @Override
    public @NonNull Optional<T> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public @NonNull Optional<@NonNull Serializer<T>> serializer() {
        return Optional.empty();
    }

    @Override
    public @NonNull Validator<T> validator() {
        return this.validator;
    }

    @Override
    public @NonNull TypeToken<T> typeToken() {
        return this.token;
    }

    @Override
    public EditableValueNode<T> editable() {
        return null;
    }

    @Override
    public @NonNull ValueNodeOriginalBuilder<T> toBuilder() {
        return new ValueNodeOriginalBuilder<>(this.token, this.key())
                .defaultValue(this.defaultValue)
                .value(this.value)
                .serializer(this.serializer)
                .validator(this.validator);
    }

    @Override
    protected void internalAppendChild(@NonNull AbstractNode node) {
        throw new IllegalStateException("Value node! should not have called");
    }
}
