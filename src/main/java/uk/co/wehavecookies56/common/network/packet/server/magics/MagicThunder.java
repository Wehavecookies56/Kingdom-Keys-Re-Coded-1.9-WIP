package uk.co.wehavecookies56.common.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.entity.magic.EntityThundaga;
import uk.co.wehavecookies56.common.entity.magic.EntityThundara;
import uk.co.wehavecookies56.common.entity.magic.EntityThunder;
import uk.co.wehavecookies56.common.lib.Constants;
import uk.co.wehavecookies56.common.lib.Strings;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.client.SpawnThunderParticles;
import uk.co.wehavecookies56.common.network.packet.client.SyncMagicData;

public class MagicThunder extends AbstractServerMessage<MagicThunder> {

	public MagicThunder () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (!player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode()) player.getCapability(ModCapabilities.PLAYER_STATS, null).remMP(Constants.getCost(Strings.Spell_Thunder));
		World world = player.worldObj;

		if (!world.isRemote) switch (player.getCapability(ModCapabilities.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Thunder)) {
			case 1:
				world.spawnEntityInWorld(new EntityThunder(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnThunderParticles(player,1), player, 64.0D);
				break;
			case 2:
				world.spawnEntityInWorld(new EntityThundara(world, player, player.posX, player.posY, player.posZ));
				PacketDispatcher.sendToAllAround(new SpawnThunderParticles(player,2), player, 64.0D);
				break;
			case 3:
				world.spawnEntityInWorld(new EntityThundaga(world, player, player.posX, player.posY, player.posZ));	
				PacketDispatcher.sendToAllAround(new SpawnThunderParticles(player,3), player, 64.0D);
				break;
		}
		PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(ModCapabilities.MAGIC_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}
}