/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public class MoCBlockRock extends Block {

    public MoCBlockRock(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.STONE));
    }
//    @Override //TODO TheidenHD
//    public Item getItemDropped(BlockState state, Random rand, int fortune) {
//        if (this == MoCBlocks.deepWyvstone) {
//            return MoCBlocks.cobbledDeepWyvstone.getItemDropped(MoCBlocks.cobbledDeepWyvstone.getDefaultState(), rand, fortune);
//        } else if (this == MoCBlocks.wyvstone) {
//            return MoCBlocks.cobbledWyvstone.getItemDropped(MoCBlocks.cobbledWyvstone.getDefaultState(), rand, fortune);
//        } else {
//            return Item.getItemFromBlock(this);
//        }
//    }
}
