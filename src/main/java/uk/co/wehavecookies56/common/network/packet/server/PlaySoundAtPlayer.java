package uk.co.wehavecookies56.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.client.sound.ModSounds;
import uk.co.wehavecookies56.common.lib.Reference;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;

public class PlaySoundAtPlayer extends AbstractServerMessage<PlaySoundAtPlayer> {

	String sound;
	float volume, pitch;

	public PlaySoundAtPlayer () {}

	public PlaySoundAtPlayer (String sound, float volume, float pitch) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		this.sound = buffer.readStringFromBuffer(100);
		this.volume = buffer.readFloat();
		this.pitch = buffer.readFloat();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(sound);
		buffer.writeFloat(volume);
		buffer.writeFloat(pitch);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		player.worldObj.playSound(null, player.getPosition(), SoundEvent.soundEventRegistry.getObject(new ResourceLocation(Reference.MODID, sound)), SoundCategory.MASTER, volume, pitch);
	}

}
