package werewolvesAndVampires.werewolves.rendering;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.werewolves.capability.ControlLevel;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;

public class WerewolfRenderPlayer extends RenderLivingBase<EntityPlayerSP> {
	
	private ResourceLocation calmTexture = new ResourceLocation(WVCore.MODID, "textures/entity/werewolf.png");
	private ResourceLocation angryTexture = new ResourceLocation(WVCore.MODID, "textures/entity/werewolfangry.png");
	
	public WerewolfRenderPlayer(RenderManager renderManager) {
		super(renderManager, new WerewolfModel(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityPlayerSP entity) {
		IWerewolf were = entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		return calmTexture;
	}
}
