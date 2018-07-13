package werewolvesAndVampires.werewolves.rendering;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.entity.WerewolfEntity;

public class WerewolfRenderMob extends RenderLiving<WerewolfEntity> {

	private ResourceLocation calmTexture = new ResourceLocation(WVCore.MODID, "textures/entity/werewolf.png");
	private ResourceLocation angryTexture = new ResourceLocation(WVCore.MODID, "textures/entity/werewolfangry.png");

	public static final Factory FACTORY = new Factory();
	
	private boolean oldState = false;
	
	public WerewolfRenderMob(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelVillager(0.0F), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(WerewolfEntity entity) {
		if(entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
			return angryTexture;
		}
		return entity.getProfessionForge().getSkin();
	}
	
	@Override
	public void doRender(WerewolfEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(oldState == entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}else {
			if(entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null).getIsTransformed()) {
				oldState = true;
				mainModel = new WerewolfModel();
			}else {
				oldState = false;
				mainModel = new ModelVillager(0.0F);
			}
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}
	}
	
	public static class Factory implements IRenderFactory<WerewolfEntity> {

        @Override
        public RenderLiving<? super WerewolfEntity> createRenderFor(RenderManager manager) {
            return new WerewolfRenderMob(manager);
        }

    }
	
}
