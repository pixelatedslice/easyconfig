package com.pixelatedslice.easyconfig.api.fileformat;

import com.pixelatedslice.easyconfig.api.config.ConfigFile;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public interface FileFormatProvider<T, F extends FileFormat> {
    /**
     * Retrieves the class type of the file format associated with this provider.
     *
     * @return the class type of the file format, which extends {@link FileFormat}
     */
    Class<F> fileFormatClass();

    /**
     * Retrieves an instance of the file format associated with this provider.
     *
     * @return an instance of the file format, which extends {@link FileFormat}
     */
    F fileFormatInstance();

    /**
     * Retrieves the name of the file format associated with this provider.
     *
     * @return the name of the file format as a {@code String}
     */
    String name();

    /**
     * Retrieves the underlying engine instance associated with this file format provider.
     * The underlying engine typically represents the core component or library used
     * for handling the specific functionality of the file format.
     *
     * @return the underlying engine instance of type {@code T}
     */
    T underlyingEngine();

    /**
     * Writes the provided configuration file to the specified path.
     * <p>
     * This method serializes the given {@code configFile} and writes its data to the
     * target {@code path}. It ensures that the configuration is saved in the format
     * supported by the implementation of this method.
     *
     * @param path       the target path where the configuration file will be written
     * @param configFile the configuration file to be written to the target path
     * @throws NullPointerException if {@code path} or {@code configFile} is {@code null}
     */
    void write(@NonNull Path path, @NonNull ConfigFile configFile);

    /**
     * Reads a configuration file from the specified file path and returns a {@link ConfigFile}
     * object representing the loaded configuration.
     *
     * @param path the file path to read the configuration file from; must not be null
     * @return the {@link ConfigFile} object representing the loaded configuration
     * @throws NullPointerException if the provided {@code path} is null
     */
    ConfigFile read(@NonNull Path path);

    /**
     * Reads a configuration file from the specified path and returns an instance of the given configuration class.
     * The method ensures that the file content is deserialized into the appropriate subclass of {@link ConfigFile}.
     *
     * @param path        the file path to read the configuration file from; must not be null
     * @param configClass the class type of the {@link ConfigFile} implementation to be returned; must not be null
     * @return an instance of the specified {@link ConfigFile} subclass representing the loaded configuration
     * @throws NullPointerException if {@code path} or {@code configClass} is {@code null}
     */
    <C extends ConfigFile> C read(@NonNull Path path, @NonNull Class<C> configClass);

    /**
     * Generates a new {@link Path} by appending the file extension associated with this file format
     * to the file name of the given path.
     *
     * @param path the original file path to which the file extension should be appended
     * @return a new {@link Path} instance with the file extension appended to the file name
     * @throws NullPointerException if the provided {@code path} is {@code null}
     */
    default Path pathWithExtension(@NonNull Path path) {
        return path.resolve(path.getFileName() + "." + this.fileFormatInstance().fileExtension());
    }
}