/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAITargetNonTamedMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.SharedMonsterAttributes;
import net.minecraft.world.entity.ai.EntityAIAttackMelee;
import net.minecraft.world.entity.ai.EntityAIHurtByTarget;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityShark extends MoCEntityTameableAquatic {

    public MoCEntityShark(EntityType<? extends MoCEntityShark> type, Level world) {
        super(type, world);
        this.texture = "shark.png";
        setSize(1.65F, 0.9F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.rand.nextInt(100));
        setAge(160);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 30));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAITargetNonTamedMoC<>(this, Player.class, false));
        // Currently doesn't function
        //this.targetTasks.addTask(3, new EntityAIHuntAquatic<>(this, Player.class, false));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.55D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels)
            return MoCreatures.proxy.getModelTexture("shark_legacy.png");
        return MoCreatures.proxy.getModelTexture("shark.png");
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected int getExperiencePoints(Player player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.level().getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && entity instanceof LivingEntity) {
                setAttackTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.SHARK;
    }

    protected Entity findPlayerToAttack() {
        if ((this.level().getDifficulty().getId() > 0) && (getAge() >= 100)) {
            Player Player = this.level().getClosestPlayer(this, 16D);
            if ((Player != null) && Player.isInWater() && !getIsTamed()) {
                return Player;
            }
        }
        return null;
    }

    public LivingEntity FindTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level().getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity o : list) {
            if (!(o instanceof LivingEntity) || (o instanceof MoCEntityAquatic) || (o instanceof MoCEntityEgg) || (o instanceof Player) || ((o instanceof WolfEntity) && !(MoCreatures.proxy.attackWolves)) || ((o instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))) {
                continue;
            } else {
                if ((o instanceof MoCEntityDolphin)) {
                    getIsTamed();
                }
            }
            double d2 = o.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) o).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) o;
            }
        }
        return entityliving;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.level().isRemote) {
            if (getAge() >= 160) {
                setAdult(true);
            }
            if (!getIsAdult() && (this.rand.nextInt(50) == 0)) {
                setAge(getAge() + 1);
            }
        }
    }

    @Override
    public void setDead() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.level().isRemote && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.setDead();
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
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

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.61F;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_FLOP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return MoCSoundEvents.ENTITY_FISH_SWIM;
    }
}
