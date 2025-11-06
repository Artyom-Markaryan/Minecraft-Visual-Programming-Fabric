package artyom.mvpf.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class RepeatBlockEntity extends CodeBlockEntity {
    private static final int INVENTORY_SIZE = 9;

    public RepeatBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REPEAT_BLOCK_ENTITY, pos, state, INVENTORY_SIZE);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.minecraft-visual-programming-fabric.repeat_block_entity");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        int rows = 1;
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, rows);
    }
}