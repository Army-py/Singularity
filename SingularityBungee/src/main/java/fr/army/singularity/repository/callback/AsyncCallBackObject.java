package fr.army.singularity.repository.callback;

public interface AsyncCallBackObject<T> {

  void done(T result);
}
