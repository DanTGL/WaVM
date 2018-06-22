package werewolvesAndVampires.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.core.WVCore;

public class BlockWolfsbane extends BlockBush {
	
	public BlockWolfsbane() {
		super();
		this.setUnlocalizedName(WVCore.MODID + ".wolfsbane");
		this.setRegistryName("wolfsbane");
		this.setCreativeTab(WVCore.ctab);
	}
	
	@SideOnly(Side.CLIENT)
	public void regModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
}
