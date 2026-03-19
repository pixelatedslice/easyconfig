package com.pixelatedslice.easyconfig.api.encoding;

import com.pixelatedslice.easyconfig.api.serialization.Serializable;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;

import java.util.Map;

public interface Decoder {
    void decodeInt(int value);

    void decodeInt(Integer value);

    void decodeString(String value);

    void decodeBoolean(boolean value);

    void decodeBoolean(Boolean value);

    void decodeDouble(double value);

    void decodeDouble(Double value);

    void decodeFloat(float value);

    void decodeFloat(Float value);

    void decodeLong(long value);

    void decodeLong(Long value);

    void decodeByte(byte value);

    void decodeByte(Byte value);

    void decodeShort(short value);

    void decodeShort(Short value);

    void decodeChar(char value);

    void decodeChar(Character value);

    void decodeByteArray(byte[] value);

    void decodeByteArray(Byte[] value);

    <T> void decodeArray(T[] value);

    <T> void decodeIterable(Iterable<T> value);

    <K, V> void decodeMap(Map<K, V> value);

    <T> void decodeSerializable(T value, Serializer<T> serializer);

    default <T extends Serializable<T>> void decodeSerializable(T value) {
        this.decodeSerializable(value, value.serializer());
    }

    void decodeNull();
}
