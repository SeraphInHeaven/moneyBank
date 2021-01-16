package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-08-28 8:54 AM
 */
public class CompletableFutureTest {

    Logger logger = LoggerFactory.getLogger(CompletableFutureTest.class);

    @Test
    public void testException() {
        for (int i = 0; i<1000; i++) {
            CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                    //.thenApply(String::toUpperCase);
                    .thenApplyAsync(String::toUpperCase);

            cf.completeExceptionally(new RuntimeException("ex")); //thenApplyAsync时会有机会执行，而thenApply则无机会执行

            try {
                Assert.assertTrue(cf.isCompletedExceptionally());
                logger.info("isCompletedExceptionally {}", cf.isCompletedExceptionally());
                logger.info(cf.get());
            } catch (Exception e) {
                logger.error("test error", e);
            }
        }

    }

    @Test
    public void testReject() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));

        ArrayList<CompletableFuture<String>> list = new ArrayList<>();
        ArrayList<CompletableFuture<?>> dependents = new ArrayList<>();
        for (int i = 0; i <100; i++) {
            CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();

            CompletableFuture<?> thisWillHaveException = stringCompletableFuture.whenCompleteAsync((r, e) -> {
                logger.info("Complete {}", r);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {e1.printStackTrace();}
            }, executorService).whenComplete((r, e) -> {

                if (e != null) {
                    if (e instanceof CompletionException) {
                        logger.error("CompletionException r {} e {} cause {}",
                                r, e.getClass().getSimpleName(), e.getCause().getClass().getSimpleName());
                    } else {
                        logger.error("Exception r {} e {}", r, e);
                    }
                } else {
                    logger.info(">>>>OK, {}", r);
                }
            });

            dependents.add(thisWillHaveException);
            list.add(stringCompletableFuture);
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).complete(i + "");
        }

        Thread.sleep(2000);

    }

}
