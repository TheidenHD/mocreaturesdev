/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public class MoCBlockFirestone extends Block {

    public MoCBlockFirestone(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.GLASS));
    }

//    @Override //TODO TheidenHD
//    public int quantityDroppedWithBonus(int fortune, Random random) {
//        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
//    }
//
//    @Override
//    public int quantityDropped(Random random) {
//        return 2 + random.nextInt(3);
//    }
//
//    @Override
//    public Item getItemDropped(BlockState state, Random rand, int fortune) {
//        return MoCItems.firestoneChunk;
//    }
}
