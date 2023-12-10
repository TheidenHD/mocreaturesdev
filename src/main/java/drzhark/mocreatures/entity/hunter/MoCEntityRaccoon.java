/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityRaccoon extends MoCEntityTameableAnimal {

    public MoCEntityRaccoon(World world) {
        super(world);
        setSize(0.6F, 0.525F);
        this.texture = "raccoon.png";
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + this.rand.nextInt(15));
        setAge(60);

        setAdult(this.rand.nextInt(3) != 0);
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        //this.targetgoalSelector.addGoal(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_DAMAGE).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && this.isNotScared() && entity instanceof LivingEntity && super.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
                setRevengeTarget((LivingEntity) entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (MoCTools.isItemEdible(stack.getItem()))) //((itemstack.getItem() == MoCItems.rawTurkey.itemID)))
        {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            this.setHealth(getMaxHealth());

            if (!this.world.isRemote && !getIsAdult() && (getAge() < 100)) {
                setAge(getAge() + 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RACCOON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RACCOON_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_RACCOON_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.RACCOON;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 0.85F;
        }
        return 0.85F * getAge() * 0.01F;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public boolean canAttackTarget(MobEntity entity) {
        return !(entity instanceof MoCEntityRaccoon) && super.canAttackTarget(entity);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.86F;
    }
}
