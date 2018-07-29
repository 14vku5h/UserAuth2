package lkv.app.zException;

public class CustomeException extends Exception{
	 	private static final long serialVersionUID = 8898714171390175625L;
	 	
	 	private String code;
	 	
	 	public CustomeException(String code, String message){	 		
	 		super(message);
	 		this.code = code;
	 	}

	 	public String getCode() {
	 		return code;
	 	}
	 	

}
