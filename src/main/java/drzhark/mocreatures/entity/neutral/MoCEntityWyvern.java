/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Random;

public class MoCEntityWyvern extends MoCEntityTameableAnimal {

    private static final EntityDataAccessor<Boolean> RIDEABLE = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHESTED = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GHOST = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ARMOR_TYPE = SynchedEntityData.defineId(MoCEntityWyvern.class, EntityDataSerializers.INT);
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    public int mouthCounter;
    public int wingFlapCounter;
    public int diveCounter;
    protected EntityAIWanderMoC2 wander;
    private int transformType;
    private int transformCounter;
    private int tCounter;
    private float fTransparency;


    public MoCEntityWyvern(EntityType<? extends MoCEntityWyvern> type, Level world) {
        super(type, world);
        //setSize(1.45F, 1.55F);
        setAdult(true);
        setTamed(false);
        this.stepHeight = 1.0F;

        // TODO: Make hitboxes adjust depending on size
        /*if (this.random.nextInt(6) == 0) {
            setAge(50 + this.random.nextInt(50));
        } else {
            setAge(80 + this.random.nextInt(20));
        }*/

        setAge(80);
        experienceValue = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, Player.class, 8.0F));
        //this.targetSelector.addGoal(1, new EntityAIHunt<>(this, Animal.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().add(Attributes.FOLLOW_RANGE, 16).add(Attributes.MAX_HEALTH, 80.0D).add(Attributes.ARMOR, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_DAMAGE, 9.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RIDEABLE, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.entityData.define(SITTING, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.entityData.define(CHESTED, Boolean.FALSE);
        this.entityData.define(FLYING, Boolean.FALSE);
        this.entityData.define(GHOST, Boolean.FALSE);
        this.entityData.define(ARMOR_TYPE, 0);// armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
    }

    @Override
    protected int getExperiencePoints(Player player) {
        return experienceValue;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (this.level().getDimensionKey() == MoCreatures.proxy.wyvernDimension) this.enablePersistence();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean getCanSpawnHere(EntityType<MoCEntityAnimal> type, IWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        BlockState iblockstate = world.getBlockState(pos.down());
        return iblockstate.canEntitySpawn(world, pos, type);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return this.level().getDimensionKey() != MoCreatures.proxy.wyvernDimension;
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        // Set persistence when wyvern becomes tamed
        if (tamed) {
            this.setPersistenceRequired();
            if (MoCreatures.proxy.debug) {
                MoCreatures.LOGGER.info("Wyvern tamed - setting persistence required for entity {}", this.getId());
            }
        }
    }

    public boolean getIsFlying() {
        return this.entityData.get(FLYING);
    }

    public void setIsFlying(boolean flag) {
        this.entityData.set(FLYING, flag);
    }

    @Override
    public int getArmorType() {
        return this.entityData.get(ARMOR_TYPE);
    }

    @Override
    public void setArmorType(int i) {
        this.entityData.set(ARMOR_TYPE, i);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.entityData.set(CHESTED, flag);
    }

    @Override
    public boolean getIsSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean flag) {
        this.entityData.set(SITTING, flag);
    }

    public boolean getIsGhost() {
        return this.entityData.get(GHOST);
    }

    public void setIsGhost(boolean flag) {
        this.entityData.set(GHOST, flag);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            if (random.nextInt(5) == 0) {
                setTypeMoC(5);
            } else {
                int i = this.random.nextInt(100);
                if (i <= 12) {
                    setTypeMoC(1);
                } else if (i <= 24) {
                    setTypeMoC(2);
                } else if (i <= 36) {
                    setTypeMoC(3);
                } else if (i <= 48) {
                    setTypeMoC(4);
                } else if (i <= 60) {
                    setTypeMoC(9);
                } else if (i <= 72) {
                    setTypeMoC(10);
                } else if (i <= 84) {
                    setTypeMoC(11);
                } else if (i <= 95) {
                    setTypeMoC(12);
                } else {
                    setTypeMoC(5);
                }
            }
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public double calculateMaxHealth() {
        if (this.getTypeMoC() == 13) {
            return 100.0D;
        }
        return 80.0D;
    }

    public double calculateAttackDmg() {
        if (this.getTypeMoC() == 5) {
            return 12.0D;
        }
        return 10.0D;
    }

    /**
     * 1-4 regular wyverns
     * 5 mother wyvern
     * 6 undead
     * 7 light
     * 8 darkness
     * 9-12 extra wyverns
     */
    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 5) {
            String newText = "wyvern_mother_dark.png";
            if (this.transformType == 6) {
                newText = "wyvern_mother_undead.png";
            }
            if (this.transformType == 7) {
                newText = "wyvern_mother_light.png";
            }
            if (this.transformType == 8) {
                newText = "wyvern_mother_dark.png";
            }

            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
        }

        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("wyvern_jungle.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("wyvern_swamp.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("wyvern_sand.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("wyvern_mother.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("wyvern_mother_undead.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("wyvern_mother_light.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("wyvern_mother_dark.png");
            case 9:
                return MoCreatures.proxy.getModelTexture("wyvern_arctic.png");
            case 10:
                return MoCreatures.proxy.getModelTexture("wyvern_cave.png");
            case 11:
                return MoCreatures.proxy.getModelTexture("wyvern_mountain.png");
            case 12:
                return MoCreatures.proxy.getModelTexture("wyvern_sea.png");
            default:
                return MoCreatures.proxy.getModelTexture("wyvern_sun.png");
        }
    }

    public void transform(int tType) {
        if (!this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), tType));
        }
        this.transformType = tType;
        this.transformCounter = 1;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.getIsFlying() && !this.isPassenger()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());

            this.setMotion(this.getMotion().scale(this.flyerFriction()));
            this.fallDistance = 0.0F;
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.15F; // Slower flying speed to prevent zooming
        }
        return super.getAIMoveSpeed();
    }

    @Override
    public void aiStep() {
        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
        if (this.wingFlapCounter == 5 && !this.level().isClientSide()) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_WYVERN_FLAP.get());
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_CONVERSION.get());
            }
            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    setTypeMoC(this.transformType);
                    selectType();
                }
            }
        }

        if (!this.level().isClientSide()) {
            if (!isMovementCeased() && !this.getIsTamed() && this.random.nextInt(300) == 0) {
                setIsFlying(!getIsFlying());
                if (getIsFlying() && this.onGround()) {
                    this.setMotion(this.getMotion().add(0, 0.4D, 0)); // immediate lift
                }
            }

            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getTarget() != null && (!this.getIsTamed() || this.getRidingEntity() != null) && !isMovementCeased() && this.random.nextInt(20) == 0) {
                setIsFlying(true);
                if (this.onGround()) {
                    this.setMotion(this.getMotion().add(0, 0.4D, 0));
                }
            }

            if (getIsFlying()) {
                // Apply gentle descent
                this.setMotion(this.getMotion().add(0.0D, -0.03D, 0.0D));

                // Clamp downward speed
                if (this.getMotion().getY() < -0.5D) {
                    this.setMotion(this.getMotion().getX(), -0.5D, this.getMotion().getZ());
                }

                // Random wobble if colliding horizontally
                if (this.collidedHorizontally) {
                    this.setMotion(this.getMotion().add(this.random.nextGaussian() * 0.05D, 0.0D, this.random.nextGaussian() * 0.05D));
                }

                // Prevent circling while idle by disabling flying controller
                if (this.getNavigation().noPath() && this.getTarget() == null) {
                    this.setMotion(this.getMotion().add(0.0D, 0.05D, 0.0D)); // Hover up
                    this.setMoveForward(0);
                    this.setNoGravity(true);
                    this.moveController = new MovementController(this); // reset controller to basic when idle
                }

                // Smoothly align rotation with motion
                if (!this.getMotion().equals(Vector3d.ZERO)) {
                    Vector3d motion = this.getMotion();
                    float targetYaw = (float)(Mth.atan2(motion.z, motion.x) * (180F / Math.PI)) - 90F;
                    this.rotationYaw = this.renderYawOffset = this.prevRotationYaw = updateRotation(this.rotationYaw, targetYaw, 4.0F);
                }

                // Flap animation
                if (this.random.nextInt(20) == 0) {
                    wingFlap();
                }

                // Idle floating behavior
                if (this.getNavigation().noPath() && this.getTarget() == null && this.random.nextInt(40) == 0) {
                    double liftAmount = 0.3D + (this.random.nextDouble() * 0.3D); // 0.3 to 0.6
                    this.setMotion(this.getMotion().add(0.0D, liftAmount, 0.0D));
                }

                this.setNoGravity(true);
            } else {
                this.setNoGravity(false);
            }

            if (getIsFlying() && this.getNavigation().noPath() && !isMovementCeased() && this.getTarget() == null && random.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (getIsGhost() && getAge() > 0 && getAge() < 10 && this.random.nextInt(5) == 0) {
                setAge(getAge() + 1);
                if (getAge() == 9) {
                    setAge(140);
                    setAdult(true);
                }
            }
        } else {
            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }
            if (this.diveCounter > 0 && ++this.diveCounter > 5) {
                this.diveCounter = 0;
            }
        }

        super.aiStep();
    }

    public boolean isOnAir() {
        return !this.onGround() && !this.isInWater() && !this.isInLava();
    }

    private float updateRotation(float current, float target, float maxChange) {
        float f = Mth.wrapDegrees(target - current);
        if (f > maxChange) f = maxChange;
        if (f < -maxChange) f = -maxChange;
        return current + f;
    }

    public void wingFlap() {
        if (this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            if (!this.level().isClientSide()) {
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 3));
            }
        }
    }

    @Override
    public float getSizeFactor() {
        return getAge() * 0.01F;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying() && !this.isBeingRidden();
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed()) return 5;
        return 18;
    }

    @Override
    public InteractionResult getEntityInteractionResult(Player player, InteractionHand hand) {
        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            setIsJumping(false);
            getNavigation().stop();
            setTarget(null);
            return true;
        }

        if (!stack.isEmpty() && !getIsRideable() && getAge() > 90 && this.getIsTamed() && (stack.getItem() instanceof ItemSaddle)) {
            if (!player.isCreative()) stack.shrink(1);
            setRideable(true);
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getAge() > 90 && stack.getItem() == Items.IRON_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            }
            dropArmor();
            setArmorType((byte) 1);
            if (!player.isCreative()) stack.shrink(1);

            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getAge() > 90 && stack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            }
            dropArmor();
            setArmorType((byte) 2);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getAge() > 90 && stack.getItem() == Items.DIAMOND_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            }
            dropArmor();
            setArmorType((byte) 3);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getAge() > 90 && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (!player.isCreative()) stack.shrink(1);
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
            return InteractionResult.SUCCESS;
        }

        if (getIsChested() && player.isCrouching()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("WyvernChest", MoCAnimalChest.Size.tiny);
            }
            if (!this.level().isClientSide()) {
                player.openContainer(this.localchest);
            }
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.getIsGhost() && this.getIsTamed() && stack.getItem() == MoCItems.amuletghost) {

            player.setItemInHand(hand, ItemStack.EMPTY);
            if (!this.level().isClientSide()) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                this.dropMyStuff();
                MoCTools.dropAmulet(this, 3, player);
                this.removed = true;
            }

            return InteractionResult.SUCCESS;

        }

        if (!stack.isEmpty() && !this.getIsGhost() && (stack.getItem() == MoCItems.essencelight) && getIsTamed() && getAge() > 90 && getTypeMoC() < 5) {
            if (!player.isCreative()) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.level().isClientSide()) {
                int i = getTypeMoC() + 49;
                MoCEntityEgg entityegg = MoCEntities.EGG.create(this.level());
                entityegg.setEggType(i);
                entityegg.setPos(player.getX(), player.getY(), player.getZ());
                player.world.addFreshEntity(entityegg);
                entityegg.setMotion(entityegg.getMotion().add((this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.3F, this.level().random.nextFloat() * 0.05F, (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.3F));
            }
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getTypeMoC() == 5 && (stack.getItem() == MoCItems.essenceundead) && getIsTamed()) {
            if (!player.isCreative()) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.level().isClientSide()) {
                transform(6);
            }
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getTypeMoC() == 5 && (stack.getItem() == MoCItems.essencelight) && getIsTamed()) {
            if (!player.isCreative()) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.level().isClientSide()) {
                transform(7);
            }
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getTypeMoC() == 5 && (stack.getItem() == MoCItems.essencedarkness) && getIsTamed()) {
            if (!player.isCreative()) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.level().isClientSide()) {
                transform(8);
            }
            return InteractionResult.SUCCESS;
        }

        if (this.getIsRideable() && getAge() > 90 && (!this.getIsChested() || !player.isCrouching()) && !this.isBeingRidden()) {
            if (!this.level().isClientSide() && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return InteractionResult.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    /**
     * Drops the current armor
     */
    @Override
    public void dropArmor() {
        if (!this.level().isClientSide()) {
            int i = getArmorType();
            if (i != 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            }

            if (i == 1) {
                ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(Items.IRON_HORSE_ARMOR, 1));
                entityitem.setDefaultPickupDelay();
                this.level().addFreshEntity(entityitem);
            }
            if (i == 2) {
                ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1));
                entityitem.setDefaultPickupDelay();
                this.level().addFreshEntity(entityitem);
            }
            if (i == 3) {
                ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1));
                entityitem.setDefaultPickupDelay();
                this.level().addFreshEntity(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_WYVERN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_AMBIENT.get();
    }
    
    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(MoCSoundEvents.ENTITY_WYVERN_STEP.get(), 0.4F, (this.random.nextFloat() - this.random.nextFloat()) * 0.4F + 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.WYVERN;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || getIsSitting();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return this.getBbHeight() * 0.85 * getSizeFactor();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.3D);
        double newPosX = this.getX() - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof Player && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof Player && this.random.nextInt(3) == 0) {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 200, 0));
        }

        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
            return false;
        }
        if (super.attackEntityFrom(damagesource, i)) {
            if (entity != null && getIsTamed() && entity instanceof Player) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    /*@Override
    public boolean entitiesToIgnore(Entity entity) {
        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityWyvern) || (entity instanceof Player));
    }*/

    @Override
    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("Chested", getIsChested());
        nbttagcompound.putInt("ArmorType", getArmorType());
        nbttagcompound.putBoolean("isSitting", getIsSitting());
        nbttagcompound.putBoolean("isGhost", getIsGhost());
        if (getIsChested() && this.localchest != null) {
            ListTag nbttaglist = new ListTag();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                this.localstack = this.localchest.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundTag nbttagcompound1 = new CompoundTag();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }
    }

    @Override
    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setArmorType(nbttagcompound.getInt("ArmorType"));
        setSitting(nbttagcompound.getBoolean("isSitting"));
        setIsGhost(nbttagcompound.getBoolean("isGhost"));
        if (getIsChested()) {
            ListTag nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("WyvernChest", MoCAnimalChest.Size.tiny);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundTag nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, ItemStack.of(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public int nameYOffset() {
        int yOff = getAge() * -1;
        if (yOff < -120) {
            yOff = -120;
        }
        if (getIsSitting()) yOff += 25;
        return yOff;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.ratRaw || stack.getItem() == MoCItems.rawTurkey);
    }

    private void openMouth() {
        if (!this.level().isClientSide()) {
            this.mouthCounter = 1;
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 1));
        }

    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //opening mouth
        {
            this.mouthCounter = 1;
        }
        if (animationType == 2) //diving mount
        {
            this.diveCounter = 1;
        }
        if (animationType == 3) {
            this.wingFlapCounter = 1;
        }
        if (animationType > 5 && animationType < 9) //transform 6 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
    }

    @Override
    public void makeEntityDive() {
        if (!this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 2));
        }
        super.makeEntityDive();
    }

    // TODO: Remove this once wyvern eggs are overhauled
    @Override
    public void dropLegacyEgg() {
        int chance = MoCreatures.proxy.wyvernEggDropChance;
        if (getTypeMoC() == 5) { //mother wyverns drop eggs more frequently
            chance = MoCreatures.proxy.motherWyvernEggDropChance;
        }
        if (this.random.nextInt(100) < chance) {
            entityDropItem(new ItemStack(MoCItems.mocegg, 1), 0.0F);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void dropMyStuff() {
        if (!this.level().isClientSide()) {
            dropArmor();
            MoCTools.dropSaddle(this, this.level());

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.level(), new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (getIsSitting()) {
            return 0.4F;
        }
        return 0F;
    }

    @Override
    public double getCustomSpeed() {
        if (this.isBeingRidden()) {
            return 1.0D;
        }
        return 0.8D;
    }

    @Override
    public int getMaxAge() {
        if (this.getTypeMoC() == 5) {
            return 180;
        }
        if (this.getTypeMoC() == 6 || this.getTypeMoC() == 7 || this.getTypeMoC() == 8) {
            return 160;
        }
        return 120;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        if (getTypeMoC() == 6 || getIsGhost()) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    public boolean isReadyToHunt() {
        return !this.isMovementCeased() && !this.isBeingRidden();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityWyvern) && entity.getBdHeight() <= 1D && entity.getBbWidth() <= 1D;
    }

    @Override
    protected double flyerThrust() {
        return 0.6D;
    }

    @Override
    protected float flyerFriction() {
        if (this.getTypeMoC() == 5) {
            return 0.96F;
        }
        if (this.getTypeMoC() == 6 || this.getTypeMoC() == 7 || this.getTypeMoC() == 8 || this.getIsGhost()) {
            return 0.96F;
        }
        return 0.94F;
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && super.shouldAttackPlayers();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (!this.level().isClientSide()) {
            if (this.getTypeMoC() == 6) {
                MoCTools.spawnMaggots(this.level(), this);
            }

            if (!getIsGhost() && getIsTamed() && this.random.nextInt(4) == 0) {
                MoCEntityWyvern entitywyvern = new MoCEntityWyvern(this.level());
                entitywyvern.setPos(this.posX, this.posY, this.posZ);
                this.level().spawnEntity(entitywyvern);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_ENCHANTED.get());

                entitywyvern.setOwnerId(this.getOwnerId());
                entitywyvern.setTamed(true);
                Player Player = this.level().getNearestPlayer(this, 24D);
                if (Player != null) {
                    MoCTools.tameWithName(Player, entitywyvern);
                }

                entitywyvern.setAdult(false);
                entitywyvern.setAge(1);
                entitywyvern.setTypeMoC(this.getTypeMoC());
                entitywyvern.selectType();
                entitywyvern.setIsGhost(true);
            }

        }
        super.onDeath(damagesource);

    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.random.nextFloat() * (0.4F - 0.2F) + 0.15F);
        }

        if (this.getAge() < 10) {
            return 0F;
        }
        return fTransparency;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return this.getIsTamed() && !this.getIsGhost();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getBbHeight() * 0.925F;
    }
}