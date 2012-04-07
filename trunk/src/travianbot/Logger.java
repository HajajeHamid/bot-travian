package travianbot;

/**
 *
 * @author Esr
 */
public class Logger {
    
    public static final int L_NOTHING = 0;
    public static final int L_FATAL = 1;
    public static final int L_ERROR = 2;
    public static final int L_WARN = 4;
    public static final int L_INFO = 8;
    public static final int L_TRACE = 16;
    public static final int L_ALL = 31;
    
    public static int logLevel = L_ALL | ~L_TRACE;
    
    private static boolean checkLvl(int msgLevel, int logLevel){
           
        return (msgLevel & logLevel) == msgLevel;
    
    }
    
    private static void msg(int msgLevel, String msg){
    
        if(checkLvl(msgLevel, logLevel))
            
            switch(msgLevel){
        
                case L_ERROR: System.out.println("Error: "+msg); break;
                case L_FATAL: System.out.println("Fatal error: "+msg); break;
                case L_INFO: System.out.println(msg); break;
                case L_TRACE: System.out.println("Trace: "+msg); break;
                case L_WARN: System.out.println("Warn: "+msg); break;

        
            }
        
    }
    
    public static void fatal(String msg){
        
        msg(L_FATAL,msg);
    
    }
    
    public static void  error(String msg){
    
        msg(L_ERROR,msg);
        
    }
    
    public static void  warn(String msg){
    
        msg(L_WARN,msg);
        
    }
    
    public static void info(String msg){
    
        msg(L_INFO,msg);
    
    }
    
    public static void trace(String msg){
    
        msg(L_TRACE,msg);
    
    }
           
}
