/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import com.google.common.collect.Multimap;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemWeapon extends MoCItem {

    private final IItemTier material;
    private final float attackDamage;
    private int specialWeaponType = 0;

    public MoCItemWeapon(String name, IItemTier par2ToolMaterial) {
        super(name);
        this.material = par2ToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2ToolMaterial.getMaxUses());
        this.attackDamage = 3F + par2ToolMaterial.getAttackDamage();
    }

    public MoCItemWeapon(String name, IItemTier par2ToolMaterial, int damageType) {
        this(name, par2ToolMaterial);
        this.specialWeaponType = damageType;
    }

    public float getAttackDamage() {
        return this.material.getAttackDamage();
    }

    public float getStrVsBlock(ItemStack stack, BlockState state) {
        if (state.getBlock() instanceof BlockWeb) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            int timer = 10; // In seconds
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new EffectInstance(Effects.POISON, timer * 20, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new EffectInstance(target instanceof PlayerEntity ? Effects.NAUSEA : Effects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new EffectInstance(target instanceof PlayerEntity ? Effects.BLINDNESS : Effects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.damageItem(1, attacker);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, MobEntity par6EntityLiving) {
        par1ItemStack.damageItem(2, par6EntityLiving);
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean canHarvestBlock(BlockState state) {
        return state.getBlock() instanceof BlockWeb;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, LivingEntity playerIn) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, playerIn);
        }

        return true;
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName() {
        return this.material.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     *
     * @param toRepair The ItemStack to be repaired
     * @param repair   The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = this.material.getRepairItemStack();
        if (OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by SwordItem to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
        }
        return multimap;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault1").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
                    break;
                case 2: // Slowness
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault2").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
                    break;
                case 3: // Fire
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault3").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault4").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault5").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
                    break;
                default:
                    break;
            }
        }
    }
}
