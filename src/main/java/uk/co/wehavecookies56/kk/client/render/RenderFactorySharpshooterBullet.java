package uk.co.wehavecookies56.kk.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import uk.co.wehavecookies56.kk.common.entity.projectiles.EntitySharpshooterBullet;

public class RenderFactorySharpshooterBullet implements IRenderFactory<EntitySharpshooterBullet> {

	@Override
	public Render<? super EntitySharpshooterBullet> createRenderFor (RenderManager manager) {
		return new RenderEntitySharpshooterBullet(manager);
	}

}