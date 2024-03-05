package fr.army.singularity.network.task;

import fr.army.singularity.network.queue.DataSenderQueueManager;
import fr.army.singularity.network.queue.DataSenderTask;

public class DataSender {

    public void send(byte[] data, String channel) {
        try {
            DataSenderTask task = new DataSenderTask(new AsyncDataSender(), (action) -> action.sendPluginMessage(data, channel));
            DataSenderQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void send(byte[] data, String channel, long tickDelay) {
        try {
            DataSenderTask task = new DataSenderTask(new AsyncDataSender(), (action) -> action.sendPluginMessage(data, channel, tickDelay));
            DataSenderQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
