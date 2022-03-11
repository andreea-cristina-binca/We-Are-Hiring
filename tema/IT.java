package tema;

public class IT extends Department {
	@Override
	public double getTotalSalaryBudget() {
		double sum = 0.0;
		for (Employee i : employees) {
			sum = sum + i.salary;
		}
		return sum;
	}
}
