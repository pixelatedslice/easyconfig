package com.pixelatedslice.easyconfig.impl;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerOverrideException;
import com.pixelatedslice.easyconfig.api.exception.BuiltInSerializerUnregisterException;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.HoconFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.JsonFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.TomlFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.YamlFileFormat;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class EasyConfigImpl implements EasyConfig {
    private static volatile EasyConfigImpl INSTANCE;
    private final CommonFormatProviders commonFormatProviders;
    private final Map<Class<? extends FileFormat>, FileFormatProvider<?>> providers;
    private final Map<Class<?>, Serializer<?>> serializers;

    private EasyConfigImpl() {
        this.providers = new HashMap<>();
        this.serializers = new HashMap<>();
        this.commonFormatProviders = new CommonFormatProvidersImpl(this);
    }

    private EasyConfigImpl(
            @NonNull Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers,
            @NonNull Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers
    ) {
        this.providers = Objects.requireNonNull(providers);
        this.serializers = Objects.requireNonNull(serializers);
        this.commonFormatProviders = new CommonFormatProvidersImpl(this);
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
    public void registerSerializers(@NonNull Serializer<?> @NonNull ... serializers) {
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
    public void unregisterSerializers(@NonNull Class<?> @NonNull ... classes) {
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


    @SuppressWarnings("unchecked")
    @Override
    public <T extends FileFormat> @NonNull Optional<@NonNull FileFormatProvider<T>> provider(
            @NonNull Class<T> fileFormatClass
    ) {
        var found = this.providers.get(Objects.requireNonNull(fileFormatClass));
        if (found == null) {
            return Optional.empty();
        }

        return found.fileFormatClass().equals(fileFormatClass)
                ? Optional.of((FileFormatProvider<T>) found)
                : Optional.empty();

    }


    @Override
    public void registerProviders(@NonNull FileFormatProvider<?> @NonNull ... providers) {
        Objects.requireNonNull(providers);

        var map = new HashMap<Class<? extends FileFormat>, FileFormatProvider<?>>();
        for (FileFormatProvider<?> provider : providers) {
            map.put(provider.fileFormatClass(), provider);
        }
        this.providers.putAll(map);
    }


    @Override
    public void unregisterProviders(@NonNull FileFormatProvider<?> @NonNull ... providers) {
        Objects.requireNonNull(providers);

        for (FileFormatProvider<?> provider : providers) {
            this.providers.remove(provider.fileFormatClass());
        }
    }

    @Override
    public @NonNull CommonFormatProviders commonFormatProviders() {
        return this.commonFormatProviders;
    }

    static class CommonFormatProvidersImpl implements CommonFormatProviders {
        private final EasyConfig easyConfig;

        CommonFormatProvidersImpl(EasyConfig easyConfig) {
            this.easyConfig = easyConfig;
        }

        @Override
        public @NonNull Optional<@NonNull FileFormatProvider<HoconFileFormat>> hocon() {
            return this.easyConfig.provider(HoconFileFormat.class);
        }

        @Override
        public @NonNull Optional<@NonNull FileFormatProvider<JsonFileFormat>> json() {
            return this.easyConfig.provider(JsonFileFormat.class);
        }

        @Override
        public @NonNull Optional<@NonNull FileFormatProvider<TomlFileFormat>> toml() {
            return this.easyConfig.provider(TomlFileFormat.class);
        }

        @Override
        public @NonNull Optional<@NonNull FileFormatProvider<YamlFileFormat>> yaml() {
            return this.easyConfig.provider(YamlFileFormat.class);
        }

        @Override
        public void reloadFormatProviderInstances() {

        }
    }
}