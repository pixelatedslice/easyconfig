package com.pixelatedslice.easyconfig.api.config.file.immutable;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.file.mutable.MutableConfigFile;
import com.pixelatedslice.easyconfig.api.config.section.immutable.ImmutableConfigSection;
import com.pixelatedslice.easyconfig.api.mutability.ImmutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.WithMutableVariant;

public interface ImmutableConfigFile extends ConfigFile<ImmutableConfigSection>, ImmutableVariant,
        WithMutableVariant<MutableConfigFile> {
}
