package fr.army.singularity.network.queue;

import fr.army.singularity.network.task.AsyncDataSender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Consumer;

public class DataSenderTask implements Runnable {

    private final AsyncDataSender asyncDataSender;
    private final Consumer<AsyncDataSender> dataOperation;

    public DataSenderTask(AsyncDataSender asyncDataSender, Consumer<AsyncDataSender> dataOperation) {
        this.asyncDataSender = asyncDataSender;
        this.dataOperation = dataOperation;
    }

    @Override
    public void run() {
        dataOperation.accept(asyncDataSender);
    }
}
