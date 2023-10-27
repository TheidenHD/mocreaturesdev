package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelSmallFish;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MoCRenderSmallFish extends MoCRenderMoC<MoCEntitySmallFish, MoCModelSmallFish> {

    public MoCRenderSmallFish(EntityRendererManager manager, MoCModelSmallFish entityModelIn) {
        super(manager, entityModelIn, 0.1F);
        this.addLayer(new MoCRenderSmallFish.MoCLayerSmallFish(this));
    }

    @Override
    public void render(MoCEntitySmallFish entitySmallFish, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entitySmallFish.getTypeMoC() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entitySmallFish.selectType();
        }
        super.render(entitySmallFish, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntitySmallFish entity) {
        return entity.getTexture();
    }

    private class MoCLayerSmallFish extends LayerRenderer<MoCEntitySmallFish, MoCModelSmallFish> {
        public MoCLayerSmallFish(IEntityRenderer<MoCEntitySmallFish, MoCModelSmallFish> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntitySmallFish entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderMain(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, entitylivingbaseIn);
        }
    }
}
