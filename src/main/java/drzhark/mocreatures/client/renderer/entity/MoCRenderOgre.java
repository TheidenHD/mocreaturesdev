package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelOgre;
import drzhark.mocreatures.entity.hostile.MoCEntityOgre;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MoCRenderOgre extends MoCRenderMoC<MoCEntityOgre, MoCModelOgre> {

    public MoCRenderOgre(EntityRendererManager manager, MoCModelOgre entityModelIn) {
        super(manager, entityModelIn, 0.6F);
        this.addLayer(new MoCRenderOgre.MoCLayerOgre(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityOgre entity) {
        return entity.getTexture();
    }

    private class MoCLayerOgre extends LayerRenderer<MoCEntityOgre, MoCModelOgre> {
        public MoCLayerOgre(IEntityRenderer<MoCEntityOgre, MoCModelOgre> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityOgre entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderHead(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
    }
}
