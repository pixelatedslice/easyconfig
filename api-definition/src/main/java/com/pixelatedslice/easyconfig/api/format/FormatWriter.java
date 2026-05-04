package com.pixelatedslice.easyconfig.api.format;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

public interface FormatWriter {
    void writeNull();

    void writeString(String value);

    void writeBoolean(boolean value);

    void writeShort(short value);

    void writeInteger(int value);

    void writeLong(long value);

    void writeDouble(double value);

    void writeFloat(float value);

    void writeBigInteger(@NonNull BigInteger value);

    void writeBigDecimal(@NonNull BigDecimal value);

    void writeUUID(@NonNull UUID value);

    void writeBooleanArray(@NonNull Boolean @NonNull [] array);

    void writeBooleanArray(boolean @NonNull [] array);

    void writeShortArray(@NonNull Short @NonNull [] array);

    void writeShortArray(short @NonNull [] array);

    void writeIntArray(@NonNull Integer @NonNull [] array);

    void writeIntArray(int @NonNull [] array);

    void writeLongArray(@NonNull Long @NonNull [] array);

    void writeLongArray(long @NonNull [] array);

    void writeDoubleArray(@NonNull Double @NonNull [] array);

    void writeDoubleArray(double @NonNull [] array);

    void writeFloatArray(@NonNull Float @NonNull [] array);

    void writeFloatArray(float @NonNull [] array);

    void writeByteArray(@NonNull Byte @NonNull [] array);

    void writeByteArray(byte @NonNull [] array);

    void writeObjectArray(@NonNull Object @NonNull [] array);

    <T> void writeCollection(@NonNull Collection<T> collection);

    <T> void writeSerializable(@NonNull T value, @NonNull Serializer<@NonNull T> serializer);
}
