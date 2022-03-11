package tema;

import java.time.*;

public class Education implements Comparable <Education> {
	LocalDate startdate, enddate;
	String institutename;
	String leveledu;
	double gradmean;
	
	public Education(LocalDate start, LocalDate end, String name, String edu, double mean) throws InvalidDatesException {
		if (end != null)
			if (start.compareTo(end) > 0)
				throw new InvalidDatesException("Datele nu sunt in ordine cronologica!");
		startdate = start;
		enddate = end;
		institutename = name;
		leveledu = edu;
		gradmean = mean;
	}
	
	@Override
	public int compareTo(Education ed) {
		if (enddate != null && ed.enddate != null) {
			if (enddate.compareTo(ed.enddate) < 0)
				return 1;
			else {
				if (enddate.compareTo(ed.enddate) > 0)
					return -1;
				else {
					if (gradmean < ed.gradmean)
						return 1;
					else
						return -1;
				}
			}
		}
		else {
			return startdate.compareTo(ed.startdate);
		}
	}
	
	public String toString() {
		String s = "";
		s = s + "\tPeriod: " + startdate + " - " + enddate + "\n";
		s = s + "\tInstitute: " + institutename + "\n";
		s = s + "\tEducation level: " + leveledu + "\n";
		s = s + "\tGrade: " + gradmean + "\n";
		return s;
	}
}
