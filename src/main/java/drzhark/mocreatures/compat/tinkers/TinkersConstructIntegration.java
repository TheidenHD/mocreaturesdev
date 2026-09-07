package drzhark.mocreatures.compat.tinkers;

public class TinkersConstructIntegration {
//    // These materials are used universally between tools and armor
//    public static final Material ANCIENT_SILVER = new Material(MoCConstants.MOD_ID + "." + "ancient_silver", 0x8E8F93);
//    public static final Material BIG_CAT_CLAW = new Material(MoCConstants.MOD_ID + "." + "big_cat_claw", 0xBBA56C);
//    public static final Material DARK_CHITIN = new Material(MoCConstants.MOD_ID + "." + "dark_chitin", 0x535A6B);
//    public static final Material EARTH_CHITIN = new Material(MoCConstants.MOD_ID + "." + "earth_chitin", 0xF37A07);
//    public static final Material FIRE_CHITIN = new Material(MoCConstants.MOD_ID + "." + "fire_chitin", 0xC62B13);
//    public static final Material FROST_CHITIN = new Material(MoCConstants.MOD_ID + "." + "frost_chitin", 0x1B7A87);
//    public static final Material SHARK_TOOTH = new Material(MoCConstants.MOD_ID + "." + "shark_tooth", 0xB7B699);
//    public static final Material UNDEAD_CHITIN = new Material(MoCConstants.MOD_ID + "." + "undead_chitin", 0x92B859);
//
//    // These traits are for tools and not armor
//    public static final AbstractTrait BIG_GAME_HUNTER = new TraitBigGameHunter(0.5F);
//    public static final AbstractTrait DARK_STING = new TraitStingEffectPlayer(MoCConstants.MOD_ID + "." + "dark_sting", 0x535A6B, 1.5F, MobEffects.WEAKNESS, MobEffects.NAUSEA);
//    public static final AbstractTrait EARTH_STING = new TraitStingEffect(MoCConstants.MOD_ID + "." + "earth_sting", 0xF37A07, 1.5F, MobEffects.POISON, 1);
//    public static final AbstractTrait FIRE_STING = new TraitStingFire(MoCConstants.MOD_ID + "." + "fire_sting", 0xC62B13, 1.5F);
//    public static final AbstractTrait FROST_STING = new TraitStingEffect(MoCConstants.MOD_ID + "." + "frost_sting", 0x1B7A87, 1.5F, MobEffects.SLOWNESS, 0);
//    public static final AbstractTrait SEA_PREDATOR = new TraitSeaPredator(0.4F);
//    public static final AbstractTrait SILVER_HOPLITE = new TraitSilverHoplite(5.0F);
//    public static final AbstractTrait UNDEAD_STING = new TraitStingEffectPlayer(MoCConstants.MOD_ID + "." + "undead_sting", 0x92B859, 1.5F, MobEffects.WITHER, MobEffects.BLINDNESS);
//
//    public static final FluidMolten ANCIENT_SILVER_FLUID = new FluidMolten("ancient_silver", 0x9D9FA3, FluidMolten.ICON_MetalStill, FluidMolten.ICON_MetalFlowing);
//
//    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
//
//    // TODO: Return bucket when picked from JEI
//    public static void registerFluid(Fluid fluid) {
//        fluid.setUnlocalizedName(MoCConstants.MOD_ID + "." + fluid.getName());
//        FluidRegistry.addBucketForFluid(fluid);
//        BlockMolten blockMolten = (BlockMolten) new BlockMolten(fluid).setRegistryName(MoCConstants.MOD_ID, "molten_" + fluid.getName());
//        ITEM_BLOCKS.add((ItemBlock) new ItemBlock(blockMolten).setRegistryName(blockMolten.getRegistryName()));
//        ForgeRegistries.BLOCKS.register(blockMolten);
//    }
//
//    public static void preInit() {
//        registerFluid(ANCIENT_SILVER_FLUID);
//        ANCIENT_SILVER_FLUID.setTemperature(580);
//
//        TinkerRegistry.addMaterialStats(DARK_CHITIN,
//                new HeadMaterialStats(300, 6.0F, 3.5F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(0.95F, 60),
//                new ExtraMaterialStats(60),
//                new BowMaterialStats(0.9F, 0.7F, 0.0F));
//        DARK_CHITIN.addTrait(DARK_STING, MaterialTypes.HEAD);
//        DARK_CHITIN.addTrait(TinkerTraits.fractured);
//        TinkerRegistry.integrate(new MaterialIntegration(DARK_CHITIN)).preInit();
//
//        TinkerRegistry.addMaterialStats(EARTH_CHITIN,
//                new HeadMaterialStats(300, 6.0F, 3.5F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(0.95F, 60),
//                new ExtraMaterialStats(60),
//                new BowMaterialStats(0.9F, 0.7F, 0.0F));
//        EARTH_CHITIN.addTrait(EARTH_STING, MaterialTypes.HEAD);
//        EARTH_CHITIN.addTrait(TinkerTraits.fractured);
//        TinkerRegistry.integrate(new MaterialIntegration(EARTH_CHITIN)).preInit();
//
//        TinkerRegistry.addMaterialStats(FIRE_CHITIN,
//                new HeadMaterialStats(300, 6.0F, 3.5F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(0.95F, 60),
//                new ExtraMaterialStats(60),
//                new BowMaterialStats(0.9F, 0.7F, 0.0F));
//        FIRE_CHITIN.addTrait(FIRE_STING, MaterialTypes.HEAD);
//        FIRE_CHITIN.addTrait(TinkerTraits.fractured);
//        TinkerRegistry.integrate(new MaterialIntegration(FIRE_CHITIN)).preInit();
//
//        TinkerRegistry.addMaterialStats(FROST_CHITIN,
//                new HeadMaterialStats(300, 6.0F, 3.5F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(0.95F, 60),
//                new ExtraMaterialStats(60),
//                new BowMaterialStats(0.9F, 0.7F, 0.0F));
//        FROST_CHITIN.addTrait(FROST_STING, MaterialTypes.HEAD);
//        FROST_CHITIN.addTrait(TinkerTraits.fractured);
//        TinkerRegistry.integrate(new MaterialIntegration(FROST_CHITIN)).preInit();
//
//        TinkerRegistry.addMaterialStats(UNDEAD_CHITIN,
//                new HeadMaterialStats(300, 6.0F, 3.5F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(0.95F, 60),
//                new ExtraMaterialStats(60),
//                new BowMaterialStats(0.9F, 0.7F, 0.0F));
//        UNDEAD_CHITIN.addTrait(UNDEAD_STING, MaterialTypes.HEAD);
//        UNDEAD_CHITIN.addTrait(TinkerTraits.fractured);
//        TinkerRegistry.integrate(new MaterialIntegration(UNDEAD_CHITIN)).preInit();
//
//        TinkerRegistry.addMaterialStats(BIG_CAT_CLAW,
//                new HeadMaterialStats(140, 5.5F, 3.0F, HarvestLevels.IRON),
//                new HandleMaterialStats(0.7F, -40),
//                new ExtraMaterialStats(50),
//                new BowMaterialStats(0.2F, 0.4F, -1.0F));
//        BIG_CAT_CLAW.addTrait(BIG_GAME_HUNTER, MaterialTypes.HEAD);
//        BIG_CAT_CLAW.addTrait(TinkerTraits.crude2, MaterialTypes.HEAD);
//        BIG_CAT_CLAW.addTrait(TinkerTraits.crude);
//        TinkerRegistry.integrate(new MaterialIntegration(BIG_CAT_CLAW)).preInit();
//
//        TinkerRegistry.addMaterialStats(SHARK_TOOTH,
//                new HeadMaterialStats(150, 5.5F, 4.0F, HarvestLevels.IRON),
//                new HandleMaterialStats(0.7F, 5),
//                new ExtraMaterialStats(40),
//                new BowMaterialStats(0.95F, 1.15F, 0.0F));
//        SHARK_TOOTH.addTrait(SEA_PREDATOR, MaterialTypes.HEAD);
//        SHARK_TOOTH.addTrait(TinkerTraits.aquadynamic, MaterialTypes.HEAD);
//        SHARK_TOOTH.addTrait(TinkerTraits.aquadynamic);
//        TinkerRegistry.integrate(new MaterialIntegration(SHARK_TOOTH)).preInit();
//
//        TinkerRegistry.addMaterialStats(ANCIENT_SILVER,
//                new HeadMaterialStats(404, 7.05F, 6.0F, HarvestLevels.OBSIDIAN),
//                new HandleMaterialStats(1.1F, 120),
//                new ExtraMaterialStats(110),
//                new BowMaterialStats(0.4F, 2.0F, 9.0F));
//        ANCIENT_SILVER.addTrait(SILVER_HOPLITE, MaterialTypes.HEAD);
//        ANCIENT_SILVER.addTrait(TinkerTraits.heavy, MaterialTypes.HEAD);
//        ANCIENT_SILVER.addTrait(TinkerTraits.heavy);
//        TinkerRegistry.integrate(new MaterialIntegration(ANCIENT_SILVER, ANCIENT_SILVER_FLUID, "AncientSilver")).toolforge().preInit();
//    }
//
//    public static void init() {
//        DARK_CHITIN.addItem(MoCItems.chitinCave, 1, Material.VALUE_Ingot);
//        DARK_CHITIN.setRepresentativeItem(MoCItems.chitinCave);
//        DARK_CHITIN.setCraftable(true).setCastable(false);
//
//        EARTH_CHITIN.addItem(MoCItems.chitin, 1, Material.VALUE_Ingot);
//        EARTH_CHITIN.setRepresentativeItem(MoCItems.chitin);
//        EARTH_CHITIN.setCraftable(true).setCastable(false);
//
//        FIRE_CHITIN.addItem(MoCItems.chitinNether, 1, Material.VALUE_Ingot);
//        FIRE_CHITIN.setRepresentativeItem(MoCItems.chitinNether);
//        FIRE_CHITIN.setCraftable(true).setCastable(false);
//
//        FROST_CHITIN.addItem(MoCItems.chitinFrost, 1, Material.VALUE_Ingot);
//        FROST_CHITIN.setRepresentativeItem(MoCItems.chitinFrost);
//        FROST_CHITIN.setCraftable(true).setCastable(false);
//
//        UNDEAD_CHITIN.addItem(MoCItems.chitinUndead, 1, Material.VALUE_Ingot);
//        UNDEAD_CHITIN.setRepresentativeItem(MoCItems.chitinUndead);
//        UNDEAD_CHITIN.setCraftable(true).setCastable(false);
//
//        BIG_CAT_CLAW.addItem(MoCItems.bigcatclaw, 1, Material.VALUE_Ingot);
//        BIG_CAT_CLAW.setRepresentativeItem(MoCItems.bigcatclaw);
//        BIG_CAT_CLAW.setCraftable(true).setCastable(false);
//
//        SHARK_TOOTH.addItem(MoCItems.sharkteeth, 1, Material.VALUE_Ingot);
//        SHARK_TOOTH.setRepresentativeItem(MoCItems.sharkteeth);
//        SHARK_TOOTH.setCraftable(true).setCastable(false);
//
//        ANCIENT_SILVER.addCommonItems("AncientSilver");
//        ANCIENT_SILVER.addItem(MoCItems.ancientSilverScrap, 1, Material.VALUE_Ingot);
//        ANCIENT_SILVER.setRepresentativeItem(MoCItems.ancientSilverIngot);
//        ANCIENT_SILVER.setFluid(ANCIENT_SILVER_FLUID);
//        ANCIENT_SILVER.setCraftable(false).setCastable(true);
//    }
//
//    public static void postInit() {
//        // Smeltery stuff goes here
//        TinkerRegistry.registerMelting(MoCItems.ancientSilverScrap, ANCIENT_SILVER_FLUID, Material.VALUE_Ingot);
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void registerModels(ModelRegistryEvent event) {
//        for (ItemBlock itemBlock : ITEM_BLOCKS) {
//            if (itemBlock.getBlock() instanceof BlockMolten) {
//                ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "normal"));
//                ModelLoader.setCustomStateMapper(itemBlock.getBlock(), new StateMapperBase() {
//                    @Override
//                    public ModelResourceLocation getModelResourceLocation(BlockState state) {
//                        return new ModelResourceLocation(itemBlock.getRegistryName(), "normal");
//                    }
//                });
//            }
//        }
//    }
}
