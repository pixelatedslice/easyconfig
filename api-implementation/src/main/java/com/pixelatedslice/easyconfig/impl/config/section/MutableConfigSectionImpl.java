package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.MutableConfigSection;
import com.pixelatedslice.easyconfig.impl.comments.AbstractMutableAndCommentable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class MutableConfigSectionImpl extends AbstractMutableAndCommentable implements MutableConfigSection {
    private final ConfigSectionImpl originalSection;
    private final @NonNull Collection<@NonNull Consumer<@NonNull Collection<@NonNull ConfigNode<?>>>>
            nodeUpdates = new ArrayList<>();
    private final @NonNull Collection<@NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>>
            sectionUpdates = new ArrayList<>();

    public MutableConfigSectionImpl(ConfigSectionImpl originalSection) {
        this.originalSection = originalSection;
    }

    @Override
    public void addNodes(@NonNull ConfigNode<?> @NonNull ... nodes) {
        this.nodeUpdates.add((Collection<@NonNull ConfigNode<?>> list) -> {
            Collections.addAll(list, nodes);
        });
    }

    @Override
    public void removeNodes(@NonNull ConfigNode<?> @NonNull ... nodes) {
        this.nodeUpdates.add((@NonNull Collection<@NonNull ConfigNode<?>> list) -> {
            for (var node : nodes) {
                list.remove(node);
            }
        });
    }

    @Override
    public void removeNodes(@NonNull String @NonNull ... keys) {
        this.nodeUpdates.add((@NonNull Collection<@NonNull ConfigNode<?>> list) -> {
            for (var key : keys) {
                list.removeIf((ConfigNode<?> node) -> node.key().equals(key));
            }
        });
    }

    @Override
    public void clearNodes() {
        this.nodeUpdates.add(Collection::clear);
    }

    @Override
    public void addSections(@NonNull ConfigSection @NonNull ... sections) {
        this.sectionUpdates.add((Collection<@NonNull ConfigSection> list) -> {
            Collections.addAll(list, sections);
        });
    }

    @Override
    public void removeSections(@NonNull ConfigSection @NonNull ... sections) {
        this.sectionUpdates.add((Collection<@NonNull ConfigSection> list) -> {
            for (var section : sections) {
                list.remove(section);
            }
        });
    }

    @Override
    public void removeSections(@NonNull String @NonNull ... keys) {
        this.sectionUpdates.add((Collection<@NonNull ConfigSection> list) -> {
            for (var key : keys) {
                list.removeIf((ConfigSection section) -> section.key().equals(key));
            }
        });
    }

    @Override
    public void clearSections() {
        this.sectionUpdates.add(Collection::clear);
    }

    @Override
    public void apply() {
        this.originalSection.pushChangesToQueue(this.nodeUpdates, this.sectionUpdates, this.commentUpdates);
    }
}
