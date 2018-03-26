package exception;

/**
 * Created by Administrator on 2018/3/19.
 */
public class ServiceException extends Exception {
    public ServiceException(String msg,Throwable cause){
        super(msg,cause);
    }
}
