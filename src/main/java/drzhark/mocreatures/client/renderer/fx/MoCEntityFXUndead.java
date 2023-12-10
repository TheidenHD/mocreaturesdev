/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.FMLClientHandler;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXUndead extends Particle {

    public MoCEntityFXUndead(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.getMotion().getX() *= 0.8D;
        this.getMotion().getY() *= 0.8D;
        this.getMotion().getZ() *= 0.8D;
        this.getMotion().getY() = this.rand.nextFloat() * 0.4F + 0.05F;

        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.particleMaxAge = (int) (32.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.8F;
    }

    /**
     * sets which texture to use (2 = items.png)
     */
    @Override
    public int getFXLayer() {
        if (this.onGround) {
            return 1;
        }
        return 2;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();

        this.getMotion().getY() -= 0.03D;
        this.move(this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ());

        this.getMotion().getX() *= 0.8D;
        this.getMotion().getY() *= 0.5D;
        this.getMotion().getZ() *= 0.8D;

        if (this.onGround) {
            this.getMotion().getX() *= 0.7D;
            this.getMotion().getZ() *= 0.7D;
        }

        if (this.particleMaxAge-- <= 0) {
            this.setExpired();
        }
    }

    private String getCurrentTexture() {
        if (this.onGround) {
            return "fx_undead1.png";
        }
        return "fx_undead2.png";
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(MoCreatures.proxy.getMiscTexture(getCurrentTexture()));
        float sizeFactor = 0.1F * this.particleScale;
        float var13 = (float) (this.prevPosX + (this.getPosX() - this.prevPosX) * partialTicks - interpPosX);
        float var14 = (float) (this.prevPosY + (this.getPosY() - this.prevPosY) * partialTicks - interpPosY);
        float var15 = (float) (this.prevPosZ + (this.getPosZ() - this.prevPosZ) * partialTicks - interpPosZ);
        float var16 = 1F;
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        worldRendererIn.pos(var13 - par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 - par5 * sizeFactor - par7
                * sizeFactor).tex(0D, 1D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 - par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 - par5 * sizeFactor + par7
                * sizeFactor).tex(1D, 1D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 + par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 + par5 * sizeFactor + par7
                * sizeFactor).tex(1D, 0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(var13 + par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 + par5 * sizeFactor - par7
                * sizeFactor).tex(0D, 0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
    }
}
