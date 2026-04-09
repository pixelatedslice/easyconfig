package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.config.BuilderWithConfigNodes;
import com.pixelatedslice.easyconfig.api.builder.config.BuilderWithConfigSections;
import com.pixelatedslice.easyconfig.api.comments.BuilderWithComments;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface ConfigSectionBuilder extends BuilderWithConfigNodes, BuilderWithConfigSections, BuilderWithComments {
    @Override
    @NonNull ConfigSectionBuilder addComment(@NonNull String comment);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    @Override
    @NonNull <T> ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    @Override
    @NonNull ConfigSectionBuilder section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    @Override
    @NonNull ConfigSectionBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    @NonNull ConfigSectionBuilder key(@NonNull String key);

    @NonNull ConfigSectionBuilder parent(@NonNull ConfigSection parent);

    @NonNull ConfigSectionBuilder comments(@NonNull String @NonNull ... comments);

    @NonNull ConfigSection build();
}
