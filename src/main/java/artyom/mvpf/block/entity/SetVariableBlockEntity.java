package artyom.mvpf.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SetVariableBlockEntity extends CodeBlockEntity {
    private static final int INVENTORY_SIZE = 27;

    public SetVariableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SET_VARIABLE_BLOCK_ENTITY, pos, state, INVENTORY_SIZE);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.minecraft-visual-programming-fabric.set_variable_block_entity");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }
}