package es.uc3m.coldes.exceptions;

/**
 * Clase que extiende de Exception usada para modela la excepción
 * de sistema de fin de sesión.
 * 
 * @author Jose Migel Blanco García.
 *
 */
public class SessionTimeoutException extends Exception {

	private static final long serialVersionUID = 1926210366040687908L;
	private int errorCode;

    public SessionTimeoutException(){
        super();
    }

    public SessionTimeoutException(String msg){
        super(msg);
    }

	public SessionTimeoutException(String msg, int errorCode){
        super(msg);
        this.setErrorCode(errorCode);
    }

    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
