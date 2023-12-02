/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.compat.CompatScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MoCEventHooksClient {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void displayCompatScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && CompatScreen.showScreen && Loader.isModLoaded("customspawner")) {
            event.setGui(new CompatScreen());
            CompatScreen.showScreen = false;
        }
    }

    /* TODO: Fix rider rotation
    @SubscribeEvent
    public void renderClimbingRiderPre(RenderLivingEvent.Pre<LivingEntity> event) {
        LivingEntity rider = event.getEntity();
        Entity mount = rider.getRidingEntity();
        if (mount instanceof MoCEntityScorpion || mount instanceof MoCEntityPetScorpion) {
            if (((LivingEntity) rider.getRidingEntity()).isOnLadder()) {
                Direction facing = rider.getHorizontalFacing();
                matrixStackIn.push();
                if (facing == Direction.NORTH) {
                    matrixStackIn.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                } else if (facing == Direction.WEST) {
                    matrixStackIn.rotate(90.0F, 0.0F, 0.0F, -1.0F);
                } else if (facing == Direction.SOUTH) {
                    matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
                } else if (facing == Direction.EAST) {
                    matrixStackIn.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                }
                matrixStackIn.translate(0.0F, -1.0F, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public void renderClimbingRiderPost(RenderLivingEvent.Post<LivingEntity> event) {
        LivingEntity rider = event.getEntity();
        Entity mount = rider.getRidingEntity();
        if (mount instanceof MoCEntityScorpion || mount instanceof MoCEntityPetScorpion) {
            if (((LivingEntity) rider.getRidingEntity()).isOnLadder()) {
                Direction facing = rider.getHorizontalFacing();
                matrixStackIn.translate(0.0F, 1.0F, 0.0F);
                if (facing == Direction.NORTH) {
                    matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
                } else if (facing == Direction.WEST) {
                    matrixStackIn.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                } else if (facing == Direction.SOUTH) {
                    matrixStackIn.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                } else if (facing == Direction.EAST) {
                    matrixStackIn.rotate(90.0F, 0.0F, 0.0F, -1.0F);
                }
                matrixStackIn.pop();
            }
        }
    }
    */
}
