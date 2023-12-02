/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemRecord extends ItemRecord {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation(MoCConstants.MOD_ID, "shuffling");

    public MoCItemRecord(String name, SoundEvent soundEvent) {
        super(name, soundEvent);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);
    }

    @OnlyIn(Dist.CLIENT)
    /*
     * Return the title for this record.
     */ public String getRecordTitle() {
        return "MoC - " + this.getRecordNameLocal();
    }
}
