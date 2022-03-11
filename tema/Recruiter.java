package tema;

import java.lang.Math;

public class Recruiter extends Employee {
	double rating = 5;

	public Recruiter() {
	}

	public int evaluate(Job job, User user) {
		rating = rating + 0.1;
		double score = Math.ceil(rating * user.getTotalScore());
		Request<Job, Consumer> req = new Request<Job, Consumer>(job, (Consumer)user, (Consumer)this, score);
		Company comp = Application.getInstance().getCompany(job.companyname);
		for (Department i : comp.departments) {
			if (i instanceof Management) {
				for (Employee j : i.employees) {
					String name = j.resume.info.getFirstName() + " " + j.resume.info.getLastName();
					if (name.compareTo(comp.manager) == 0) {
						((Manager)j).requests.add(req);
					}
				}
			}
		}
		return (int)score;
	}
}
