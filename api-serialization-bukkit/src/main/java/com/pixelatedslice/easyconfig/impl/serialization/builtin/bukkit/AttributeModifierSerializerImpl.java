package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.util.NumberConversions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public final class AttributeModifierSerializerImpl implements BuiltInBukkitSerializer<AttributeModifier> {
    private static final TypeToken<AttributeModifier> typeToken = new TypeToken<>() {
    };

    private AttributeModifierSerializerImpl() {
    }

    public static AttributeModifierSerializerImpl instance() {
        return AttributeModifierSerializerImplHolder.INSTANCE;
    }

    @Override
    public @NonNull TypeToken<AttributeModifier> forType() {
        return typeToken;
    }

    @Override
    public void serialize(@Nullable AttributeModifier value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("key", (value != null) ? value.getKey().toString() : null, String.class);
        sectionBuilder.node("operation", (value != null) ? value.getOperation().ordinal() : null, int.class);
        sectionBuilder.node("amount", (value != null) ? value.getAmount() : null, double.class);
        sectionBuilder.node("slot",
                ((value != null) && (value.getSlotGroup() != EquipmentSlotGroup.ANY))
                        ? value.getSlotGroup().toString() : null,
                String.class);
    }

    @Override
    public @NonNull AttributeModifier deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var key = section
                .node(String.class, "key")
                .flatMap(ConfigNode::value)
                .map(NamespacedKey::fromString)
                .orElseThrow();
        var operation = section.node(int.class, "operation").flatMap(ConfigNode::value)
                .map(NumberConversions::toInt)
                .map((Integer index) -> AttributeModifier.Operation.values()[index])
                .orElseThrow();
        var amount = section
                .node(double.class, "amount")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var slot = section.node(String.class, "slot").flatMap(ConfigNode::value)
                .map((String slotString) -> {
                    var slotGroup = EquipmentSlotGroup.getByName(slotString.toLowerCase(Locale.ROOT));
                    return (slotGroup == null) ? EquipmentSlotGroup.ANY : slotGroup;
                })
                .orElseThrow();

        return new AttributeModifier(key, amount, operation, slot);
    }

    private static final class AttributeModifierSerializerImplHolder {
        private static final AttributeModifierSerializerImpl INSTANCE = new AttributeModifierSerializerImpl();
    }
}
