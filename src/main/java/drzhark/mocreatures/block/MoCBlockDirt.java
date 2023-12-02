/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockDirt extends Block {

    public MoCBlockDirt(MapColor mapColor) {
        super(Material.GROUND, mapColor);
        this.setSoundType(SoundType.GROUND);
        this.setHarvestLevel("shovel", 0);
    }
}
