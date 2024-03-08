package fr.army.singularity.network.task.sender;

import fr.army.singularity.network.task.queue.DataSenderQueueManager;
import fr.army.singularity.network.task.queue.DataSenderTask;

public class QueuedDataSender {

    public void sendPluginMessage(byte[] data, String channel) {
        try {
            final DataSenderTask task = new DataSenderTask(new AsyncDataSender(), (action) -> action.sendPluginMessage(data, channel));
            DataSenderQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void sendPluginMessage(byte[] data, String channel, long tickDelay) {
        try {
            final DataSenderTask task = new DataSenderTask(new AsyncDataSender(), (action) -> action.sendPluginMessage(data, channel, tickDelay));
            DataSenderQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
