package com.pixelatedslice.easyconfig.impl.test.testUtils;

import org.mockito.Mockito;

public class MockitoHelper {

    public static <T> T whenReturn(T methodCall, T value) {
        Mockito.when(methodCall).thenReturn(value);
        return value;
    }
}
