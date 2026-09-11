/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public class MoCEntityPetScorpion extends MoCEntityTameableAnimal {

    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(MoCEntityPetScorpion.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIDEABLE = SynchedEntityData.defineId(MoCEntityPetScorpion.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_BABIES = SynchedEntityData.defineId(MoCEntityPetScorpion.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_SITTING = SynchedEntityData.defineId(MoCEntityPetScorpion.class, EntityDataSerializers.BOOLEAN);
    public int mouthCounter;
    public int armCounter;
    public int transformType;
    private boolean isPoisoning;
    private int poisontimer;
    private int transformCounter;

    public MoCEntityPetScorpion(EntityType<? extends MoCEntityPetScorpion> type, Level world) {
        super(type, world);
        this.poisontimer = 0;
        setAdult(false);
        setAge(20);
        setHasBabies(false);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MoCEntityPetScorpion.AIPetScorpionAttack(this));
        this.goalSelector.addGoal(4, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.goalSelector.addGoal(6, new EntityAIFollowOwnerPlayer(this, 1.0D, 2F, 10F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        //this.targetSelector.addGoal(1, new EntityAIHunt<>(this, Animal.class, true));
    }

    // TODO: Varied stats depending on type
    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        boolean saddle = getIsRideable();

        // Textures based on type for transforming
        if (this.transformCounter != 0 && this.transformType != 0) {
            String newText;
            switch (this.transformType) {
                case 2: // Cave Scorpion
                    newText = saddle ? "scorpion_cave_saddled.png" : "scorpion_cave.png";
                    break;
                case 3: // Fire Scorpion
                    newText = saddle ? "scorpion_fire_saddled.png" : "scorpion_fire.png";
                    break;
                case 4: // Frost Scorpion
                    newText = saddle ? "scorpion_frost_saddled.png" : "scorpion_frost.png";
                    break;
                case 5: // Undead Scorpion
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
                default:
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
            }

            // Textures flashing during transformation
            if (this.transformCounter > 60 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
        }

        switch (getTypeMoC()) {
            case 1:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_dirt.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_dirt_saddled.png");
            case 2:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_cave.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_cave_saddled.png");
            case 3:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_fire.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_fire_saddled.png");
            case 4:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_frost.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_frost_saddled.png");
            case 5:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_undead.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_undead_saddled.png");
            default:
                return MoCreatures.proxy.getModelTexture("scorpion_dirt.png");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLIMBING, Boolean.FALSE);
        this.entityData.define(HAS_BABIES, Boolean.FALSE);
        this.entityData.define(IS_SITTING, Boolean.FALSE);
        this.entityData.define(RIDEABLE, Boolean.FALSE);
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() {
        return !this.isMovementCeased();
    }

    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    public boolean getHasBabies() {
        return getIsAdult() && this.entityData.get(HAS_BABIES);
    }

    public void setHasBabies(boolean flag) {
        this.entityData.set(HAS_BABIES, flag);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    @Override
    public boolean getIsSitting() {
        return this.entityData.get(IS_SITTING);
    }

    public void setSitting(boolean flag) {
        this.entityData.set(IS_SITTING, flag);
    }

    public void setPoisoning(boolean flag) {
        if (flag && !this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 0));
        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) // Tail Animation
        {
            setPoisoning(true);
        } else if (animationType == 1) // Attack Animation (Claws)
        {
            this.armCounter = 1;
            swingArm();
        } else if (animationType == 3) // Mouth Movement Animation
        {
            this.mouthCounter = 1;
        } else if (animationType == 5) // Type Transformation (e.g. Undead)
        {
            this.transformCounter = 1;
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return this.entityData.get(CLIMBING);
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        // Claw Attack Sound
        if (this.poisontimer != 1) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_ATTACK.get());
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    public void aiStep() {

        if (!this.onGround() && (this.isPassenger())) {
            this.rotationYaw = this.getVehicle().rotationYaw;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_STING.get());
            }

            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }

        if (this.transformCounter > 0) {
            // Sound plays after this amount of time has passed during transformation
            if (this.transformCounter == 60) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_CONVERSION.get());
            }

            // Transformation completed
            if (++this.transformCounter > 100) {
                this.transformCounter = 0;

                if (this.transformType != 0) {
                    setTypeMoC(this.transformType);
                }
            }
        }

        super.aiStep();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (!(entity instanceof LivingEntity) || entity instanceof Player && getIsTamed()) {
                return false;
            }
            if (entity != this && super.shouldAttackPlayers() && getIsAdult()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.random.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            if (getTypeMoC() <= 1) // Dirt Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 15 * 20, 1)); // 15 seconds
            } else if (getTypeMoC() == 2) // Cave Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.NAUSEA, 15 * 20, 0)); // 15 seconds
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 15 * 20, 0));
            } else if (getTypeMoC() == 3) // Fire Scorpion
            {
                entityIn.setFire(15);
            } else if (getTypeMoC() == 4) // Frost Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 25 * 20, 0)); // 25 seconds
            } else if (getTypeMoC() == 5) // Undead Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 15 * 20, 0)); // 15 seconds
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WITHER, 15 * 20, 0));
            }
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    public void swingArm() {
        if (!this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 1));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SCORPION_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SCORPION_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Mouth Movement Animation
        if (!this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 3));
        }
        return MoCSoundEvents.ENTITY_SCORPION_AMBIENT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.5F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        switch (getTypeMoC()) {
            case 1:
                return MoCLootTables.DIRT_SCORPION;
            case 2:
                return MoCLootTables.CAVE_SCORPION;
            case 3:
                return MoCLootTables.FIRE_SCORPION;
            case 4:
                return MoCLootTables.FROST_SCORPION;
            case 5:
                return MoCLootTables.UNDEAD_SCORPION;
            default:
                return MoCLootTables.DIRT_SCORPION;
        }
    }

    // TODO: Make it not give items in creative
    @Override
    public InteractionResult getEntityInteractionResult(Player player, InteractionHand hand) {
        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && getIsAdult() && !getIsRideable()
                && (stack.getItem() instanceof ItemSaddle)) {
            if (!player.isCreative()) stack.shrink(1);
            setRideable(true);
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            setIsJumping(false);
            getNavigation().stop();
            setTarget(null);
            return true;
        }

        // Transformations
        if (!stack.isEmpty() && this.getIsTamed() && !this.isBeingRidden() && !this.isRiding() && this.transformCounter < 1) {
            // Cave Scorpion (Essence of Darkness)
            if (stack.getItem() == MoCItems.essencedarkness && this.getType() != 2) {
                if (!player.isCreative()) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(2);
                return true;
            }

            // Fire Scorpion (Essence of Fire)
            if (stack.getItem() == MoCItems.essencefire && this.getTypeMoC() != 3) {
                if (!player.isCreative()) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(3);
                return InteractionResult.SUCCESS;
            }

            // Frost Scorpion (Essence of Ice)
            if (stack.getItem() == MoCItems.essenceIce && this.getType() != 4) {
                if (!player.isCreative()) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(4);
                return true;
            }

            // Undead Scorpion (Essence of Undead)
            if (!stack.isEmpty() && this.getIsTamed() && !this.isBeingRidden() && !this.isPassenger() && this.transformCounter < 1 && stack.getItem() == MoCItems.essenceundead && this.getTypeMoC() != 5) {
                if (!player.isCreative()) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(5);
                return InteractionResult.SUCCESS;
            }
        }

        if (this.getVehicle() == null && this.getAge() < 60 && !getIsAdult()) {
            if (this.startRidingPlayer(player)) {
                this.rotationYaw = player.rotationYaw;
                if (!this.level().isClientSide() && !getIsTamed()) {
                    MoCTools.tameWithName(player, this);
                }
            }

            return InteractionResult.SUCCESS;
        } else if (this.isPassenger()) {
            MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
            this.dismount();
            this.setMotion(player.getMotion().getX() * 5D, (player.getMotion().getY() / 2D) + 0.5D, player.getMotion().getZ() * 5D);
            return InteractionResult.SUCCESS;
        }

        if (getIsRideable() && getIsTamed() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            if (!this.level().isClientSide()) {
                player.startRiding(this);
            }

            return InteractionResult.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Override
    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
        setRideable(nbttagcompound.getBoolean("Saddled"));
    }

    @Override
    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Babies", getHasBabies());
        nbttagcompound.putBoolean("Saddled", getIsRideable());
    }

    @Override
    public int nameYOffset() {
        int n = (int) (1 - (getAge() * 0.8));
        if (n < -60) {
            n = -60;
        }
        if (getIsAdult()) {
            n = -60;
        }
        if (getIsSitting()) {
            n = (int) (n * 0.8);
        }
        return n;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() { return !this.isMovementCeased(); }

    // TODO: Overhaul acceptable food
    @Override
    protected boolean isMyHealFood(ItemStack itemstack) {
        return (itemstack.getItem() == MoCItems.ratRaw || itemstack.getItem() == MoCItems.ratCooked);
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || getIsSitting();
    }

    @Override
    public void dropMyStuff() {
        MoCTools.dropSaddle(this, this.level());
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public double getMountedYOffset() {
        return (this.getBbHeight() * 0.75D) - 0.15D;
    }

    @Override
    public double getYOffset() {
        if (this.getVehicle() instanceof Player && this.getVehicle() == MoCreatures.proxy.getPlayer() && this.level().isClientSide()) {
            return 0.1F;
        }

        if ((this.getVehicle() instanceof Player) && this.level().isClientSide()) {
            return (super.getYOffset() + 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.2D);
        double newPosX = this.getX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    public void transform(int tType) {
        if (!this.level().isClientSide()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), tType));
        }

        // Set what type to transform to based on type integer
        this.transformType = tType;
        if (this.transformType != 0) {
            this.transformCounter = 1;
        }
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityFox) && entity.getBdHeight() <= 1D && entity.getBbWidth() <= 1D;
    }

    static class AIPetScorpionAttack extends MeleeAttackGoal {
        public AIPetScorpionAttack(MoCEntityPetScorpion scorpion) {
            super(scorpion, 1.0D, true);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
        }
    }
}
