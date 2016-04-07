package uk.co.wehavecookies56.kk.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.wehavecookies56.kk.common.container.inventory.InventoryKeychain;
import uk.co.wehavecookies56.kk.common.container.slot.SlotCustom;
import uk.co.wehavecookies56.kk.common.item.base.ItemKeychain;

public class ContainerKeychain extends Container {
	private static final int INV_START = InventoryKeychain.INV_SIZE, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerKeychain (EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryKeychain inventoryKeychain) {
		int i;
		addSlotToContainer(new SlotCustom(inventoryKeychain, 0, 80, 30, 1));
		for (i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith (EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot (EntityPlayer player, int par2) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) return null;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (itemstack1.getItem() instanceof ItemKeychain) {
				if (!mergeItemStack(itemstack1, 0, InventoryKeychain.INV_SIZE, false)) return null;
			} else if (par2 >= INV_START && par2 < HOTBAR_START) {
				if (!mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_START + 1, false)) return null;
			} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) if (!mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize) return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
