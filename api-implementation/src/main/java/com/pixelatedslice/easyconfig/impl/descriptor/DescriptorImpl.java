package com.pixelatedslice.easyconfig.impl.descriptor;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public record DescriptorImpl<T>(@NonNull TypeToken<T> token) implements Descriptor<T> {
    DescriptorImpl(@NonNull Class<T> simpleType) {
        this(TypeToken.of(simpleType));
    }

    @Override
    public Optional<TypeToken<T>> typeToken() {
        return Optional.of(this.token);
    }
}
