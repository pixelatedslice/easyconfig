package com.pixelatedslice.easyconfig.impl.config.file;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.impl.EasyConfigImpl;
import com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeIteratorImpl;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionIteratorImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

public class ConfigFileImpl implements ConfigFile {
    private final Path filePath;
    private final Class<? extends FileFormat> fileFormatClass;
    private final List<ConfigNode<?>> childNodes = new ArrayList<>();
    private final List<ConfigSection> nestedSections = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();

    public ConfigFileImpl(@NonNull Path filePathWithoutExtension, @NonNull Class<? extends FileFormat> fileFormatClass)
            throws IOException, ParseException {
        this.filePath = filePathWithoutExtension;
        this.fileFormatClass = fileFormatClass;

        ConfigFile loadedConfig = Objects.requireNonNull(EasyConfigImpl
                .instance()
                .provider(fileFormatClass)
                .orElseThrow()
                .load(this.filePath));

        this.childNodes.addAll(loadedConfig.nodes());
        this.nestedSections.addAll(loadedConfig.sections());
        this.comments.addAll(loadedConfig.comments());
    }

    public ConfigFileImpl(
            @NonNull Path filePathWithoutExtension,
            @NonNull Class<? extends FileFormat> fileFormatClass,
            @NonNull Collection<ConfigNode<?>> childNodes,
            @NonNull Collection<ConfigSection> nestedSections,
            @NonNull Collection<String> comments
    ) {
        this.filePath = filePathWithoutExtension;
        this.fileFormatClass = fileFormatClass;
        this.childNodes.addAll(childNodes);
        this.nestedSections.addAll(nestedSections);
        this.comments.addAll(comments);
    }

    @Override
    public @NonNull Path filePathWithoutExtension() {
        return this.filePath;
    }

    @Override
    public @NonNull Class<? extends FileFormat> fileFormatClass() {
        return this.fileFormatClass;
    }

    @Override
    public void save() throws IOException, ParseException {
        EasyConfigImpl.instance().provider(this.fileFormatClass).orElseThrow().write(this.filePath, this);
    }

    @Override
    public ConfigFile reload() throws IOException, ParseException {
        this.childNodes.clear();
        this.nestedSections.clear();
        this.comments.clear();

        ConfigFile loadedConfig = EasyConfigImpl
                .instance()
                .provider(this.fileFormatClass)
                .orElseThrow()
                .load(this.filePath);
        Objects.requireNonNull(loadedConfig);

        this.childNodes.addAll(loadedConfig.nodes());
        this.nestedSections.addAll(loadedConfig.sections());
        this.comments.addAll(loadedConfig.comments());

        return this;
    }

    @Override
    public @NonNull Collection<String> comments() {
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public ConfigFile addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }

    @Override
    public ConfigFile removeComment(@NonNull String comment) {
        this.comments.remove(comment);
        return this;
    }

    @Override
    public ConfigFile removeComment(int index) {
        this.comments.remove(index);
        return this;
    }

    @Override
    public ConfigFile clearComments() {
        this.comments.clear();
        return this;
    }

    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    @Override
    public ConfigFile addNode(@NonNull ConfigNode<?> child) {
        this.childNodes.add(child);
        return this;
    }

    @Override
    public ConfigFile removeNode(@NonNull String key) {
        this.childNodes.removeIf((ConfigNode<?> node) -> node.key().equals(key));
        return this;
    }

    @Override
    public @NonNull ConfigNodeIterator nodeIterator() {
        return new ConfigNodeIteratorImpl(this);
    }

    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        return Collections.unmodifiableList(this.nestedSections);
    }

    @Override
    public ConfigFile addSection(@NonNull ConfigSection section) {
        this.nestedSections.add(section);
        return this;
    }

    @Override
    public ConfigFile removeSection(@NonNull String key) {
        this.nestedSections.removeIf((ConfigSection section) -> section.key().equals(key));
        return this;
    }

    @Override
    public @NonNull ConfigSectionIterator sectionIterator() {
        return new ConfigSectionIteratorImpl(this);
    }

    @Override
    public @NonNull String key() {
        return "root";
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.empty();
    }

    @Override
    public ConfigFile setParent(@Nullable ConfigSection parent) {
        throw new UnsupportedOperationException();
    }
}
