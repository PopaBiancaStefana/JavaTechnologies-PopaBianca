package com.cdi.interceptors;

import com.cdi.qualifiers.Loggable;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.io.Serializable;
import java.util.logging.Logger;

@Interceptor
@Loggable
public class LoggingInterceptor implements Serializable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        logger.info("Entering method: " + ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            logger.info("Exiting method: " + ic.getMethod().getName());
        }
    }
}
