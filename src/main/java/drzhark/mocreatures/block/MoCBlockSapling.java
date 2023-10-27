/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.trees.Tree;

public class MoCBlockSapling extends SaplingBlock{

    public MoCBlockSapling(Tree treeIn, AbstractBlock.Properties properties) {
        super(treeIn, properties.sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
    }
}
