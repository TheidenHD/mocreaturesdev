/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCaveScorpion extends MoCEntityScorpion {

    public MoCEntityCaveScorpion(World world) {
        super(world, 2);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.325D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.5D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(4.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("scorpion_cave.png");
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.NAUSEA, 15 * 20, 0)); // 15 seconds
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 15 * 20, 0));
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && !this.world.canSeeSky(new BlockPos(this)) && (this.getPosY() < 50.0D);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.CAVE_SCORPION;
    }
}
