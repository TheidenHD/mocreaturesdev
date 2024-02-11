/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageEntityDive;
import drzhark.mocreatures.network.message.MoCMessageEntityJump;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class MoCKeyHandler {

    static KeyBinding diveBinding = new KeyBinding("MoCreatures Dive", -1, "key.categories.movement"); //TODO TheidenHD

    public MoCKeyHandler() {
        ClientRegistry.registerKeyBinding(diveBinding);
    }

    @SubscribeEvent
    public void onInput(InputEvent event) {
//        int keyPressed = (Mouse.getEventButton() - 100); //TODO TheidenHD
//        if (keyPressed == -101) {
//            keyPressed = Keyboard.getEventKey();
//        }
//
//        PlayerEntity ep = MoCProxyClient.mc.player;
//        if (ep == null) {
//            return;
//        }
//        if (FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().getChatOpen()) {
//            return; // if chatting return
//        }
//        if (Keyboard.getEventKeyState() && ep.getRidingEntity() != null) {
//            Keyboard.enableRepeatEvents(true); // allow holding down key. Fixes flying
//        }
//
//        boolean kbJump = MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode() >= 0 ? Keyboard.isKeyDown(MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode()) : keyPressed == MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode();
//        boolean kbDive = diveBinding.getKeyCode() >= 0 ? Keyboard.isKeyDown(diveBinding.getKeyCode()) : keyPressed == diveBinding.getKeyCode();
//
//        /*
//         * this avoids double jumping
//         */
//        if (kbJump && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
//            // jump code needs to be executed client/server simultaneously to take
//            ((IMoCEntity) ep.getRidingEntity()).makeEntityJump();
//            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityJump());
//        }
//
//        if (kbDive && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
//            // jump code needs to be executed client/server simultaneously to take
//            ((IMoCEntity) ep.getRidingEntity()).makeEntityDive();
//            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityDive());
//        }
    }
}
