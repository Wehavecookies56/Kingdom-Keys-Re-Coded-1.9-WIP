package wehavecookies56.kk.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import wehavecookies56.kk.lib.Reference;

public class ModSounds {

	public static SoundEvent
	kupo,
	summon,
	alarm,
	unsummon,
	sharpshooterbullet,
	error,
	select,
	cancel,
	move,
	drive,
	antidrive,
	potion,
	savepoint,
	savespawn,
	birthBySleepALinkToTheFuture
	//TODO The rest of the sounds, records and music
	;
	
	public static void init() {
		kupo = getSound("kupo");
	}
	
	public static SoundEvent getSound(String loc) {
		SoundEvent soundEvent = SoundEvent.soundEventRegistry.getObject(new ResourceLocation(Reference.MODID, loc));
		return soundEvent;
	}
	
}
