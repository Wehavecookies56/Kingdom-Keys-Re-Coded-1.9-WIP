package wehavecookies56.kk.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.entities.magic.EntityAero;
import wehavecookies56.kk.entities.magic.EntityAeroga;
import wehavecookies56.kk.entities.magic.EntityAerora;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnAeroParticles;
import wehavecookies56.kk.network.packet.client.SyncMagicData;

public class MagicAero extends AbstractServerMessage<MagicAero> {

	public MagicAero () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override 
	public void process (EntityPlayer player, Side side) {
		if (!player.getCapability(KingdomKeys.CHEAT_MODE, null).getCheatMode()) player.getCapability(KingdomKeys.PLAYER_STATS, null).remMP(Constants.getCost(Strings.Spell_Aero));
		World world = player.worldObj;
		if (!world.isRemote) {
			switch (player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Aero)) {
			case 1:
				world.spawnEntityInWorld(new EntityAero(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnAeroParticles(player,1), player, 64.0D);
				break;
			case 2:
				world.spawnEntityInWorld(new EntityAerora(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnAeroParticles(player,2), player, 64.0D);
				break;
			case 3:
				world.spawnEntityInWorld(new EntityAeroga(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnAeroParticles(player,3), player, 64.0D);
				break;
			}
			PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(KingdomKeys.MAGIC_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
		}
	}

}
