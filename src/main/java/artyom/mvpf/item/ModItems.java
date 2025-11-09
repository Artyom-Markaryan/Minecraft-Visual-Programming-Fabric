package artyom.mvpf.item;

import artyom.mvpf.MinecraftVisualProgrammingFabric;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item VARIABLE_ITEM = registerModItems("variable_item");
    public static final Item VALUE_TEXT_ITEM = registerModItems("value_text_item");
    public static final Item VALUE_NUMBER_ITEM = registerModItems("value_number_item");

    public static Item registerModItems(String name) {
        Identifier identifier = Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, name);
        RegistryKey<Item> itemRegistryKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
        Item.Settings itemSettings = new Item.Settings().registryKey(itemRegistryKey).rarity(Rarity.RARE);
        Item item = new Item(itemSettings);
        return Registry.register(Registries.ITEM, itemRegistryKey, item);
    }

    public static void registerModItems() {
        MinecraftVisualProgrammingFabric.LOGGER.info("Registering Mod Items for " + MinecraftVisualProgrammingFabric.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(
            fabricItemGroupEntries -> {
                fabricItemGroupEntries.add(VARIABLE_ITEM);
                fabricItemGroupEntries.add(VALUE_TEXT_ITEM);
                fabricItemGroupEntries.add(VALUE_NUMBER_ITEM);
            }
        );
    }
}