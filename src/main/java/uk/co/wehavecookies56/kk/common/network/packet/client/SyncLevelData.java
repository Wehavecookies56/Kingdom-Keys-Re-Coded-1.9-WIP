package uk.co.wehavecookies56.kk.common.network.packet.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toby on 04/05/2016.
 */
public class SyncLevelData extends AbstractMessage.AbstractClientMessage<SyncLevelData> {

    int experience, level, strength, magic, defense, hp;
    List<String> messages;

    public SyncLevelData() {}

    public SyncLevelData(PlayerStatsCapability.IPlayerStats stats) {
        this.experience = stats.getExperience();
        this.level = stats.getLevel();
        this.defense = stats.getDefense();
        this.strength = stats.getStrength();
        this.magic = stats.getMagic();
        this.hp = stats.getHP();
        this.messages = stats.getMessages();
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.experience = buffer.readInt();
        this.level = buffer.readInt();
        this.defense = buffer.readInt();
        this.strength = buffer.readInt();
        this.magic = buffer.readInt();
        this.hp = buffer.readInt();
        this.messages = new ArrayList<String>();
        while(buffer.isReadable()) {
            this.messages.add(buffer.readStringFromBuffer(100));
        }
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(this.experience);
        buffer.writeInt(this.level);
        buffer.writeInt(this.defense);
        buffer.writeInt(this.strength);
        buffer.writeInt(this.magic);
        buffer.writeInt(this.hp);
        for (int i = 0; i < this.messages.size(); i++) {
            buffer.writeString(this.messages.get(i));
        }
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        final PlayerStatsCapability.IPlayerStats stats = player.getCapability(ModCapabilities.PLAYER_STATS, null);
        stats.setExperience(this.experience);
        stats.setLevel(this.level);
        stats.setDefense(this.defense);
        stats.setStrength(this.strength);
        stats.setMagic(this.magic);
        stats.setHP(this.hp);
        stats.getMessages().clear();
        for (int i = 0; i < this.messages.size(); i++) {
            stats.getMessages().add(this.messages.get(i));
        }
    }
}
