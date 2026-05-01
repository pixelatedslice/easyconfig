package com.pixelatedslice.easyconfig.api.config.section.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.builder.config.ConfigNodeChildrenStep;
import com.pixelatedslice.easyconfig.api.builder.config.NestedConfigSectionStep;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface ConfigSectionBuilder {

    @NonNull ParentStep key(@NonNull String key);

    interface ParentStep extends BuilderStep, NestedConfigSectionStep<NestedSectionStep> {
        @NonNull NestedSectionStep parent(@NonNull ConfigSection parent);
    }

    interface NestedSectionStep extends BuilderStep, NestedConfigSectionStep<NestedSectionStep>, ChildNodeStep {
    }

    interface ChildNodeStep extends BuilderStep, ConfigNodeChildrenStep<ChildNodeStep>,
            com.pixelatedslice.easyconfig.api.builder.config.CommentStep<CommentStep> {
    }

    interface CommentStep
            extends BuilderStep, com.pixelatedslice.easyconfig.api.builder.config.CommentStep<CommentStep>, FinalStep {
    }

    @FunctionalInterface
    interface FinalStep extends BuilderStep {
        @NonNull ConfigSection build();
    }

    interface Handler extends ConfigSectionBuilder,
            ConfigSectionBuilder.ParentStep, NestedSectionStep, ConfigSectionBuilder.ChildNodeStep,
            CommentStep, FinalStep {
    }
}
