package fr.army.singularity.database.repository.callback;

public interface AsyncCallBackObject<T> {

  void done(T result);
}
