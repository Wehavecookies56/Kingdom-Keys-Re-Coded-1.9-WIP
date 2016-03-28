package uk.co.wehavecookies56.kk.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.entity.PlayerLevel;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

public class SyncStatMessagesPacket extends AbstractMessage.AbstractClientMessage<SyncStatMessagesPacket> {

	String stat;

	public SyncStatMessagesPacket () {}

	public SyncStatMessagesPacket (String stat) {
		this.stat = stat;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		this.stat = buffer.readStringFromBuffer(3);
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(this.stat);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (this.stat != null) {
			if (this.stat.equals("clr")) PlayerLevel.messages.clear();
			if (this.stat.equals("def")) PlayerLevel.messages.add("def");
			if (this.stat.equals("str")) PlayerLevel.messages.add("str");
			if (this.stat.equals("mag")) PlayerLevel.messages.add("mag");
			if (this.stat.equals("hp")) PlayerLevel.messages.add("hp");
			if (this.stat.equals("fir")) PlayerLevel.messages.add("fir");
			if (this.stat.equals("bli")) PlayerLevel.messages.add("bli");
			if (this.stat.equals("thu")) PlayerLevel.messages.add("thu");
			if (this.stat.equals("cur")) PlayerLevel.messages.add("cur");
			if (this.stat.equals("gra")) PlayerLevel.messages.add("gra");
			if (this.stat.equals("aer")) PlayerLevel.messages.add("aer");
			if (this.stat.equals("sto")) PlayerLevel.messages.add("sto");
		}
	}

}
