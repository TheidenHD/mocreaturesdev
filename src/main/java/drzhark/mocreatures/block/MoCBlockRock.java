/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class MoCBlockRock extends Block {

    public MoCBlockRock(MapColor mapColor) {
        super(Material.ROCK, mapColor);
        this.setSoundType(SoundType.STONE);
    }

    public MoCBlockRock(Material material, MapColor mapColor) {
        super(material, mapColor);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        if (this == MoCBlocks.deepWyvstone) {
            return MoCBlocks.cobbledDeepWyvstone.getItemDropped(MoCBlocks.cobbledDeepWyvstone.getDefaultState(), rand, fortune);
        } else if (this == MoCBlocks.wyvstone) {
            return MoCBlocks.cobbledWyvstone.getItemDropped(MoCBlocks.cobbledWyvstone.getDefaultState(), rand, fortune);
        } else {
            return Item.getItemFromBlock(this);
        }
    }
}
