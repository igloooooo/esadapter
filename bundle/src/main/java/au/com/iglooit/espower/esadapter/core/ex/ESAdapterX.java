package au.com.iglooit.espower.esadapter.core.ex;

/**
 * Created by nicholaszhu on 7/11/2015.
 */
public class ESAdapterX extends RuntimeException {

    public ESAdapterX() {
    }

    public ESAdapterX(String message) {
        super(message);
    }

    public ESAdapterX(String message, Throwable cause) {
        super(message, cause);
    }

    public ESAdapterX(Throwable cause) {
        super(cause);
    }

    public ESAdapterX(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
