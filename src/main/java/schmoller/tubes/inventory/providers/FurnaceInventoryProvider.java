package schmoller.tubes.inventory.providers;

import schmoller.tubes.api.interfaces.IInterfaceProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class FurnaceInventoryProvider implements IInterfaceProvider
{
	@Override
	public IInventory provide( Object object )
	{
		if(object instanceof TileEntityFurnace)
			return new FurnaceInventory((TileEntityFurnace)object);
		
		return null;
	}
	
	
	public static class FurnaceInventory implements ISidedInventory
	{
		private ISidedInventory mFurnace;
		
		public FurnaceInventory(TileEntityFurnace furnace)
		{
			mFurnace = furnace;
		}

		@Override
		public int getSizeInventory()
		{
			return mFurnace.getSizeInventory();
		}

		@Override
		public ItemStack getStackInSlot( int i )
		{
			return mFurnace.getStackInSlot(i);
		}

		@Override
		public ItemStack decrStackSize( int i, int j )
		{
			return mFurnace.decrStackSize(i, j);
		}

		@Override
		public ItemStack getStackInSlotOnClosing( int i )
		{
			return mFurnace.getStackInSlotOnClosing(i);
		}

		@Override
		public void setInventorySlotContents( int i, ItemStack itemstack )
		{
			mFurnace.setInventorySlotContents(i, itemstack);
		}

		@Override
		public String getInventoryName()
		{
			return mFurnace.getInventoryName();
		}

		@Override
		public boolean hasCustomInventoryName()
		{
			return mFurnace.hasCustomInventoryName();
		}

		@Override
		public int getInventoryStackLimit()
		{
			return mFurnace.getInventoryStackLimit();
		}

		@Override
		public void markDirty()
		{
			mFurnace.markDirty();
		}

		@Override
		public boolean isUseableByPlayer( EntityPlayer entityplayer )
		{
			return mFurnace.isUseableByPlayer(entityplayer);
		}

		@Override
		public void openInventory()
		{
			mFurnace.openInventory();
		}

		@Override
		public void closeInventory()
		{
			mFurnace.closeInventory();
		}

		@Override
		public boolean isItemValidForSlot( int slot, ItemStack item )
		{
			if(slot == 0) // Stuff to smelt
				return (FurnaceRecipes.smelting().getSmeltingResult(item) != null);
			else if(slot == 1) // Fuel
				return TileEntityFurnace.isItemFuel(item);
			else // Output
				return false;
		}

		@Override
		public int[] getAccessibleSlotsFromSide( int side )
		{
			int[] slots = new int[3];
			slots[0] = 0;
			slots[1] = 1;
			slots[2] = 2;
			return slots;
		}

		@Override
		public boolean canInsertItem( int slot, ItemStack item, int side )
		{
			return isItemValidForSlot(slot, item);
		}

		@Override
		public boolean canExtractItem( int slot, ItemStack item, int side )
		{
			return slot == 2;
		}
		
		
	}
}
