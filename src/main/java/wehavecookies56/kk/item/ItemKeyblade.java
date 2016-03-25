package wehavecookies56.kk.item;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.AttackEntity;

public class ItemKeyblade extends ItemSword {

	public ItemKeyblade (ToolMaterial material) {
		super(material);
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
		if(player.isSneaking()) {
			
		} else {
			if (worldIn.isRemote){
				RayTraceResult rtr = Minecraft.getMinecraft().objectMouseOver;
				if (player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemKeyblade) {
					if(player.swingProgress <= 0)
						player.swingArm(hand.OFF_HAND);
					if (rtr.entityHit != null) {
						PacketDispatcher.sendToServer(new AttackEntity(rtr.entityHit.getEntityId()));
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
					}
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
				}
				
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (stack.getItem() == ModItems.WoodenKeyblade || stack.getItem() == ModItems.WoodenStick || stack.getItem() == ModItems.DreamSword)
			return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);

		if (world.getBlockState(pos).getBlock() instanceof BlockDoor) {
			SoundEvent sound;
			IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
			if ((!STATS.getRecharge()) || player.getCapability(KingdomKeys.CHEAT_MODE, null).getCheatMode()) {
				if (!player.getCapability(KingdomKeys.CHEAT_MODE, null).getCheatMode()) STATS.remMP(30);

				if (world.getBlockState(pos).getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
					world.setBlockState(pos.down(), world.getBlockState(pos.down()).withProperty(BlockDoor.OPEN, !world.getBlockState(pos.down()).getValue(BlockDoor.OPEN)));
					sound = world.getBlockState(pos.down()).getValue(BlockDoor.OPEN) ? SoundEvents.block_iron_door_close : SoundEvents.block_iron_door_open;
					world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
					return EnumActionResult.SUCCESS;
				} else {
					world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockDoor.OPEN, !world.getBlockState(pos).getValue(BlockDoor.OPEN)));
					sound = world.getBlockState(pos).getValue(BlockDoor.OPEN) ? SoundEvents.block_iron_door_close : SoundEvents.block_iron_door_open;
					world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}
