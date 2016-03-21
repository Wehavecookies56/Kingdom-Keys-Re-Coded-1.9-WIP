package wehavecookies56.kk.client.audio;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import wehavecookies56.kk.lib.Reference;

public class ModSounds {

	public static final SoundEvent kupo;
	//TODO The rest of the sounds, records and music
	;
	
	private static SoundEvent getRegisteredSoundEvent(String id)
    {
        SoundEvent soundevent = (SoundEvent)SoundEvent.soundEventRegistry.getObject(new ResourceLocation(id));

        if (soundevent == null)
        {
            throw new IllegalStateException("Invalid Sound requested: " + id);
        }
        else
        {
            return soundevent;
        }
    }

    static
    {
        if (!Bootstrap.isRegistered())
        {
            throw new RuntimeException("Accessed Sounds before Bootstrap!");
        }
        else
        {
            kupo = getRegisteredSoundEvent("kupo");
        }
    }
}
