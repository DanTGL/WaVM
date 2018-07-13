package werewolvesAndVampires.werewolves.rendering;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.werewolves.entity.EntityAngryPlayer;
import werewolvesAndVampires.werewolves.entity.WerewolfEntity;

public class RenderPlayerMob extends RenderLiving<EntityAngryPlayer> {

	private ResourceLocation angryTexture = new ResourceLocation(WVCore.MODID, "textures/entity/werewolfangry.png");

	public static final Factory FACTORY = new Factory();

	
	public RenderPlayerMob(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new WerewolfModel(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAngryPlayer entity) {
			return angryTexture;
	}
	
	public static class Factory implements IRenderFactory<EntityAngryPlayer> {

        @Override
        public RenderLiving<? super EntityAngryPlayer> createRenderFor(RenderManager manager) {
            return new RenderPlayerMob(manager);
        }

    }
	
}

