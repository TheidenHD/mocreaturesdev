/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXStar extends TexturedParticle {

    public MoCEntityFXStar(ClientWorld world, double posX, double posY, double posZ, float red, float green, float blue) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.8D;
        this.motionY *= 0.8D;
        this.motionZ *= 0.8D;
        this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.maxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.6F; //it was 0.8 for the old star //0.4 if I'm not using the shrinking
    }

    /**
     * sets which texture to use (2 = items.png)
     */
    @Override
    public int getFXLayer() {
        return 1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.particleScale *= 0.995F; //slowly shrinks it

        this.motionY -= 0.03D;
        this.move(this.motionX, this.motionY, this.getMotion().getZ());
        this.motionX *= 0.9D;
        this.motionY *= 0.2D;
        this.motionZ *= 0.9D;

        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }

        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    @Override
    public void setParticleTextureIndex(int par1) {
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(MoCreatures.proxy.getMiscTexture("fx_star.png"));
        Vector3d vector3d = renderInfo.getProjectedView();
        float sizeFactor = 0.1F * this.particleScale;
        float var13 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX) - vector3d.getX());
        float var14 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY) - vector3d.getY());
        float var15 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ) - vector3d.getZ());
        float var16 = 1.2F - ((float) Math.random() * 0.5F);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        worldRendererIn.pos(var13 - par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 - par5 * sizeFactor - par7
                * sizeFactor).tex(0, 1).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 - par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 - par5 * sizeFactor + par7
                * sizeFactor).tex(1, 1).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 + par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 + par5 * sizeFactor + par7
                * sizeFactor).tex(1, 0).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 + par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 + par5 * sizeFactor - par7
                * sizeFactor).tex(0, 0).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
    }
}
