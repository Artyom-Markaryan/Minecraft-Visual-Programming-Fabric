package artyom.mvpf.block;

import artyom.mvpf.block.entity.SetVariableBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;

public class SetVariableBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final MapCodec<SetVariableBlock> CODEC = SetVariableBlock.createCodec(SetVariableBlock::new);

    public SetVariableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SetVariableBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SetVariableBlockEntity setVariableBlockEntity) {
                if (!player.isSneaking()) {
                    player.openHandledScreen(setVariableBlockEntity);
                } else {
                    setVariableBlockEntity.cycleCodeBlockAction();
                    player.sendMessage(Text.literal("§6Mode: " + setVariableBlockEntity.getCodeBlockAction()), true);
                    world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 1F, 0F);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify) {
        if (!world.isClient()) {
            if (world.isReceivingRedstonePower(pos)) {
                for (PlayerEntity player : world.getPlayers()) {
                    player.sendMessage(Text.literal("§7[§3INFO§7] §bSet Variable executed"), false);
                }
            }
        }
    }
}