/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityShark extends MoCEntityTameableAquatic {

    public MoCEntityShark(World world) {
        super(world);
        this.texture = "shark.png";
        setSize(1.65F, 0.9F);
        setAdult(true);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.rand.nextInt(100));
        setAge(160);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 30));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.world.getDifficulty().getId() > 0)) {
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
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.SHARK;
    }

    protected Entity findPlayerToAttack() {
        if ((this.world.getDifficulty().getId() > 0) && (getAge() >= 100)) {
            EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16D);
            if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) {
                return entityplayer;
            }
        }
        return null;
    }

    public LivingEntity FindTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(d));
        for (Entity o : list) {
            if (!(o instanceof LivingEntity) || (o instanceof MoCEntityAquatic) || (o instanceof MoCEntityEgg) || (o instanceof EntityPlayer) || ((o instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)) || ((o instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))) {
                continue;
            } else {
                if ((o instanceof MoCEntityDolphin)) {
                    getIsTamed();
                }
            }
            double d2 = o.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) o).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) o;
            }
        }
        return entityliving;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (!getIsAdult() && (this.rand.nextInt(50) == 0)) {
                setAge(getAge() + 1);
                if (getAge() >= 200) {
                    setAdult(true);
                }
            }
        }
    }

    @Override
    public void setDead() {
        if (!this.world.isRemote && getIsTamed() && (getHealth() > 0)) {
        } else {
            super.setDead();
        }
    }

    public boolean isMyHealFood(Item item1) {
        return false;
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

    public float getEyeHeight() {
        return this.height * 0.61F;
    }
}
