package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.world.item.ItemDoor;

public class MoCItemDoor extends ItemDoor {
    public MoCItemDoor(Block block, String name) {
        super(block);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);
    }
}
