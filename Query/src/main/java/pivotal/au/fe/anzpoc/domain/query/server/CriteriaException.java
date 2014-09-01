package pivotal.au.fe.anzpoc.domain.query.server;

@SuppressWarnings("serial")
public class CriteriaException extends RuntimeException {

  public CriteriaException() {
    super();
  }

  public CriteriaException(String message, Throwable cause) {
    super(message, cause);
  }

  public CriteriaException(String message) {
    super(message);
  }

  public CriteriaException(Throwable cause) {
    super(cause);
  }
  
}
