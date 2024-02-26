package fr.army.singularity.database.repository.exception.impl;

import fr.army.singularity.database.repository.exception.RepositoryException;

public class EntityManagerNotInitializedException extends RepositoryException {

    public EntityManagerNotInitializedException() {
        super("Entity manager not initialized", "Entity manager not initialized");
    }
}
