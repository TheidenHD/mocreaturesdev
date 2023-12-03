/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityTurtle extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> IS_UPSIDE_DOWN = EntityDataManager.createKey(MoCEntityTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(MoCEntityTurtle.class, DataSerializers.BOOLEAN);
    private boolean isSwinging;
    private boolean twistright;
    private int flopcounter;

    public MoCEntityTurtle(World world) {
        super(world);
        setSize(0.6F, 0.425F);
        setAdult(true);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.rand.nextInt(50));
        setAge(90);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.8D, 50));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_UPSIDE_DOWN, Boolean.FALSE);
        // rideable: 0 nothing, 1 saddle
        this.dataManager.register(IS_HIDING, Boolean.FALSE);
        // rideable: 0 nothing, 1 saddle
    }

    @Override
    public ResourceLocation getTexture() {
        String tempText = "turtle.png";

        if (MoCreatures.proxy.easterEggs) {
            if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
                tempText = "turtle_donatello.png";
            }

            if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
                tempText = "turtle_leonardo.png";
            }

            if (getPetName().equals("raphael") || getPetName().equals("Raphael")) {
                tempText = "turtle_raphael.png";
            }

            if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo")) {
                tempText = "turtle_michelangelo.png";
            }
        }

        return MoCreatures.proxy.getModelTexture(tempText);
    }

    public boolean getIsHiding() {
        return this.dataManager.get(IS_HIDING);
    }

    public void setIsHiding(boolean flag) {
        this.dataManager.set(IS_HIDING, flag);
    }

    public boolean getIsUpsideDown() {
        return this.dataManager.get(IS_UPSIDE_DOWN);
    }

    public void setIsUpsideDown(boolean flag) {
        this.flopcounter = 0;
        this.swingProgress = 0.0F;
        this.dataManager.set(IS_UPSIDE_DOWN, flag);
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity) {
            if (this.getRidingEntity().isSneaking()) {
                return -0.25D + ((300D - this.getAge()) / 500D);
            }
            return (300D - this.getAge()) / 500D;
        }

        return super.getYOffset();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (getIsTamed()) {
            if (getIsUpsideDown()) {
                flipflop(false);
                return true;
            }
            if (this.getRidingEntity() == null) {
                if (this.startRiding(player)) {
                    this.rotationYaw = player.rotationYaw;
                }
            }
            return true;
        }

        flipflop(!getIsUpsideDown());

        return super.processInteract(player, hand);
    }

    @Override
    protected void jump() {
        if (isInsideOfMaterial(Material.WATER)) {
            this.getMotion().getY() = 0.3D;
            if (isSprinting()) {
                float f = this.rotationYaw * 0.01745329F;
                this.getMotion().getX() -= MathHelper.sin(f) * 0.2F;
                this.getMotion().getZ() += MathHelper.cos(f) * 0.2F;
            }
            this.isAirBorne = true;
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (!getIsUpsideDown() && !getIsTamed()) {
                MobEntity entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving)) {
                    if (!getIsHiding() && !isInWater()) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_ANGRY);
                        setIsHiding(true);
                    }

                    this.getNavigator().clearPath();
                } else {

                    setIsHiding(false);
                    if (!hasPath() && this.rand.nextInt(50) == 0) {
                        ItemEntity entityitem = getClosestItem(this, 10D, Items.MELON, Items.REEDS);
                        if (entityitem != null) {
                            float f = entityitem.getDistance(this);
                            if (f > 2.0F) {
                                setPathToEntity(entityitem, f);
                            }
                            if (f < 2.0F && this.deathTime == 0) {
                                entityitem.setDead();
                                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_EATING);
                                PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                                if (entityplayer != null) {
                                    MoCTools.tameWithName(entityplayer, this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (this.getRidingEntity() != null) {
            return false;
        }
        if (entity == null) {
            return super.attackEntityFrom(damagesource, i);
        }
        if (getIsHiding()) {
            if (this.rand.nextInt(10) == 0) {
                flipflop(true);
            }
            return false;
        } else {
            boolean flag = super.attackEntityFrom(damagesource, i);
            if (this.rand.nextInt(3) == 0) {
                flipflop(true);
            }
            return flag;
        }
    }

    public void flipflop(boolean flip) {
        setIsUpsideDown(flip);
        setIsHiding(false);
        this.getNavigator().clearPath();
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return (entity instanceof MoCEntityTurtle) || ((entity.getHeight() <= this.getHeight()) && (entity.getWidth() <= this.getWidth()))
                || super.entitiesToIgnore(entity);
    }

    @Override
    public void tick() {
        super.tick();

        if ((this.getRidingEntity() != null) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            if (entityplayer != null) {
                this.rotationYaw = entityplayer.rotationYaw;
            }
        }
        //to make mega turtles if tamed
        if (getIsTamed() && getAge() < 300 && this.rand.nextInt(900) == 0) {
            setAge(getAge() + 1);
        }
        if (getIsUpsideDown() && isInWater()) {
            setIsUpsideDown(false);
        }
        if (getIsUpsideDown() && (this.getRidingEntity() == null) && this.rand.nextInt(20) == 0) {
            setSwinging(true);
            this.flopcounter++;
        }

        if (getIsSwinging()) {
            this.swingProgress += 0.2F;

            boolean flag = (this.flopcounter > (this.rand.nextInt(3) + 8));

            if (this.swingProgress > 2.0F && (!flag || this.rand.nextInt(20) == 0)) {
                setSwinging(false);
                this.swingProgress = 0.0F;
                if (this.rand.nextInt(2) == 0) {
                    this.twistright = !this.twistright;
                }

            } else if (this.swingProgress > 9.0F) {
                setSwinging(false);
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                setIsUpsideDown(false);
            }
        }
    }

    public boolean getIsSwinging() {
        return this.isSwinging;
    }

    public void setSwinging(boolean flag) {
        this.isSwinging = flag;
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsUpsideDown() || getIsHiding());
    }

    public int getFlipDirection() {
        if (this.twistright) {
            return 1;
        }
        return -1;
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setIsUpsideDown(nbttagcompound.getBoolean("UpsideDown"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("UpsideDown", getIsUpsideDown());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_TURTLE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_TURTLE_DEATH;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.TURTLE;
    }

    // TODO: Remove this after the weapons get reworked
    @Override
    protected Item getDropItem() {
        if (MoCreatures.proxy.easterEggs) {
            if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
                return MoCItems.bo;
            }

            if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
                return MoCItems.katana;
            }

            if (getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")) {
                return MoCItems.sai;
            }

            if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                    || getPetName().equals("michaelangelo")) {
                return MoCItems.nunchaku;
            }
        }

        return null;
    }

    /**
     * Used to avoid rendering the top shell cube
     */
    public boolean isTMNT() {
        return getPetName().equals("Donatello") || getPetName().equals("donatello") || getPetName().equals("Leonardo") || getPetName().equals("leonardo")
                || getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")
                || getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo");
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.REEDS || stack.getItem() == Items.MELON);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public int nameYOffset() {
        return -10 - (getAge() / 5);
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (isInWater()) {
            return 0.08F;
        }
        return 0.12F;
    }

    @Override
    protected double minDivingDepth() {
        return (getAge() + 8D) / 340D;
    }

    @Override
    protected double maxDivingDepth() {
        return (this.getAge() / 100D);
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean canRidePlayer() {
        return true;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.525F;
    }
}
