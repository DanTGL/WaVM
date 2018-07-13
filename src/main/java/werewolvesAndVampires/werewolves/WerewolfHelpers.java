package werewolvesAndVampires.werewolves;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import werewolvesAndVampires.packets.PacketRegister;
import werewolvesAndVampires.packets.SyncWerewolfCap;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.entity.EntityAngryPlayer;

public class WerewolfHelpers {
	public static Random rand = new Random();
	public static Map<String,Integer> playerTowereform = new HashMap<String,Integer>();
	
	public static void transformEntity(EntityLivingBase p, IWerewolf were, boolean transform) {
		were.setIsTransformed(transform);
		PacketRegister.INSTANCE.sendToDimension(new SyncWerewolfCap(were, p), p.dimension);
		WorldServer ws = (WorldServer) p.world;
		int i = 0;
		while (i < 450) {
			ws.spawnParticle(EnumParticleTypes.CLOUD, p.posX + rand.nextGaussian(),
					p.posY + rand.nextGaussian(), p.posZ + rand.nextGaussian(), 1, 0, 1, 0, 0,
					null);
			++i;
		}
		
		if(were.getIsTransformed()) {
			p.setEntityBoundingBox(p.getEntityBoundingBox().expand(0, 1, 0));
		}else {
			p.setEntityBoundingBox(p.getEntityBoundingBox().contract(0, 1, 0));
		}
			
	}
	
	public static void loseControl(EntityPlayer p) {
		EntityAngryPlayer ap = new EntityAngryPlayer(p.world, p);
		p.world.spawnEntity(ap);
	}
	
	public static void gainControl(EntityPlayer p) {
		p.world.getEntityByID(playerTowereform.get(p.getName())).setDead();
	}
	
}
