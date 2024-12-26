package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/horse.yaml")
public class Horse extends Herbivorous {

    private final Record record;

    public Horse(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Horse multiply() {
        return new Horse(record, getLocation());
    }
}