package artyom.mvpf;

import artyom.mvpf.block.ModBlocks;
import artyom.mvpf.block.entity.ModBlockEntities;
import artyom.mvpf.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftVisualProgrammingFabric implements ModInitializer {
	public static final String MOD_ID = "minecraft-visual-programming-fabric";

    /**
     * This logger is used to write text to the console and the log file. <br>
     * It is considered best practice to use your mod id as the logger's name. <br>
     * That way, it's clear which mod wrote info, warnings, and errors.
     */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * This code runs as soon as Minecraft is in a mod-load-ready state. <br>
     * However, some things (like resources) may still be uninitialized. <br>
     * Proceed with mild caution.
     */
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing " + MOD_ID);
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerModBlockEntities();
	}
}