package fr.army.singularity.network.queue;

import fr.army.singularity.database.queue.DatabaseTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DataSenderQueueManager {

    private static final int QUEUE_CAPACITY = 99;
    private static final BlockingQueue<DataSenderTask> taskQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    static {
        startService();
    }

    private static void startService() {
        executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    taskQueue.take().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public static void enqueueTask(DataSenderTask task) throws InterruptedException {
        taskQueue.put(task);
    }

    public static void shutdown() {
        executorService.shutdownNow();
        taskQueue.clear();
    }
}
