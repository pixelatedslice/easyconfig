package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public final class JacksonReadUtils {
    private static final Map<Class<?>, EnvironmentVariableConverter> ENV_PARSERS = new HashMap<>();
    private static final Pattern ENV_REGEX = Pattern.compile("env\\((.+?)\\)",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Map<TypeToken<?>, TypeReader> FLAT_REGISTRY = new HashMap<>();
    private static final Map<Class<?>, TypeTokenTypeReader> TYPE_TOKEN_REGISTRY = new HashMap<>();
    private static final TypeToken<Collection<?>> collectionTypeToken = new TypeToken<>() {
    };

    static {
        // Register Primitives
        FLAT_REGISTRY.put(TypeToken.of(BigDecimal.class), JsonParser::getDecimalValue);
        FLAT_REGISTRY.put(TypeToken.of(BigInteger.class), JsonParser::getBigIntegerValue);
        FLAT_REGISTRY.put(TypeToken.of(boolean[].class), JacksonReadUtils::readBooleanArray);
        FLAT_REGISTRY.put(TypeToken.of(Boolean.class), JsonParser::getBooleanValue);
        FLAT_REGISTRY.put(TypeToken.of(byte[].class), JsonParser::getBinaryValue);
        FLAT_REGISTRY.put(TypeToken.of(double[].class), JacksonReadUtils::readDoubleArray);
        FLAT_REGISTRY.put(TypeToken.of(Double.class), JsonParser::getDoubleValue);
        FLAT_REGISTRY.put(TypeToken.of(float[].class), JacksonReadUtils::readFloatArray);
        FLAT_REGISTRY.put(TypeToken.of(Float.class), JsonParser::getFloatValue);
        FLAT_REGISTRY.put(TypeToken.of(int[].class), JacksonReadUtils::readIntArray);
        FLAT_REGISTRY.put(TypeToken.of(Integer.class), JsonParser::getIntValue);
        FLAT_REGISTRY.put(TypeToken.of(long[].class), JacksonReadUtils::readLongArray);
        FLAT_REGISTRY.put(TypeToken.of(Long.class), JsonParser::getLongValue);
        FLAT_REGISTRY.put(TypeToken.of(short[].class), JacksonReadUtils::readShortArray);
        FLAT_REGISTRY.put(TypeToken.of(Short.class), JsonParser::getShortValue);
        FLAT_REGISTRY.put(TypeToken.of(String.class), JsonParser::getString);

        TYPE_TOKEN_REGISTRY.put(Deque.class, JacksonReadUtils::readDeque);
        TYPE_TOKEN_REGISTRY.put(List.class, JacksonReadUtils::readList);
        TYPE_TOKEN_REGISTRY.put(Object[].class, JacksonReadUtils::readArray);
        TYPE_TOKEN_REGISTRY.put(Set.class, JacksonReadUtils::readSet);

        ENV_PARSERS.put(String.class, (String s) -> s);
        ENV_PARSERS.put(Integer.class, Integer::valueOf);
        ENV_PARSERS.put(int.class, Integer::parseInt);
        ENV_PARSERS.put(Boolean.class, Boolean::valueOf);
        ENV_PARSERS.put(boolean.class, Boolean::parseBoolean);
        ENV_PARSERS.put(Double.class, Double::valueOf);
        ENV_PARSERS.put(double.class, Double::parseDouble);
        ENV_PARSERS.put(Float.class, Float::valueOf);
        ENV_PARSERS.put(float.class, Float::parseFloat);
        ENV_PARSERS.put(Long.class, Long::valueOf);
        ENV_PARSERS.put(long.class, Long::parseLong);
        ENV_PARSERS.put(Short.class, Short::valueOf);
        ENV_PARSERS.put(short.class, Short::parseShort);
        ENV_PARSERS.put(BigDecimal.class, BigDecimal::new);
        ENV_PARSERS.put(BigInteger.class, BigInteger::new);
    }

    private JacksonReadUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T read(@NonNull JsonParser parser, TypeToken<T> expectedType)
            throws IOException, IllegalStateException {
        if (parser.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }

        var reader = FLAT_REGISTRY.get(expectedType);
        if (reader != null) {
            return (T) reader.read(parser);
        }

        var ttReader = TYPE_TOKEN_REGISTRY.get(expectedType.getRawType());
        if (ttReader != null) {
            if (collectionTypeToken.isSupertypeOf(expectedType)) {
                TypeToken<?> componentType = expectedType.resolveType(Collection.class.getTypeParameters()[0]);
                return (T) ttReader.read(parser, componentType);
            }

            return (T) ttReader.read(parser, expectedType.getComponentType());
        }

        throw new IllegalStateException("Value with unexpected type: " + expectedType);
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T readEnv(@NonNull JsonParser parser, TypeToken<T> expectedType) throws IOException {
        if (parser.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }

        if (parser.currentToken() == JsonToken.VALUE_STRING) {
            var string = parser.getString();

            if (ENV_REGEX.matcher(string).matches()) {
                var envKey = string.replaceAll(ENV_REGEX.pattern(), "$1");
                var envValue = System.getenv(envKey);

                if (envValue == null) {
                    return null;
                }

                var rawType = expectedType.getRawType();
                var environmentVariableConverter = ENV_PARSERS.get(rawType);

                if (environmentVariableConverter == null) {
                    throw new IllegalStateException("Cannot parse environment variable into unexpected type: "
                            + rawType);
                }

                try {
                    return (T) environmentVariableConverter.convert(envValue);
                } catch (Exception e) {
                    throw new IOException("Failed to parse environment variable '" + envKey
                            + "' (" + envValue + ") as " + rawType.getSimpleName(), e);
                }
            }
        }

        throw new IllegalStateException("Value with unexpected type: " + parser.currentToken().name());
    }

    private static <T> @NonNull List<?> readList(
            @NonNull JsonParser parser,
            @NonNull TypeToken<? extends T> expectedType
    ) throws IOException {
        return new ArrayList<>(Arrays.asList(readArray(parser, expectedType)));
    }

    private static <T> @NonNull Set<?> readSet(
            @NonNull JsonParser parser,
            @NonNull TypeToken<? extends T> expectedType
    ) throws IOException {
        return new HashSet<>(Arrays.asList(readArray(parser, expectedType)));
    }

    private static <T> @NonNull Deque<?> readDeque(
            @NonNull JsonParser parser,
            @NonNull TypeToken<? extends T> expectedType
    ) throws IOException {
        return new ArrayDeque<>(Arrays.asList(readArray(parser, expectedType)));
    }

    @SuppressWarnings("unchecked")
    private static <T> @NonNull T @NonNull [] readArray(
            @NonNull JsonParser parser,
            @NonNull TypeToken<? extends T> expectedType
    ) throws IOException, IllegalStateException {
        var list = new ArrayList<T>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(read(parser, expectedType));
        }

        T[] arr = (T[]) java.lang.reflect.Array.newInstance(expectedType.getRawType(), list.size());
        return list.toArray(arr);
    }

    private static boolean @NonNull [] readBooleanArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Boolean>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getBooleanValue());
        }

        var size = list.size();
        var arr = new boolean[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    private static short @NonNull [] readShortArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Short>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getShortValue());
        }

        var size = list.size();
        var arr = new short[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    private static int @NonNull [] readIntArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Integer>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getIntValue());
        }

        var size = list.size();
        var arr = new int[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    private static long @NonNull [] readLongArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Long>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getLongValue());
        }

        var size = list.size();
        var arr = new long[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    private static double @NonNull [] readDoubleArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Double>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getDoubleValue());
        }

        var size = list.size();
        var arr = new double[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    private static float @NonNull [] readFloatArray(@NonNull JsonParser parser) {
        var list = new ArrayList<Float>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parser.getFloatValue());
        }

        var size = list.size();
        var arr = new float[size];

        for (var i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    @FunctionalInterface
    private interface TypeReader {
        Object read(JsonParser parser) throws IOException;
    }

    @FunctionalInterface
    private interface TypeTokenTypeReader {
        <T> Object read(JsonParser parser, TypeToken<T> expectedType) throws IOException;
    }

    @FunctionalInterface
    private interface EnvironmentVariableConverter {
        Object convert(String variable) throws IOException;
    }
}
