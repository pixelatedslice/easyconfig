package com.pixelatedslice.easyconfig.impl.test.testUtils;

import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionAssert {

    public static <Value> void isEqualTo(Collection<Value> expected, Collection<Value> compare, boolean matchOrder) {
        if (matchOrder) {
            isEqualToOrdered(expected, compare);
            return;
        }
        isEqualToUnordered(expected, compare);
    }

    private static <Value> void isEqualToOrdered(Collection<Value> expected, Collection<Value> compare) {
        if (expected.size() != compare.size()) {
            Assertions.assertEquals(expected.size(), compare.size(), "Size do not match");
            return;
        }
        var expectedIterator = expected.iterator();
        var compareIterator = compare.iterator();
        int index = 0;
        while (expectedIterator.hasNext()) {
            var expectedValue = expectedIterator.next();
            var compareValue = compareIterator.next();
            Assertions.assertEquals(expectedValue, compareValue, "values did not match at " + index);
            index++;
        }
    }

    private static <Value> void isEqualToUnordered(Collection<Value> expected, Collection<Value> compare) {
        if (expected.size() != compare.size()) {
            Assertions.assertEquals(expected.size(), compare.size(), "Size do not match");
            return;
        }
        var mutableCompare = new LinkedList<>(compare);
        for (var entry : expected) {
            if (mutableCompare.contains(entry)) {
                mutableCompare.remove(entry);
            } else {
                Assertions.fail("Could not find entry " + entry.toString());
                return;
            }
        }
    }
}
