/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowHerd;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityPiranha extends MoCEntitySmallFish {

    public MoCEntityPiranha(World world) {
        super(world);
        experienceValue = 3;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(4, new EntityAIFollowHerd(this, 0.6D, 4D, 20D, 1));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
    }

    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_piranha.png");
    }

    protected Entity findPlayerToAttack() {
        if ((this.world.getDifficulty().getId() > 0)) {
            PlayerEntity entityplayer = this.world.getClosestPlayerToEntity(this, 12D);
            if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) {
                return entityplayer;
            }
        }
        return null;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.world.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getTrueSource();
            if (entity instanceof EntityLivingBase) {
                if (this.isRidingOrBeingRiddenBy(entity)) {
                    return true;
                }
                if (entity != this) {
                    this.setAttackTarget((EntityLivingBase) entity);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.PIRANHA;
    }
}
