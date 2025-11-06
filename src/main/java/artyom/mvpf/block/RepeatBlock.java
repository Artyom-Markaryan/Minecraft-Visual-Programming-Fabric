package artyom.mvpf.block;

import artyom.mvpf.block.entity.RepeatBlockEntity;
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

public class RepeatBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final MapCodec<RepeatBlock> CODEC = RepeatBlock.createCodec(RepeatBlock::new);

    public RepeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RepeatBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RepeatBlockEntity repeatBlockEntity) {
                player.openHandledScreen(repeatBlockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }
}