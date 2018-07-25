package werewolvesAndVampires.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;

public class WWTestItem extends BaseItem {

	public WWTestItem() {
		super("ww_test_item");
		setCreativeTab(null);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		IWerewolf were = playerIn.getCapability(WerewolfProvider.WEREWOLF_CAP, null);

		if (!playerIn.isSneaking()) {
			were.setWerewolfType(WerewolfType.INFECTED);
		} else {
			were.setWerewolfType(WerewolfType.NONE);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}
