package artyom.mvpf.block;

import artyom.mvpf.block.entity.SetVariableBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
                player.openHandledScreen(setVariableBlockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }
}