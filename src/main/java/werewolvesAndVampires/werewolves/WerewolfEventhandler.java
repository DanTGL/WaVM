package werewolvesAndVampires.werewolves;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.core.WVItems;
import werewolvesAndVampires.core.WVPotions;
import werewolvesAndVampires.packets.PacketRegister;
import werewolvesAndVampires.packets.SyncWerewolfCap;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;
import werewolvesAndVampires.werewolves.entity.EntityAngryPlayer;
import werewolvesAndVampires.werewolves.entity.WerewolfEntity;
import werewolvesAndVampires.werewolves.rendering.WerewolfRenderPlayer;

@Mod.EventBusSubscriber
public class WerewolfEventhandler {

	public static final ResourceLocation werewolfCapLoc = new ResourceLocation(WVCore.MODID, "werewolf");

	@SideOnly(Side.CLIENT)
	private static WerewolfRenderPlayer wereRender = null;

	@SubscribeEvent
	public static void attachCapabilitys(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer || e.getObject() instanceof EntityVillager
				|| e.getObject() instanceof EntityAngryPlayer) {
			e.addCapability(werewolfCapLoc, new WerewolfProvider());
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void renderPlayer(RenderPlayerEvent.Pre e) {
		IWerewolf were = e.getEntityPlayer().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (were.getIsTransformed() && Minecraft.getMinecraft().getRenderViewEntity() == e.getEntityPlayer()) {
			e.setCanceled(true);
			if (wereRender == null)
				wereRender = new WerewolfRenderPlayer(Minecraft.getMinecraft().getRenderManager());

			wereRender.doRender((EntityPlayerSP) e.getEntityPlayer(), e.getX(), e.getY(), e.getZ(),
					e.getEntityPlayer().rotationYaw, e.getPartialRenderTick());
		}else if(were.getIsTransformed()) {
			e.setCanceled(true);
		}
	}

	public static Entity cam = null;

	@SubscribeEvent
	public static void onPreRender(RenderGameOverlayEvent.Pre e) {
		if (cam != null) {
			Minecraft.getMinecraft().setRenderViewEntity(cam); 
		} else {
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		}
	}

	@SubscribeEvent
	public static void copy(PlayerEvent.Clone e) {
		if (e.isWasDeath()) {
			IWerewolf were = e.getEntityPlayer().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			IWerewolf old = e.getOriginal().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			were.setBloodLust(old.getBloodLust());
			were.setIsTransformed(old.getIsTransformed());
			were.setWerewolfType(old.getWerewolfType());
			were.setTransformCount(old.getTransformCount());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone e) {
		if (e.isWasDeath()) {
			PotionEffect effect = e.getOriginal().getActivePotionEffect(WVPotions.WW_FEVER);
			if (effect != null) {
				e.getEntityPlayer().addPotionEffect(effect);
			}
		}
	}

	@SubscribeEvent
	public static void livingTick(LivingEvent.LivingUpdateEvent e) {
	}

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent e) {
		//System.out.println(WerewolfHelpers.timeUntilFullMoon(e.player.world));
		EntityPlayer p = e.player;
		World world = p.world;
		BlockPos pos = p.getPosition();

		IWerewolf were = p.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (e.side.isServer() && e.player.world.getCurrentMoonPhaseFactor() == 1F && !e.player.world.isDaytime()
				&& !e.player.inventory.hasItemStack(new ItemStack(WVItems.werewolf_totem))
				&& were.getWerewolfType() == WerewolfType.FULL) {

			if (!were.getIsTransformed()
					&& e.player.world.canBlockSeeSky(new BlockPos(e.player.posX, e.player.posY + 1, e.player.posZ))) {
				WerewolfHelpers.transformEntity(p, were, true);
			} else {
				WerewolfHelpers.controlTick(e, were);
			}
		} else if (e.side.isServer() && !e.player.inventory.hasItemStack(new ItemStack(WVItems.werewolf_totem))) {
			if (were.getIsTransformed()) {
				p.stepHeight = 1.25F;
				p.eyeHeight  = 1.97F;
				p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
				p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8);
				//p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15000000149011612D);
			} else {
				p.stepHeight = 0.6F;
				p.eyeHeight  = p.getDefaultEyeHeight();
				p.removePotionEffect(MobEffects.NIGHT_VISION);
				p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
				p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
			}

			if (were.getWerewolfType() == WerewolfType.INFECTED) {
				if (p.getActivePotionEffect(WVPotions.WW_FEVER) == null)
					p.addPotionEffect(new PotionEffect(WVPotions.WW_FEVER, 2 * 20, 0, false, false));
			} else {
				if (p.getActivePotionEffect(WVPotions.WW_FEVER) != null)
					p.removePotionEffect(WVPotions.WW_FEVER);
			}
			if (were.getWerewolfType() == WerewolfType.INFECTED) {
				were.setWerewolfType(WerewolfType.FULL);
			}
		}
	}

	@SubscribeEvent
	public static void getCollisionBoxesEvent(GetCollisionBoxesEvent e) {
		Entity entity = e.getEntity();
		if (entity != null) {
			AxisAlignedBB originalBB = entity.getEntityBoundingBox();
			IWerewolf were = e.getEntity().getCapability(WerewolfProvider.WEREWOLF_CAP, null);

			if (were != null && were.getIsTransformed()) {
				AxisAlignedBB newBB = originalBB.setMaxY(entity.posY+2.4);
				entity.setEntityBoundingBox(newBB);
			}
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent e) {
		if (e.getEntityLiving().hasCapability(WerewolfProvider.WEREWOLF_CAP, null) && !e.getEntity().world.isRemote) {
			IWerewolf were = e.getEntityLiving().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (were.getIsTransformed() && e.getDistance() < 20) {
				e.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent e) {
		if (e.getEntityLiving().hasCapability(WerewolfProvider.WEREWOLF_CAP, null) && e.getEntity().world.isRemote) {
			EntityLivingBase p = e.getEntityLiving();
			IWerewolf were = p.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (were.getIsTransformed()) {
				p.motionY += 0.2;
			}
		}
	}

	@SubscribeEvent
	public static void onDamage(LivingHurtEvent e) {
		if (e.getEntityLiving().hasCapability(WerewolfProvider.WEREWOLF_CAP, null) && !e.getEntity().world.isRemote) {
			IWerewolf were = e.getEntityLiving().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			if (e.getSource().getTrueSource() == null)
				return;
			Iterator<ItemStack> i = e.getSource().getTrueSource().getHeldEquipment().iterator();

			boolean extraDamageItem = false;
			while (i.hasNext()) {
				ItemStack is = i.next();
				if (is.getItem().getItemStackDisplayName(is).contains("gold"))
					extraDamageItem = true;
				if (is.getItem().getItemStackDisplayName(is).contains("Gold"))
					extraDamageItem = true;
			}

			if (were.getIsTransformed() && !extraDamageItem && were.getWerewolfType() == WerewolfType.FULL) {
				if (e.getAmount() < 4) {
					e.setCanceled(true);
				} else {
					e.setAmount(e.getAmount() / 2);
				}
			} else if (extraDamageItem) {
				e.setAmount(e.getAmount() + (e.getAmount() / 2));
			}
		}
	}

	@SubscribeEvent
	public static void onJoin(EntityJoinWorldEvent e) {
		if (e.getEntity() instanceof EntityPlayer && !e.getWorld().isRemote) {
			PacketRegister.INSTANCE.sendTo(new SyncWerewolfCap(
					e.getEntity().getCapability(WerewolfProvider.WEREWOLF_CAP, null), e.getEntity()),
					((EntityPlayerMP) e.getEntity()));
		}

		if (!e.getWorld().isRemote && e.getEntity() instanceof EntityVillager
				&& WerewolfHelpers.rand.nextDouble() > 0.5) {
			WerewolfEntity werewolf = new WerewolfEntity(e.getWorld());
			werewolf.setPosition(e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ);
			e.getWorld().spawnEntity(werewolf);
			IWerewolf were = werewolf.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
			were.setWerewolfType(WerewolfType.FULL);
			e.setCanceled(true);
		}

		if (e.getEntity() instanceof EntityVillager && !e.getWorld().isRemote) {
			PacketRegister.INSTANCE.sendToDimension(
					new SyncWerewolfCap(e.getEntity().getCapability(WerewolfProvider.WEREWOLF_CAP, null),
							e.getEntity()),
					e.getEntity().dimension);
		}
	}

	static String[] poss = new String[] { "hoowl ", "aarf ", "grrr " };

	@SubscribeEvent
	public static void chatEvent(ServerChatEvent e) {
		IWerewolf were = e.getPlayer().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (were.getIsTransformed() && were.getWerewolfType() == WerewolfType.FULL) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void interact(PlayerInteractEvent.RightClickBlock e) {
		IWerewolf were = e.getEntityPlayer().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (were.getIsTransformed() && were.getWerewolfType() == WerewolfType.FULL) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void pickupEvent(EntityItemPickupEvent e) {
		IWerewolf were = e.getEntityPlayer().getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (were.getIsTransformed() && e.getItem().getItem().getItem() != WVItems.werewolf_totem
				&& were.getWerewolfType() == WerewolfType.FULL) {
			e.setCanceled(true);
		}
	}
}
