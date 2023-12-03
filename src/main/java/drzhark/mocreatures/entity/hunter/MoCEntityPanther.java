/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityPanther extends MoCEntityBigCat {

    public MoCEntityPanther(World world) {
        super(world);
        setSize(1.175F, 1.065F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels)
            return MoCreatures.proxy.getModelTexture("big_cat_panther_legacy.png");
        return MoCreatures.proxy.getModelTexture("big_cat_panther.png");
    }

    @Override
    public void selectType() {

        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
        super.selectType();
    }

    @Override
    public boolean isFlyer() {
        return this.getTypeMoC() == 2;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getTypeMoC() == 1 && (stack.getItem() == MoCItems.essencedarkness)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setTypeMoC(2);
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
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

        return MoCLootTables.PANTHER;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && mate.getTypeMoC() == 1) {
            return "Panthard";//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && mate.getTypeMoC() == 1) {
            return "Panthger";//4; //panthger
        }
        if (mate instanceof MoCEntityLion && mate.getTypeMoC() == 2) {
            return "Lither";//5; //lither
        }

        return "Panther";

    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && mate.getTypeMoC() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && mate.getTypeMoC() == 1) {
            return 1;//4; //panthger
        }
        if (mate instanceof MoCEntityLion && mate.getTypeMoC() == 2) {
            return 1;//5; //lither
        }
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getTypeMoC() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getTypeMoC() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getTypeMoC() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getTypeMoC() == 2);
    }

    @Override
    public int getMaxAge() {
        if (getTypeMoC() >= 4) return 110;
        return 100;
    }

    @Override
    public boolean canAttackTarget(MobEntity entity) {
        if (!this.getIsAdult() && (this.getAge() < this.getMaxAge() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityPanther) {
            return false;
        }
        return entity.getHeight() < 1.5F && entity.getWidth() < 1.5F;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.92F;
    }
}
