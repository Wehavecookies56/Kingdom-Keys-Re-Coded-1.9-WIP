package wehavecookies56.kk.item;

import net.minecraft.entity.player.EntityPlayer;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.lib.Strings;

public class ItemEther extends ItemKKPotion {

	String potionType;

	public ItemEther (int food, boolean wolf, String type) {
		super(food, wolf, type, "ether");
		setUnlocalizedName(Strings.Potion);
		setAlwaysEdible();
		this.potionType = type;
	}

	@Override
	public void potionEffect (EntityPlayer player) {
		player.getCapability(KingdomKeys.PLAYER_STATS, null).addMP(player.getCapability(KingdomKeys.PLAYER_STATS, null).getMaxMP() / 3);
	}

}