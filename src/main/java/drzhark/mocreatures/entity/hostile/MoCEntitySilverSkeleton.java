/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntitySilverSkeleton extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(World world) {
        super(world);
        this.texture = "silver_skeleton.png";
        setSize(0.6F, 2.125F);
        experienceValue = 5 + this.world.rand.nextInt(4);
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetgoalSelector.addGoal(1, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        MoCEntityMob.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.ARMOR, 11.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            setSprinting(this.getAttackTarget() != null);
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 10) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 10) {
            this.attackCounterRight = 0;
        }

        super.livingTick();
    }

    @Override
    public void performAnimation(int animationType) {

        if (animationType == 1) //left arm
        {
            this.attackCounterLeft = 1;
        }
        if (animationType == 2) //right arm
        {
            this.attackCounterRight = 1;
        }
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startAttackAnimation() {
        if (!this.world.isRemote) {
            boolean leftArmW = this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounterLeft = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            } else {
                this.attackCounterRight = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startAttackAnimation();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public float getAIMoveSpeed() {
        if (isSprinting()) {
            return 0.35F;
        }
        return 0.2F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.SILVER_SKELETON;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.905F;
    }
}
