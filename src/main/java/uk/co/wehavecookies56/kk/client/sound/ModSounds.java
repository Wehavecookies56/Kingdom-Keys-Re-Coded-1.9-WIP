package uk.co.wehavecookies56.kk.client.sound;

import java.lang.reflect.Field;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import uk.co.wehavecookies56.kk.common.lib.Reference;

public class ModSounds {

	public static final SoundEvent 
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
	Birth_by_Sleep_A_Link_to_the_Future,
	Darkness_of_the_Unknown,
	Dearly_Beloved_Symphony_Version,
	Dream_Drop_Distance_The_Next_Awakening,
	Hikari_KINGDOM_Instrumental_Version,
	L_Oscurita_Dell_Ignoto,
	Musique_pour_la_tristesse_de_Xion,
	No_More_Bugs_Bug_Version,
	Organization_XIII,
	Sanctuary,
	Simple_And_Clean_PLANITb_Remix,
	Sinister_Sundown,
	The_13th_Anthology
	//TODO music
	;
	
	private static SoundEvent getRegisteredSoundEvent(String id)
    {
		Field soundEventId = ReflectionHelper.findField(SoundEvent.class, "soundEventId", "field_187507_c");
		try {
			SoundEvent.soundEventRegistry.register((int) soundEventId.get(new SoundEvent(new ResourceLocation(Reference.MODID, id))), new ResourceLocation(Reference.MODID, id), new SoundEvent(new ResourceLocation(Reference.MODID, id)));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
        SoundEvent soundevent = (SoundEvent)SoundEvent.soundEventRegistry.getObject(new ResourceLocation(Reference.MODID, id));

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
            summon = getRegisteredSoundEvent("summon");
            alarm = getRegisteredSoundEvent("alarm");
            unsummon = getRegisteredSoundEvent("unsummon");
            sharpshooterbullet = getRegisteredSoundEvent("sharpshooterbullet");
            error = getRegisteredSoundEvent("error");
            select = getRegisteredSoundEvent("select");
            cancel = getRegisteredSoundEvent("cancel");
            move = getRegisteredSoundEvent("move");
            drive = getRegisteredSoundEvent("drive");
            antidrive = getRegisteredSoundEvent("antidrive");
            potion = getRegisteredSoundEvent("potion");
            savepoint = getRegisteredSoundEvent("savepoint");
            savespawn = getRegisteredSoundEvent("savespawn");
            Birth_by_Sleep_A_Link_to_the_Future = getRegisteredSoundEvent("records.Birth by Sleep -A Link to the Future-");
            Darkness_of_the_Unknown = getRegisteredSoundEvent("records.Darkness of the Unknown");
            Dearly_Beloved_Symphony_Version = getRegisteredSoundEvent("records.Dearly Beloved -Symphony Version-");
            Dream_Drop_Distance_The_Next_Awakening = getRegisteredSoundEvent("records.Dream Drop Distance -The Next Awakening-");
            Hikari_KINGDOM_Instrumental_Version = getRegisteredSoundEvent("records.Hikari -KINGDOM Instrumental Version-");
            L_Oscurita_Dell_Ignoto = getRegisteredSoundEvent("records.L'Oscurita Dell'Ignoto");
            Musique_pour_la_tristesse_de_Xion = getRegisteredSoundEvent("records.Musique pour la tristesse de Xion");
            No_More_Bugs_Bug_Version = getRegisteredSoundEvent("records.No More Bugs -Bug Version-");
            Organization_XIII = getRegisteredSoundEvent("records.Organization XIII");
            Sanctuary = getRegisteredSoundEvent("records.Sanctuary");
            Simple_And_Clean_PLANITb_Remix = getRegisteredSoundEvent("records.Simple And Clean PLANITb Remix");
            Sinister_Sundown = getRegisteredSoundEvent("records.Sinister Sundown");
            The_13th_Anthology = getRegisteredSoundEvent("records.The 13th Anthology");
        }
    }
}
