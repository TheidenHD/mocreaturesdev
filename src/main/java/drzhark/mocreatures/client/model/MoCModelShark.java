/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelShark<T extends Entity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer torso_1;
    private final ModelRenderer neck;
    private final ModelRenderer head;
    private final ModelRenderer upper_head;
    private final ModelRenderer jaw_base_r1;
    private final ModelRenderer teeth_row_left_r1;
    private final ModelRenderer teeth_row_front_r1;
    private final ModelRenderer upper_skull_r1;
    private final ModelRenderer nose_bot_r1;
    private final ModelRenderer lower_jaw;
    private final ModelRenderer teeth_row_front_r2;
    private final ModelRenderer dorsal_fin;
    private final ModelRenderer dorsal_fin_d_r1;
    private final ModelRenderer dorsal_fin_c_r1;
    private final ModelRenderer dorsal_fin_b_r1;
    private final ModelRenderer dorsal_fin_a_r1;
    private final ModelRenderer pectoral_fins;
    private final ModelRenderer left_fin;
    private final ModelRenderer left_fin_d_r1;
    private final ModelRenderer left_fin_a_r1;
    private final ModelRenderer left_fin_a_r2;
    private final ModelRenderer right_fin;
    private final ModelRenderer right_fin_a_r1;
    private final ModelRenderer right_fin_a_r2;
    private final ModelRenderer right_fin_d_r1;
    private final ModelRenderer torso_2;
    private final ModelRenderer pelvic_fin_right_r1;
    private final ModelRenderer pelvic_fin_left_r1;
    private final ModelRenderer tail;
    private final ModelRenderer caudal_fin;
    private final ModelRenderer caudal_fin_bot_c_r1;
    private final ModelRenderer caudal_fin_bot_b_r1;
    private final ModelRenderer caudal_fin_bot_a_r1;
    private final ModelRenderer caudal_fin_top_d_r1;
    private final ModelRenderer caudal_fin_top_c_r1;
    private final ModelRenderer caudal_fin_top_b_r1;
    private final ModelRenderer caudal_fin_top_a_r1;

    public MoCModelShark() {
        textureWidth = 256;
        textureHeight = 256;

        body = new ModelRenderer(this);
        body.setRotationPoint(-0.5F, 16.0F, -3.0F);


        torso_1 = new ModelRenderer(this);
        torso_1.setRotationPoint(0.0F, -4.0F, 16.0F);
        body.addChild(torso_1);
        torso_1.cubeList.add(new ModelBox(torso_1, 0, 0, -10.5F, -9.0F, -30.0F, 22, 20, 33, 0.0F, false));

        neck = new ModelRenderer(this);
        neck.setRotationPoint(-0.5F, 2.0F, -30.0F);
        torso_1.addChild(neck);
        neck.cubeList.add(new ModelBox(neck, 56, 73, -9.0F, -10.0F, -14.0F, 20, 18, 15, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.5F, 0.0F, -14.0F);
        neck.addChild(head);


        upper_head = new ModelRenderer(this);
        upper_head.setRotationPoint(1.15F, -5.5F, 30.5F);
        head.addChild(upper_head);
        upper_head.cubeList.add(new ModelBox(upper_head, 89, 24, 7.451F, 4.5311F, -43.1503F, 0, 4, 4, 0.0F, true));
        upper_head.cubeList.add(new ModelBox(upper_head, 89, 24, -4.749F, 4.5311F, -43.1503F, 0, 4, 4, 0.0F, true));

        jaw_base_r1 = new ModelRenderer(this);
        jaw_base_r1.setRotationPoint(1.351F, 5.5014F, -37.2182F);
        upper_head.addChild(jaw_base_r1);
        setRotation(jaw_base_r1, 1.5708F, 0.0F, 0.0F);
        jaw_base_r1.cubeList.add(new ModelBox(jaw_base_r1, 160, 4, -7.001F, -1.9315F, -4.8207F, 14, 9, 8, 0.0F, false));
        jaw_base_r1.cubeList.add(new ModelBox(jaw_base_r1, 0, 0, -5.9794F, -10.9315F, 2.1793F, 12, 20, 3, 0.0F, false));

        teeth_row_left_r1 = new ModelRenderer(this);
        teeth_row_left_r1.setRotationPoint(2.351F, 7.5014F, -41.2182F);
        upper_head.addChild(teeth_row_left_r1);
        setRotation(teeth_row_left_r1, 0.0F, 0.0F, 0.0F);
        teeth_row_left_r1.cubeList.add(new ModelBox(teeth_row_left_r1, 8, 92, 5.0F, -3.5704F, -5.5322F, 0, 3, 8, 0.0F, false));
        teeth_row_left_r1.cubeList.add(new ModelBox(teeth_row_left_r1, 8, 92, -7.0F, -3.5704F, -5.5322F, 0, 3, 8, 0.0F, false));

        teeth_row_front_r1 = new ModelRenderer(this);
        teeth_row_front_r1.setRotationPoint(1.351F, 8.5014F, -41.2182F);
        upper_head.addChild(teeth_row_front_r1);
        setRotation(teeth_row_front_r1, 0.0F, 1.5708F, 0.0F);
        teeth_row_front_r1.cubeList.add(new ModelBox(teeth_row_front_r1, 1, 80, 5.4612F, -4.5704F, -6.072F, 0, 3, 12, 0.0F, false));

        upper_skull_r1 = new ModelRenderer(this);
        upper_skull_r1.setRotationPoint(1.351F, 5.5014F, -36.7182F);
        upper_head.addChild(upper_skull_r1);
        setRotation(upper_skull_r1, 1.7017F, 0.0F, 0.0F);
        upper_skull_r1.cubeList.add(new ModelBox(upper_skull_r1, 78, 0, -7.9794F, -11.3154F, 1.1579F, 16, 19, 7, 0.0F, false));
        upper_skull_r1.cubeList.add(new ModelBox(upper_skull_r1, 68, 107, -7.9794F, -16.3154F, 5.1579F, 16, 5, 3, 0.0F, false));

        nose_bot_r1 = new ModelRenderer(this);
        nose_bot_r1.setRotationPoint(1.3716F, 2.5064F, -53.5873F);
        upper_head.addChild(nose_bot_r1);
        setRotation(nose_bot_r1, 1.1083F, 0.0F, 0.0F);
        nose_bot_r1.cubeList.add(new ModelBox(nose_bot_r1, 129, 1, -8.0F, 0.0207F, -0.0117F, 16, 7, 3, -0.02F, false));

        lower_jaw = new ModelRenderer(this);
        lower_jaw.setRotationPoint(2.4828F, 1.6811F, -8.3576F);
        head.addChild(lower_jaw);
        setRotation(lower_jaw, 0.1309F, 0.0F, 0.0F);
        lower_jaw.cubeList.add(new ModelBox(lower_jaw, 55, 57, -5.9828F, -0.25F, -6.8927F, 12, 3, 10, 0.05F, false));
        lower_jaw.cubeList.add(new ModelBox(lower_jaw, 10, 89, 5.0182F, -3.25F, -5.8927F, 0, 3, 7, 0.0F, false));
        lower_jaw.cubeList.add(new ModelBox(lower_jaw, 10, 89, -4.9818F, -3.25F, -5.8927F, 0, 3, 7, 0.0F, false));

        teeth_row_front_r2 = new ModelRenderer(this);
        teeth_row_front_r2.setRotationPoint(-0.4828F, 2.7552F, 10.1978F);
        lower_jaw.addChild(teeth_row_front_r2);
        setRotation(teeth_row_front_r2, 0.0F, 1.5708F, 0.0F);
        teeth_row_front_r2.cubeList.add(new ModelBox(teeth_row_front_r2, 4, 78, 16.0196F, -6.0052F, -4.571F, 0, 3, 10, 0.0F, false));

        dorsal_fin = new ModelRenderer(this);
        dorsal_fin.setRotationPoint(0.5F, -20.1292F, -6.2277F);
        torso_1.addChild(dorsal_fin);


        dorsal_fin_d_r1 = new ModelRenderer(this);
        dorsal_fin_d_r1.setRotationPoint(-9.0F, 18.324F, -2.6875F);
        dorsal_fin.addChild(dorsal_fin_d_r1);
        setRotation(dorsal_fin_d_r1, 1.5708F, 0.0F, 0.0F);
        dorsal_fin_d_r1.cubeList.add(new ModelBox(dorsal_fin_d_r1, 111, 39, 8.0F, -5.1949F, 7.2152F, 2, 7, 9, 0.01F, false));

        dorsal_fin_c_r1 = new ModelRenderer(this);
        dorsal_fin_c_r1.setRotationPoint(0.0F, 10.7262F, 3.0681F);
        dorsal_fin.addChild(dorsal_fin_c_r1);
        setRotation(dorsal_fin_c_r1, 1.8326F, 0.0F, 0.0F);
        dorsal_fin_c_r1.cubeList.add(new ModelBox(dorsal_fin_c_r1, 170, 28, -1.0F, -4.0F, -1.0F, 2, 4, 12, 0.0F, false));

        dorsal_fin_b_r1 = new ModelRenderer(this);
        dorsal_fin_b_r1.setRotationPoint(0.0F, 7.6647F, -2.5701F);
        dorsal_fin.addChild(dorsal_fin_b_r1);
        setRotation(dorsal_fin_b_r1, 0.6981F, 0.0F, 0.0F);
        dorsal_fin_b_r1.cubeList.add(new ModelBox(dorsal_fin_b_r1, 161, 22, -1.0F, -8.0F, -1.0F, 2, 4, 8, -0.01F, false));

        dorsal_fin_a_r1 = new ModelRenderer(this);
        dorsal_fin_a_r1.setRotationPoint(0.0F, 12.0248F, -8.2271F);
        dorsal_fin.addChild(dorsal_fin_a_r1);
        setRotation(dorsal_fin_a_r1, 1.1781F, 0.0F, 0.0F);
        dorsal_fin_a_r1.cubeList.add(new ModelBox(dorsal_fin_a_r1, 143, 25, -1.0F, -4.0F, -1.0F, 2, 4, 10, 0.0F, false));

        pectoral_fins = new ModelRenderer(this);
        pectoral_fins.setRotationPoint(0.0F, 0.0F, 0.0F);
        torso_1.addChild(pectoral_fins);


        left_fin = new ModelRenderer(this);
        left_fin.setRotationPoint(11.5F, 7.0F, -21.0F);
        pectoral_fins.addChild(left_fin);
        setRotation(left_fin, 0.0F, 0.0F, -0.7854F);


        left_fin_d_r1 = new ModelRenderer(this);
        left_fin_d_r1.setRotationPoint(0.3933F, -2.7076F, -1.1278F);
        left_fin.addChild(left_fin_d_r1);
        setRotation(left_fin_d_r1, 0.3316F, 0.0F, 0.0F);
        left_fin_d_r1.cubeList.add(new ModelBox(left_fin_d_r1, 225, 0, -1.0F, 0.3827F, -3.0761F, 2, 19, 5, -0.01F, false));

        left_fin_a_r1 = new ModelRenderer(this);
        left_fin_a_r1.setRotationPoint(0.3933F, 12.7347F, -9.897F);
        left_fin.addChild(left_fin_a_r1);
        setRotation(left_fin_a_r1, 0.9076F, 0.0F, 0.0F);
        left_fin_a_r1.cubeList.add(new ModelBox(left_fin_a_r1, 216, 23, -1.0F, 3.9561F, 4.8879F, 2, 11, 4, 0.0F, false));

        left_fin_a_r2 = new ModelRenderer(this);
        left_fin_a_r2.setRotationPoint(0.3933F, -1.6235F, -5.2607F);
        left_fin.addChild(left_fin_a_r2);
        setRotation(left_fin_a_r2, 0.3316F, 0.0F, 0.0F);
        left_fin_a_r2.cubeList.add(new ModelBox(left_fin_a_r2, 200, 17, -1.0F, -1.2777F, -2.8042F, 2, 14, 5, 0.01F, false));

        right_fin = new ModelRenderer(this);
        right_fin.setRotationPoint(-10.5F, 6.0F, -21.0F);
        pectoral_fins.addChild(right_fin);
        setRotation(right_fin, 0.0F, 0.0F, 0.7854F);


        right_fin_a_r1 = new ModelRenderer(this);
        right_fin_a_r1.setRotationPoint(0.9246F, -1.4211F, -5.2607F);
        right_fin.addChild(right_fin_a_r1);
        setRotation(right_fin_a_r1, 0.3316F, 0.0F, 0.0F);
        right_fin_a_r1.cubeList.add(new ModelBox(right_fin_a_r1, 200, 17, -1.0F, -1.2777F, -2.8042F, 2, 14, 5, 0.01F, false));

        right_fin_a_r2 = new ModelRenderer(this);
        right_fin_a_r2.setRotationPoint(0.9246F, 12.9372F, -9.897F);
        right_fin.addChild(right_fin_a_r2);
        setRotation(right_fin_a_r2, 0.9076F, 0.0F, 0.0F);
        right_fin_a_r2.cubeList.add(new ModelBox(right_fin_a_r2, 216, 23, -1.0F, 3.9561F, 4.8879F, 2, 11, 4, 0.0F, false));

        right_fin_d_r1 = new ModelRenderer(this);
        right_fin_d_r1.setRotationPoint(0.9246F, -2.5052F, -1.1278F);
        right_fin.addChild(right_fin_d_r1);
        setRotation(right_fin_d_r1, 0.3316F, 0.0F, 0.0F);
        right_fin_d_r1.cubeList.add(new ModelBox(right_fin_d_r1, 225, 0, -1.0F, 0.3827F, -3.0761F, 2, 19, 5, -0.01F, true));

        torso_2 = new ModelRenderer(this);
        torso_2.setRotationPoint(-0.5F, 0.0F, 2.0F);
        torso_1.addChild(torso_2);
        torso_2.cubeList.add(new ModelBox(torso_2, 0, 54, -8.0F, -8.0F, 0.0F, 18, 16, 17, 0.0F, false));

        pelvic_fin_right_r1 = new ModelRenderer(this);
        pelvic_fin_right_r1.setRotationPoint(-5.0F, 7.0F, 10.5F);
        torso_2.addChild(pelvic_fin_right_r1);
        setRotation(pelvic_fin_right_r1, 0.0F, 0.0F, 0.3927F);
        pelvic_fin_right_r1.cubeList.add(new ModelBox(pelvic_fin_right_r1, 0, 16, 1.5F, 0.0F, -4.5F, 0, 6, 9, 0.0F, false));

        pelvic_fin_left_r1 = new ModelRenderer(this);
        pelvic_fin_left_r1.setRotationPoint(6.0F, 7.0F, 10.5F);
        torso_2.addChild(pelvic_fin_left_r1);
        setRotation(pelvic_fin_left_r1, 0.0F, 0.0F, -0.3927F);
        pelvic_fin_left_r1.cubeList.add(new ModelBox(pelvic_fin_left_r1, 0, 16, -1.5F, 0.0F, -4.5F, 0, 6, 9, 0.0F, false));

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.5F, -1.0F, 17.0F);
        torso_2.addChild(tail);
        tail.cubeList.add(new ModelBox(tail, 0, 88, -3.5F, -6.2608F, -4.1248F, 8, 10, 25, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 78, 22, 0.5F, -10.8811F, 0.3854F, 0, 5, 5, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 10, 98, 0.5F, 3.35F, 4.3854F, 0, 7, 7, 0.0F, false));

        caudal_fin = new ModelRenderer(this);
        caudal_fin.setRotationPoint(0.25F, 0.229F, 69.8752F);
        tail.addChild(caudal_fin);
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 148, 69, -2.75F, -4.1371F, -50.1669F, 6, 6, 9, 0.0F, false));

        caudal_fin_bot_c_r1 = new ModelRenderer(this);
        caudal_fin_bot_c_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_c_r1);
        setRotation(caudal_fin_bot_c_r1, -1.1781F, 0.0F, 0.0F);
        caudal_fin_bot_c_r1.cubeList.add(new ModelBox(caudal_fin_bot_c_r1, 189, 49, -1.0F, -3.8859F, -6.906F, 2, 4, 15, 0.0F, false));

        caudal_fin_bot_b_r1 = new ModelRenderer(this);
        caudal_fin_bot_b_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_b_r1);
        setRotation(caudal_fin_bot_b_r1, -0.7418F, 0.0F, 0.0F);
        caudal_fin_bot_b_r1.cubeList.add(new ModelBox(caudal_fin_bot_b_r1, 146, 45, -0.99F, -0.0384F, -9.3692F, 2, 4, 19, 0.02F, false));

        caudal_fin_bot_a_r1 = new ModelRenderer(this);
        caudal_fin_bot_a_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_a_r1);
        setRotation(caudal_fin_bot_a_r1, -1.1781F, 0.0F, 0.0F);
        caudal_fin_bot_a_r1.cubeList.add(new ModelBox(caudal_fin_bot_a_r1, 129, 13, -1.01F, 0.1241F, -8.396F, 2, 4, 8, -0.01F, false));

        caudal_fin_top_d_r1 = new ModelRenderer(this);
        caudal_fin_top_d_r1.setRotationPoint(1.24F, -7.2547F, -40.024F);
        caudal_fin.addChild(caudal_fin_top_d_r1);
        setRotation(caudal_fin_top_d_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_top_d_r1.cubeList.add(new ModelBox(caudal_fin_top_d_r1, 113, 27, -2.0F, -3.5F, 3.0F, 2, 3, 6, 0.01F, false));

        caudal_fin_top_c_r1 = new ModelRenderer(this);
        caudal_fin_top_c_r1.setRotationPoint(1.25F, -19.9763F, -26.8568F);
        caudal_fin.addChild(caudal_fin_top_c_r1);
        setRotation(caudal_fin_top_c_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_top_c_r1.cubeList.add(new ModelBox(caudal_fin_top_c_r1, 171, 45, -2.0F, -3.9791F, -14.9833F, 2, 4, 14, 0.0F, false));

        caudal_fin_top_b_r1 = new ModelRenderer(this);
        caudal_fin_top_b_r1.setRotationPoint(1.26F, -16.7717F, -35.6639F);
        caudal_fin.addChild(caudal_fin_top_b_r1);
        setRotation(caudal_fin_top_b_r1, 0.5236F, 0.0F, 0.0F);
        caudal_fin_top_b_r1.cubeList.add(new ModelBox(caudal_fin_top_b_r1, 124, 41, -2.02F, -2.0F, -9.0F, 2, 4, 18, 0.03F, false));

        caudal_fin_top_a_r1 = new ModelRenderer(this);
        caudal_fin_top_a_r1.setRotationPoint(0.25F, -4.3334F, -46.33F);
        caudal_fin.addChild(caudal_fin_top_a_r1);
        setRotation(caudal_fin_top_a_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_top_a_r1.cubeList.add(new ModelBox(caudal_fin_top_a_r1, 112, 65, -1.0F, -4.0F, -3.0F, 2, 11, 12, 0.01F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        body.render(f5);
    }

    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.tail.rotateAngleY = (MathHelper.cos(f * 0.6662F) * f1) / 4;
    }
}
