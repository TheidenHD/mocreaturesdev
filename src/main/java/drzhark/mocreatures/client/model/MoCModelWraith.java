/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Port of MoCModelWraith from 1.16.5’s BipedModel to 1.20.1’s HumanoidModel.
 */
@OnlyIn(Dist.CLIENT)
public class MoCModelWraith<T extends MoCEntityWraith> extends HumanoidModel<T> {

    @SuppressWarnings("removal")
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("mocreatures", "wraith"), "main"
    );

    private int attackCounter;
    private boolean isSneaking;

    public MoCModelWraith(ModelPart root) {
        super(root);
    }

    /**
     * Build a LayerDefinition that replaces BipedModel’s old constructor (0,0,64,40).
     * We delegate to HumanoidModel.createMesh, then tell LayerDefinition our texture is 64×40.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8),
                PartPose.ZERO);

        // the original never rendered the hat layer, so make it empty
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 4.0F, -2.0F, 8, 20, 4),
                PartPose.ZERO);

        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 16).addBox(-2.75F, -2.0F, 2.0F, 4, 12, 4),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.25F, -2.0F, 2.0F, 4, 12, 4),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        // legs are left as the vanilla ones from createMesh
        return LayerDefinition.create(mesh, 64, 40);
    }

    /**
     * Called before rendering each frame; replaces the old setLivingAnimations.
     * We pull “isSneaking” and “attackCounter” from the entity here.
     */
    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.isSneaking    = entity.isCrouching();
        this.attackCounter = entity.attackCounter;
    }

    /**
     * Called every frame to set up rotations on head, arms, legs, etc.
     * We call super.setupAnim(...) to apply vanilla walking/head animations to our HumanoidModel parts,
     * then layer on the Wraith’s “attack” arm swing and idle arm oscillation exactly as in 1.16.5.
     */
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // First, apply vanilla biped/humanoid animations (walk, head turn, etc.).
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Copy old BipedModel logic for swinging arms on attack vs. idle:
        float swing = this.attackTime;
        float f6 = Mth.sin(swing * (float) Math.PI);
        float f7 = Mth.sin((1.0F - (1.0F - swing) * (1.0F - swing)) * (float) Math.PI);
        // (Above two lines are vestigial for attackCounter > 0 vs. 0—mirror of original.)

        // Reset Z-rotation on both arms to zero:
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot  = 0.0F;

        // Y-rotation adjustment during the “swingProgress” phase:
        float swingProg = this.attackTime;
        float sinSwing = Mth.sin(swingProg * (float) Math.PI);
        this.rightArm.yRot = -(0.1F - (sinSwing * 0.6F));
        this.leftArm.yRot  =  (0.1F - (sinSwing * 0.6F));

        if (this.attackCounter != 0) {
            // When attackCounter > 0, fists raise and lower based on cos(attackCounter * 0.12)
            float armMov = Mth.cos(this.attackCounter * 0.12F) * 4.0F;
            this.rightArm.xRot = -armMov;
            this.leftArm.xRot  = -armMov;
        } else {
            // Idle “swing” posture: arms start at straight down (−π/2), then add sub-swing
            this.rightArm.xRot = -((float) Math.PI / 2F);
            this.leftArm.xRot  = -((float) Math.PI / 2F);

            // Layer on “swingProgress” bobbing
            float fSwing = sinSwing * 1.2F - f7 * 0.4F;
            this.rightArm.xRot -= fSwing;
            this.leftArm.xRot  -= fSwing;

            // Slight sinusoidal idle motion on arms (ageInTicks * 0.067)
            this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
            this.leftArm.xRot  -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
        }

        // Finally, add a subtle Z-rotation “breathing” motion on arms:
        this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.zRot  -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    }
}