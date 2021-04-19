package com.evie.autotest.util;

import com.evie.autotest.exception.TestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetryHandler {
    private static final int DEFAULT_RETRY_INTERVAL = 2;

    private static final Logger LOGGER = LogManager.getLogger(RetryHandler.class);

    /**
     * retry until the method call have no more throwable (errors, exceptions)
     *
     * @param iretry     function interface
     * @param interval   retry interval in seconds
     * @param maxTimeout max retry in seconds
     */
    public static void retryTillNoError(IRetry iretry, int interval, int maxTimeout) {
        long startTime = System.currentTimeMillis();
        Throwable throwable = null;
        while (System.currentTimeMillis() - startTime < maxTimeout * 1000) {
            try {
                throwable = null;
                iretry.retry();
                break;
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                sleep(interval);
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
    }

    /**
     * retry until the method get no more errors or exceptions
     *
     * @param iretry
     * @param retryTimes max retry times
     */
    public static void retryTillNoError(IRetry iretry, int retryTimes) {
        Throwable throwable = null;
        for (int i = 0; i < retryTimes; i++) {
            try {
                throwable = null;
                iretry.retry();
                break;
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                sleep(DEFAULT_RETRY_INTERVAL);
                LOGGER.info(
                        "Retried " + i + " times. Retry times left: " + (retryTimes - i - 1));
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
    }

    /**
     * retry until the method get no more errors or exceptions, or interrupt if needed.
     *
     * @param retry
     * @param retryTimes max retry times
     */
    public static void retryTillNoErrorOrInterrupt(IRetryWithInteruption retry, int retryTimes) {
        Throwable throwable = null;
        Interruptor interruptor = new Interruptor();
        for (int i = 0; i < retryTimes; i++) {
            try {
                throwable = null;
                retry.retryWithInteruption(interruptor);
                break;
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                if (interruptor.isInterrupt()) {
                    LOGGER.info("Retry interrupte. Interrupt reason: "
                            + interruptor.getInterruptReason());
                    break;
                }
                sleep(DEFAULT_RETRY_INTERVAL);
                LOGGER.info(
                        "Retried " + i + " times. Retry times left: " + (retryTimes - i - 1));
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
    }

    /**
     * Always retry, unless it is interrupted or reaching the max retry times..
     * This method would retry even if no error occurred.
     * Upon finishing the retry, throw whatever error occurred in the last retry.
     *
     * @param retry
     * @param retryTimes max retry times
     */
    public static void retryUntilInterrupt(IRetryWithInteruption retry, int retryTimes) {
        retryUntilInterrupt(retry, retryTimes, DEFAULT_RETRY_INTERVAL);
    }

    /**
     * Always retry, unless it is interrupted or reaching the max retry times..
     * This method would retry even if no error occurred.
     * Upon finishing the retry, throw whatever error occurred in the last retry.
     *
     * @param retry         : IRetryWithInteruption
     * @param retryTimes    : retry times
     * @param retryInterval : retry interval
     */
    public static void retryUntilInterrupt(IRetryWithInteruption retry, int retryTimes, int retryInterval) {
        Throwable throwable = null;
        Interruptor interruptor = new Interruptor();
        for (int i = 0; i < retryTimes; i++) {
            try {
                throwable = null;
                retry.retryWithInteruption(interruptor);
                sleep(retryInterval);
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                if (interruptor.isInterrupt()) {
                    LOGGER.info("Retry interrupte. Interrupt reason: "
                            + interruptor.getInterruptReason());
                    break;
                }
                sleep(retryInterval);
                LOGGER.info(
                        "Retried " + i + " times. Retry times left: " + (retryTimes - i - 1));
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
    }

    /**
     * retry until the method get no more errors or exceptions
     *
     * @param iretry
     * @param retryTimes
     * @return the method's return or null
     */
    public static Object retryTillNoError(IRetryWithReturn iretry, int retryTimes) {
        Object result = null;
        Throwable throwable = null;
        for (int i = 0; i < retryTimes; i++) {
            try {
                throwable = null;
                result = iretry.retryWithReturn();
                break;
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                sleep(DEFAULT_RETRY_INTERVAL);
                LOGGER.info(
                        "Retried " + i + " times. Retry times left: " + (retryTimes - i - 1));
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
        return result;
    }

    /**
     * retry until the method get no more errors or exceptions
     *
     * @param iretry     method
     * @param interval   interval in seconds to retry
     * @param maxTimeout max timeout in seconds
     * @return the method's return or null
     */
    public static Object retryTillNoError(IRetryWithReturn iretry, int interval, int maxTimeout) {
        Object result = null;
        long startTime = System.currentTimeMillis();
        Throwable throwable = null;
        while (System.currentTimeMillis() - startTime < maxTimeout * 1000) {
            try {
                throwable = null;
                result = iretry.retryWithReturn();
                break;
            } catch (Throwable error) {
                LOGGER.warn(throwable = error);
                sleep(interval);
            }
        }
        if (null != throwable)
            throw new TestException(throwable);
        return result;
    }

    /**
     * sleep
     *
     * @param seconds seconds
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
