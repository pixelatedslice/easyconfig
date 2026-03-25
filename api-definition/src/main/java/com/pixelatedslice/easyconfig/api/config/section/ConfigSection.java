package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.Commentable;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface ConfigSection extends WithConfigNodeChildren, WithNestedConfigSection, Commentable {
    /**
     * Retrieves the key associated with this configuration node.
     * The key serves as the unique identifier for the node within its hierarchy.
     *
     * @return the non-null key of this configuration node
     */
    @NonNull String key();

    /**
     * Retrieves the parent configuration node of this node, if present.
     * The parent node represents the next higher level in the hierarchy,
     * providing context for this node within the overall configuration structure.
     *
     * @return an {@link Optional} containing the parent configuration node if it exists, or an empty
     * {@link Optional} if this node has no parent
     */
    @NonNull Optional<@NonNull ConfigSection> parent();

    /**
     * Sets the parent configuration node for this node.
     * The parent node represents the immediate higher level in the hierarchy and
     * helps establish the relationship between this node and its parent within the
     * overall configuration structure.
     *
     * @param parent the {@link ConfigNode} to be set as the parent of this node;
     *               can be null to indicate that this node has no parent
     */
    void setParent(@Nullable ConfigSection parent);

    /**
     * Retrieves a child configuration node that matches the specified typeToken and keys
     * within the current configuration section's nested structure.
     *
     * @param <T>          the typeToken of the value associated with the target configuration node
     * @param typeToken    the non-null class of the value typeToken expected for the target node
     * @param providedKeys the non-null array of keys used to identify the target configuration node
     * @return an {@link Optional} containing the matching {@link ConfigNode} if found, or an empty
     * {@link Optional} if no matching node exists
     * @throws NullPointerException if {@code typeToken} or {@code providedKeys} is null
     */
    default <T> @NonNull Optional<@NonNull ConfigNode<T>> childNode(
            @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String... providedKeys
    ) {
        return this.childNode(this, typeToken, providedKeys);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> childNode(
            @NonNull Class<@NonNull T> simpleType,
            @NonNull String... providedKeys
    ) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.childNode(this, typeToken, providedKeys);
    }

    /**
     * Retrieves a child configuration node of a specified typeToken and key from the hierarchical
     * configuration structure. This method follows a style inspired by the Bukkit API for streamlined
     * access to child nodes based on a specific key.
     *
     * @param <T>       the typeToken of the value associated with the target configuration node
     * @param typeToken the non-null class of the value typeToken expected for the target node
     * @param key       the non-null key identifying the target configuration node
     * @return an {@link Optional} containing the matching {@link ConfigNode} if found, or an empty {@link Optional}
     * if no matching node exists
     * @throws NullPointerException if {@code typeToken} or {@code key} is null
     */
    default <T> @NonNull Optional<@NonNull ConfigNode<T>> childNodeButInTheBukkitAPIStyle(
            @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String key
    ) {
        return this.childNode(this, typeToken, key);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> childNodeButInTheBukkitAPIStyle(
            @NonNull Class<@NonNull T> simpleType,
            @NonNull String key
    ) {
        var typeToken = TypeToken.of(simpleType);

        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.childNode(this, typeToken, key);
    }
}
