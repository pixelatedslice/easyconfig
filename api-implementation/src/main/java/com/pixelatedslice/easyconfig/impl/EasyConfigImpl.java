package com.pixelatedslice.easyconfig.impl;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerOverrideException;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerUnregisterException;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class EasyConfigImpl implements EasyConfig {
    private static volatile EasyConfigImpl INSTANCE;
    private final Map<Class<? extends FileFormat>, FileFormatProvider<?>> providers;
    private final Map<Class<?>, Serializer<?>> serializers;

    public EasyConfigImpl() {
        this.providers = new HashMap<>();
        this.serializers = new HashMap<>();
    }

    private EasyConfigImpl(
            @NonNull Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers,
            @NonNull Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers
    ) {
        this.providers = Objects.requireNonNull(providers);
        this.serializers = Objects.requireNonNull(serializers);
    }

    public static EasyConfigImpl instance() {
        if (INSTANCE == null) {
            synchronized (EasyConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EasyConfigImpl();
                }
            }
        }

        return INSTANCE;
    }


    @NonNull
    public EasyConfig copy() {
        return new EasyConfigImpl(this.providers, this.serializers);
    }


    @Override
    @NonNull
    public Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers() {
        return this.serializers;
    }


    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public <T> Optional<@NonNull Serializer<T>> serializer(@NonNull Class<T> clazz) {
        return Optional.ofNullable((Serializer<T>) this.serializers.get(Objects.requireNonNull(clazz)));
    }


    @Override
    public void registerSerializers(@NonNull Serializer<?>... serializers) {
        Objects.requireNonNull(serializers);

        Map<Serializer<?>, Serializer<?>> illegal = null;
        for (var serializer : serializers) {
            var previous = this.serializers.get(serializer.forClass());
            if (Serializer.isBuiltIn(previous)) {
                if (illegal == null) {
                    illegal = new HashMap<>(4);
                }
                illegal.put(previous, serializer);
            }
        }

        if (illegal != null) {
            throw new BuiltInSerializerOverrideException(illegal);
        }

        for (var ser : serializers) {
            this.serializers.put(ser.forClass(), ser);
        }
    }


    @Override
    public void unregisterSerializers(@NonNull Class<?>... classes) {
        Objects.requireNonNull(classes);

        List<Class<?>> illegal = null;
        for (var clazz : classes) {
            var serializer = this.serializers.get(clazz);
            if (serializer == null) {
                continue;
            }

            if (Serializer.isBuiltIn(serializer)) {
                if (illegal == null) {
                    illegal = new ArrayList<>(4);
                }
                illegal.add(serializer.forClass());
            }
        }

        if (illegal != null) {
            throw new BuiltInSerializerUnregisterException(illegal);
        }

        for (var serializer : classes) {
            this.serializers.remove(serializer);
        }
    }


    @Override
    @NonNull
    public Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers() {
        return this.providers;
    }


    @Override
    public @NonNull Optional<@NonNull FileFormatProvider<?>> provider(
            @NonNull Class<? extends FileFormat> fileFormatClass
    ) {
        return Optional.ofNullable(this.providers.get(Objects.requireNonNull(fileFormatClass)));
    }


    @Override
    public void registerProviders(@NonNull FileFormatProvider<?>... providers) {
        Objects.requireNonNull(providers);

        var map = new HashMap<Class<? extends FileFormat>, FileFormatProvider<?>>();
        for (FileFormatProvider<?> provider : providers) {
            map.put(provider.fileFormatClass(), provider);
        }
        this.providers.putAll(map);
    }


    @Override
    public void unregisterProviders(@NonNull FileFormatProvider<?>... providers) {
        Objects.requireNonNull(providers);

        for (FileFormatProvider<?> provider : providers) {
            this.providers.remove(provider.fileFormatClass());
        }
    }
}