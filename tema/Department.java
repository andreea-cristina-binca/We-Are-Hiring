package tema;

import java.util.*;

public abstract class Department {
	ArrayList<Employee> employees;
	ArrayList<Job> availablejobs;
	
	public abstract double getTotalSalaryBudget();
	
	public Department() {
		employees = new ArrayList<Employee>();
		availablejobs = new ArrayList<Job>();
	}
	
	public ArrayList<Job> getJobs(){
		ArrayList<Job> openjobs = new ArrayList<Job>();
		for (Job i : availablejobs) {
			if (i.open == true)
				openjobs.add(i);
		}
		return openjobs;
	}
	
	public void add(Employee employee) {
		employees.add(employee);
	}
	
	public void remove(Employee employee) {
		employees.remove(employee);
	}
	
	public void add(Job job) {
		availablejobs.add(job);
		Application.getInstance().getCompany(job.companyname).notifyAllObservers();
	}
	
	public ArrayList<Employee> getEmployees(){
		return employees;
	}
}
