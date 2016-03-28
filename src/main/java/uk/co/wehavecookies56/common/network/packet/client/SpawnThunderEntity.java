package uk.co.wehavecookies56.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractClientMessage;

public class SpawnThunderEntity extends AbstractClientMessage<SpawnThunderEntity> {

	double x, y, z;
	int lvl;
	double ex, ey, ez;

	public SpawnThunderEntity () {}

	public SpawnThunderEntity (Entity entity, int level) {
		x = entity.posX;
		y = entity.posY;
		z = entity.posZ;
		lvl = level;
	}

	public SpawnThunderEntity (double ex, double ey, double ez) {
		this.ex = ex;
		this.ey = ey;
		this.ez = ez;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		lvl = buffer.readInt();
		ex = buffer.readDouble();
		ey = buffer.readDouble();
		ez = buffer.readDouble();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeInt(lvl);
		buffer.writeDouble(ex);
		buffer.writeDouble(ey);
		buffer.writeDouble(ez);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		player.worldObj.addWeatherEffect((new EntityLightningBolt(player.worldObj, ex, ey, ez, false)));
	}
}
