package com.pixelatedslice.easyconfig.api.format;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

public interface FormatReader {
    default <T> @Nullable T readNull() {
        return null;
    }

    @NonNull String readString();

    boolean readBoolean();

    short readShort();

    int readInteger();

    long readLong();

    double readDouble();

    float readFloat();

    @NonNull BigInteger readBigInteger();

    @NonNull BigDecimal readBigDecimal();

    @NonNull UUID readUUID();

    boolean @NonNull [] readBooleanArray();

    short @NonNull [] readShortArray();

    int @NonNull [] readIntArray();

    long @NonNull [] readLongArray();

    double @NonNull [] readDoubleArray();

    float @NonNull [] readFloatArray();

    byte @NonNull [] readByteArray();

    @NonNull Object @NonNull [] readObjectArray();

    <T> @NonNull Collection<T> readCollection();

    <T> @NonNull T readSerializable(@NonNull Serializer<@NonNull T> serializer);
}
