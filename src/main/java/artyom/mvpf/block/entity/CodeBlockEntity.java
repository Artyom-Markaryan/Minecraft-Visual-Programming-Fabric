package artyom.mvpf.block.entity;

import artyom.mvpf.MinecraftVisualProgrammingFabric;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ContainerUser;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public abstract class CodeBlockEntity<A extends Enum<A>> extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory;
    private A codeBlockAction;

    public CodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, A codeBlockAction) {
        super(type, pos, state);
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.codeBlockAction = codeBlockAction;
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public void onOpen(ContainerUser user) {
        ImplementedInventory.super.onOpen(user);
        if (world != null) {
            world.playSound(null, pos, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 1.0F, 0.0F);
        }
    }

    public void cycleCodeBlockAction() {
        List<A> codeBlockActions = List.of(codeBlockAction.getDeclaringClass().getEnumConstants());
        int currentIndex = codeBlockAction.ordinal();
        int nextIndex = (currentIndex + 1) % codeBlockActions.size();
        codeBlockAction = codeBlockActions.get(nextIndex);
        markDirty();
    }

    public A getCodeBlockAction() {
        return codeBlockAction;
    }

    @Override
    protected void writeData(WriteView writeView) {
        super.writeData(writeView);
        Inventories.writeData(writeView, inventory);
        writeView.putString("codeBlockAction", codeBlockAction.name());
    }

    @Override
    protected void readData(ReadView readView) {
        super.readData(readView);
        Inventories.readData(readView, inventory);
        try {
            String codeBlockActionName = readView.getString("codeBlockAction", "IllegalArgumentException");
            codeBlockAction = Enum.valueOf(codeBlockAction.getDeclaringClass(), codeBlockActionName);
        } catch (IllegalArgumentException e) {
            MinecraftVisualProgrammingFabric.LOGGER.error(e.getMessage());
            codeBlockAction = List.of(codeBlockAction.getDeclaringClass().getEnumConstants()).getFirst();
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}