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
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFrostManticore extends MoCEntityManticore {

    public MoCEntityFrostManticore(World world) {
        super(world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.5D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("manticore_frost.png");
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 25 * 20, 0)); // 25 seconds
        } else {
            openMouth();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FROST_MANTICORE;
    }
}
