package fr.army.singularity.repository.callback;

import java.util.List;

public interface AsyncCallBackObjectList<T> {

  void done(List<T> result);
}