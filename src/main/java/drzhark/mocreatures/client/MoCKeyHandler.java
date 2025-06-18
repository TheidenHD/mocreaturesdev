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
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.blaze3d.platform.InputConstants;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, value = Dist.CLIENT)
public class MoCKeyHandler {

    static KeyBinding diveBinding = new KeyBinding("Flying Mount Descent (Mo' Creatures)", Keyboard.KEY_Z, "key.categories.movement");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(diveBinding);
    }

    @SubscribeEvent
    public static void onInput(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;

        boolean kbJump = MoCProxyClient.mc.options.keyJump.isDown();
        boolean kbDive = diveBinding.isDown();

        boolean kbJump = MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode() >= 0 ? GameSettings.isKeyDown(MoCProxyClient.mc.gameSettings.keyBindJump) : keyPressed == MoCProxyClient.mc.gameSettings.keyBindJump.getKeyCode();
        boolean kbDive = diveBinding.getKeyCode() >= 0 ? GameSettings.isKeyDown(diveBinding) : keyPressed == diveBinding.getKeyCode();

        /*
         * this avoids double jumping
         */
        if (kbJump && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
            // jump code needs to be executed client/server simultaneously to take
            ((IMoCEntity) e.player.getVehicle()).makeEntityJump();
            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityJump());
        }

        if (kbDive && e.player.getVehicle() != null && e.player.getVehicle() instanceof IMoCEntity) {
            // dive code needs to be executed client/server simultaneously to take
            ((IMoCEntity) e.player.getVehicle()).makeEntityDive();
            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityDive());
        }
    }
}
