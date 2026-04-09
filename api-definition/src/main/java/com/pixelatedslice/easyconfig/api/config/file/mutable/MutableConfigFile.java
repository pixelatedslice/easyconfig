package com.pixelatedslice.easyconfig.api.config.file.mutable;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.file.immutable.ImmutableConfigFile;
import com.pixelatedslice.easyconfig.api.config.section.mutable.MutableConfigSection;
import com.pixelatedslice.easyconfig.api.mutability.MutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.WithImmutableVariant;
import org.jspecify.annotations.NonNull;

public interface MutableConfigFile extends ConfigFile<MutableConfigSection>, MutableVariant,
        WithImmutableVariant<ImmutableConfigFile> {
    @Override
    @NonNull MutableConfigSection rootSection();
}
