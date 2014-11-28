package refinery.model;

public class EmptyArticleListException extends RuntimeException {
	
	public EmptyArticleListException (String message) {
		super(message);
	}
	
	public EmptyArticleListException (String message, Throwable cause) {
		super(message, cause);
	}
}
