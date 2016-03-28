package uk.co.wehavecookies56.common.container.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import uk.co.wehavecookies56.common.item.ItemSpellOrb;
import uk.co.wehavecookies56.common.lib.Strings;
import uk.co.wehavecookies56.common.core.helper.TextHelper;

public class InventorySpells extends AbstractInventory {
	private final String name = TextHelper.localize(Strings.SpellsInventory);

	/** The key used to store and retrieve the inventory from NBT */
	private static final String SAVE_KEY = "SpellsInvKey";
	private ItemStack[] inv;
	public static final int INV_SIZE = 7;

	public InventorySpells () {
		this.inventory = new ItemStack[INV_SIZE];
	}

	@Override
	public boolean hasCustomName () {
		return true;
	}

	@Override
	public int getInventoryStackLimit () {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer (EntityPlayer player) {
		return true;
	}

	@Override
	public void markDirty () {
		super.markDirty();
	}

	@Override
	public boolean isItemValidForSlot (int index, ItemStack stack) {
		return stack.getItem() instanceof ItemSpellOrb;
	}

	@Override
	protected String getNbtKey () {
		return SAVE_KEY;
	}

	public void copy (AbstractInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			inventory[i] = (stack == null ? null : stack.copy());
		}
		markDirty();
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public ITextComponent getDisplayName () {
		return new TextComponentString(name);
	}
}
