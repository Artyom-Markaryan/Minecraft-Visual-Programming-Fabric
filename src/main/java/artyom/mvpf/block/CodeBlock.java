package artyom.mvpf.block;

import artyom.mvpf.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Set;

public class CodeBlock {
    private static final Set<Item> CORRECT_CONTAINER_PARAMETERS = Set.of(
        ModItems.VARIABLE_ITEM,
        ModItems.VALUE_TEXT_ITEM,
        ModItems.VALUE_NUMBER_ITEM
    );

    public static ItemStack getContainerParameter(ItemStack containerParameter, boolean isFirst) {
        if (isFirst) {
            if (!containerParameter.isOf(ModItems.VARIABLE_ITEM))
                throw new IllegalArgumentException("First container parameter must be a variable item");
            return containerParameter;
        } else if (CORRECT_CONTAINER_PARAMETERS.stream().noneMatch(containerParameter::isOf))
            throw new IllegalArgumentException("Container parameter must be a variable, text or number item");

        return containerParameter;
    }

    public static String getContainerParameterCustomNameString(Text containerParameterCustomName) {
        if (containerParameterCustomName == null)
            throw new IllegalArgumentException("Container parameter must have a custom name");
        return containerParameterCustomName.getString();
    }
}