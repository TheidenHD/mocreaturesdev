/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.ai.EntityAITargetNonTamedMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityShark extends MoCEntityTameableAquatic {

    public MoCEntityShark(EntityType<? extends MoCEntityShark> type, Level world) {
        super(type, world);
        this.texture = "shark.png";
        //setSize(1.65F, 0.9F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.random.nextInt(100));
        setMoCAge(160);
        xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D, 30));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new EntityAITargetNonTamedMoC<>(this, Player.class, false));
        // Currently doesn't function
        //this.targetTasks.addTask(3, new EntityAIHuntAquatic<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityTameableAquatic.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.55D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels)
            return MoCreatures.proxy.getModelTexture("shark_legacy.png");
        return MoCreatures.proxy.getModelTexture("shark.png");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public int getExperienceReward() {
        return xpReward;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i) && (this.level().getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getEntity();
            if (entity != null && this.hasIndirectPassenger(entity)) {
                return true;
            }
            if (entity != this && entity instanceof LivingEntity) {
                setTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.SHARK;
    }

    protected Entity findPlayerToAttack() {
        if ((this.level().getDifficulty().getId() > 0) && (getMoCAge() >= 100)) {
            Player Player = this.level().getNearestPlayer(this, 16D);
            if ((Player != null) && Player.isInWater() && !getIsTamed()) {
                return Player;
            }
        }
        return null;
    }

    public LivingEntity FindTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level().getEntities(this, getBoundingBox().inflate(d));
        for (Entity o : list) {
            if (!(o instanceof LivingEntity) || (o instanceof MoCEntityAquatic) || (o instanceof MoCEntityEgg) || (o instanceof Player) || ((o instanceof Wolf) && !(MoCreatures.proxy.attackWolves)) || ((o instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))) {
                continue;
            } else {
                if ((o instanceof MoCEntityDolphin)) {
                    getIsTamed();
                }
            }
            double d2 = o.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) o).hasLineOfSight(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) o;
            }
        }
        return entityliving;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            if (getMoCAge() >= 160) {
                setAdult(true);
            }
            if (!getIsAdult() && (this.random.nextInt(50) == 0)) {
                setMoCAge(getMoCAge() + 1);
            }
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.level().isClientSide() && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.remove(reason);
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.12F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 1D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxAge() {
        return 200;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.61F;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_FLOP.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return MoCSoundEvents.ENTITY_FISH_SWIM.get();
    }
}
