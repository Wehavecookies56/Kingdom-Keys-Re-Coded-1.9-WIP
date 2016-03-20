package wehavecookies56.kk.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.item.ItemKKPotion;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;

public class PotionConsume extends AbstractServerMessage<PotionConsume> {

	public PotionConsume () {}

	String potion;

	public PotionConsume (String potion) {
		this.potion = potion;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		potion = buffer.readStringFromBuffer(100);
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(potion);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		switch (potion) {
		case "potion":
			((ItemKKPotion) ModItems.Potion).getPotionEffect(player);
			player.heal(10);
			break;
		case "ether":
			((ItemKKPotion) ModItems.Ether).getPotionEffect(player);
			player.getCapability(KingdomKeys.PLAYER_STATS, null).addMP(33);
			break;
		case "elixir":
			((ItemKKPotion) ModItems.Elixir).getPotionEffect(player);
			player.heal(10);
			player.getCapability(KingdomKeys.PLAYER_STATS, null).addMP(33);
			break;
		default:
			break;
	}
	}
}
