package werewolvesAndVampires.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.WorldTimeQueue;
import werewolvesAndVampires.core.WVBlocks;
import werewolvesAndVampires.core.WVItems;
import werewolvesAndVampires.werewolves.WerewolfHelpers;
import werewolvesAndVampires.werewolves.capability.IWerewolf;
import werewolvesAndVampires.werewolves.capability.WerewolfProvider;
import werewolvesAndVampires.werewolves.capability.WerewolfType;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockWolfsbaneDust extends BaseBlock {

	public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);

	protected static final AxisAlignedBB[] DUST_AABB = new AxisAlignedBB[]{
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D)
	};

	public BlockWolfsbaneDust() {
		super(Material.CIRCUITS, MapColor.MAGENTA, "wolfsbane_dust");
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, EnumAttachPosition.NONE)
				.withProperty(EAST, EnumAttachPosition.NONE)
				.withProperty(SOUTH, EnumAttachPosition.NONE)
				.withProperty(WEST, EnumAttachPosition.NONE));

	}


	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
//		EntityPlayer player = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2, p -> {
//			IWerewolf were = p.getCapability(WerewolfProvider.WEREWOLF_CAP, null);
//			return were != null && were.getWerewolfType() == WerewolfType.FULL;
//		});
//
//		if (player != null) {
//
//			boolean flag  = false;
//			boolean flag1 = false;
//			boolean flag3 = false;
//
//			stateIn = stateIn.getActualState(worldIn, pos);
//
//			EnumAttachPosition north = stateIn.getValue(NORTH);
//			EnumAttachPosition south = stateIn.getValue(SOUTH);
//			EnumAttachPosition west  = stateIn.getValue(WEST);
//			EnumAttachPosition east  = stateIn.getValue(EAST);
//
//			if (north == EnumAttachPosition.SIDE && south == EnumAttachPosition.SIDE && west == EnumAttachPosition.SIDE && east == EnumAttachPosition.SIDE) {
//				flag3 = true;
//			} if (north == EnumAttachPosition.SIDE || south == EnumAttachPosition.SIDE) {
//				flag1 = true;
//			} else if (west == EnumAttachPosition.SIDE || east == EnumAttachPosition.SIDE) {
//				flag = true;
//			}
//
//			int i = 0;
//			while (i < 10) {
//				if (flag3) {
//					worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST,
//							pos.getX()+(i*0.1), pos.getY()+1, pos.getZ()+0.5,
//							0, 0, 0,
//							Block.getStateId(WVBlocks.wolfsbane_dust.getDefaultState()));
//					worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST,
//							pos.getX()+0.5, pos.getY()+1, pos.getZ()+(i*0.1),
//							0, 0, 0,
//							Block.getStateId(WVBlocks.wolfsbane_dust.getDefaultState()));
//				} else {
//					worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST,
//							pos.getX() + (flag ? (i * 0.1) : 0.5), pos.getY() + 1, pos.getZ() + (flag1 ? (i * 0.1) : 0.5),
//							0, 0, 0,
//							Block.getStateId(WVBlocks.wolfsbane_dust.getDefaultState()));
//				}
//
//				++i;
//			}
//
//		}
	}


	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!world.isRemote && entity instanceof EntityLivingBase) {
			IWerewolf were = entity.getCapability(WerewolfProvider.WEREWOLF_CAP, null);

			if (were != null && were.getWerewolfType() == WerewolfType.FULL) {

				boolean transforms = false;

				if (!were.getIsTransformed()) {
					transforms = true;
					WerewolfHelpers.transformEntity((EntityLivingBase) entity, were, true);

					WorldTimeQueue.scheduleFor(world.getWorldTime()+30*20, () -> {
						WerewolfHelpers.transformEntity((EntityLivingBase) entity, were, false);
					});
				}

				if (were.getIsTransformed() || transforms) {
					Vec3d block = new Vec3d(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
					Vec3d direction = block.subtract(entity.getPositionVector());

					direction = direction.normalize().scale(0.2);

					entity.addVelocity(-direction.x, -direction.y, -direction.z);
					entity.velocityChanged = true;
				}
			}
		}
	}

	private static int getAABBIndex(IBlockState state) {
		int i = 0;
		boolean flag = state.getValue(NORTH) != EnumAttachPosition.NONE;
		boolean flag1 = state.getValue(EAST) != EnumAttachPosition.NONE;
		boolean flag2 = state.getValue(SOUTH) != EnumAttachPosition.NONE;
		boolean flag3 = state.getValue(WEST) != EnumAttachPosition.NONE;

		if (flag || flag2 && !flag && !flag1 && !flag3) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (flag1 || flag3 && !flag && !flag1 && !flag2) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (flag2 || flag && !flag1 && !flag2 && !flag3) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (flag3 || flag1 && !flag && !flag2 && !flag3) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
		return canConnectTo(worldIn.getBlockState(pos), null, worldIn, pos);
	}

	protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos) {
		return blockState.getBlock() instanceof BlockWolfsbaneDust;
	}

	@Override
	public void regModel() {
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DUST_AABB[getAABBIndex(state.getActualState(source, pos))];
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DUST_AABB[getAABBIndex(state.getActualState(source, pos))];
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));

		if (!canConnectTo(worldIn.getBlockState(blockpos), direction, worldIn, blockpos) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn, blockpos.down()))) {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.up());

			if (!iblockstate1.isNormalCube()) {
				boolean flag = worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, EnumFacing.UP);

				if (flag && canConnectUpwardsTo(worldIn, blockpos.up())) {
					if (iblockstate.isBlockNormalCube()) {
						return EnumAttachPosition.UP;
					}

					return EnumAttachPosition.SIDE;
				}
			}

			return EnumAttachPosition.NONE;
		} else {
			return EnumAttachPosition.SIDE;
		}
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isTopSolid();
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * block, etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (!this.canPlaceBlockAt(worldIn, pos)) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return WVItems.wolfsbane_dust;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(WVItems.wolfsbane_dust);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
			case CLOCKWISE_180:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
			case CLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
			default:
				return state;
		}
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
			case LEFT_RIGHT:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
			default:
				return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	enum EnumAttachPosition implements IStringSerializable {
		UP("up"),
		SIDE("side"),
		NONE("none");

		private final String name;

		EnumAttachPosition(String name) {
			this.name = name;
		}

		public String toString() {
			return this.getName();
		}

		public String getName() {
			return this.name;
		}
	}
}
