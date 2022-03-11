package tema;

import java.util.*;

public class Job {
	String jobname, companyname, deptname;
	boolean open;
	Constraint<Integer> graduationyear;
	Constraint<Integer> experienceyears;
	Constraint<Double> meancons;
	ArrayList<User> candidates;
	int noapplicants;
	double givensalary;
	
	public Job(String name, String comp, String dept, double sal) {
		jobname = name;
		companyname = comp;
		deptname = dept;
		givensalary = sal;
		candidates = new ArrayList<User>();
	}
	
	public void setConstraints(Constraint<Integer> gradyear, Constraint<Integer> expyears, Constraint<Double> mean) {
		graduationyear = gradyear;
		experienceyears = expyears;
		meancons = mean;
	}
	
	public void setNoApplicants(int no) {
		noapplicants = no;
	}
	
	public void addCandidate(User user) {
		candidates.add(user);
	}
	
	public void removeCandidate(User user) {
		candidates.remove(user);
	}
	
	public void apply(User user) {
		if (open == true) {
			if (meetsRequirments(user) == true) {
				Company comp = Application.getInstance().getCompany(companyname);
				Recruiter rec = comp.getRecruiter(user);
				rec.evaluate(this, user);
			}
		}	
	}
	
	public boolean meetsRequirments(User user) {
		int gradlowlim, gradupplim, explowlim, expupplim;
		double meanlowlim, meanupplim;
		if (graduationyear.lowerlimit == null)
			gradlowlim = 0;
		else
			gradlowlim = graduationyear.lowerlimit;
		if (graduationyear.upperlimit == null)
			gradupplim = 2020;
		else
			gradupplim = graduationyear.upperlimit;
		if (experienceyears.lowerlimit == null)
			explowlim = 0;
		else
			explowlim = experienceyears.lowerlimit;
		if (experienceyears.upperlimit == null)
			expupplim = 100;
		else
			expupplim = experienceyears.upperlimit;
		if (meancons.lowerlimit == null)
			meanlowlim = 0;
		else
			meanlowlim = meancons.lowerlimit;
		if (meancons.upperlimit == null)
			meanupplim = 10;
		else
			meanupplim = meancons.upperlimit;
		if (user.getGraduationYear() >= gradlowlim && 
				user.getGraduationYear() <= gradupplim) {
			if (user.yearsOfExp() >= explowlim &&
					user.yearsOfExp() <= expupplim) {
				if (user.meanGPA().compareTo(meanlowlim) >= 0) {
					if (user.meanGPA().compareTo(meanupplim) <= 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
