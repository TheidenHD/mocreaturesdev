/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf extends RenderLiving<MoCEntityWerewolf> {

    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(MoCModelWerehuman modelwerehuman, ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWerewolf) modelbase;
    }

    @Override
    public void doRender(MoCEntityWerewolf entitywerewolf, double d, double d1, double d2, float f, float f1) {
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRender(entitywerewolf, d, d1, d2, f, f1);

    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWerewolf entitywerewolf) {
        return entitywerewolf.getTexture();
    }

    private class LayerMoCWereHuman implements LayerRenderer<MoCEntityWerewolf> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWerehuman mocModel = new MoCModelWerehuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityWerewolf entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            int myType = entity.getTypeMoC();

            if (!entity.getIsHumanForm()) {
                bindTexture(MoCreatures.proxy.getModelTexture("wereblank.png"));
            } else {
                switch (myType) {

                    case 1:
                        bindTexture(MoCreatures.proxy.getModelTexture("werehuman_dude.png"));
                        break;
                    case 2:
                        bindTexture(MoCreatures.proxy.getModelTexture("werehuman_classic.png"));
                        break;
                    case 4:
                        bindTexture(MoCreatures.proxy.getModelTexture("werehuman_woman.png"));
                        break;
                    default:
                        bindTexture(MoCreatures.proxy.getModelTexture("werehuman_oldie.png"));
                }
            }

            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entity, f, f1, f2);
            this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
