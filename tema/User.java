package tema;

import java.util.*;

public class User extends Consumer implements Observer {
	ArrayList<String> interested;
	String notifs;
	
	public User() {
		interested = new ArrayList<String>();
	}
	
	public Employee convert() {
		return new Employee(resume, socials);
	}
	
	public Double getTotalScore() {
		return ((double)yearsOfExp()) * 1.5 + meanGPA();
	}
	
	public void addInterestedComp(String compname) {
		interested.add(compname);
		Application.getInstance().getCompany(compname).addObserver(this);
	}

	@Override
	public void update(String s) {
		notifs = s;
	}
	
}
