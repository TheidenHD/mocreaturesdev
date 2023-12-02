/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.Item;

public class MoCItem extends Item {

    public MoCItem(Item.Properties properties, String name) {
        this(properties, name, 0);
    }

    public MoCItem(Item.Properties properties, String name, int meta) {
        super(properties.group(MoCreatures.tabMoC));
        this.setRegistryName(MoCConstants.MOD_ID, name);
    }
}
