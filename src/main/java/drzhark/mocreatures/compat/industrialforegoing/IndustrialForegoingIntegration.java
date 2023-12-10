/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.industrialforegoing;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.FluidExtractorRecipe;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

// Courtesy of SokyranTheDragon
public class IndustrialForegoingIntegration {

    public static void generateLatexEntries() {
        new FluidExtractorRecipe(new ResourceLocation(MoCConstants.MOD_ID, "wyvwood"), new Ingredient.SingleItemList(new ItemStack(MoCBlocks.wyvwoodLog)), MoCBlocks.strippedwyvwoodLog, 0.005f, new FluidStack(ModuleCore.LATEX.getSourceFluid(), 1), false);
    }
}
