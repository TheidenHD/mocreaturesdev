/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends InventoryBasic implements ILockableContainer {

    private LockCode lockCode = LockCode.EMPTY_CODE;

    public MoCAnimalChest(String name, int size) {
        super(name, true, size);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, PlayerEntity playerIn) {
        return new ContainerChest(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return "";
    }

    @Override
    public boolean isLocked() {
        return this.lockCode != null && !this.lockCode.isEmpty();
    }

    @Override
    public LockCode getLockCode() {
        return this.lockCode;
    }

    @Override
    public void setLockCode(LockCode code) {
        this.lockCode = code;
    }
}
