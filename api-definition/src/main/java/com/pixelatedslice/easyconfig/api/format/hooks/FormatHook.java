package com.pixelatedslice.easyconfig.api.format.hooks;

import com.pixelatedslice.easyconfig.api.format.FormatReader;
import com.pixelatedslice.easyconfig.api.format.FormatWriter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface FormatHook {
    @NonNull Stage stage();

    interface Writing extends FormatHook {
        @Override
        @NonNull WriterStage stage();

        <T> void write(@NonNull FormatWriter writer, @NonNull T value);

        enum WriterStage implements Stage {
            WRITING_NODE,
            WRITING_CONTAINER_NODE,
            WRITING_VALUE_NODE,
            SERIALIZING_VALUE_NODE_VALUE,
            WRITING_NULL,
            WRITING_STRING,
            WRITING_BOOLEAN,
            WRITING_SHORT,
            WRITING_INTEGER,
            WRITING_LONG,
            WRITING_BIG_INTEGER,
            WRITING_DOUBLE,
            WRITING_FLOAT,
            WRITING_BIG_DECIMAL,
            WRITING_BOOLEAN_ARRAY,
            WRITING_SHORT_ARRAY,
            WRITING_INT_ARRAY,
            WRITING_LONG_ARRAY,
            WRITING_DOUBLE_ARRAY,
            WRITING_FLOAT_ARRAY,
            WRITING_BYTE_ARRAY,
            WRITING_OBJECT_ARRAY,
            WRITING_COLLECTION,
        }
    }

    interface Reading extends FormatHook {
        @Override
        @NonNull ReaderStage stage();

        <T> @Nullable T read(@NonNull FormatReader reader);

        enum ReaderStage implements Stage {
            READING_NODE,
            READING_CONTAINER_NODE,
            READING_VALUE_NODE,
            DESERIALIZING_VALUE_NODE_VALUE,
            READING_NULL,
            READING_STRING,
            READING_BOOLEAN,
            READING_SHORT,
            READING_INTEGER,
            READING_LONG,
            READING_BIG_INTEGER,
            READING_DOUBLE,
            READING_FLOAT,
            READING_BIG_DECIMAL,
            READING_BOOLEAN_ARRAY,
            READING_SHORT_ARRAY,
            READING_INT_ARRAY,
            READING_LONG_ARRAY,
            READING_DOUBLE_ARRAY,
            READING_FLOAT_ARRAY,
            READING_BYTE_ARRAY,
            READING_OBJECT_ARRAY,
            READING_COLLECTION,
        }
    }

    interface Stage {
    }
}
