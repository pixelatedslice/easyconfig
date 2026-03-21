package com.pixelatedslice.easyconfig.api.config;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

/**
 * Represents a configuration file containing hierarchical configuration nodes.
 * Provides methods to access, modify, and manage configuration nodes as well as
 * functionality to persist and load the configuration file.
 */
public interface ConfigFile extends WithConfigNodeChildren {
    /**
     * Retrieves the file path associated with the configuration file.
     * The path represents the location of the file used for loading
     * and persisting the configuration data.
     *
     * @return the file path as a {@link Path} object
     */
    @NotNull Path filePath();

    /*da*
     * Persists the current state of the configuration to the underlying storage.
     * This method writes all the modifications made to the configuration nodes
     * and their values back to the storage medium (e.g., file, database) associated
     * with the configuration file.
     * <p>
     * Implementations of this method are responsible for handling the appropriate
     * serialization and ensuring that the current configuration state is accurately
     * and consistently saved.
     *
     * @throws IOException if an error occurs while saving the configuration to the
     *                     underlying storage medium
     */
    void save() throws IOException;

    /**
     * Saves the current state of the configuration to the underlying storage and then unloads
     * the in-memory representation of the configuration.
     * <p>
     * This method combines two operations into a single step:
     * 1. Persists any modifications made to the configuration nodes and their values
     * to the associated storage medium (e.g., file, database).
     * 2. Clears or releases the in-memory representation of the configuration, effectively
     * unloading the configuration data from memory.
     * <p>
     * Implementations of this method are responsible for ensuring that the save operation is
     * successfully completed before proceeding to unload the configuration. If the save operation
     * fails, the unloading process should not occur, and an appropriate exception should be thrown.
     * <p>
     * This method might be useful in scenarios where memory consumption needs to be minimized
     * or when the configuration data needs to be saved and discarded together.
     *
     * @throws IOException if an error occurs during the save operation or while releasing the
     *                     in-memory representation of the configuration.
     */
    void saveAndUnload() throws IOException;

    /**
     * Loads the configuration data from the file associated with this configuration file.
     * This method reads the underlying file, parses its contents, and populates the
     * configuration nodes managed by this object. If the file does not exist or an error
     * occurs during the loading process, an appropriate exception may be thrown.
     * <p>
     * Implementations of this method are expected to ensure that the configuration state
     * is synchronized with the contents of the source file after execution.
     *
     * @throws IOException    if an I/O error occurs while reading the file
     * @throws ParseException if an error occurs during parsing the configuration data
     */
    void load() throws IOException, ParseException;

    /**
     * Reloads the configuration file and updates its in-memory representation.
     * This method typically performs the following steps:
     * <ul>
     *   - Discards any unsaved changes made to the currently loaded configuration.
     *   - Loads the configuration data from the source file path associated with this object.
     *   - Rebuilds any in-memory structures representing the loaded configuration.
     * </ul>
     * It is recommended to call this method cautiously since it overwrites the in-memory
     * state with the stored state from the external source. Any unsaved changes will be lost.
     * <p>
     * Implementations of this method should handle any exceptions that occur during
     * the reloading process (e.g., file reading errors) and provide meaningful feedback
     * or recovery mechanisms, if appropriate.
     *
     * @throws IOException    if an I/O error occurs while reading the file
     * @throws ParseException if an error occurs during parsing the configuration data
     */
    void reload() throws IOException, ParseException;
}
