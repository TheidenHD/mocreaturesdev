/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf extends MobRenderer<MoCEntityWerewolf> {

    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(MoCModelWerehuman modelwerehuman, ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWerewolf) modelbase;
    }

    @Override
    public void render(MoCEntityWerewolf entitywerewolf, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.render(entitywerewolf, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWerewolf entitywerewolf) {
        return entitywerewolf.getTexture();
    }

    private class LayerMoCWereHuman implements LayerRenderer<MoCEntityWerewolf> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWerehuman mocModel = new MoCModelWerehuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            this.mocRenderer = render;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityWerewolf entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
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
