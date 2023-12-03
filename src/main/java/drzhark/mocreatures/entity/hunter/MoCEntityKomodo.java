/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Effects;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntityKomodo extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityKomodo.class, DataSerializers.BOOLEAN);
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;
    private int sitCounter;

    public MoCEntityKomodo(World world) {
        super(world);
        setSize(1.25F, 0.9F);
        this.texture = "komodo_dragon.png";
        setTamed(false);
        setAdult(true);
        this.stepHeight = 1.0F;

        // TODO: Make hitboxes adjust depending on size
        /*if (this.rand.nextInt(6) == 0) {
            setAge(30 + this.rand.nextInt(40));
        } else {
            setAge(90 + this.rand.nextInt(20));
        }*/
        setAge(90);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.1D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.1D, 4D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 0.9D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        //this.targetTasks.addTask(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
        this.targetTasks.addTask(2, new EntityAIHunt<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(RIDEABLE, Boolean.FALSE);
        // rideable: 0 nothing, 1 saddle
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.KOMODO_DRAGON;
    }

    @Override
    public int getTalkInterval() {
        return 500;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.sitCounter > 0 && (this.isBeingRidden() || ++this.sitCounter > 150)) {
            this.sitCounter = 0;
        }
        if (!this.world.isRemote) {
            if (!this.isSwimming() && !this.isBeingRidden() && this.sitCounter == 0 && this.rand.nextInt(500) == 0) { //TODO
                sit();
            }

        } else //animation counters, not needed on server
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 60) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tailCounter = 1;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tongueCounter = 1;
            }

            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.tongueCounter > 0 && ++this.tongueCounter > 20) {
                this.tongueCounter = 0;
            }
        }
    }

    private void openmouth() {
        this.mouthCounter = 1;
    }

    private void sit() {
        this.sitCounter = 1;
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        this.getNavigator().clearPath();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPath();
        }
    }

    @Override
    public float getSizeFactor() {
        if (!getIsAdult()) {
            return getAge() * 0.01F;
        }
        return 1.2F;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (getAge() > 90 || getIsAdult()) && !getIsRideable()
                && (stack.getItem() instanceof ItemSaddle || stack.getItem() == MoCItems.horsesaddle)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            setRideable(true);
            return true;
        }

        if (getIsRideable() && getIsTamed() && getAge() > 90 && (!this.isBeingRidden())) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || (this.isBeingRidden());
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-50);
        }
        return (-50 + (getAge() / 2));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0.15F;
        if (getIsAdult()) {
            return yOff + (this.getHeight());
        }
        return this.getHeight() * ((double) 120 / getAge());
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if ((entity != null && getIsTamed() && entity instanceof PlayerEntity) || !(entity instanceof LivingEntity)) {
                return false;
            }

            if ((this.isBeingRidden()) && (entity == this.getRidingEntity())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.ratRaw || stack.getItem() == MoCItems.rawTurkey);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityKomodo) && super.canAttackTarget(entity);
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean getIsSitting() {
        return this.sitCounter != 0;
    }

    @Override
    public boolean isNotScared() {
        return getAge() > 70;
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 150, 0));
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isBeingRidden();
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }

    public float getEyeHeight() {
        return !this.isMovementCeased() ? this.getHeight() * 0.7F : this.getHeight() * 0.365F;
    }
}
