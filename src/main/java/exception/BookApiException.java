package exception;

public class BookApiException extends Exception{

	public BookApiException() {
		super();
	}

	public BookApiException(String message) {
		super(message);
	}
	
	public BookApiException(String message, Throwable cause) {
		super(message,cause);
	}
	public BookApiException(Throwable cause) {
		super(cause);
	
	}
	
	protected BookApiException(String message, Throwable cause, boolean enableSuppression,boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
	
}
