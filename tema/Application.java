package tema;

import java.util.*;

public class Application {
	ArrayList<Company> companies;
	ArrayList<User> users;
	
	private static Application instance = new Application();
	
	private Application() {
		companies = new ArrayList<Company>();
		users = new ArrayList<User>();
	}
	
	public static Application getInstance() {
		return instance;
	}
	
	public ArrayList<Company> getCompanies(){
		return companies;
	}
	
	public Company getCompany(String name) {
		for (Company i : companies) {
			if (i.name.equals(name))
				return i;
		}
		return null;
	}
	
	public void add(Company company) {
		companies.add(company);
	}
	
	public void add(User user) {
		users.add(user);
	}
	
	public boolean remove(Company company) {
		return companies.remove(company);
	}
	
	public boolean remove(User user) {
		return users.remove(user);
	}
	
	public ArrayList<Job> getJobs(List<String> companies) {
		ArrayList<Job> jobs = new ArrayList<Job>();
		for (String i : companies) {
			Company c = getCompany(i);
			jobs.addAll(c.getJobs());
		}
		return jobs;
	}
}
