package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

public class MoCItemRecord extends RecordItem {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = ResourceLocation.fromNamespaceAndPath(MoCConstants.MOD_ID, "music_disc.shuffling");

    public MoCItemRecord(int comparatorValueIn, java.util.function.Supplier<SoundEvent> soundSupplier, Item.Properties properties) {
        super(comparatorValueIn, soundSupplier, properties, 2400);
    }
}
