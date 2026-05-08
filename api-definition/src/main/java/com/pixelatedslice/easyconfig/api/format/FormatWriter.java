package com.pixelatedslice.easyconfig.api.format;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

public interface FormatWriter {

    void writeNull();

    void writeString(@NonNull String value);

    void writeBoolean(boolean value);

    void writeShort(short value);

    void writeInteger(int value);

    void writeLong(long value);

    void writeDouble(double value);

    void writeFloat(float value);

    void writeByte(byte value);

    void writeBigInteger(@NonNull BigInteger value);

    void writeBigDecimal(@NonNull BigDecimal value);

    void writeUUID(@NonNull UUID value);

    void writeObject(@Nullable Object object);

    default void writeBooleanArray(@NonNull Boolean @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeBooleanArray(boolean @NonNull [] array);

    default void writeShortArray(@NonNull Short @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeShortArray(short @NonNull [] array);

    default void writeIntArray(@NonNull Integer @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeIntArray(int @NonNull [] array);

    default void writeLongArray(@NonNull Long @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeLongArray(long @NonNull [] array);

    default void writeDoubleArray(@NonNull Double @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeDoubleArray(double @NonNull [] array);

    default void writeFloatArray(@NonNull Float @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeFloatArray(float @NonNull [] array);

    default void writeByteArray(@NonNull Byte @NonNull [] array) {
        this.writeObjectArray(array);
    }

    void writeBinary(byte @NonNull [] array);

    <T> void writeCollection(@NonNull Collection<T> collection);

    void writeObjectArray(@NonNull Object @NonNull [] array);
}
