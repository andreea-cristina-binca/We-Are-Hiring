package tema;

public class Marketing extends Department {
	@Override
	public double getTotalSalaryBudget() {
		double sum = 0.0;
		for (Employee i : employees) {
			if (i.salary < 3000)
				sum = sum + i.salary;
			else
				if (i.salary > 5000)
					sum = sum + i.salary + i.salary * 0.1;
				else
					sum = sum + i.salary + i.salary * 0.16;
		}
		return sum;
	}
}
