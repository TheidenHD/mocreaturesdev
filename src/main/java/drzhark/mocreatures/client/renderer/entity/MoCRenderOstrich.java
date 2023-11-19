/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC<MoCEntityOstrich> {

    public MoCRenderOstrich(ModelBase modelbase, float f) {
        super(modelbase, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityOstrich entityostrich) {
        return entityostrich.getTexture();
    }

    protected void adjustHeight(MoCEntityOstrich entityliving, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityOstrich entityliving, float f) {
        MoCEntityOstrich entityostrich = entityliving;
        if (entityostrich.getType() == 1) {
            stretch(entityostrich);
        }

        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityOstrich entityostrich) {

        float f = entityostrich.getAge() * 0.01F;
        GlStateManager.scale(f, f, f);
    }
}
