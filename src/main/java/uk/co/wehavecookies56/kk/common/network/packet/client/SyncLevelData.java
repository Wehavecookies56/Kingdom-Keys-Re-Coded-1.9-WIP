package uk.co.wehavecookies56.kk.common.network.packet.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

import java.io.IOException;

/**
 * Created by Toby on 04/05/2016.
 */
public class SyncLevelData extends AbstractMessage.AbstractClientMessage<SyncLevelData> {

    int experience, level;

    public SyncLevelData() {}

    public SyncLevelData(PlayerStatsCapability.IPlayerStats stats) {
        this.experience = stats.getExperience();
        this.level = stats.getLevel();
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.experience = buffer.readInt();
        this.level = buffer.readInt();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(this.experience);
        buffer.writeInt(this.level);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        final PlayerStatsCapability.IPlayerStats stats = player.getCapability(ModCapabilities.PLAYER_STATS, null);
        stats.setExperience(this.experience);
        stats.setLevel(this.level);
    }
}
