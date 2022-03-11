package tema;

import java.util.*;

public class Manager extends Employee {
	ArrayList<Request<Job, Consumer>> requests;
	
	public Manager() {
		requests = new ArrayList<Request<Job, Consumer>>();
	}
	
	public void process(Job job) {
		Company comp = Application.getInstance().getCompany(compname);
		
		ArrayList<Request<Job, Consumer>> applicants = new ArrayList<Request<Job, Consumer>>();
		ArrayList<Double> scores = new ArrayList<Double>();
		int noPositions = job.noapplicants;
		
		for (Request<Job, Consumer> i : requests) {
			if (i.getKey() == job) {
				applicants.add(i);
				scores.add(i.getScore());
			}
		}
		
		while ((applicants.size() > 0) && (noPositions > 0)) {
			double max = Collections.max(scores);
			int index = scores.indexOf(max);
			Request<Job, Consumer> r = applicants.get(index);
			Consumer c = r.getValue1();
			
			if (Application.getInstance().users.contains(c)) {
				Employee newemp = ((User)c).convert();
				newemp.compname = job.companyname;
				newemp.salary = job.givensalary;
				
				for (Department i : comp.departments) {
					if (i.getClass().getSimpleName().equals(job.deptname))
						i.employees.add(newemp);
				}
				
				scores.remove(index);
				applicants.remove(index);
				requests.remove(r);
				Application.getInstance().users.remove(c);
				for(Company i : Application.getInstance().companies) {
					i.removeObserver((User)c);
				}
				
				noPositions--;
			}
			else {
				scores.remove(index);
				applicants.remove(index);
				requests.remove(r);
			}
		}
		
		if (applicants.size() != 0) {
			for (Request<Job, Consumer> i : applicants) {
				requests.remove(i);
			}
		}
		
		if (noPositions != 0) {
			comp.notifyAllObservers();
			noPositions = 0;
		}
		
		job.open = false;
		comp.notifyAllObservers();
	}
}
