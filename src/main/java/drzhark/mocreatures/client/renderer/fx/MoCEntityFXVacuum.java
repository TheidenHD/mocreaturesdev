/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVacuum extends Particle {

    private final float portalParticleScale;
    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;

    public MoCEntityFXVacuum(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float red, float green,
                             float blue, int partTexture) {
        super(par1World, par2, par4, par6, par8, par10, par12);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.portalPosX = this.getPosX() = par2;
        this.portalPosY = this.getPosY() = par4;// + 0.7D;
        this.portalPosZ = this.getPosZ() = par6;
        this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
        this.setParticleTextureIndex(partTexture);
        this.particleMaxAge = (int) (Math.random() * 10.0D) + 30;
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
        float var8 = (this.particleAge + partialTicks) / this.particleMaxAge;
        var8 = 1.0F - var8;
        var8 *= var8;
        var8 = 1.0F - var8;
        this.particleScale = this.portalParticleScale * var8;
        super.renderParticle(worldRendererIn, entityIn, partialTicks, par3, par4, par5, par6, par7);
    }

    @Override
    public int getBrightnessForRender(float par1) {
        int var2 = super.getBrightnessForRender(par1);
        float var3 = (float) this.particleAge / (float) this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        int var4 = var2 & 255;
        int var5 = var2 >> 16 & 255;
        var5 += (int) (var3 * 15.0F * 16.0F);

        if (var5 > 240) {
            var5 = 240;
        }

        return var4 | var5 << 16;
    }

    /*@Override
    public float getBrightness(float par1) {
        float var2 = super.getBrightness(par1);
        float var3 = (float) this.particleAge / (float) this.particleMaxAge;
        var3 = var3 * var3 * var3 * var3;
        return var2 * (1.0F - var3) + var3;
    }*/

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();
        float var1 = (float) this.particleAge / (float) this.particleMaxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * 2.0F;
        var1 = 1.0F - var1;
        this.getPosX() = this.portalPosX + this.motionX * var1;
        this.getPosY() = this.portalPosY + this.motionY * var1 + (1.0F - var2);
        this.getPosZ() = this.portalPosZ + this.motionZ * var1;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }
    }
}
