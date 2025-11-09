package artyom.mvpf.block;

import artyom.mvpf.MinecraftVisualProgrammingFabric;
import artyom.mvpf.block.entity.SetVariableBlockEntity;
import artyom.mvpf.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetVariableBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final MapCodec<SetVariableBlock> CODEC = SetVariableBlock.createCodec(SetVariableBlock::new);
    public static final Map<String, String> VARIABLE_DICTIONARY = new HashMap<>();
    private static final Map<Item, String> VARIABLE_TYPES = Map.of(
        ModItems.VALUE_TEXT_ITEM, "[TEXT]",
        ModItems.VALUE_NUMBER_ITEM, "[NUMBER]"
    );

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
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (world.isReceivingRedstonePower(pos) && blockEntity instanceof SetVariableBlockEntity setVariableBlockEntity) {
                try {
                    DefaultedList<ItemStack> containerParameters = setVariableBlockEntity.getItems();

                    ItemStack variableItem = CodeBlock.getContainerParameter(containerParameters.getFirst(), true);
                    String variableName = CodeBlock.getContainerParameterCustomNameString(variableItem.getCustomName());

                    SetVariableBlockEntity.CodeBlockActions codeBlockAction = setVariableBlockEntity.getCodeBlockAction();
                    if (codeBlockAction.equals(SetVariableBlockEntity.CodeBlockActions.SET))
                        setVariable(containerParameters, variableName);
                    else if (codeBlockAction.equals(SetVariableBlockEntity.CodeBlockActions.ADD_NUMBERS))
                        addNumbers(containerParameters, variableName);
                } catch (IllegalArgumentException e) {
                    MinecraftVisualProgrammingFabric.LOGGER.error("[{}] {}", e.getClass(), e.getMessage());
                }
            }
        }
    }

    private void setVariable(DefaultedList<ItemStack> containerParameters, String variableName) {
        int second = 1; // Second index
        ItemStack valueItem = CodeBlock.getContainerParameter(containerParameters.get(second), false);
        String variableValue = CodeBlock.getContainerParameterCustomNameString(valueItem.getCustomName());
        if (valueItem.isOf(ModItems.VARIABLE_ITEM)) {
            String existingVariableValue = VARIABLE_DICTIONARY.get(variableValue);
            VARIABLE_DICTIONARY.put(variableName, existingVariableValue);
        } else {
            String variableType = VARIABLE_TYPES.get(valueItem.getItem());
            VARIABLE_DICTIONARY.put(variableName, variableValue + " " + variableType);
        }
    }

    private void addNumbers(DefaultedList<ItemStack> containerParameters, String variableName) {
        List<Integer> numbersToAdd = new ArrayList<>();
        for (int i = 1; i < containerParameters.size(); i++) {
            if (containerParameters.get(i).isEmpty()) break;
            ItemStack valueItem = CodeBlock.getContainerParameter(containerParameters.get(i), false);
            String variableValue = CodeBlock.getContainerParameterCustomNameString(valueItem.getCustomName());
            if (valueItem.isOf(ModItems.VARIABLE_ITEM)) {
                String existingVariableValue = VARIABLE_DICTIONARY.get(variableValue);
                existingVariableValue = existingVariableValue.substring(0, existingVariableValue.lastIndexOf("[")).trim();
                variableValue = existingVariableValue;
            }
            numbersToAdd.add(Integer.parseInt(variableValue));
        }
        int sum = 0;
        for (int number : numbersToAdd) sum += number;
        VARIABLE_DICTIONARY.put(variableName, sum + " [NUMBER]");
    }
}