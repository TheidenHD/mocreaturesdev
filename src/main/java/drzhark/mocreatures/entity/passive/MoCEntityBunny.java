/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MoCEntityBunny extends MoCEntityTameableAnimal {

    private static final EntityDataAccessor<Boolean> HAS_EATEN = SynchedEntityData.defineId(MoCEntityBunny.class, EntityDataSerializers.BOOLEAN);
    public int bunnyReproduceTickerA;
    public int bunnyReproduceTickerB;
    private int jumpTimer;

    public MoCEntityBunny(EntityType<? extends MoCEntityBunny> type, Level world) {
        super(type, world);
        setAdult(true);
        setTamed(false);
        setAge(50 + getRNG().nextInt(15));
        if (getRNG().nextInt(4) == 0) {
            setAdult(false);
        }
        this.bunnyReproduceTickerA = getRNG().nextInt(64);
        this.bunnyReproduceTickerB = 0;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIBunnyReproduce(this));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 0.8D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, Player.class, 6.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityTameableAnimal.createAttributes().add(Attributes.FOLLOW_RANGE, 12.0D).add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.ARMOR, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.entityData.define(HAS_EATEN, false);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entityLivingData) {
        if (this.level().provider.getDimension() == MoCreatures.proxy.wyvernDimension) this.enablePersistence();
        return super.onInitialSpawn(difficulty, entityLivingData);
    }

    public boolean getHasEaten() {
        return this.entityData.get(HAS_EATEN);
    }

    public void setHasEaten(boolean flag) {
        this.entityData.set(HAS_EATEN, flag);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(getRNG().nextInt(5) + 1);
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = Mth.floor(this.getX());
        int j = Mth.floor(getBoundingBox().minY);
        int k = Mth.floor(this.getZ());
        BlockPos pos = new BlockPos(i, j, k);

        try {
            // In 1.20.1, check biome directly
            String biomeName = this.level().getBiome(pos).unwrapKey().orElseThrow().location().getPath();
            if (biomeName.contains("snow")) {
                setTypeMoC(3); //snow-white bunnies!
                return true;
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_beige.png") : MoCreatures.proxy.getModelTexture("bunny_beige_detailed.png");
            case 3:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_white.png") : MoCreatures.proxy.getModelTexture("bunny_white_detailed.png");
            case 4:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_black.png") : MoCreatures.proxy.getModelTexture("bunny_black_detailed.png");
            case 5:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_spotted.png") : MoCreatures.proxy.getModelTexture("bunny_spotted_detailed.png");
            default:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_golden.png") : MoCreatures.proxy.getModelTexture("bunny_golden_detailed.png");
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BUNNY_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_BUNNY_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.BUNNY;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty()) {
            if (stack.getItem() == Items.CARROT && !getHasEaten()) {
                if (!player.isCreative()) stack.shrink(1);
                setHasEaten(true);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT.get());
                if (!getIsTamed() && !this.level().isClientSide()) {
                    MoCTools.tameWithName(player, this);
                }
                return true;
            }
        } else if (getRidingEntity() == null) {
            if (startRidingPlayer(player)) {
                this.rotationYaw = player.rotationYaw;
            }
            return true;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getVehicle() != null) {
            this.setYRot(this.getVehicle().getYRot());
        }

        if (!this.level().isClientSide()) {
            if (--this.jumpTimer <= 0 && this.onGround() && ((this.motionX > 0.05D) || (this.motionZ > 0.05D) || (this.motionX < -0.05D) || (this.motionZ < -0.05D))) {
                this.motionY = 0.3D;
                this.jumpTimer = 15;
            }
        }
    }

    @Override
    public int nameYOffset() {
        return -40;
    }
    
    @Override
    public boolean isReadyToFollowOwnerPlayer() { return !this.isMovementCeased(); }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return (stack.getItem() == Items.CARROT);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (this.isInvulnerableTo(damagesource)) {
            return false;
        }
        
        // Add protection for bunny-hats (bunnies that are riding)
        if (this.getVehicle() != null) {
            return false;
        }
        
        return super.hurt(damagesource, i);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof Player) {
            return this.getVehicle().isCrouching() ? 0.25 : 0.5F;
        }

        return super.getMyRidingOffset();
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }

    @Override
    public boolean canRidePlayer() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.675F;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() {
        return !this.isMovementCeased();
    }
}
