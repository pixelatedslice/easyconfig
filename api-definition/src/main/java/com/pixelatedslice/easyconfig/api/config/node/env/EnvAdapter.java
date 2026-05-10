package com.pixelatedslice.easyconfig.api.config.node.env;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@FunctionalInterface
public interface EnvAdapter<T> extends Function<@NonNull String, @Nullable T> {


}
