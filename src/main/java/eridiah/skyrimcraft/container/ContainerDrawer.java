package eridiah.skyrimcraft.container;

import eridiah.skyrimcraft.tileentity.TileEntityDrawer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDrawer extends Container
{
	public ContainerDrawer(InventoryPlayer inventoryPlayer, TileEntityDrawer tileEntity) 
	{
		if(tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH))
		{
			IItemHandler inventory = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
			
			//custom drawer slots
			for(int y = 0; y < 3; y++)
			{
				for(int x = 0; x < 3; x++)
				{
					addSlotToContainer(new SlotItemHandler(inventory, x + (y * 3), 62 + x * 18, 17 + y * 18));
				}
			}
			
			//player inventory
			for(int y = 0; y < 3; y++)
			{
				for(int x = 0; x < 9; x++)
				{
					addSlotToContainer(new Slot(inventoryPlayer, x + (y * 9) + 9, 8 + x * 18, 84 + y * 18));
				}
			}
			
			//player hotbar
			for(int i = 0; i < 9; i++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (i * 18), 142));
			}
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
			
			if(index < containerSlots)
			{
				if(!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.mergeItemStack(stackInSlot, 0, containerSlots, false))
			{
				return ItemStack.EMPTY;
			}
			
			if(stackInSlot.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			slot.onTake(player, stackInSlot);
		}
		return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return true;
	}
}
