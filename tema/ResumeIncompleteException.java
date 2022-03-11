package tema;

@SuppressWarnings("serial")
public class ResumeIncompleteException extends Exception {
	public ResumeIncompleteException(String note) {
		super(note);
	}
}
