package drzhark.mocreatures.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.WebBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.SharedMonsterAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EquipmentSlotType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemWeapon extends MoCItem {

    private final Tier material;
    private final float attackDamage;
    private int specialWeaponType = 0;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public MoCItemWeapon(String name, Item.ToolMaterial material) {
        super(name);
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.attackDamage = 3F + material.getAttackDamage();
    }

    public MoCItemWeapon(Item.Properties properties, Tier material, int damageType) {
        this(properties, material);
        this.specialWeaponType = damageType;
    }

    public float getAttackDamage() {
        return this.material.getAttackDamageBonus();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            //Material material = state.getMaterial();
            //return (material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && material != Material.LEAVES && material != Material.VEGETABLE) ? 1.0F : 1.5F;
            return 1.5F;
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            EnumHand hand = attacker.getActiveHand() == null ? EnumHand.MAIN_HAND : attacker.getActiveHand();
            int timer = 15; // In seconds
            int fire_aspect = 5 * EnchantmentHelper.getFireAspectModifier(attacker); // Fire Aspect
            int poisonous = 5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getHeldItem(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, (timer * 20) + poisonous, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer + fire_aspect);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new EffectInstance(target instanceof Player ? Effects.NAUSEA : Effects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new EffectInstance(target instanceof Player ? Effects.BLINDNESS : Effects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.success(stack);
    }

    // TODO: Cobweb harvesting
    /*@Override
    public boolean canHarvestBlock(BlockState state) {
        return state.is(Blocks.COBWEB);
    }*/

    @Override
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, net.minecraft.core.BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    public String getToolMaterialName() {
        return this.material.toString();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by SwordItem to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
        }
        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable Level world, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_dirt", 15));
                    break;
                case 2: // Slowness
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_frost", 15));
                    break;
                case 3: // Fire
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_fire", 15));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_cave", 15));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_undead", 15));
                    break;
                default:
                    break;
            }
        }
    }
}
