package com.pixelatedslice.easyconfig.impl.descriptor;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import com.pixelatedslice.easyconfig.api.descriptor.DescriptorFactory;
import org.jspecify.annotations.NonNull;

@AutoService(DescriptorFactory.class)
public class DescriptorFactoryImpl implements DescriptorFactory {
    @Override
    public @NonNull <T> Descriptor<T> create(@NonNull TypeToken<T> typeToken) {
        return new DescriptorImpl<>(typeToken);
    }
}
