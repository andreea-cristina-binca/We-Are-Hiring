package tema;

import java.time.*;

public class Experience implements Comparable <Experience> {
	LocalDate startdate, enddate;
	String position, company, department;
	
	public Experience(LocalDate start, LocalDate end, String position, String department, String company) throws InvalidDatesException {
		if (end != null)
			if (start.compareTo(end) > 0)
				throw new InvalidDatesException("Datele nu sunt in ordine cronologica!");
		startdate = start;
		enddate = end;
		this.position = position;
		this.department = department;
		this.company = company;
	}
	
	@Override
	public int compareTo(Experience exp) {
		if (enddate != null && exp.enddate != null) {
			if (enddate.compareTo(exp.enddate) < 0)
				return 1;
			else 
				return -1;
		}
		else {	
			return company.compareTo(exp.company);
		}
	}
	
	public String toString() {
		String s = "";
		s = s + "\tPeriod: " + startdate + " - " + enddate + "\n";
		s = s + "\tPosition: " + position + "\n";
		s = s + "\tCompany: " + company + "\n";
		s = s + "\tDepartment: " + department + "\n";
		return s;
	}
}
