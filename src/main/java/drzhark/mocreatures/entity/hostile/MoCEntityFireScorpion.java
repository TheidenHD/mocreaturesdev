/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFireScorpion extends MoCEntityScorpion {

    public MoCEntityFireScorpion(World world) {
        super(world, 3);
        this.isImmuneToFire = true;
        experienceValue = 7;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(5.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("scorpion_fire.png");
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            entityIn.setFire(15);
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.FIRE_SCORPION;
    }
}
