package wehavecookies56.kk.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.item.ItemSpellOrb;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.util.TextHelper;

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
		Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getSpellsList().clear();
		for (int i = 0; i < getSizeInventory(); i++)
			if (getStackInSlot(i) != null) Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.PLAYER_STATS, null).getSpellsList().add(((ItemSpellOrb) getStackInSlot(i).getItem()).getMagicName());
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
