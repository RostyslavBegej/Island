package org.application.objects.animals.predators;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Predator;

@Config(filePath = "animals/wolf.yaml")
public class Wolf extends Predator {

    private final Record record;

    public Wolf(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Wolf multiply() {
        return new Wolf(record, getLocation());
    }
}