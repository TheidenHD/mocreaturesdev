/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;


public class MoCModelPetScorpion extends MoCModelScorpion {

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        MoCEntityPetScorpion scorpy = (MoCEntityPetScorpion) entity;
        poisoning = scorpy.swingingTail();
        isTalking = scorpy.mouthCounter != 0;
        babies = scorpy.getHasBabies();
        attacking = scorpy.armCounter;
        sitting = scorpy.getIsSitting();
        renderParts(f5);
    }
}
