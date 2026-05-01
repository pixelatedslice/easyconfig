package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.*;


public class ConfigSectionIterator implements Iterator<ConfigSection> {
    private final Deque<ConfigSection> nodeStack;

    public ConfigSectionIterator(@NonNull ConfigSection rootSection) {
        Objects.requireNonNull(rootSection);
        this.nodeStack = new ArrayDeque<>(rootSection.sections());
    }

    public static @NonNull Optional<@NonNull ConfigSection> findSection(
            @NonNull Collection<? extends @NonNull ConfigSection> nestedSections,
            @NonNull String @NonNull ... providedKeys
    ) {
        Objects.requireNonNull(nestedSections);
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }
        List<String> keys = List.of(providedKeys);
        var currentNestedSections = nestedSections;
        int last = keys.size() - 1;
        for (int i = 0; i <= last; i++) {
            String wanted = keys.get(i);
            ConfigSection next = null;
            for (ConfigSection section : currentNestedSections) {
                if (section.key().equals(wanted)) {
                    next = section;
                    break;
                }
            }
            if (next == null) {
                return Optional.empty();
            }
            if (i == last) {
                return Optional.of(next);
            }
            currentNestedSections = next.sections();
        }
        return Optional.empty();
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
