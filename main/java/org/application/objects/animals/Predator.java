package org.application.objects.animals;

import org.application.config.database.Record;
import org.application.island.Location;

public abstract class Predator extends Animal {

    public Predator(Record record, Location location) {
        super(record, location);
    }
}