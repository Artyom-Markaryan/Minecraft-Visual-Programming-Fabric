package artyom.mvpf.block;

import artyom.mvpf.MinecraftVisualProgrammingFabric;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks {
    public static final Block SET_VARIABLE_BLOCK = registerModBlock(
        "set_variable_block",
        AbstractBlock.Settings.create()
            .strength(5.0f)
            .requiresTool()
            .sounds(BlockSoundGroup.LODESTONE)
    );

    private static void registerModBlockItem(String name, Block block) {
        Identifier identifier = Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, name);
        RegistryKey<Item> itemRegistryKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
        Item.Settings itemSettings = new Item.Settings().registryKey(itemRegistryKey).rarity(Rarity.RARE);
        BlockItem blockItem = new BlockItem(block, itemSettings);
        Registry.register(Registries.ITEM, itemRegistryKey, blockItem);
    }

    private static Block registerModBlock(String name, AbstractBlock.Settings blockSettings) {
        Identifier identifier = Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, name);
        RegistryKey<Block> blockRegistryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        Block block = switch (name) {
            case "set_variable_block" -> new SetVariableBlock(blockSettings.registryKey(blockRegistryKey));
            default -> new Block(blockSettings.registryKey(blockRegistryKey));
        };
        registerModBlockItem(name, block);
        return Registry.register(Registries.BLOCK, blockRegistryKey, block);
    }

    public static void registerModBlocks() {
        MinecraftVisualProgrammingFabric.LOGGER.info("Registering Mod Blocks for " + MinecraftVisualProgrammingFabric.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(
            fabricItemGroupEntries -> fabricItemGroupEntries.add(SET_VARIABLE_BLOCK)
        );
    }
}