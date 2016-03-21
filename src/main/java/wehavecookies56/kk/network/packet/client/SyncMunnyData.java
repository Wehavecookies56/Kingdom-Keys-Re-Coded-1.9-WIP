package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.MunnyCapability.IMunny;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncMunnyData extends AbstractClientMessage<SyncMunnyData> {

	int munny;
	
	public SyncMunnyData() {}
	
	public SyncMunnyData(IMunny munny) {
		this.munny = munny.getMunny();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.munny = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.munny);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		final IMunny munny = player.getCapability(KingdomKeys.MUNNY, null);
		munny.setMunny(this.munny);
	}

}
