package schmoller.tubes.parts;

import java.util.List;

import schmoller.tubes.IDirectionalTube;
import schmoller.tubes.TubeRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import codechicken.core.vec.BlockCoord;
import codechicken.core.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTubeBase extends JItemMultiPart
{

	public ItemTubeBase( int id )
	{
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabTransport);
	}

	@Override
	@SideOnly( Side.CLIENT )
	public void registerIcons( IconRegister register )
	{
		itemIcon = register.registerIcon("missing");
	}
	
	@Override
	public boolean onItemUse( ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ )
	{
		if(super.onItemUse(item, player, world, x, y, z, side, hitX, hitY, hitZ))
		{
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Block.soundGlassFootstep.getPlaceSound(), (Block.soundGlassFootstep.getVolume() * 5.0F), Block.soundGlassFootstep.getPitch() * .9F);
			return true;
		}
		
		return false;
	}
	
	@Override
	public TMultiPart newPart( ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 hit )
	{
		TMultiPart part = MultiPartRegistry.createPart("tubes_" + getTubeType(item), false);
		
		if(part instanceof IDirectionalTube)
		{
			int face = (player.isSneaking() ? side : side ^ 1);
			
			if(((IDirectionalTube)part).canFaceDirection(face))
				((IDirectionalTube)part).setFacing(face);
		}
		
		return part;
	}
	
	@Override
	public String getUnlocalizedName( ItemStack stack )
	{
		return "tubes." + getTubeType(stack);
	}
	
	public ItemStack createForType(String tubeType)
	{
		ItemStack item = new ItemStack(this);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("tube", tubeType);
		item.setTagCompound(tag);
		
		return item;
	}
	
	public String getTubeType(ItemStack item)
	{
		if(item.hasTagCompound())
		{
			String type = item.getTagCompound().getString("tube");
			if(!type.isEmpty())
				return type;
		}
		
		return "basic";
	}
	
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	@SideOnly( Side.CLIENT )
	public void getSubItems( int id, CreativeTabs tab, List items )
	{
		for(String type : TubeRegistry.instance().getTypeNames())
			items.add(createForType(type));
	}

	
}