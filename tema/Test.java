package tema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.*;

public class Test {
	public static Consumer.Resume read(JSONObject obj) {
			Consumer.Resume.ResumeBuilder rb = new Consumer.Resume.ResumeBuilder();
			
			Information info = new Information();
			String[] names = obj.getString("name").split(" ");
			info.setFirstName(names[0]);
			info.setLastName(names[1]);
			info.setEmail(obj.getString("email"));
			info.setPhone(obj.getString("phone"));
			LocalDate ld = LocalDate.parse(obj.getString("date_of_birth"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			info.setBirthDate(ld);
			info.setSex(obj.getString("genre"));
			
			JSONArray lang = obj.getJSONArray("languages");
			JSONArray attr = obj.getJSONArray("languages_level");
			for (int j = 0; j < lang.length(); j++) {
				info.addNewLang(lang.getString(j), attr.getString(j));
			}
			
			rb.addInformation(info);
			
			JSONArray edu = obj.getJSONArray("education");
			for (int j = 0; j < edu.length(); j++) {
				JSONObject o = edu.getJSONObject(j);
				String name = o.getString("name");
				String level = o.getString("level");
				LocalDate start = LocalDate.parse(o.getString("start_date"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				LocalDate end;
				if (o.isNull("end_date")) {
					end = null;
				}
				else {
					 end = LocalDate.parse(o.getString("end_date"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				}
				double grade = o.getDouble("grade");
				try {
					Education ed = new Education(start, end, name, level, grade);
					rb.addEducation(ed);
				}
				catch (InvalidDatesException e) {
					e.printStackTrace();
				}
			}
			
			JSONArray exp = obj.getJSONArray("experience");
			for (int j = 0; j < exp.length(); j++) {
				JSONObject o = exp.getJSONObject(j);
				String company = o.getString("company");
				String position = o.getString("position");
				
				String department;
				try{
					department = o.getString("department");
				}
				catch (JSONException e) {
					if (position.equals("Recruiter"))
						department = "IT";
					else {
						if(position.equals("Manager") || position.equals("CEO"))
							department = "Management";
						else
							department = "";
					}
				}
				LocalDate start = LocalDate.parse(o.getString("start_date"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				LocalDate end;
				if (o.isNull("end_date")) {
					end = null;
				}
				else {
					 end = LocalDate.parse(o.getString("end_date"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				}
				try {
					Experience ex = new Experience(start, end, position, department, company);
					rb.addExperience(ex);
				}
				catch (InvalidDatesException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Consumer.Resume res = rb.build();
				return res;
			}
			catch (ResumeIncompleteException e) {
				e.getStackTrace();
			}
			
			return null;
	}
	
	public static void startApplication() {
		Application app = Application.getInstance();
		
		Company google = new Company("Google");
		Company amazon = new Company("Amazon");
		
		app.add(google);
		app.add(amazon);
		
		DeptFactory df = new DeptFactory();
		
		google.departments.add(df.createDepartment("IT"));
		google.departments.add(df.createDepartment("Management"));
		google.departments.add(df.createDepartment("Marketing"));
		google.departments.add(df.createDepartment("Finance"));
		
		amazon.departments.add(df.createDepartment("IT"));
		amazon.departments.add(df.createDepartment("Management"));
		amazon.departments.add(df.createDepartment("Marketing"));
		amazon.departments.add(df.createDepartment("Finance"));
		
		Job gsde = new Job("Software Developer Engineer", "Google", "IT", 10000);
		gsde.setNoApplicants(1);
		Constraint<Integer> graduationyear = new Constraint<Integer>(2002, 2020);
		Constraint<Integer> experienceyears = new Constraint<Integer>(2, 6);
		Constraint<Double> meancons = new Constraint<Double>(8.0, null);
		gsde.setConstraints(graduationyear, experienceyears, meancons);
		gsde.open = true;
		
		Job gsdei = new Job("Software Developer Engineer Intern", "Google", "IT", 5000);
		gsdei.setNoApplicants(1);
		graduationyear = new Constraint<Integer>(null, null);
		experienceyears = new Constraint<Integer>(0, 2);
		meancons = new Constraint<Double>(9.0, null);
		gsdei.setConstraints(graduationyear, experienceyears, meancons);
		gsdei.open = true;
		
		google.departments.get(0).availablejobs.add(gsde);
		google.departments.get(0).availablejobs.add(gsdei);
		
		Job asde = new Job("Software Developer Engineer", "Amazon", "IT", 12000);
		asde.setNoApplicants(1);
		graduationyear = new Constraint<Integer>(2014, 2020);
		experienceyears = new Constraint<Integer>(1, null);
		meancons = new Constraint<Double>(9.0, null);
		asde.setConstraints(graduationyear, experienceyears, meancons);
		asde.open = true;
		
		Job asdei = new Job("Software Developer Engineer Intern", "Amazon", "IT", 6000);
		asdei.setNoApplicants(1);
		graduationyear = new Constraint<Integer>(null, null);
		experienceyears = new Constraint<Integer>(0, 2);
		meancons = new Constraint<Double>(9.35, null);
		asdei.setConstraints(graduationyear, experienceyears, meancons);
		asdei.open = true;
		
		amazon.departments.get(0).availablejobs.add(asde);
		amazon.departments.get(0).availablejobs.add(asdei);
		
		String path = "./consumers.json";
		try {
			String contents = new String(Files.readAllBytes(Paths.get(path)));
			JSONObject jobj = new JSONObject(contents);
			
			JSONArray employees = jobj.getJSONArray("employees");
			ArrayList<Employee> emplarr = new ArrayList<Employee>();
			for (int i = 0; i < employees.length(); i++) {
				JSONObject obj = employees.getJSONObject(i);
				Consumer.Resume res = Test.read(obj);
				Employee emp = new Employee();
				emp.resume = res;
				emp.salary = obj.getDouble("salary");

				for (Experience j : emp.resume.exp) {
					if (j.enddate == null) {
						if (j.company.equals("Google")) {
							emp.compname = "Google";
							for (Department k : google.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(emp);
									break;
								}
							}
						}
						else {
							emp.compname = "Amazon";
							for (Department k : amazon.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(emp);
									break;
								}
							}
						}
					}
				}
				emplarr.add(emp);
			}
			
			JSONArray recruiters = jobj.getJSONArray("recruiters");
			ArrayList<Recruiter> recrarr = new ArrayList<Recruiter>();
			for (int i = 0; i < recruiters.length(); i++) {
				JSONObject obj = recruiters.getJSONObject(i);
				Consumer.Resume res = Test.read(obj);
				Recruiter rec = new Recruiter();
				rec.resume = res;
				rec.salary = obj.getDouble("salary");
				
				for (Experience j : rec.resume.exp) {
					if (j.enddate == null) {
						if (j.company.equals("Google")) {
							google.recruiters.add(rec);
							rec.compname = "Google";
							for (Department k : google.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(rec);
									break;
								}
							}
						}
						else {
							amazon.recruiters.add(rec);
							rec.compname = "Amazon";
							for (Department k : amazon.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(rec);
									break;
								}
							}
						}
					}
				}
				recrarr.add(rec);
			}
			
			JSONArray users = jobj.getJSONArray("users");
			ArrayList<User> userarr = new ArrayList<User>();
			for (int i = 0; i < users.length(); i++) {
				JSONObject obj = users.getJSONObject(i);
				Consumer.Resume res = Test.read(obj);
				User user = new User();
				user.resume = res;
				
				JSONArray interest = obj.getJSONArray("interested_companies");
				for (int j = 0; j < interest.length(); j++) {
					user.interested.add(interest.getString(j));
					Company comp = Application.getInstance().getCompany(interest.getString(j));
					comp.observers.add(user);
				}

				app.add(user);
				userarr.add(user);
			}
			
			JSONArray managers = jobj.getJSONArray("managers");
			ArrayList<Manager> mangarr = new ArrayList<Manager>();
			for (int i = 0; i < managers.length(); i++) {
				JSONObject obj = managers.getJSONObject(i);
				Consumer.Resume res = Test.read(obj);
				Manager man = new Manager();
				man.resume = res;
				man.salary = obj.getDouble("salary");

				for (Experience j : man.resume.exp) {
					if (j.enddate == null) {
						if (j.company.equals("Google")) {
							google.manager = man.resume.info.getFirstName() + " " + man.resume.info.getLastName();
							man.compname = "Google";
							for (Department k : google.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(man);
									break;
								}
							}
						}
						else {
							amazon.manager = man.resume.info.getFirstName() + " " + man.resume.info.getLastName();
							man.compname = "Amazon";
							for (Department k : amazon.departments) {
								if (k.getClass().getName().equals("tema." + j.department)) {
									k.employees.add(man);
									break;
								}
							}
						}
					}
				}
				mangarr.add(man);
			}
			
			userarr.get(0).socials.add(userarr.get(1));
			userarr.get(0).socials.add(emplarr.get(2));
			
			userarr.get(1).socials.add(userarr.get(0));
			userarr.get(1).socials.add(emplarr.get(6));
			userarr.get(1).socials.add(recrarr.get(0));
			
			userarr.get(2).socials.add(userarr.get(3));
			userarr.get(2).socials.add(emplarr.get(2));
			
			userarr.get(3).socials.add(userarr.get(2));
			userarr.get(3).socials.add(emplarr.get(9));
			
			emplarr.get(1).socials.add(emplarr.get(9));
			emplarr.get(1).socials.add(recrarr.get(2));
			
			emplarr.get(2).socials.add(userarr.get(0));
			emplarr.get(2).socials.add(userarr.get(2));
			emplarr.get(2).socials.add(emplarr.get(5));
			emplarr.get(2).socials.add(recrarr.get(1));
			
			emplarr.get(5).socials.add(emplarr.get(2));
			emplarr.get(5).socials.add(recrarr.get(3));
			
			emplarr.get(9).socials.add(userarr.get(3));
			emplarr.get(9).socials.add(emplarr.get(1));
			
			emplarr.get(6).socials.add(userarr.get(1));
			
			recrarr.get(0).socials.add(userarr.get(1));
			recrarr.get(1).socials.add(emplarr.get(2));
			recrarr.get(2).socials.add(emplarr.get(1));
			recrarr.get(3).socials.add(emplarr.get(5));
			
			for (User i : userarr) {
				for (String j : i.interested) {
					Company comp = app.getCompany(j);
					for (Department k : comp.departments) {
						for (Job l : k.availablejobs) {
							l.apply(i);
						}
					}
				}	
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Test.startApplication();
	}
}
