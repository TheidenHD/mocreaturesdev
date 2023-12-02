package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelFly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoCRenderFly extends MoCRenderInsect<MoCEntityFly, MoCModelFly> {
    public MoCRenderFly(EntityRendererManager manager, MoCModelFly modelbase) {
        super(manager, modelbase);
        this.addLayer(new MoCLayerFly(this));
    }

    private class MoCLayerFly extends LayerRenderer<MoCEntityFly, MoCModelFly> {
        public MoCLayerFly(IEntityRenderer<MoCEntityFly, MoCModelFly> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityFly entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderWings(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
    }
}
