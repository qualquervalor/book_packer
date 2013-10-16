package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import products.Box;
import products.Item;

/**
 * This algorithm will take a list of items and pack them into boxes.  The strategy will be to
 * sort the items by weight. The sorted list will be processed by placing one heavy bookitem first and then 
 * filling the box with light items until the limit would be exceeded.  This strategy assumes
 * that there are items that will utilize the majority of the boxes weight limit and by pairing them with
 * the lightest items, they will best utilize the space.
 */
public class HeavyFirstFit {

	
	/**
	 * This method will return a list of packed boxes using the passed in list of items to fill them.
	 * Box instance are created as needed.
	 * 
	 * @param itemList
	 * @return
	 */
	public ArrayList<Box> packItems(List<Item> itemList)
	{
		//initialize the result
		ArrayList<Box> packedBoxes = new ArrayList<Box>();
		
		//check for invalid inputs
		if  (itemList == null || itemList.isEmpty())
			return packedBoxes;
		
		//create a working copy of the input list
		LinkedList<Item> listCopy = new LinkedList<Item>();
		listCopy.addAll(itemList);
		
		//sort the items by weight
		Collections.sort(listCopy, new Comparator<Item> () {
		    public int compare(Item a, Item b) {
		    	if (a.getWeightValue()==b.getWeightValue()) return 0;
		    	
		        if (a.getWeightValue()<b.getWeightValue()) return 1;
		        else return -1;
		    }		
		});
				
		//loop until we have tried to pack all items
		while (!listCopy.isEmpty())
		{
			Item heavy = listCopy.getFirst();		
			addHeavy(heavy,packedBoxes,listCopy);			
		}
		
		return packedBoxes;
	}

	/**
	 * This method takes one heavy item to pack into a new box and then fills the box with light items
	 * @param heavy
	 * @param packedBoxes
	 * @param items
	 */
	private void addHeavy(Item heavy,ArrayList<Box> packedBoxes, LinkedList<Item> items) 
	{
		//add new empty box to the pool
		Box current = new Box();
		packedBoxes.add(current);
		
		//pack heavy box
		boolean sucess = current.addItem(heavy);
		items.remove(heavy);
		if (!sucess)
		{
			//For this simple algorithm, I made the default assumption that 
			//all items that were attempted to be packed would fit in an 
			//empty box.  We have arrived here at this exception to the rule.
			//Here we simply ignore the unpackable item and destroy the now
			//badly misshapen empty box to be garbage collected.
			packedBoxes.remove(current);
			return;
		}
		
		//here we try to fill the remaining empty space in the box with lighter items		
		boolean flag = true;
		while (flag && items.size()>0)
		{
			Item light = items.getLast();
			flag = current.addItem(light);
			if (flag)
				items.remove(light);
		}
	}
}
