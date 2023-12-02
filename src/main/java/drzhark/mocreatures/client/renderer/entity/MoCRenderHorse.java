/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorse extends MoCRenderMoC<MoCEntityHorse> {

    public MoCRenderHorse(MoCModelHorse modelbase) {
        super(modelbase, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorse entityhorse) {
        return entityhorse.getTexture();
    }

    protected void adjustHeight(MoCEntityHorse entityhorse, float FHeight) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityHorse entityhorse, float f) {
        if (!entityhorse.getIsAdult() || entityhorse.getTypeMoC() > 64) {
            stretch(entityhorse);
        }
        if (entityhorse.getIsGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F));
        }
        super.preRenderCallback(entityhorse, f);
    }

    protected void stretch(MoCEntityHorse entityhorse) {
        float sizeFactor = entityhorse.getAge() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getTypeMoC() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
