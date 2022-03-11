package tema;

@SuppressWarnings("serial")
class InvalidDatesException extends Exception {
	public InvalidDatesException(String note) {
		super(note);
	}
}
