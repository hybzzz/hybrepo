package com.hyb.quartz;


/**
 * 自定义异常
 * @author yewg
 *
 */
public class ScheduleException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -1921648378954132894L;
    /**
     * Exception code
     */
    protected String          resultCode       = "UN_KNOWN_EXCEPTION";

    /**
     * Exception message
     */
    protected String          resultMsg        = "错误信息";
    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
    	super(e);
        this.resultMsg = e.getMessage();
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
    	 super(message);
         this.resultMsg = message;
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public ScheduleException(String code, String message) {
    	 super(message);
         this.resultCode = code;
         this.resultMsg = message;
    }
}
