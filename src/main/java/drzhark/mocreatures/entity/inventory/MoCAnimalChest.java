/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends Inventory implements INamedContainerProvider {

    private LockCode lockCode = LockCode.EMPTY_CODE;
    private ITextComponent name;

    public MoCAnimalChest(String name, int size) {
        super(size);
        this.name = new StringTextComponent(name);
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerIn) {
        return LockableTileEntity.canUnlock(playerIn, this.lockCode, this.getDisplayName()) ? new ChestContainer(ContainerType.GENERIC_9X2, id, playerInventory, this, 2) : null;
    }

    public void write(CompoundNBT nbttagcompound) {
        this.lockCode.write(nbttagcompound);
    }
    public void read(CompoundNBT nbttagcompound) {
        this.lockCode = LockCode.read(nbttagcompound);
    }

    @Override
    public ITextComponent getDisplayName() {
        return name;
    }
}
