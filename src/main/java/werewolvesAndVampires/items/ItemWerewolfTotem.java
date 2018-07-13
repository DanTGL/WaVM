package werewolvesAndVampires.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import werewolvesAndVampires.packets.PacketRegister;
import werewolvesAndVampires.packets.SyncWerewolfCap;
import werewolvesAndVampires.werewolves.WerewolfHelpers;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;

public class ItemWerewolfTotem extends BaseItem {
	
	public ItemWerewolfTotem() {
		super("werewolf_totem");
		this.setCreativeTab(CreativeTabs.MISC);
		this.setMaxDamage(10);
		this.maxStackSize = 1;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
		IWerewolf were = playerIn.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
		WerewolfHelpers.transformEntity(playerIn, were, !were.getIsTransformed());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
}
