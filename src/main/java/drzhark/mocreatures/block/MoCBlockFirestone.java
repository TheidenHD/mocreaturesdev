/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class MoCBlockFirestone extends Block {

    public MoCBlockFirestone(MapColor mapColor) {
        super(Material.ROCK, mapColor);
        this.setSoundType(SoundType.GLASS);
    }

    public MoCBlockFirestone(Material material, MapColor mapColor) {
        super(material, mapColor);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    @Override
    public int quantityDropped(Random random) {
        return 2 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MoCItems.firestoneChunk;
    }
}
