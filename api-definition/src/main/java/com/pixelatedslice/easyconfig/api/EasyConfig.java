package com.pixelatedslice.easyconfig.api;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 * Defines the contract for a configuration system supporting the registration
 * and management of serializers and file format providers. Provides methods for
 * copying configuration instances, retrieving and modifying serializers, and managing
 * file format providers.
 */
public interface EasyConfig {
    /**
     * Creates and returns a new copy of the current {@code EasyConfig} instance.
     * The returned copy will have the same configurations and registered serializers
     * and providers as the original instance. Any modifications made to the returned
     * copy will not affect the original instance, and vice versa.
     *
     * @return a non-null {@code EasyConfig} instance that is a copy of the current instance.
     */
    @NotNull EasyConfig copy();

    /**
     * Retrieves a map of serializers associated with their respective target classes.
     *
     * @return a non-null map where the keys are non-null classes and the values are non-null
     * serializers responsible for handling the serialization and deserialization
     * of objects of those classes.
     */
    @NotNull Map<@NotNull Class<?>, @NotNull Serializer<?>> serializers();

    /**
     * Retrieves an {@link Optional} containing the {@link Serializer} associated with the specified class.
     * This method allows querying for a serializer capable of handling the serialization and
     * deserialization of objects of the given class type.
     *
     * @param <T>   the type of the objects handled by the serializer
     * @param clazz the {@link Class} object representing the type for which the serializer is to be retrieved; must
     *              not be null
     * @return an {@link Optional} containing the {@link Serializer} if available, or an empty {@link Optional} if
     * none is found; never null
     * @throws NullPointerException if the {@code clazz} parameter is null
     */
    <T> @NotNull Optional<@NotNull Serializer<T>> serializer(@NotNull Class<T> clazz);

    /**
     * Registers one or more {@link Serializer} instances for use within the configuration system.
     * The provided serializers are added to the registry, enabling serialization and deserialization
     * of objects of the respective types associated with each serializer.
     *
     * @param serializers the varargs array of {@link Serializer} instances to register;
     *                    must not be null or contain null elements.
     * @throws NullPointerException if the {@code serializers} array or any of its elements is null.
     */
    void registerSerializers(@NotNull Serializer<?>... serializers);

    /**
     * Unregisters one or more serializers associated with the specified classes.
     * This method removes the serializers associated with the provided class types,
     * effectively disabling serialization and deserialization for those types
     * within the configuration system.
     *
     * @param classes a varargs array of {@link Class} objects representing the types
     *                for which the serializers should be unregistered; must not be null
     *                or contain null elements.
     * @throws NullPointerException if the {@code classes} array or any of its elements is null.
     */
    void unregisterSerializers(@NotNull Class<?>... classes);

    /**
     * Retrieves a map of registered file format providers associated with their respective file format classes.
     * The map's keys are classes representing specific file formats, and the values are the corresponding
     * providers capable of handling those file formats.
     *
     * @return a non-null map where the keys are non-null classes extending {@link FileFormat} and the values
     * are non-null instances of {@link FileFormatProvider} responsible for handling the associated file formats.
     * @throws NullPointerException if any key or value in the map is null.
     */
    @NotNull Map<@NotNull Class<? extends FileFormat>, @NotNull FileFormatProvider<?, ?>> providers();

    /**
     * Retrieves an {@link Optional} containing the {@link FileFormatProvider} for the specified
     * {@link FileFormat} class. This method returns the provider responsible for handling file
     * operations for the given file format.
     *
     * @param fileFormatClass the {@link Class} object representing the {@link FileFormat};
     *                        must not be null
     * @return an {@link Optional} containing the corresponding {@link FileFormatProvider} if available,
     * or an empty {@link Optional} if no provider is registered for the specified file format;
     * never null
     * @throws NullPointerException if the {@code fileFormatClass} parameter is null
     */
    @NotNull Optional<@NotNull FileFormatProvider<?, ?>> provider(
            @NotNull Class<? extends FileFormat> fileFormatClass
    );

    /**
     * Registers one or more {@link FileFormatProvider} instances for use within the configuration system.
     * The provided providers are added to the registry, enabling interaction with specific file formats
     * for reading and writing configuration files.
     *
     * @param providers the varargs array of {@link FileFormatProvider} instances to register;
     *                  must not be null or contain null elements.
     * @throws NullPointerException if the {@code providers} array or any of its elements is null.
     */
    void registerProviders(@NotNull FileFormatProvider<?, ?>... providers);

    /**
     * Unregisters one or more {@link FileFormatProvider} instances from the configuration system.
     * The specified providers will no longer be available for handling file format-related operations.
     *
     * @param providers the varargs array of {@link FileFormatProvider} instances to unregister;
     *                  must not be null or contain null elements.
     * @throws NullPointerException if the {@code providers} array or any of its elements is null.
     */
    void unregisterProviders(@NotNull FileFormatProvider<?, ?>... providers);
}