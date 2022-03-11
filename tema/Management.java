package tema;

public class Management extends Department {
	@Override
	public double getTotalSalaryBudget() {
		double sum = 0.0;
		for (Employee i : employees) {
			sum = sum + i.salary + i.salary * 0.16;
		}
		return sum;
	}
}
