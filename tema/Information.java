package tema;

import java.util.*;
import java.time.*;

public class Information {
	private String firstname, lastname;
	private String email, phone;
	private LocalDate datebirth;
	private String sex;
	private ArrayList<String> languages;
	private ArrayList<String> langattributes;
	
	public Information() {
		languages = new ArrayList<String>();
		langattributes = new ArrayList<String>();
	}
	
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String name) {
		firstname = name;
	}
	
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String name) {
		lastname = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		email = mail;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String number) {
		phone = number;
	}
	
	public LocalDate getBirthDate() {
		return datebirth;
	}
	public void setBirthDate(LocalDate date) {
		datebirth = date;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String gender) {
		sex = gender;
	}
	
	public void addNewLang(String lang, String attr) {
		languages.add(lang);
		langattributes.add(attr);
	}
	public ArrayList<String> getLanguages() {
		return languages;
	}
	public ArrayList<String> getLangAttributes() {
		return langattributes;
	}
	
	public String toString() {
		String s = "";
		s = s + getFirstName() + " " + getLastName() + "\n";
		s = s + "Information:\n";
		s = s + "\tEmail: " + getEmail() + "\n";
		s = s + "\tPhone: " + getPhone() + "\n";
		s = s + "\tDate of Birth: " + getBirthDate() + "\n";
		s = s + "\tSex: " + getSex() + "\n";
		s = s + "\tLanguages: " + getLanguages() + "\n";
		s = s + "\tLanguage levels: " + getLangAttributes() + "\n";
		return s;
	}
}
