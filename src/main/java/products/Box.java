package products;

import java.util.ArrayList;
import java.util.List;

/**
 * A Box contains items with the restriction that the sum of the items mass is
 * less than the weight Tolerance for the box.
 * 
 */
public class Box {
	protected static int boxIdCount = 1;

	final long boxId;
	float contentsWeight = 0;
	final float WEIGHT_TOLERANCE = 10;
	ArrayList<Item> contents;

	public Box() {
		super();
		contents = new ArrayList<Item>();
		boxId = boxIdCount++;
	}

	public boolean addItem(Item stuff) {
		if (contentsWeight + stuff.getWeightValue() > WEIGHT_TOLERANCE)
			return false;

		contents.add(stuff);
		contentsWeight = contentsWeight + stuff.getWeightValue();
		return true;
	}

	public boolean removeItem(Item stuff) {
		if (!contents.contains(stuff))
			return false;
		contents.remove(stuff);
		contentsWeight = contentsWeight-stuff.getWeightValue();
		return true;
	}

	public final long getId() {
		return boxId;
	}

	public final float getContentsWeight() {
		return contentsWeight;
	}

	public final List<Item> getContents() {
		return contents;
	}

	public String printSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Box\n");
		sb.append("id : "+this.getId()+"\n");
		sb.append("TotalWeight : "+this.getContentsWeight()+" pounds\n");//we could store units associated with box
		sb.append("Contents : [\n");
		//loop over contents
		for (Item val: contents)
		{
			sb.append(val.printItem());
		}
		sb.append("]\n");
		return sb.toString();
	}
	public String toString()
	{
		return printSummary();
	}
}
