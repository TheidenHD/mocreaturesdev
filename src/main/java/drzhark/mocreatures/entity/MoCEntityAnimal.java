/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class MoCEntityAnimal extends AnimalEntity implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.STRING);
    protected boolean divePending;
    protected boolean jumpPending;
    protected int temper;
    protected boolean isEntityJumping;
    protected boolean riderIsDisconnecting; // used to prevent dupes when a player disconnects on animal from server
    protected String texture;
    protected boolean isTameable;
    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;
    private int huntingCounter;
    private double divingDepth;
    private boolean randomAttributesUpdated; //used to update divingDepth on world load

    protected MoCEntityAnimal(World world) {
        super(world);
        this.riderIsDisconnecting = false;
        this.isTameable = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new PathNavigateSwimmer(this, world);
        this.moveHelper = new EntityAIMoverHelperMoC(this);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        String entityString = EntityList.getEntityString(this);
        if (!MoCreatures.proxy.verboseEntityNames || entityString == null) return super.getName();
        String translationKey = "entity." + entityString + ".verbose.name";
        String translatedString = I18n.format(translationKey);
        return !translatedString.equals(translationKey) ? translatedString : super.getName();
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture(this.texture);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
        selectType();
        return super.onInitialSpawn(difficulty, par1EntityLivingData);
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ADULT, false);
        this.dataManager.register(TYPE, 0);
        this.dataManager.register(AGE, 45);
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public int getTypeMoC() {
        return this.dataManager.get(TYPE);
    }

    @Override
    public void setTypeMoC(int i) {
        this.dataManager.set(TYPE, i);
    }

    public boolean isMale() {
        return (this.getTypeMoC() == 1);
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName() && (getPetName() != null && !getPetName().isEmpty() && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
    }

    @Override
    public boolean getIsAdult() {
        return this.dataManager.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.dataManager.set(ADULT, flag);
    }

    @Override
    public String getPetName() {
        return this.dataManager.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, name);
    }

    @Override
    public int getAge() {
        return this.dataManager.get(AGE);
    }

    @Override
    public void setAge(int i) {
        this.dataManager.set(AGE, i);
        if (getAge() >= getMaxAge()) {
            setAdult(true);
        }
    }

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public UUID getOwnerId() {
        return null;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    /**
     * called in getCanSpawnHere to make sure the right type of creature spawns
     * in the right biome i.e. snakes, rays, bears, BigCats and later wolves,
     * etc.
     */
    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    protected MobEntity getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        MobEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (entitiesToIgnore(entity1)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((MobEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (MobEntity) entity1;
            }
        }

        return entityliving;
    }

    public MobEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        MobEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof MobEntity) || (entity1 == entity) || (entity1 == entity.getRidingEntity()) || (entity1 instanceof PlayerEntity) || (entity1 instanceof MobEntity) || (this.getHeight() <= entity1.getHeight()) || (this.getWidth() <= entity1.getWidth())) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((MobEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (MobEntity) entity1;
            }
        }
        return entityliving;
    }

    public boolean entitiesToIgnore(Entity entity) {
        return !(entity instanceof MobEntity) || entity instanceof MobEntity || entity instanceof MoCEntityKittyBed || entity instanceof MoCEntityLitterBox || this.getIsTamed() && entity instanceof IMoCEntity && ((IMoCEntity) entity).getIsTamed() || entity instanceof EntityWolf && !MoCreatures.proxy.attackWolves || entity instanceof MoCEntityHorse && !MoCreatures.proxy.attackHorses || entity.getWidth() >= this.getWidth() || entity.getHeight() >= this.getHeight() || entity instanceof MoCEntityEgg || entity instanceof IMoCEntity && !MoCreatures.proxy.enableHunters;
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     */
    protected MobEntity getBoogey(double d) {
        MobEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d, 4D, d));
        for (Entity entity : list) {
            if (entitiesToInclude(entity)) {
                entityliving = (MobEntity) entity;
            }
        }
        return entityliving;
    }

    /**
     * Used in getBoogey to specify what kind of entity to look for
     */
    public boolean entitiesToInclude(Entity entity) {
        return ((entity.getClass() != this.getClass()) && (entity instanceof MobEntity) && ((entity.getWidth() >= 0.5D) || (entity.getHeight() >= 0.5D)));
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {  // Server Side
            if (rideableEntity() && this.isBeingRidden()) {
                riding();
            }

            if (isMovementCeased()) {
                this.getNavigator().clearPath();
            }
            if (getAge() == 0) {
                setAge(getMaxAge() - 10); //fixes tiny creatures spawned by error
            }
            if (!getIsAdult() && (this.rand.nextInt(300) == 0) && getAge() <= getMaxAge()) {
                setAge(getAge() + 1);
                if (getAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            if (MoCreatures.proxy.enableHunters && this.isReadyToHunt() && !this.getIsHunting() && this.rand.nextInt(500) == 0) {
                setIsHunting(true);
            }

            if (getIsHunting() && ++this.huntingCounter > 50) {
                setIsHunting(false);
            }

            this.getNavigator().onUpdateNavigation();
        }

        if (this.isInWater() && this.isAmphibian() && (this.rand.nextInt(500) == 0 || !this.randomAttributesUpdated)) {
            this.setNewDivingDepth();
            this.randomAttributesUpdated = true;
        }

        if (this.canRidePlayer() && this.isRiding()) MoCTools.dismountSneakingPlayer(this);
        super.onLivingUpdate();
    }

    public int getMaxAge() {
        return 100;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return isInsideOfMaterial(Material.WATER);
    }

    //used to drop armor, inventory, saddles, etc.
    public void dropMyStuff() {
    }

    /**
     * Used to heal the animal
     */
    protected boolean isMyHealFood(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isInWater() {
        if (isAmphibian()) {
            return this.world.handleMaterialAcceleration(this.getBoundingBox().grow(0.0D, -0.2D, 0.0D), Material.WATER, this);
        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isAmphibian();
    }

    public ItemEntity getClosestItem(Entity entity, double d, Item item, Item item1) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem().getItem() != item) && (entityitem1.getItem().getItem() != item1)) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public ItemEntity getClosestEntityItem(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.getPosX();
        double var8 = k + 0.5D - this.getPosZ();
        double var6 = j + 0.5D - this.getPosY();
        double var14 = MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.rotationPitch = -this.updateRotation2(this.rotationPitch, var13, f);
        this.rotationYaw = this.updateRotation2(this.rotationYaw, var12, f);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation2(float par1, float par2, float par3) {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        if (var4 > par3) {
            var4 = par3;
        }

        if (var4 < -par3) {
            var4 = -par3;
        }

        return par1 + var4;
    }

    public void setPathToEntity(Entity entity, float distance) {
        Path pathEntity = this.getNavigator().getPathToEntityLiving(entity);
        if (pathEntity != null) {
            this.getNavigator().setPath(pathEntity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(1.0D, 0.0D, 1.0D));
            for (Entity entity : list) {
                if (entity.isDead) {
                    continue;
                }
                entity.onCollideWithPlayer(entityplayer);
                if (!(entity instanceof MobEntity)) {
                    continue;
                }
                float f = getDistance(entity);
                if (f < 2.0F && this.rand.nextInt(10) == 0) {
                    attackEntityFrom(DamageSource.causeMobDamage((MobEntity) entity), (float) ((MobEntity) entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                }
            }
            if (entityplayer.isSneaking()) {
                this.makeEntityDive();
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor(entity.getPosX()) - 2;
            int j = MathHelper.floor(entity.getPosZ()) - 2;
            int k = MathHelper.floor(entity.getBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, k, j);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.world.getBlockState(pos.add(l, -1, i1)).isNormalCube() && !this.world.getBlockState(pos.add(l, 0, i1)).isNormalCube() && !this.world.getBlockState(pos.add(l, 1, i1)).isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }
            }
        } else {
            this.navigator.setPath(pathentity, 1D);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        boolean willSpawn;
        boolean debug = MoCreatures.proxy.debug;
        int x = MathHelper.floor(this.getPosX());
        int y = MathHelper.floor(this.getBoundingBox().minY);
        int z = MathHelper.floor(this.getPosZ());
        BlockPos blockpos = new BlockPos(x, y, z);
        BlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        willSpawn = this.world.getLight(blockpos) > 8 && iblockstate.canEntitySpawn(this);
        if (debug && willSpawn)
            MoCreatures.LOGGER.info("Animal: " + this.getName() + " at: " + this.getPosition() + " State: " + this.world.getBlockState(this.getPosition()) + " biome: " + MoCTools.biomeName(world, getPosition()));
        return willSpawn;
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getAge());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getTypeMoC());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setAge(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setTypeMoC(nbttagcompound.getInt("TypeInt"));
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(float strafe, float vertical, float forward) {

        if (this.isBeingRidden()) {
            MobEntity passenger = (MobEntity) this.getControllingPassenger();
            if (passenger != null) this.moveWithRider(strafe, vertical, forward, passenger); //riding movement
            return;
        }
        if ((this.isAmphibian() && isInWater()) || (this.isFlyer() && getIsFlying())) { //amphibian in water movement
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ());
            this.getMotion().getX() *= 0.8999999761581421D;
            this.getMotion().getY() *= 0.8999999761581421D;
            this.getMotion().getZ() *= 0.8999999761581421D;
            if (this.getAttackTarget() == null) {
                this.getMotion().getY() -= 0.005D;
            }
        } else // regular movement
        {
            super.travel(strafe, vertical, forward);
        }

    }

    /**
     * riding Code
     */
    public void moveWithRider(float strafe, float vertical, float forward, MobEntity passenger) {
        if (passenger == null) {
            return;
        }
        if (this.isBeingRidden() && !getIsTamed()) {
            this.moveEntityWithRiderUntamed(strafe, forward, passenger);
            return;
        }
        boolean flySelfPropelled = selfPropelledFlyer() && isOnAir(); //like the black ostrich
        boolean flyingMount = isFlyer() && (this.isBeingRidden()) && getIsTamed() && !this.onGround && isOnAir();
        this.rotationYaw = passenger.rotationYaw;
        this.prevRotationYaw = this.rotationYaw;
        this.rotationPitch = passenger.rotationPitch * 0.5F;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.renderYawOffset = this.rotationYaw;
        this.rotationYawHead = this.renderYawOffset;
        if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
            strafe = (float) (passenger.moveStrafing * 0.5F * this.getCustomSpeed());
            forward = (float) (passenger.moveForward * this.getCustomSpeed());
        }

        if (this.jumpPending && (isFlyer())) {
            this.getMotion().getY() += flyerThrust();
            this.jumpPending = false;

            if (flySelfPropelled) {
                float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
                float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

                this.getMotion().getX() += (-0.5F * velX);
                this.getMotion().getZ() += (0.5F * velZ);
            }
        } else if (this.jumpPending && !getIsJumping()) {
            this.getMotion().getY() = getCustomJump() * 2;
            setIsJumping(true);
            this.jumpPending = false;
        }

        if (this.divePending) {
            this.divePending = false;
            this.getMotion().getY() -= 0.3D;
        }
        if (flyingMount) {
            this.move(MoverType.SELF, this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ());
            this.moveRelative(strafe, vertical, forward, this.flyerFriction() / 10F);
            this.getMotion().getY() *= this.myFallSpeed();
            this.getMotion().getY() -= 0.055D;
            this.getMotion().getZ() *= this.flyerFriction();
            this.getMotion().getX() *= this.flyerFriction();
        } else {
            this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            super.travel(strafe, vertical, forward);

        }
        if (this.onGround) {
            setIsJumping(false);
            this.divePending = false;
            this.jumpPending = false;
        }
    }

    public void moveEntityWithRiderUntamed(float strafe, float forward, MobEntity passenger) {
        if (!this.world.isRemote) {
            if (this.rand.nextInt(10) == 0) {
                this.getMotion().getX() = this.rand.nextGaussian() / 30D;
                this.getMotion().getZ() = this.rand.nextGaussian() / 10D;
            }

            this.move(MoverType.SELF, this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ());

            if (this.rand.nextInt(50) == 0) {
                passenger.dismountRidingEntity();
                this.jump();
            }

            if (this instanceof IMoCTameable && passenger instanceof PlayerEntity) {
                int chance = (this.getMaxTemper() - this.getTemper());
                if (chance <= 0) chance = 1;
                if (this.rand.nextInt(chance * 8) == 0)
                    MoCTools.tameWithName((PlayerEntity) passenger, (IMoCTameable) this);
            }
        }
    }

    /**
     * Maximum flyer height when moving autonomously
     */
    public int maxFlyingHeight() {
        return 5;
    }

    /**
     * Used for flyer mounts, to calculate fall speed
     */
    protected double myFallSpeed() {
        return 0.6D;
    }

    /**
     * flyer mounts Y thrust
     */
    protected double flyerThrust() {
        return 0.3D;
    }

    /**
     * flyer deceleration on Z and X axis
     */
    protected float flyerFriction() {
        return 0.91F;
    }

    /**
     * Alternative flyer mount movement, when true, the player only controls
     * frequency of wing flaps
     */
    protected boolean selfPropelledFlyer() {
        return false;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
        this.jumpPending = true;
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int i) {
        this.temper = i;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    public int getMaxTemper() {
        return 100;
    }

    /**
     * mount speed
     */
    public double getCustomSpeed() {
        return 0.6D;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    protected SoundEvent getAngrySound() {
        return null;
    }

    /**
     * Is this a rideable entity?
     */
    public boolean rideableEntity() {
        return false;
    }

    @Override
    public int nameYOffset() {
        return -80;
    }


    /**
     * fixes bug with entities following a player carrying wheat
     */
    @SuppressWarnings("unused")
    protected Entity findPlayerToAttack() {
        return null;
    }

    @SuppressWarnings("unused")
    public boolean isFlyingAlone() {
        return false;
    }

    /**
     * Used to synchronize animations between server and client
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Used to follow the player carrying the item
     */
    @SuppressWarnings("unused")
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    public boolean isOnAir() {
        return (this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 0.2D), MathHelper.floor(this.getPosZ()))) && this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 1.2D), MathHelper.floor(this.getPosZ()))));
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (!this.world.isRemote) {
            dropMyStuff();
        }

        super.onDeath(damagesource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (isNotScared()) {
            MobEntity tempEntity = this.getAttackTarget();
            boolean flag = super.attackEntityFrom(damagesource, i);
            setAttackTarget(tempEntity);
            return flag;
        }

        return super.attackEntityFrom(damagesource, i);
    }

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    public int getArmorType() {
        return 0;
    }

    @Override
    public void setArmorType(int i) {
    }

    /**
     * Drops armor if the animal has one
     */
    public void dropArmor() {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    @Override
    public boolean canAttackTarget(MobEntity entity) {
        return this.getHeight() >= entity.getHeight() && this.getWidth() >= entity.getWidth();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean isReadyToHunt() {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        if (!this.world.isRemote && !MoCTools.isThisPlayerAnOP(player) && this.getIsTamed() && !player.getUniqueID().equals(this.getOwnerId())) {
            return false;
        }
        return super.canBeLeashedTo(player);
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || this.isBeingRidden();
    }

    public boolean getIsHunting() {
        return this.huntingCounter != 0;
    }

    public void setIsHunting(boolean flag) {
        if (flag) {
            this.huntingCounter = this.rand.nextInt(30) + 1;
        } else {
            this.huntingCounter = 0;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public void onKillEntity(MobEntity entityLivingIn) {
        if (!(entityLivingIn instanceof PlayerEntity)) {
            MoCTools.destroyDrops(this, 3D);
        }
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer() && getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     */
    @Override
    public double getDivingDepth() {
        return (float) this.divingDepth;
    }

    /**
     * Sets diving depth. if setDepth given = 0.0D, will then choose a random value within proper range
     */
    protected void setNewDivingDepth(double setDepth) {
        if (setDepth != 0.0D) {
            if (setDepth > maxDivingDepth()) {
                setDepth = maxDivingDepth();
            }
            if (setDepth < minDivingDepth()) {
                setDepth = minDivingDepth();
            }
            this.divingDepth = setDepth;
        } else {
            this.divingDepth = (float) ((this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth())) + minDivingDepth());
        }
    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.2D;
    }

    protected double maxDivingDepth() {
        return 1.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    /**
     * Returns true if the entity is of the @link{EntityClassification} provided
     *
     * @param type          The EntityClassification type this entity is evaluating
     * @param forSpawnCount If this is being invoked to check spawn count caps.
     * @return If the creature is of the type provided
     */
    @Override
    public boolean isCreatureType(EntityClassification type, boolean forSpawnCount) {
        return type == EntityClassification.CREATURE;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    /***
     * Used to select Animals that can 'ride' the player. Like mice, snakes, turtles, birds
     */
    public boolean canRidePlayer() {
        return false;
    }

    @Override
    public String getClazzString() {
        return EntityList.getEntityString(this);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }

    @Override
    public void setLeashHolder(Entity entityIn, boolean sendAttachNotification) {
        if (this.getIsTamed() && entityIn instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityIn;
            if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null && !entityplayer.getUniqueID().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP((entityplayer))) {
                return;
            }
        }
        super.setLeashHolder(entityIn, sendAttachNotification);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.isRiding() && super.isEntityInsideOpaqueBlock();
    }
}
