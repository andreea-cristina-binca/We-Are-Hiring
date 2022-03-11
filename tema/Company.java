package tema;

import java.util.*;

public class Company implements Subject {
	String name, manager;
	ArrayList<Department> departments;
	ArrayList<Recruiter> recruiters;
	ArrayList<User> observers;
	
	public Company(String name) {
		this.name = name;
		departments = new ArrayList<Department>();
		recruiters = new ArrayList<Recruiter>();
		observers = new ArrayList<User>();
	}
	
	public void add(Department department) {
		departments.add(department);
	}
	public void add(Recruiter recruiter) {
		recruiters.add(recruiter);
	}
	public void add(Employee employee, Department department) {
		department.employees.add(employee);
	}
	
	public void remove(Employee employee) {
		for (Department i : departments) {
			if (i.employees.contains(employee))
				i.employees.remove(employee);
		}
	}
	public void remove(Department department) {
		departments.remove(department);
	}
	public void remove(Recruiter recruiter) {
		recruiters.remove(recruiter);
	}
	
	public void move(Department source, Department destination) {
		for (Job i : source.availablejobs) {
			destination.availablejobs.add(i);
		}
		for (Employee i : source.employees) {
			destination.employees.add(i);
		}
		remove(source);
	}
	public void move(Employee employee, Department newDepartment){
		newDepartment.employees.add(employee);
		remove(employee);
	}
	
	public boolean contains(Department department) {
		if (departments.contains(department))
			return true;
		return false;
	}
	public boolean contains(Employee employee) {
		for (Department i : departments) {
			if (i.employees.contains(employee))
				return true;
		}
		return false;
	}
	public boolean contains(Recruiter recruiter) {
		if (recruiters.contains(recruiter))
			return true;
		return false;
	}
	
	public Recruiter getRecruiter(User user) {
		Recruiter r = new Recruiter();
		r.rating = 0;
		int maxdeg = 0;
		for (Recruiter i : recruiters) {
			int degree = i.getDegreeInFriendship(user);
			if (degree > maxdeg) {
				r = i;
				maxdeg = degree;
			}
			if (degree == maxdeg) {
				if (i.rating > r.rating) {
					r = i;
				}
			}
		}
		
		if (maxdeg == 0) {
			for (Recruiter i : recruiters) {
				if (i.rating > r.rating) {
					r = i;
				}
			}
		}
		return r;
	}
	
	public ArrayList<Job> getJobs(){
		ArrayList<Job> jobs = new ArrayList<Job> ();
		for (Department i : departments) {
			jobs.addAll(i.getJobs());
		}
		return jobs;
	}
	
	@Override
	public void addObserver(User user) {
		observers.add(user);
	}
	@Override
	public void removeObserver(User user) {
		observers.remove(user);
	}
	@Override
	public void notifyAllObservers() {
		String s = "New Notif";
		for (User i : observers)
			i.update(s);
	}
}
