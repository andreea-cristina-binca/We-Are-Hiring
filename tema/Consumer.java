package tema;

import java.util.*;
import java.time.*;

public abstract class Consumer {
	Resume resume;
	ArrayList<Consumer> socials;
	
	public Consumer() {
		socials = new ArrayList<Consumer>();
	}
	
	public void add(Education education) {
		resume.edu.add(education);
	}

	public void add(Experience experience) {
		resume.exp.add(experience);
	}

	public void add(Consumer consumer) {
		socials.add(consumer);
	}

	public int getDegreeInFriendship(Consumer consumer) {
		ArrayList<Consumer> visited = new ArrayList<Consumer>();
		ArrayList<Consumer> queue = new ArrayList<Consumer>();
		ArrayList<Integer> degree = new ArrayList<Integer>();
		int degrees = 0;
		
		visited.add(this);
		queue.add(this);
		degree.add(0);
		
		while (queue.size() > 0) {
			Consumer cons = queue.remove(0);
			degrees = degree.remove(0);
			if (cons == consumer) {
				return degrees;
			}
			else {
				for (Consumer i : cons.socials) {
					if (!visited.contains(i)) {
						visited.add(i);
						queue.add(i);
						degree.add(degrees + 1);
					}
				}
			}
		}
		return degrees;
		
	}
	
	public void remove(Consumer consumer) {
		for (Consumer i : socials) {
			if (i.equals(consumer)) {
				socials.remove(i);
			}
		}
	}
	
	public Integer getGraduationYear() {
		int year = 0;
		String level = "";
		for (Education i : resume.edu) {
			if (i.enddate != null) {
				year = i.enddate.getYear();
				level = i.leveledu;
			}
		}
		if (level.equals("highschool")) {
				return 0;
		}
		return year;
	}
	
	public Double meanGPA() {
		double mean = 0.0;
		int no = 0;
		for (Education i : resume.edu) {
			mean = mean + i.gradmean;
			no++;
		}
		return mean/no;
	}
	
	public Integer yearsOfExp() {
		Period total = Period.of(0, 0, 0);
		for (Experience i : resume.exp) {
			if (i.enddate != null)
				total = total.plus(Period.between(i.startdate, i.enddate));
			else
				total = total.plus(Period.between(i.startdate, LocalDate.now()));
		}
		if (total.getMonths() >= 3)
			return (total.getYears() + 1);
		else
			return total.getYears();
	}
	
	public static class Resume {
		Information info;
		ArrayList<Education> edu;
		ArrayList<Experience> exp;
		
		public Resume(ResumeBuilder builder) {
			info = builder.info;
			edu = builder.edu;
			exp = builder.exp;
		}
		
		public static class ResumeBuilder {
			Information info;
			ArrayList<Education> edu;
			ArrayList<Experience> exp;
			
			public ResumeBuilder() {
				edu = new ArrayList<Education>();
				exp = new ArrayList<Experience>();
			}
			
			public ResumeBuilder addInformation(Information inform) {
				info = inform;
				return this;
			}
			
			public ResumeBuilder addEducation(Education educ) {
				edu.add(educ);
				return this;
			}
			
			public ResumeBuilder addExperience(Experience expr) {
				exp.add(expr);
				return this;
			}
			
			public Resume build() throws ResumeIncompleteException {
				if (info == null || edu == null) {
					throw new ResumeIncompleteException("Resume incomplete!");
				}
				return new Resume(this);
			}
			
		}
		
		public String toString() {
			String s = "";
			s = s + info + "\n";
			s = s + "Education:\n";
			for (Education i : edu) {
				s = s + i + "\n";
			}
			s = s + "Experience:\n";
			for (Experience i : exp) {
				s = s + i + "\n";
			}
			return s;
		}
	}
}
