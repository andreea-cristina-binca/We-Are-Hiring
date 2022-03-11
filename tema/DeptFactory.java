package tema;

public class DeptFactory {
	public Department createDepartment(String id) {
		switch (id) {
		case "IT":
			return new IT();
		case "Management":
			return new Management();
		case "Finance":
			return new Finance();
		case "Marketing":
			return new Marketing();
		default:
			return null;
		}
	}
}
