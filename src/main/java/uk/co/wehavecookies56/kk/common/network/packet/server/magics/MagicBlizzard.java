package uk.co.wehavecookies56.kk.common.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.entity.magic.EntityBlizzaga;
import uk.co.wehavecookies56.kk.common.entity.magic.EntityBlizzara;
import uk.co.wehavecookies56.kk.common.entity.magic.EntityBlizzard;
import uk.co.wehavecookies56.kk.common.lib.Constants;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.SpawnBlizzardParticles;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncMagicData;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

public class MagicBlizzard extends AbstractMessage.AbstractServerMessage<MagicBlizzard> {

	public MagicBlizzard () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (!player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) player.getCapability(ModCapabilities.PLAYER_STATS, null).remMP(Constants.getCost(Strings.Spell_Blizzard));
		World world = player.worldObj;
		
		switch (player.getCapability(ModCapabilities.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Blizzard)) {
			case 1:
				EntityBlizzard entityBlizzard = new EntityBlizzard(world, player);
				world.spawnEntityInWorld(entityBlizzard);
				entityBlizzard.func_184538_a(player, player.rotationPitch, player.rotationYaw, 0, 1, 0);
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzard(world), 1), player, 64.0D);
				break;
			case 2:
				EntityBlizzara entityBlizzara = new EntityBlizzara(world, player);
				world.spawnEntityInWorld(entityBlizzara);
				entityBlizzara.func_184538_a(player, player.rotationPitch, player.rotationYaw, 0, 1, 0);
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzara(world), 1), player, 64.0D);
				break;
			case 3:
				EntityBlizzaga entityBlizzaga = new EntityBlizzaga(world, player);
				world.spawnEntityInWorld(entityBlizzaga);
				entityBlizzaga.func_184538_a(player, player.rotationPitch, player.rotationYaw, 0, 1, 0);
				PacketDispatcher.sendToAllAround(new SpawnBlizzardParticles(new EntityBlizzaga(world), 1), player, 64.0D);
				break;
		}
		PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(ModCapabilities.MAGIC_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}

}
