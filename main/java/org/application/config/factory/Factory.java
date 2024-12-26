package org.application.config.factory;

import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.Organism;

import java.lang.reflect.InvocationTargetException;

public class Factory {

    private static Factory instance;

    private Factory() {
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public Organism create(Class<? extends Organism> clazz, Record record, Location location) {
        try {
            return clazz.getConstructor(Record.class, Location.class).newInstance(record, location);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}