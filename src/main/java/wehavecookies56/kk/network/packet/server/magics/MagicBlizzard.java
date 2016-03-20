package wehavecookies56.kk.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.entities.magic.EntityBlizzaga;
import wehavecookies56.kk.entities.magic.EntityBlizzara;
import wehavecookies56.kk.entities.magic.EntityBlizzard;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnBlizzardParticles;

public class MagicBlizzard extends AbstractServerMessage<MagicBlizzard> {

	public MagicBlizzard () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (!player.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode()) player.getCapability(KingdomKeys.PLAYER_STATS, null).remMP(Constants.getCost(Strings.Spell_Blizzard));
		World world = player.worldObj;
		switch (player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Blizzard)) {
			case 1:
				world.spawnEntityInWorld(new EntityBlizzard(world, player));
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzard(world), 1), player, 64.0D);
				break;
			case 2:
				world.spawnEntityInWorld(new EntityBlizzara(world, player));
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzara(world), 1), player, 64.0D);
				break;
			case 3:
				world.spawnEntityInWorld(new EntityBlizzaga(world, player));
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzaga(world), 1), player, 64.0D);
				break;
		}
	}

}
