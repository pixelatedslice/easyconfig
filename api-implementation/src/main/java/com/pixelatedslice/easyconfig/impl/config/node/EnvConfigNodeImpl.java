package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.EnvConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class EnvConfigNodeImpl<T> extends ConfigNodeImpl<T> implements EnvConfigNode<T> {
    private final String envKey;

    public EnvConfigNodeImpl(
            @NonNull String key,
            @NonNull String envKey,
            @NonNull TypeToken<T> typeToken,
            @Nullable ConfigSection parent,
            @Nullable T defaultValue,
            @NonNull Validator<T> validator,
            @NonNull List<@NonNull String> comments
    ) {
        super(key, typeToken, parent, null, defaultValue, validator, comments);
        this.envKey = envKey;
    }

    @Override
    public String envKey() {
        return this.envKey;
    }
}