/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends Inventory {

    private LockCode lockCode = LockCode.EMPTY_CODE;

    public MoCAnimalChest(String name, int size) {
        super(size);
    }

    @Override
    public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
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
