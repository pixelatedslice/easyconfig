package com.pixelatedslice.easyconfig.impl.config.file;

import com.google.auto.service.AutoService;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.BaseConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.EnvConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.builder.ConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.node.builder.EnvConfigNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionImpl;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

@AutoService(ConfigFileBuilder.class)
public class ConfigFileBuilderImpl implements ConfigFileBuilder.Handler {
    private final ConfigSection rootSection = ConfigSectionImpl.newRootSection();
    private final Collection<ConfigNode<?>> nodes = new ArrayList<>();
    private final Collection<ConfigSection> sections = new ArrayList<>();
    private Path filePathWithoutExtension;

    @Override
    public @NonNull ConfigFileBuilder filePath(@NonNull Path filePathWithoutExtension) {
        this.filePathWithoutExtension = filePathWithoutExtension;
        return this;
    }

    @Override
    public @NonNull <T> ChildNodeStep node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder) {
        return this.addNodeBuilder(() -> new ConfigNodeBuilderImpl<T>(), nodeBuilder);
    }

    @Override
    public @NonNull <T> ChildNodeStep env(@NonNull Consumer<? super EnvConfigNodeBuilder<T>> nodeBuilder) {
        return this.addNodeBuilder(EnvConfigNodeBuilderImpl<T>::new, nodeBuilder);
    }

    private @NonNull <T, H extends BaseConfigNodeBuilder.Handler<T, ? extends ConfigNode<T>, ? extends BuilderStep>>
    ChildNodeStep addNodeBuilder(
            @NonNull Supplier<H> constructor,
            @NonNull Consumer<? super H> builder
    ) {
        var builderImpl = constructor.get();
        builder.accept(builderImpl);
        this.nodes.add(builderImpl.build());
        return this;
    }

    @Override
    public @NonNull NestedSectionStep section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder) {
        var sectionBuilder = new ConfigSectionBuilderImpl();
        nestedSectionBuilder.accept(sectionBuilder);
        this.sections.add(sectionBuilder.build());
        return this;
    }

    @Override
    public @NonNull ConfigFile build() {
        try (var mut = this.rootSection.mutable()) {
            mut.setNodes(this.nodes);
            mut.setSections(this.sections);
        }

        return new ConfigFileImpl(
                this.rootSection,
                this.filePathWithoutExtension
        );
    }
}
