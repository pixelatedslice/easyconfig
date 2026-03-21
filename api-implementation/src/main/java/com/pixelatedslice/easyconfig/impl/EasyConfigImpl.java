package com.pixelatedslice.easyconfig.impl;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerOverrideException;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerUnregisterException;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EasyConfigImpl implements EasyConfig {
    private final Map<Class<? extends FileFormat>, FileFormatProvider<?, ?>> providers;
    private final Map<Class<?>, Serializer<?>> serializers;

    public EasyConfigImpl() {
        this.providers = new HashMap<>();
        this.serializers = new HashMap<>();
    }

    private EasyConfigImpl(
            @NotNull Map<@NotNull Class<? extends FileFormat>, @NotNull FileFormatProvider<?, ?>> providers,
            @NotNull Map<@NotNull Class<?>, @NotNull Serializer<?>> serializers
    ) {
        this.providers = Objects.requireNonNull(providers);
        this.serializers = Objects.requireNonNull(serializers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns a new instance of {@code EasyConfigImpl} with the same
     * internal state.
     */
    public @NotNull EasyConfigImpl copy() {
        return new EasyConfigImpl(this.providers, this.serializers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the internal map of serializers.
     */
    @Override
    public @NotNull Map<@NotNull Class<?>, @NotNull Serializer<?>> serializers() {
        return this.serializers;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the serializer associated with the specified class type.
     */
    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T> Optional<@NotNull Serializer<T>> serializer(@NotNull Class<T> clazz) {
        return Optional.ofNullable((Serializer<T>) this.serializers.get(Objects.requireNonNull(clazz)));
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds the specified serializers to the internal map.
     */
    @Override
    public void registerSerializers(@NotNull Serializer<?>... serializers) {
        Objects.requireNonNull(serializers);

        Map<Serializer<?>, Serializer<?>> illegal = null;
        for (var serializer : serializers) {
            var previous = this.serializers.get(serializer.forClass());
            if ((previous != null) && Serializer.isBuiltIn(previous)) {
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

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes the specified serializers from the internal map.
     */
    @Override
    public void unregisterSerializers(@NotNull Class<?>... classes) {
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

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the internal map of providers.
     */
    @Override
    public @NotNull Map<@NotNull Class<? extends FileFormat>, @NotNull FileFormatProvider<?, ?>> providers() {
        return this.providers;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the provider associated with the specified file format class.
     */
    @Override
    public @NotNull Optional<@NotNull FileFormatProvider<?, ?>> provider(
            @NotNull Class<? extends FileFormat> fileFormatClass
    ) {
        return Optional.ofNullable(this.providers.get(Objects.requireNonNull(fileFormatClass)));
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds the specified providers to the internal map.
     */
    @Override
    public void registerProviders(@NotNull FileFormatProvider<?, ?>... providers) {
        Objects.requireNonNull(providers);

        var map = new HashMap<Class<? extends FileFormat>, FileFormatProvider<?, ?>>();
        for (FileFormatProvider<?, ?> provider : providers) {
            map.put(provider.fileFormatClass(), provider);
        }
        this.providers.putAll(map);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes the specified providers from the internal map.
     */
    @Override
    public void unregisterProviders(@NotNull FileFormatProvider<?, ?>... providers) {
        Objects.requireNonNull(providers);

        for (FileFormatProvider<?, ?> provider : providers) {
            this.providers.remove(provider.fileFormatClass());
        }
    }
}