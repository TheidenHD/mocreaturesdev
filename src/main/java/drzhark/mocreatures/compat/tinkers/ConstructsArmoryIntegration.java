package drzhark.mocreatures.compat.tinkers;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import c4.conarm.lib.traits.AbstractArmorTrait;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.compat.tinkers.traits.armor.TraitShellEffectArmor;
import drzhark.mocreatures.compat.tinkers.traits.armor.TraitShellEffectPlayerArmor;
import drzhark.mocreatures.compat.tinkers.traits.armor.TraitShellFireArmor;
import drzhark.mocreatures.compat.tinkers.traits.armor.TraitSpeedDemonArmor;
import drzhark.mocreatures.compat.tinkers.traits.armor.TraitThresherArmor;
import net.minecraft.init.MobEffects;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ConstructsArmoryIntegration {
    // These traits are for armor and not tools
    public static final AbstractArmorTrait DARK_SHELL_ARMOR = new TraitShellEffectPlayerArmor(MoCConstants.MOD_ID + "." + "dark_shell", 0x535A6B, 0.0625F, MobEffects.NIGHT_VISION,
            MobEffects.WEAKNESS, MobEffects.NAUSEA);
    public static final AbstractArmorTrait EARTH_SHELL_ARMOR = new TraitShellEffectArmor(MoCConstants.MOD_ID + "." + "earth_shell", 0xF37A07, 0.0625F, MobEffects.SPEED, MobEffects.POISON, 1);
    public static final AbstractArmorTrait FIRE_SHELL_ARMOR = new TraitShellFireArmor(MoCConstants.MOD_ID + "." + "fire_shell", 0xC62B13, 0.0625F, MobEffects.FIRE_RESISTANCE);
    public static final AbstractArmorTrait FROST_SHELL_ARMOR = new TraitShellEffectArmor(MoCConstants.MOD_ID + "." + "frost_shell", 0x1B7A87, 0.0625F, MobEffects.RESISTANCE, MobEffects.SLOWNESS, 0);
    public static final AbstractArmorTrait UNDEAD_SHELL_ARMOR = new TraitShellEffectPlayerArmor(MoCConstants.MOD_ID + "." + "undead_shell", 0x92B859, 0.0625F, MobEffects.STRENGTH,
            MobEffects.WITHER, MobEffects.BLINDNESS);
    public static final AbstractArmorTrait SPEED_DEMON_ARMOR = new TraitSpeedDemonArmor();
    public static final AbstractArmorTrait THRESHER_ARMOR = new TraitThresherArmor();

    // Materials are already registered in the tools class, we are just registering support for armor sets here
    public static void preInit() {
        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.DARK_CHITIN,
                new CoreMaterialStats(12.0F, 14.0F),
                new PlatesMaterialStats(0.95F, 5.0F, 4.0F),
                new TrimMaterialStats(5.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.DARK_CHITIN, DARK_SHELL_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.DARK_CHITIN, ArmorTraits.spiny, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.DARK_CHITIN, ArmorTraits.spiny, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.DARK_CHITIN, ArmorTraits.spiny, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.EARTH_CHITIN,
                new CoreMaterialStats(12.0F, 14.0F),
                new PlatesMaterialStats(0.95F, 5.0F, 4.0F),
                new TrimMaterialStats(5.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.EARTH_CHITIN, EARTH_SHELL_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.EARTH_CHITIN, ArmorTraits.spiny, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.EARTH_CHITIN, ArmorTraits.spiny, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.EARTH_CHITIN, ArmorTraits.spiny, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.FIRE_CHITIN,
                new CoreMaterialStats(12.0F, 14.0F),
                new PlatesMaterialStats(0.95F, 5.0F, 4.0F),
                new TrimMaterialStats(5.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FIRE_CHITIN, FIRE_SHELL_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FIRE_CHITIN, ArmorTraits.spiny, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FIRE_CHITIN, ArmorTraits.spiny, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FIRE_CHITIN, ArmorTraits.spiny, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.FROST_CHITIN,
                new CoreMaterialStats(12.0F, 14.0F),
                new PlatesMaterialStats(0.95F, 5.0F, 4.0F),
                new TrimMaterialStats(5.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FROST_CHITIN, FROST_SHELL_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FROST_CHITIN, ArmorTraits.spiny, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FROST_CHITIN, ArmorTraits.spiny, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.FROST_CHITIN, ArmorTraits.spiny, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.UNDEAD_CHITIN,
                new CoreMaterialStats(12.0F, 14.0F),
                new PlatesMaterialStats(0.95F, 5.0F, 4.0F),
                new TrimMaterialStats(5.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.UNDEAD_CHITIN, UNDEAD_SHELL_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.UNDEAD_CHITIN, ArmorTraits.spiny, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.UNDEAD_CHITIN, ArmorTraits.spiny, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.UNDEAD_CHITIN, ArmorTraits.spiny, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.BIG_CAT_CLAW,
                new CoreMaterialStats(10.0F, 4.0F),
                new PlatesMaterialStats(0.9F, 2.0F, 1.0F),
                new TrimMaterialStats(3.6F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.BIG_CAT_CLAW, ArmorTraits.skeletal, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.BIG_CAT_CLAW, ArmorTraits.mundane2, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.BIG_CAT_CLAW, ArmorTraits.mundane, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.BIG_CAT_CLAW, ArmorTraits.mundane, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.SHARK_TOOTH,
                new CoreMaterialStats(11.0F, 4.0F),
                new PlatesMaterialStats(1.0F, 2.0F, 1.0F),
                new TrimMaterialStats(4.9F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.SHARK_TOOTH, THRESHER_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.SHARK_TOOTH, ArmorTraits.aquaspeed, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.SHARK_TOOTH, ArmorTraits.aquaspeed, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.SHARK_TOOTH, ArmorTraits.aquaspeed, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstructIntegration.ANCIENT_SILVER,
                new CoreMaterialStats(15.0F, 17.0F),
                new PlatesMaterialStats(0.95F, 8.5F, 3.0F),
                new TrimMaterialStats(4.0F));
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.ANCIENT_SILVER, SPEED_DEMON_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.ANCIENT_SILVER, ArmorTraits.heavy, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.ANCIENT_SILVER, ArmorTraits.heavy, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstructIntegration.ANCIENT_SILVER, ArmorTraits.heavy, ArmorMaterialType.TRIM);
    }
}
