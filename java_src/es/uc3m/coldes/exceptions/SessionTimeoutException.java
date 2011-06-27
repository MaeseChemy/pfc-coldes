package es.uc3m.coldes.exceptions;

public class SessionTimeoutException extends Exception {

    /**
	 * 
	 */
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

    /**
     * Obtiene el codigo de error de la excepcion
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

}
