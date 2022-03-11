package tema;

import java.time.*;

public class Finance extends Department {
	@Override
	public double getTotalSalaryBudget() {
		double sum = 0.0;
		for (Employee i : employees) {
			Period p = Period.of(0, 0, 0);
			for (Experience j : i.resume.exp) {
				if (j.enddate == null)
					p = Period.between(j.startdate, LocalDate.now());
				if (p.getYears() == 0) {
					sum = sum + i.salary + i.salary * 0.1;
				}
				else
					sum =sum + i.salary + i.salary * 0.16;
			}
		}
		return sum;
	}
}
