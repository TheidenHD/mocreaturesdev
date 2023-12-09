/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.init.MoCEntities;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoCEventHooksTerrain {

    public static List<Biome.SpawnListEntry> creatureList = new ArrayList<>();
    public static List<Biome.SpawnListEntry> waterCreatureList = new ArrayList<>();
    public static Object2ObjectOpenHashMap<Biome, List<Biome.SpawnListEntry>> creatureSpawnMap = new Object2ObjectOpenHashMap<>();
    public static Object2ObjectOpenHashMap<Biome, List<Biome.SpawnListEntry>> waterCreatureSpawnMap = new Object2ObjectOpenHashMap<>();

    public static void buildWorldGenSpawnLists() {
        for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {
            creatureList = new ArrayList<>(biome.getSpawnableList(EnumCreatureType.CREATURE));
            creatureList.removeIf(entry -> entry.itemWeight == 0 || !IMoCEntity.class.isAssignableFrom(entry.entityClass));
            creatureSpawnMap.put(biome, creatureList);

            waterCreatureList = new ArrayList<>(biome.getSpawnableList(EnumCreatureType.WATER_CREATURE));
            waterCreatureList.removeIf(entry -> entry.itemWeight == 0 || !IMoCEntity.class.isAssignableFrom(entry.entityClass));
            waterCreatureSpawnMap.put(biome, waterCreatureList);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public static void addBiomeTypes() {
        // Extreme Hills
        BiomeDictionary.addTypes(Biomes.EXTREME_HILLS, MoCEntities.STEEP);
        BiomeDictionary.addTypes(Biomes.EXTREME_HILLS_EDGE, MoCEntities.STEEP);
        BiomeDictionary.addTypes(Biomes.MUTATED_EXTREME_HILLS, MoCEntities.STEEP);
        BiomeDictionary.addTypes(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, MoCEntities.STEEP);

        // Mesa
        BiomeDictionary.addTypes(Biomes.MUTATED_MESA, BiomeDictionary.Type.MESA);
        BiomeDictionary.addTypes(Biomes.MUTATED_MESA_ROCK, BiomeDictionary.Type.MESA);
        BiomeDictionary.addTypes(Biomes.MUTATED_MESA_CLEAR_ROCK, BiomeDictionary.Type.MESA);

        // Traverse
        ResourceLocation rockyPlateau = new ResourceLocation("traverse:rocky_plateau");
        if (ForgeRegistries.BIOMES.containsKey(rockyPlateau)) {
            BiomeDictionary.addTypes(ForgeRegistries.BIOMES.getValue(rockyPlateau), BiomeDictionary.Type.PLAINS);
        }
        ResourceLocation aridHighland = new ResourceLocation("traverse:arid_highland");
        if (ForgeRegistries.BIOMES.containsKey(aridHighland)) {
            BiomeDictionary.addTypes(ForgeRegistries.BIOMES.getValue(aridHighland), BiomeDictionary.Type.SAVANNA);
        }
    }

    @SubscribeEvent
    public void onPopulateChunk(PopulateChunkEvent.Populate event) {
        // Regular spawning
        if (event.getType() == PopulateChunkEvent.Populate.EventType.ANIMALS) {
            int chunkX = event.getChunkX() * 16;
            int chunkZ = event.getChunkZ() * 16;
            int centerX = chunkX + 8;
            int centerZ = chunkZ + 8;
            World world = event.getWorld();
            Random rand = event.getRand();
            BlockPos blockPos = new BlockPos(chunkX, 0, chunkZ);
            Biome biome = world.getBiome(blockPos.add(16, 0, 16));

            MoCTools.performCustomWorldGenSpawning(world, biome, centerX, centerZ, 16, 16, rand, creatureSpawnMap.get(biome), MobEntity.SpawnPlacementType.ON_GROUND);
            MoCTools.performCustomWorldGenSpawning(world, biome, centerX, centerZ, 16, 16, rand, waterCreatureSpawnMap.get(biome), MobEntity.SpawnPlacementType.IN_WATER);
        }
    }
}
