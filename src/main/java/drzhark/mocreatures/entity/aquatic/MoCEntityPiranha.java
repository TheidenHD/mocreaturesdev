/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowHerd;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
        this.goalSelector.addGoal(3, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(4, new EntityAIFollowHerd(this, 0.6D, 4D, 20D, 1));
        this.targetgoalSelector.addGoal(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(Attributes.MAX_HEALTH, 5.0D);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_DAMAGE).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.5D);
    }

    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_piranha.png");
    }

    protected Entity findPlayerToAttack() {
        if ((this.world.getDifficulty().getId() > 0)) {
            PlayerEntity entityplayer = this.world.getClosestPlayer(this, 12D);
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
            if (entity instanceof LivingEntity) {
                if (this.isRidingOrBeingRiddenBy(entity)) {
                    return true;
                }
                if (entity != this) {
                    this.setAttackTarget((LivingEntity) entity);
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
