package com.pixelatedslice.easyconfig.api.encoding;

import com.pixelatedslice.easyconfig.api.serialization.Serializable;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;

import java.util.Map;

public interface Encoder {
    void encodeInt(int value);

    void encodeInt(Integer value);

    void encodeString(String value);

    void encodeBoolean(boolean value);

    void encodeBoolean(Boolean value);

    void encodeDouble(double value);

    void encodeDouble(Double value);

    void encodeFloat(float value);

    void encodeFloat(Float value);

    void encodeLong(long value);

    void encodeLong(Long value);

    void encodeByte(byte value);

    void encodeByte(Byte value);

    void encodeShort(short value);

    void encodeShort(Short value);

    void encodeChar(char value);

    void encodeChar(Character value);

    void encodeByteArray(byte[] value);

    void encodeByteArray(Byte[] value);

    <T> void encodeArray(T[] value);

    <T> void encodeIterable(Iterable<T> value);

    <K, V> void encodeMap(Map<K, V> value);

    <T> void encodeSerializable(T value, Serializer<T> serializer);

    default <T extends Serializable<T>> void encodeSerializable(T value) {
        this.encodeSerializable(value, value.serializer());
    }

    void encodeNull();
}
