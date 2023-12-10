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

public class MoCEntityFireManticore extends MoCEntityManticore {

    public MoCEntityFireManticore(World world) {
        super(world);
        this.isImmuneToFire = true;
        experienceValue = 10;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.5D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("manticore_fire.png");
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            entityIn.setFire(15);
        } else {
            openMouth();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FIRE_MANTICORE;
    }
}
