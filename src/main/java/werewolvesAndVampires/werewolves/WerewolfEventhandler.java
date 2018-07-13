package werewolvesAndVampires.werewolves;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVCore;
import werewolvesAndVampires.core.WVItems;
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
		if (were.getIsTransformed()) {
			e.setCanceled(true);
			if (wereRender == null)
				wereRender = new WerewolfRenderPlayer(Minecraft.getMinecraft().getRenderManager());
			wereRender.doRender((EntityPlayerSP) e.getEntityPlayer(), e.getX(), e.getY(), e.getZ(),
					e.getEntityPlayer().rotationYaw, e.getPartialRenderTick());
		}
	}

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent e) {
		EntityPlayer p = e.player;
		IWerewolf were = p.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		if (e.side.isServer() && e.player.world.getCurrentMoonPhaseFactor() == 1F && !e.player.world.isDaytime()
				&& !e.player.inventory.hasItemStack(new ItemStack(WVItems.werewolf_totem))) {

			if (!were.getIsTransformed()
					&& e.player.world.canBlockSeeSky(new BlockPos(e.player.posX, e.player.posY + 1, e.player.posZ))) {
				WerewolfHelpers.transformEntity(p, were, true);
			} else {
				/*if (were.getBloodLust() == -1) {
					switch (e.player.world.getDifficulty().getDifficultyId()) {
					case 0:
						break;
					case 1:
						List<Entity> list = e.player.world
								.getEntitiesInAABBexcluding(
										e.player, new AxisAlignedBB(e.player.posX, e.player.posY, e.player.posZ,
												e.player.posX, e.player.posY, e.player.posZ).grow(10),
										EntitySelectors.NOT_SPECTATING);
						if (!list.isEmpty())
							were.setBloodLust(0);
						break;
					case 2:
						List<Entity> list2 = e.player.world
								.getEntitiesInAABBexcluding(
										e.player, new AxisAlignedBB(e.player.posX, e.player.posY, e.player.posZ,
												e.player.posX, e.player.posY, e.player.posZ).grow(10),
										EntitySelectors.NOT_SPECTATING);
						if (!list2.isEmpty())
							were.setBloodLust(0);
						if (list2.isEmpty())
							were.setBloodLust(3600);
						// TODO Decrement each tick and do control logic
						break;
					case 3:
						were.setBloodLust(0);
						break;
					default:
						break;
					}
				} else if (were.getBloodLust() > 1) {
					were.setBloodLust(were.getBloodLust() - 1);
				} else if (were.getBloodLust() == 0) {
					WerewolfHelpers.loseControl(p);
				}*/
			}
		} else if (e.side.isServer() && !e.player.inventory.hasItemStack(new ItemStack(WVItems.werewolf_totem))) {

			if (were.getIsTransformed()) {
				WerewolfHelpers.transformEntity(p, were, false);
			}
		}

		if (were.getIsTransformed()) {
			p.stepHeight = 1.25F;
			p.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 300, 0, false, false));
			p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8);
			p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15000000149011612D);
		} else {
			p.stepHeight = 0.6F;
			p.removePotionEffect(Potion.getPotionById(16));
			p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
			p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
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

			if (were.getIsTransformed() && !extraDamageItem) {
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
					((EntityPlayerMP) e.getEntity()).getCapability(WerewolfProvider.WEREWOLF_CAP, null), e.getEntity()),
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

}
