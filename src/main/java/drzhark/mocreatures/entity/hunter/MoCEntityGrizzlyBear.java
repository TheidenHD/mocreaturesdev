/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityGrizzlyBear extends MoCEntityBear {

    public MoCEntityGrizzlyBear(World world) {
        super(world);
        setSize(1.125F, 1.57F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("bear_grizzly.png");
    }

    @Override
    public float getBearSize() {
        return 1.2F;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    public double getAttackRange() {
        int factor = 1;
        if (this.world.getDifficulty().getId() > 1) {
            factor = 2;
        }
        return 6D * factor;
    }

    /*public int getAttackStrength() {
        int factor = (this.world.getDifficulty().getId());
        return 3 * factor;
    }*/

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.4F) && super.shouldAttackPlayers();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && this.getAge() < 80 && MoCTools.isItemEdibleforCarnivores(stack.getItem())) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            if (!getIsTamed() && !this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getAge() < 100)) {
                setAge(getAge() + 1);
            }
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            if (getBearState() == 0) {
                setBearState(2);
            } else {
                setBearState(0);
            }
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setBearState(0);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.GRIZZLY_BEAR;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "GrizzlyBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityGrizzlyBear;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.76F;
    }
}
