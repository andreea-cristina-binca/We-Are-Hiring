package tema;

import java.util.*;

public class Employee extends Consumer {
	String compname;
	double salary;
	
	public Employee() {
	}
	
	public Employee(Resume res, ArrayList<Consumer> friends) {
		resume = res;
		socials = friends;
	}

}
