package werewolvesAndVampires.werewolves;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import werewolvesAndVampires.core.WVItems;
import werewolvesAndVampires.core.WVPotions;
import werewolvesAndVampires.packets.PacketRegister;
import werewolvesAndVampires.packets.SyncWerewolfCap;
import werewolvesAndVampires.packets.WerewolfCameraUpdate;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.entity.EntityAngryPlayer;

public class WerewolfHelpers {
	public static Random rand = new Random();


	public static void transformEntity(EntityLivingBase p, IWerewolf were, boolean transform) {
		were.setIsTransformed(transform);
		PacketRegister.INSTANCE.sendToDimension(new SyncWerewolfCap(were, p), p.dimension);
		if (p instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) p;
			for (int i = 0; i < pl.inventory.mainInventory.size(); ++i) {
				if (pl.inventory.mainInventory.get(i).getItem() == WVItems.werewolf_totem) {
					pl.dropItem(pl.inventory.mainInventory.get(i), true, false);
					pl.inventory.mainInventory.set(i, ItemStack.EMPTY);
				}
			}
			for (int i = 0; i < pl.inventory.armorInventory.size(); ++i) {
				if (pl.inventory.armorInventory.get(i).getItem() == WVItems.werewolf_totem) {
					pl.inventory.armorInventory.get(i).damageItem(Integer.MAX_VALUE, pl);
				}
			}
		}
		
		if (!transform && p instanceof EntityPlayer)
			gainControl((EntityPlayer) p, were);

		WorldServer ws = (WorldServer) p.world;
		int i = 0;
		while (i < 250) {
			ws.spawnParticle(EnumParticleTypes.CLOUD, p.posX + rand.nextGaussian(), p.posY + rand.nextGaussian(),
					p.posZ + rand.nextGaussian(), 1, 0, 1, 0, 0, null);
			++i;
		}

		p.addPotionEffect(new PotionEffect(WVPotions.WW_HANGOVER, 1, 0, false, false));
		if (were.getIsTransformed()) {
			p.setEntityBoundingBox(p.getEntityBoundingBox().expand(0, 1, 0));
		} else {
			p.setEntityBoundingBox(p.getEntityBoundingBox().contract(0, 1, 0));
		}
	}

	public static void loseControl(EntityPlayer p, IWerewolf were) {
		EntityAngryPlayer ap = new EntityAngryPlayer(p.world, p);
		p.world.spawnEntity(ap);
		PacketRegister.INSTANCE.sendTo(new WerewolfCameraUpdate(ap.getEntityId()), (EntityPlayerMP) p);
		were.setBloodLust(200);
		were.setEntity(ap.getEntityId());
	}

	public static void gainControl(EntityPlayer p, IWerewolf were) {
		if(p.world.getEntityByID(were.getEntity()) instanceof EntityAngryPlayer)
		((EntityAngryPlayer)p.world.getEntityByID(were.getEntity())).setIdNull();
		were.setEntity(0);
		PacketRegister.INSTANCE.sendTo(new WerewolfCameraUpdate(p.getEntityId()), (EntityPlayerMP) p);
	}
	

	public static long timeUntilFullMoon(World world) {
		if (world != null) {
			long worldTime = world.getWorldTime();

			MoonPhase moonPhase = MoonPhase.byOrdinal(world.getMoonPhase());

			long timeSinceBeginningOfDay = (long) (worldTime - Math.floor(worldTime / 24000) * 24000);
			long timeUntilMoonRises = 12566 - timeSinceBeginningOfDay;

			return moonPhase == MoonPhase.FULL ? (timeUntilMoonRises < 0 ? 0 : timeUntilMoonRises) : (moonPhase.getDaysToFullMoon() * 24000 + (24000 - 12566)) - timeSinceBeginningOfDay;
		}
		return  1;
	}

	public static void controlTick(TickEvent.PlayerTickEvent e, IWerewolf were) {
		if (were.getBloodLust() == 0) {
			switch (e.player.world.getDifficulty()) {
			case EASY:
				if (!e.player.world.getEntitiesInAABBexcluding(e.player, e.player.getEntityBoundingBox().grow(10),
						EntitySelectors.CAN_AI_TARGET).isEmpty())
					loseControl(e.player, were);
				break;
			case HARD:
				if (!e.player.world.getEntitiesInAABBexcluding(e.player, e.player.getEntityBoundingBox().grow(20),
						EntitySelectors.CAN_AI_TARGET).isEmpty())
					loseControl(e.player, were);
				break;
			case NORMAL:
				if (!e.player.world.getEntitiesInAABBexcluding(e.player, e.player.getEntityBoundingBox().grow(15),
						EntitySelectors.CAN_AI_TARGET).isEmpty())
					loseControl(e.player, were);
				break;
			case PEACEFUL:
				break;
			default:
				break;
			}
		}else if(were.getBloodLust() > 1) {
			were.setBloodLust(were.getBloodLust() - 1);
		}else if(were.getBloodLust() == 1) {
			gainControl(e.player, were);
			were.setBloodLust(0);
		}
	}
}
