package utilities;

import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Logged
@Interceptor
public class LoggingInterceptor {

    public Object logMethod(InvocationContext ic) throws Exception {
        System.out.println("Entering method: " + ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            System.out.println("Exiting method: " + ic.getMethod().getName());
        }
    }
}