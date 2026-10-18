package drzhark.mocreatures.client;

import drzhark.mocreatures.event.MoCEventHooksClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

public class ClientOnlyEventRegistrar {
    @OnlyIn(Dist.CLIENT)
    public static void registerClientEvents() {
        MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
        MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
    }
}