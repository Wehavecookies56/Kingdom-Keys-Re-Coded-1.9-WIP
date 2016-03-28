package uk.co.wehavecookies56.common.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.entity.magic.EntityStop;
import uk.co.wehavecookies56.common.entity.magic.EntityStopga;
import uk.co.wehavecookies56.common.entity.magic.EntityStopra;
import uk.co.wehavecookies56.common.lib.Constants;
import uk.co.wehavecookies56.common.lib.Strings;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.client.SpawnStopParticles;
import uk.co.wehavecookies56.common.network.packet.client.SyncMagicData;

public class MagicStop extends AbstractServerMessage<MagicStop> {

	public MagicStop () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (!player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) player.getCapability(ModCapabilities.PLAYER_STATS, null).remMP(Constants.getCost(Strings.Spell_Aero));
		World world = player.worldObj;
		if (!world.isRemote) switch (player.getCapability(ModCapabilities.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Stop)) {
			case 1:
				world.spawnEntityInWorld(new EntityStop(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnStopParticles(player,1), player, 64.0D);
				break;
			case 2:
				world.spawnEntityInWorld(new EntityStopra(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnStopParticles(player,2), player, 64.0D);
				break;
			case 3:
				world.spawnEntityInWorld(new EntityStopga(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnStopParticles(player,3), player, 64.0D);
				break;
		}
		PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(ModCapabilities.MAGIC_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}

}
