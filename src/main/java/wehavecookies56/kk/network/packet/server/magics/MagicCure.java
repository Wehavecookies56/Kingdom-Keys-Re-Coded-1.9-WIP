package wehavecookies56.kk.network.packet.server.magics;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.entities.magic.EntityCura;
import wehavecookies56.kk.entities.magic.EntityCuraga;
import wehavecookies56.kk.entities.magic.EntityCure;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;

public class MagicCure extends AbstractServerMessage<MagicCure> {

	public MagicCure () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		if (!ExtendedPlayer.get(player).cheatMode) player.getCapability(KingdomKeys.PLAYER_STATS, null).setMP(0);
		World world = player.worldObj;
		player.extinguish();
		switch (player.getCapability(KingdomKeys.MAGIC_STATE, null).getMagicLevel(Strings.Spell_Cure)) {
			case 1:
				if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() / 3 + player.getHealth() > player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP())
					player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() - player.getHealth());
				else
					player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() / 3);
				world.spawnEntityInWorld(new EntityCure(world, player, player.posX, player.posY, player.posZ));
				break;
			case 2:
				if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() / 3 * 2 + player.getHealth() > player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP())
					player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() - player.getHealth());
				else
					player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() / 3 * 2);
				world.spawnEntityInWorld(new EntityCura(world, player, player.posX, player.posY, player.posZ));
				break;
			case 3:
				player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP() - player.getHealth());
				world.spawnEntityInWorld(new EntityCuraga(world, player, player.posX, player.posY, player.posZ));
				break;
		}

	}
}
