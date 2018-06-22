package werewolvesAndVampires.blocks;

import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVCore;

public class BlockWolfsbaneCake extends BlockCake {
	
	public BlockWolfsbaneCake() {
		//super();
		this.setUnlocalizedName(WVCore.MODID + ".wolfsbane_cake");
		this.setRegistryName("wolfsbane_cake");
		this.setCreativeTab(WVCore.ctab);
	}
	
	@SideOnly(Side.CLIENT)
	public void regModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
}
