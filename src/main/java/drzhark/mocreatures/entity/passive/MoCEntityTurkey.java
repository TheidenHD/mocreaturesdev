/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import com.google.common.collect.Sets;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMateMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class MoCEntityTurkey extends MoCEntityTameableAnimal {
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

    public MoCEntityTurkey(World world) {
        super(world);
        setSize(0.6F, 0.9F);
        setAdult(true);
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(1, new EntityAIPanic(this, 1.4D));
        this.goalSelector.addGoal(2, new EntityAIMateMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAITempt(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.contains(stack.getItem());
    }

    @Override
    public AgeableEntity createChild(AgeableEntity entity) {
        return new MoCEntityTurkey(entity.world);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 1 && !this.isChild()) {
            return MoCreatures.proxy.getModelTexture("turkey_male.png");
        } else {
            return MoCreatures.proxy.getModelTexture("turkey_female.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_TURKEY_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_TURKEY_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_TURKEY_AMBIENT;
    }

    // TODO: Add unique sound event
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.TURKEY;
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else if (this.isMale() == ((MoCEntityTurkey) otherAnimal).isMale()) {
            return false;
        } else {
            return this.isInLove() && otherAnimal.isInLove();
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty()) {
            if (this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && this.inLove <= 0) {
                this.consumeItemFromStack(player, itemstack);
                this.setInLove(player);

                // Extend mating period for Males
                if (this.getTypeMoC() == 1) {
                    this.inLove = 1800;
                }
                return true;
            }

            if (this.isChild() && this.isBreedingItem(itemstack)) {
                this.consumeItemFromStack(player, itemstack);
                this.ageUp((int) ((float) (-this.getGrowingAge() / 20) * 0.1F), true);
                return true;
            }
        }

        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!getIsTamed() && !stack.isEmpty() && (stack.getItem() == Items.MELON_SEEDS)) {
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.onGround && this.getMotion().getY() < 0.0D) {
            this.getMotion().getY() *= 0.8D;
        }
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }

        if (this.inLove > 0) {
            --this.inLove;

            if (this.inLove % 10 == 0) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.HEART, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
            }
        }
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.PUMPKIN_SEEDS;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 18) {
            for (int i = 0; i < 7; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.HEART, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.945F;
    }
}
