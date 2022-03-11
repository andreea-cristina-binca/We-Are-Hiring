package tema;

public class Constraint<K> {
	K upperlimit, lowerlimit;
	
	public Constraint(K lower, K upper) {
		upperlimit = upper;
		lowerlimit = lower;
	}
}
