package uk.co.wehavecookies56.kk.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

public class HealPlayer extends AbstractMessage.AbstractServerMessage<HealPlayer> {

	public HealPlayer () {}

	float amount;

	public HealPlayer (float amount) {
		this.amount = amount;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		amount = buffer.readFloat();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeFloat(amount);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		player.setHealth(amount);
	}
}
