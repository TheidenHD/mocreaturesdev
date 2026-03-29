/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.entity.CreatureAttribute;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtGoal;
import net.minecraft.world.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MoCEntityCrab extends MoCEntityTameableAnimal {

    public MoCEntityCrab(EntityType<? extends MoCEntityCrab> type, Level world) {
        super(type, world);
        //setSize(0.45F, 0.3F);
        setAge(50 + this.rand.nextInt(50));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.goalSelector.addGoal(4, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new LookAtGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.ARMOR, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("crab_blue.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("crab_spotted.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("crab_green.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("crab_russet.png");
            default:
                return MoCreatures.proxy.getModelTexture("crab_red.png");
        }
    }

    @Override
    protected int getExperiencePoints(Player player) {
        return 1 + this.level().rand.nextInt(3);
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.CRAB;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }

    @Override
    protected void collideWithEntity(Entity entity) {
        if (entity instanceof Player && this.getAttackTarget() == null && !(entity.world.getDifficulty() == Difficulty.PEACEFUL)) {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.5F);
        }

        super.collideWithEntity(entity);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        this.playSound(MoCSoundEvents.ENTITY_GENERIC_SMACK, 1.0F, 2.0F);
        return super.attackEntityAsMob(entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getSizeFactor() {
        return 0.7F * getAge() * 0.01F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFleeing() {
        return MoCTools.getMyMovementSpeed(this) > 0.09F;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -20;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() {
        return !this.isMovementCeased();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
