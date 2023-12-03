/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityToxicManticore extends MoCEntityManticore {

    public MoCEntityToxicManticore(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(45.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.5D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("manticore_toxic.png");
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            ((LivingEntity) entityIn).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 15 * 20, 0)); // 15 seconds
            ((LivingEntity) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 15 * 20, 0));
        } else {
            openMouth();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.TOXIC_MANTICORE;
    }
}
