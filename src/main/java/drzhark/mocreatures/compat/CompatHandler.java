/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.compat.industrialforegoing.IndustrialForegoingIntegration;
import drzhark.mocreatures.compat.jer.JERIntegration;
import drzhark.mocreatures.compat.morph.MorphIntegration;
import drzhark.mocreatures.compat.thaumcraft.ThaumcraftIntegration;
import drzhark.mocreatures.compat.thermalexpansion.ThermalExpansionIntegration;
import drzhark.mocreatures.compat.tinkers.ConstructsArmoryIntegration;
import drzhark.mocreatures.compat.tinkers.TinkersConstructIntegration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CompatHandler {

    static {
        try {
            File file = new File(Minecraft.getInstance().gameDirectory, "config" + File.separator + "mia" + File.separator + "mocreatures.cfg");
            if (Files.exists(file.toPath())) {
                File tempFile = new File(Minecraft.getInstance().gameDirectory, "config" + File.separator + "mia" + File.separator + "mocreatures_temp.cfg");
                List<String> configEntries = new ArrayList<>();
                configEntries.add("Enable Hatchery integration");
                configEntries.add("Enable Ice and Fire additions");
                configEntries.add("Enable Industrial Foregoing integration");
                configEntries.add("Enable JER integration");
                configEntries.add("Enable Thermal Expansion integration");
                configEntries.add("Increase damage to werewolves from other mod silver weapons");
                configEntries.add("Replace cod and clownfish drops with their corresponding item");
                try (BufferedReader br = new BufferedReader(new FileReader(file)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        for (String targetConfigEntry : configEntries) {
                            if (line.contains(targetConfigEntry)) {
                                line = line.replace("=true", "=false");
                            }
                        }
                        bw.write(line + "\n");
                    }
                }
                file.delete();
                tempFile.renameTo(file);
            }
            file = new File(Minecraft.getInstance().gameDirectory, "config" + File.separator + "mia" + File.separator + "base.cfg");
            if (Files.exists(file.toPath())) {
                File tempFile = new File(Minecraft.getInstance().gameDirectory, "config" + File.separator + "mia" + File.separator + "base_temp.cfg");
                String targetConfigEntry = "Replaces all raw meat drops with cooked ones";
                try (BufferedReader br = new BufferedReader(new FileReader(file)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.contains(targetConfigEntry)) {
                            line = line.replace("=true", "=false");
                        }
                        bw.write(line + "\n");
                    }
                }
                file.delete();
                tempFile.renameTo(file);
            }
            System.out.println("MIA config files modified successfully!");
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        if (Loader.isModLoaded("thermalexpansion")) ThermalExpansionIntegration.addRecipes();
    }

    public static void preInit() {
        if (Loader.isModLoaded("tconstruct")) {
            TinkersConstructIntegration.preInit();

            // Only load Construct's Armory if Tinkers' Construct is also loaded
            if (Loader.isModLoaded("conarm")) {
                ConstructsArmoryIntegration.preInit();
            }
        }
    }

    public static void init() {
        if (ModList.get().isLoaded("industrialforegoing")) {
            IndustrialForegoingIntegration.generateLatexEntries();
        }
        if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(ThaumcraftIntegration.class);
        if (Loader.isModLoaded("tconstruct")) TinkersConstructIntegration.init();
        if (Loader.isModLoaded("jeresources")) JERIntegration.init();
    }

    public static void postInit() {
        if (Loader.isModLoaded("morph")) MorphIntegration.mapAbilities();
        if (Loader.isModLoaded("tconstruct")) TinkersConstructIntegration.postInit();
    }
}
