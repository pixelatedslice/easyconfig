package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import org.jspecify.annotations.NonNull;

import java.util.*;


public class ConfigSectionIteratorImpl implements ConfigSectionIterator {
    private final Deque<ConfigSection> nodeStack;


    public ConfigSectionIteratorImpl(@NonNull ConfigSection rootSection) {
        Objects.requireNonNull(rootSection);
        this.nodeStack = new ArrayDeque<>(rootSection.sections());
    }


    @Override
    public boolean hasNext() {
        return !this.nodeStack.isEmpty();
    }


    @Override
    public @NonNull ConfigSection next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        ConfigSection current = this.nodeStack.pop();
        List<ConfigSection> children = new ArrayList<>(current.sections());

        if (!children.isEmpty()) {
            for (int i = children.size() - 1; i >= 0; i--) {
                this.nodeStack.push(children.get(i));
            }
        }

        return current;
    }
}
