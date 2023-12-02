/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MoCEntityDolphin extends MoCEntityTameableAquatic {

    private static final DataParameter<Boolean> IS_HUNGRY = EntityDataManager.createKey(MoCEntityDolphin.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.createKey(MoCEntityDolphin.class, DataSerializers.BOOLEAN);
    public int gestationtime;

    public MoCEntityDolphin(World world) {
        super(world);
        setSize(1.3F, 0.605F);
        setAdult(true);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.rand.nextInt(100));
        setAge(120);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 30));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 35) {
                setTypeMoC(1);
            } else if (i <= 60) {
                setTypeMoC(2);
            } else if (i <= 85) {
                setTypeMoC(3);
            } else if (i <= 96) {
                setTypeMoC(4);
            } else if (i <= 98) {
                setTypeMoC(5);
            } else {
                setTypeMoC(6);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("dolphin_green.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("dolphin_purple.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("dolphin_black.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("dolphin_pink.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("dolphin_white.png");
            default:
                return MoCreatures.proxy.getModelTexture("dolphin_blue.png");
        }
    }

    @Override
    public int getMaxTemper() {

        switch (getTypeMoC()) {
            case 1:
                return 50;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 100;
        }
    }

    public int getInitialTemper() {
        switch (getTypeMoC()) {
            case 2:
                return 100;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 50;
        }
    }

    @Override
    public double getCustomSpeed() {
        switch (getTypeMoC()) {
            case 2:
                return 2.0D;
            case 3:
                return 2.5D;
            case 4:
                return 3.D;
            case 5:
                return 3.5D;
            case 6:
                return 4.D;
            default:
                return 1.5D;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_HUNGRY, Boolean.FALSE);
        this.dataManager.register(HAS_EATEN, Boolean.FALSE);
    }

    public boolean getIsHungry() {
        return (this.dataManager.get(IS_HUNGRY));
    }

    public void setIsHungry(boolean flag) {
        this.dataManager.set(IS_HUNGRY, flag);
    }

    public boolean getHasEaten() {
        return (this.dataManager.get(HAS_EATEN));
    }

    public void setHasEaten(boolean flag) {
        this.dataManager.set(HAS_EATEN, flag);
    }

    //TODO
    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f < 3.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY) && (getAge() >= 100)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
        }
    }*/

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.world.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getTrueSource();
            if (entity instanceof LivingEntity) {
                LivingEntity entityliving = (LivingEntity) entity;
                if (this.isRidingOrBeingRiddenBy(entity)) {
                    return true;
                }
                if (entity != this && this.getAge() >= 100) {
                    setAttackTarget(entityliving);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    private int Genetics(MoCEntityDolphin entitydolphin, MoCEntityDolphin entitydolphin1) {
        if (entitydolphin.getTypeMoC() == entitydolphin1.getTypeMoC()) {
            return entitydolphin.getTypeMoC();
        }
        int i = entitydolphin.getTypeMoC() + entitydolphin1.getTypeMoC();
        boolean flag = this.rand.nextInt(3) == 0;
        boolean flag1 = this.rand.nextInt(10) == 0;
        if ((i < 5) && flag) {
            return i;
        }
        if (((i == 5) || (i == 6)) && flag1) {
            return i;
        } else {
            return 0;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_DOLPHIN_AMBIENT;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return MoCSoundEvents.ENTITY_DOLPHIN_UPSET;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.DOLPHIN;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == Items.FISH)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 1);
                }

                if ((getHealth() + 15) > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }

                if (!getIsAdult()) {
                    setAge(getAge() + 1);
                }
            }

            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);

            return true;
        }
        if (!stack.isEmpty() && (stack.getItem() == Items.COOKED_FISH) && getIsTamed() && getIsAdult()) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if ((getHealth() + 25) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            setHasEaten(true);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            return true;
        }
        if (!this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                player.getPosY() = this.getPosY();
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {

            //TODO
            /*if (!getIsHungry() && (this.rand.nextInt(100) == 0)) {
                setIsHungry(true);
            }*/
            // fixes growth
            if (!getIsAdult() && (rand.nextInt(50) == 0)) {
                setAge(getAge() + 1);
                if (getAge() >= 150) {
                    setAdult(true);
                }
            }
            //TODO
            if ((!this.isBeingRidden()) && (this.deathTime == 0) && (!getIsTamed() || getIsHungry())) {
                ItemEntity entityitem = getClosestFish(this, 12D);
                if (entityitem != null) {
                    moveToNextEntity(entityitem);
                    ItemEntity entityitem1 = getClosestFish(this, 2D);
                    if ((this.rand.nextInt(20) == 0) && (entityitem1 != null) && (this.deathTime == 0)) {

                        entityitem1.setDead();
                        setTemper(getTemper() + 25);
                        if (getTemper() > getMaxTemper()) {
                            setTemper(getMaxTemper() - 1);
                        }
                        this.setHealth(getMaxHealth());
                    }
                }
            }
            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(8D, 2D, 8D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityDolphin) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(4D, 2D, 4D));
            for (Entity entity1 : list1) {
                if (!(entity1 instanceof MoCEntityDolphin) || (entity1 == this)) {
                    continue;
                }
                MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entitydolphin)) {
                    continue;
                }
                if (this.rand.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
                            new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                MoCEntityDolphin babydolphin = new MoCEntityDolphin(this.world);
                babydolphin.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                if (this.world.addEntity(babydolphin)) {
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    setHasEaten(false);
                    entitydolphin.setHasEaten(false);
                    this.gestationtime = 0;
                    entitydolphin.gestationtime = 0;
                    int l = Genetics(this, entitydolphin);
                    babydolphin.setAge(35);
                    babydolphin.setAdult(false);
                    babydolphin.setOwnerId(this.getOwnerId());
                    babydolphin.setTamed(true);
                    UUID ownerId = this.getOwnerId();
                    PlayerEntity entityplayer = null;
                    if (ownerId != null) {
                        entityplayer = this.world.getPlayerEntityByUUID(this.getOwnerId());
                    }
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, babydolphin);
                    }
                    babydolphin.setTypeInt(l);
                    break;
                }
            }
        }
    }

    public boolean ReadyforParenting(MoCEntityDolphin entitydolphin) {
        LivingEntity passenger = (LivingEntity) this.getControllingPassenger();
        return (entitydolphin.getRidingEntity() == null) && (passenger == null) && entitydolphin.getIsTamed()
                && entitydolphin.getHasEaten() && entitydolphin.getIsAdult();
    }

    @Override
    public void setDead() {
        if (!this.world.isRemote && getIsTamed() && (getHealth() > 0)) {
        } else {
            super.setDead();
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.4D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxAge() {
        return 160;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.8D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public double getMountedYOffset() {
        return this.getAge() * 0.01F * (this.height * 0.3D);
    }

    public float getEyeHeight() {
        return this.height * 0.315F;
    }
}
