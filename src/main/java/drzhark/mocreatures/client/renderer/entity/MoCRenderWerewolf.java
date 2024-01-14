/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf<M extends EntityModel<MoCEntityWerewolf>> extends MobRenderer<MoCEntityWerewolf, M> {

    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(EntityRendererManager renderManagerIn, MoCModelWerehuman modelwerehuman, M modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWerewolf)modelbase;
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

    private class LayerMoCWereHuman<M extends MoCModelWerehuman<MoCEntityWerewolf>> extends LayerRenderer<MoCEntityWerewolf, M> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWerehuman mocModel = new MoCModelWerehuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            super(render);
            this.mocRenderer = render;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityWerewolf entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            int myType = entity.getTypeMoC();

            ResourceLocation resourcelocation;
            if (!entity.getIsHumanForm()) {
                resourcelocation = MoCreatures.proxy.getModelTexture("wereblank.png");
            } else {
                switch (myType) {

                    case 1:
                        resourcelocation = MoCreatures.proxy.getModelTexture("werehuman_dude.png");
                        break;
                    case 2:
                        resourcelocation = MoCreatures.proxy.getModelTexture("werehuman_classic.png");
                        break;
                    case 4:
                        resourcelocation = MoCreatures.proxy.getModelTexture("werehuman_woman.png");
                        break;
                    default:
                        resourcelocation = MoCreatures.proxy.getModelTexture("werehuman_oldie.png");
                }
            }

            this.mocModel.copyModelAttributesTo(this.mocRenderer.getEntityModel());
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(resourcelocation));
            this.mocModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
