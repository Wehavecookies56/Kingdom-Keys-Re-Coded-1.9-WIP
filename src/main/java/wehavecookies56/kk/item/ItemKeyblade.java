package wehavecookies56.kk.item;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.entities.ExtendedPlayer;

public class ItemKeyblade extends ItemSword {

	public ItemKeyblade (ToolMaterial material) {
		super(material);
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (stack.getItem() == ModItems.WoodenKeyblade || stack.getItem() == ModItems.WoodenStick || stack.getItem() == ModItems.DreamSword) 		return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);

		if (world.getBlockState(pos).getBlock() instanceof BlockDoor) {
			SoundEvent sound;
			IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
			if ((!STATS.getRecharge()) || player.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode()) {
				if (!player.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode()) STATS.remMP(30);

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
