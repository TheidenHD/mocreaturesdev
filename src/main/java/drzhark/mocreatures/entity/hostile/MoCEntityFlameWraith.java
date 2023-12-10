/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFlameWraith extends MoCEntityWraith implements IMob {

    protected int burningTime;

    public MoCEntityFlameWraith(World world) {
        super(world);
        this.texture = MoCreatures.proxy.alphaWraithEyes ? "wraith_flame_alpha.png" : "wraith_flame.png";
        this.isImmuneToFire = true;
        this.burningTime = 30;
        experienceValue = 7;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FLAME_WRAITH;
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            if (this.world.isDaytime()) {
                float f = getBrightness();
                if ((f > 0.5F) && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ()))) && ((this.rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F))) {
                    this.setHealth(getHealth() - 2);
                }
            }
        } else {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.FLAME, this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(), this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 0.0D, 0.0D, 0.0D);
            }
        }
        super.livingTick();
    }

    //TODO TEST
    /*@Override
    public float getMoveSpeed() {
        return 1.1F;
    }*/

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!this.world.isRemote && !this.world.provider.doesWaterVaporize()) {
            entityLivingBaseIn.setFire(this.burningTime);
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }
}
