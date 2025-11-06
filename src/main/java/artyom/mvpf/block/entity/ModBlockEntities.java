package artyom.mvpf.block.entity;

import artyom.mvpf.MinecraftVisualProgrammingFabric;
import artyom.mvpf.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<SetVariableBlockEntity> SET_VARIABLE_BLOCK_ENTITY = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, "set_variable_block_entity"),
        FabricBlockEntityTypeBuilder.create(SetVariableBlockEntity::new, ModBlocks.SET_VARIABLE_BLOCK).build()
    );
    public static final BlockEntityType<RepeatBlockEntity> REPEAT_BLOCK_ENTITY = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, "repeat_block_entity"),
        FabricBlockEntityTypeBuilder.create(RepeatBlockEntity::new, ModBlocks.REPEAT_BLOCK).build()
    );
    public static final BlockEntityType<LoggerBlockEntity> LOGGER_BLOCK_ENTITY = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(MinecraftVisualProgrammingFabric.MOD_ID, "logger_block_entity"),
        FabricBlockEntityTypeBuilder.create(LoggerBlockEntity::new, ModBlocks.LOGGER_BLOCK).build()
    );

    public static void registerModBlockEntities() {
        MinecraftVisualProgrammingFabric.LOGGER.info("Registering Mod Block Entities for " + MinecraftVisualProgrammingFabric.MOD_ID);
    }
}