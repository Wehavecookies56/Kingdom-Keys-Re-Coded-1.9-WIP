package wehavecookies56.kk.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.GiveItemInSlot;
import wehavecookies56.kk.util.EventHandler;

public class ItemIceCream extends ItemFood {

	int win;

	public ItemIceCream (int food, boolean wolf) {
		super(food, wolf);
		setUnlocalizedName(Strings.Potion);
		setAlwaysEdible();
	}

	@Override
	public EnumAction getItemUseAction (ItemStack p_77661_1_) {
		return EnumAction.EAT;
	}

	@Override
	public void onFoodEaten (ItemStack item, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode && world.isRemote) {
			win = EventHandler.randomWithRange(0, 20);
			//ItemStack item = ItemStack(ModItems.WinnerStick);
			if (win == 3)
				PacketDispatcher.sendToServer(new GiveItemInSlot(new ItemStack(ModItems.WinnerStick)));
			//player.inventory.addItemStackToInventory(new ItemStack(ModItems.WinnerStick));
			else
				PacketDispatcher.sendToServer(new GiveItemInSlot(new ItemStack(Items.stick)));
		}
	}
}
