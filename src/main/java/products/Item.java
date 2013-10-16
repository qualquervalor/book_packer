package products;

/**
 * This interface is meant to represent the minimum requirement for something we would like to pack.
 * 
 */
public interface Item {
	public static final Float DEFAULT_UNKNOWN_WEIGHT = 10.0f;

public Float getWeightValue();
public String getWeightUnit();
public String printItem();
}
