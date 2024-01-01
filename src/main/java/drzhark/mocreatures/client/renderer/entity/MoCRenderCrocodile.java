/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.hunter.MoCEntityCrocodile;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.entity.MobEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCrocodile extends RenderLiving<MoCEntityCrocodile> {

    public MoCModelCrocodile croc;

    public MoCRenderCrocodile(MoCModelCrocodile modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
        this.croc = modelbase;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCrocodile entitycrocodile) {
        return entitycrocodile.getTexture();
    }

    @Override
    public void doRender(MoCEntityCrocodile entitycrocodile, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitycrocodile, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(MoCEntityCrocodile entitycrocodile, float f) {
        this.croc.biteProgress = entitycrocodile.biteProgress;
        this.croc.swimming = entitycrocodile.isSwimming();
        this.croc.resting = entitycrocodile.getIsSitting();
        if (entitycrocodile.isSpinning()) {
            spinCroc(entitycrocodile, (MobEntity) entitycrocodile.getRidingEntity());
        }
        stretch(entitycrocodile);
        if (entitycrocodile.getIsSitting()) {
            if (!entitycrocodile.areEyesInFluid(FluidTags.WATER)) {
                adjustHeight(entitycrocodile, 0.2F);
            } else {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        // if(!entitycrocodile.getIsAdult()) { }
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile) {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //matrixStackIn.rotate(180F + f, 0.0F, 0.0F, -1.0F);
        //matrixStackIn.translate(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(MoCEntityCrocodile entitycrocodile, float FHeight) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile entitycrocodile, MobEntity prey) {
        int intSpin = entitycrocodile.spinInt;
        int direction = 1;
        if (intSpin > 40) {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20) {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0) {
            intEndSpin = 1;
        }
        float f3 = (((intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = MathHelper.sqrt(f3);
        if (f3 > 1.0F) {
            f3 = 1.0F;
        }
        f3 *= direction;
        matrixStackIn.rotate(f3 * 90F, 0.0F, 0.0F, 1.0F);

        if (prey != null) {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entitycrocodile) {
        // float f = 1.3F;
        float f = entitycrocodile.getAge() * 0.01F;
        // if(!entitycrocodile.getIsAdult()) { f = entitycrocodile.age; }
        matrixStackIn.scale(f, f, f);
    }
}
