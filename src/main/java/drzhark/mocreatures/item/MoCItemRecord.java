package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class MoCItemRecord extends RecordItem {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation(MoCConstants.MOD_ID, "music_disc.shuffling");

    public MoCItemRecord(int comparatorValueIn, java.util.function.Supplier<SoundEvent> soundSupplier, Item.Properties properties) {
        super(comparatorValueIn, soundSupplier, properties, 2400);
    }
}
