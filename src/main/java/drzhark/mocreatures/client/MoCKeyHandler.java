/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageEntityDive;
import drzhark.mocreatures.network.message.MoCMessageEntityJump;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, value = Dist.CLIENT)
public class MoCKeyHandler {

    static KeyBinding diveBinding = new KeyBinding("Flying Mount Descent (Mo' Creatures)", Keyboard.KEY_Z, "key.categories.movement");

    public MoCKeyHandler() {
        ClientRegistry.registerKeyBinding(diveBinding);
    }

    @SubscribeEvent
    public void onInput(TickEvent.PlayerTickEvent e) {

        boolean kbJump = MoCProxyClient.mc.gameSettings.keyBindJump.isKeyDown();
        boolean kbDive = diveBinding.isKeyDown();

        boolean kbJump = MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode() >= 0 ? GameSettings.isKeyDown(MoCProxyClient.mc.gameSettings.keyBindJump) : keyPressed == MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode();
        boolean kbDive = diveBinding.getKeyCode() >= 0 ? GameSettings.isKeyDown(diveBinding) : keyPressed == diveBinding.getKeyCode();

        /*
         * this avoids double jumping
         */
        if (kbJump && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
            // jump code needs to be executed client/server simultaneously to take
            ((IMoCEntity) e.player.getRidingEntity()).makeEntityJump();
            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityJump());
        }

        if (kbDive && e.player.getRidingEntity() != null && e.player.getRidingEntity() instanceof IMoCEntity) {
            // jump code needs to be executed client/server simultaneously to take
            ((IMoCEntity) e.player.getRidingEntity()).makeEntityDive();
            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityDive());
        }
    }
}
