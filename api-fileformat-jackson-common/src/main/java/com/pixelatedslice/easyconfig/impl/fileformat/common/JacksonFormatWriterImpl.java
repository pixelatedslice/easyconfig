package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.pixelatedslice.easyconfig.api.format.FormatWriter;
import com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

public class JacksonFormatWriterImpl implements FormatWriter {
    private final @NonNull JsonGenerator generator;
    private final @NonNull SerializerRegistry serializerRegistry;

    public JacksonFormatWriterImpl(@NonNull JsonGenerator generator, @NonNull SerializerRegistry serializerRegistry) {
        this.generator = generator;
        this.serializerRegistry = serializerRegistry;
    }

    @Override
    public void writeNull() {
        this.generator.writeNull();
    }

    @Override
    public void writeString(@NonNull String value) {
        this.generator.writeString(value);
    }

    @Override
    public void writeBoolean(boolean value) {
        this.generator.writeBoolean(value);
    }

    @Override
    public void writeShort(short value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeInteger(int value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeLong(long value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeDouble(double value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeFloat(float value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeByte(byte value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeBigInteger(@NonNull BigInteger value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeBigDecimal(@NonNull BigDecimal value) {
        this.generator.writeNumber(value);
    }

    @Override
    public void writeUUID(@NonNull UUID value) {
        this.generator.writeString(value.toString());
    }

    @Override
    public void writeBooleanArray(boolean @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeBoolean(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeShortArray(short @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeIntArray(@NonNull Integer @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeIntArray(int @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeLongArray(@NonNull Long @NonNull [] array) {

    }

    @Override
    public void writeLongArray(long @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeDoubleArray(@NonNull Double @NonNull [] array) {
    }

    @Override
    public void writeDoubleArray(double @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeFloatArray(@NonNull Float @NonNull [] array) {

    }

    @Override
    public void writeFloatArray(float @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.generator.writeNumber(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeBinary(byte @NonNull [] binary) {
        this.generator.writeBinary(binary);
    }

    @Override
    public <T> void writeCollection(@NonNull Collection<T> collection) {
        this.generator.writeStartArray();
        for (var item : collection) {
            this.writeObject(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public void writeObjectArray(@NonNull Object @NonNull [] array) {
        this.generator.writeStartArray();
        for (var item : array) {
            this.writeObject(item);
        }
        this.generator.writeEndArray();
    }

    @Override
    public <T> void writeObject(@Nullable T object) {
        switch (object) {
            case null -> this.writeNull();
            case String value -> this.writeString(value);
            case String[] array -> this.writeObjectArray(array);
            case Boolean value -> this.writeBoolean(value);
            case Boolean[] array -> this.writeBooleanArray(array);
            case boolean[] array -> this.writeBooleanArray(array);
            case Short value -> this.writeShort(value);
            case Short[] array -> this.writeShortArray(array);
            case short[] array -> this.writeShortArray(array);
            case Integer value -> this.writeInteger(value);
            case Integer[] array -> this.writeIntArray(array);
            case int[] array -> this.writeIntArray(array);
            case Long value -> this.writeLong(value);
            case Long[] array -> this.writeLongArray(array);
            case long[] array -> this.writeLongArray(array);
            case Float value -> this.writeFloat(value);
            case Float[] array -> this.writeFloatArray(array);
            case float[] array -> this.writeFloatArray(array);
            case Double value -> this.writeDouble(value);
            case Double[] array -> this.writeDoubleArray(array);
            case double[] array -> this.writeDoubleArray(array);
            case BigInteger value -> this.writeBigInteger(value);
            case BigInteger[] array -> this.writeObjectArray(array);
            case BigDecimal value -> this.writeBigDecimal(value);
            case BigDecimal[] array -> this.writeObjectArray(array);
            case UUID value -> this.writeUUID(value);
            case UUID[] array -> this.writeObjectArray(array);
            case Byte value -> this.writeByte(value);
            case byte[] binary -> this.writeBinary(binary);
            case Object[] array -> this.writeObjectArray(array);
            case Collection<?> collection -> this.writeCollection(collection);
            default -> {
                var clazz = object.getClass();
                var serializerOpt = this.serializerRegistry
                        .findFormatSerializer(clazz)
                        .orElseThrow(() -> new IllegalStateException(String.format(
                                "The object \"%s\" can't be serialized!",
                                object)
                        ));
                serializerOpt.serialize(object, this);
            }
        }
    }
}
