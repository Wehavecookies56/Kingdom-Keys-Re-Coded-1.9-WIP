package wehavecookies56.kk.network.packet.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.capabilities.SynthesisMaterialCapability.ISynthesisMaterial;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncMaterialData extends AbstractClientMessage<SyncMaterialData> {

	private Map<String, Integer> materials;
	
	public SyncMaterialData() {}
	
	public SyncMaterialData(ISynthesisMaterial material) {
		this.materials = material.getKnownMaterialsMap();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		materials = new HashMap<String, Integer>();
		while(buffer.isReadable()){
			materials.put(buffer.readStringFromBuffer(100), buffer.readInt());
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		Iterator<Entry<String, Integer>> it = materials.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
			buffer.writeString(pair.getKey().toString());
			buffer.writeInt(pair.getValue());
		}
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		final ISynthesisMaterial material = player.getCapability(KingdomKeys.SYNTHESIS_MATERIALS, null);
		Iterator<Entry<String, Integer>> it = materials.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
			material.setMaterial(MaterialRegistry.get(pair.getKey().toString()), pair.getValue());
		}
	}


}
