package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/goat.yaml")
public class Goat extends Herbivorous {

    private final Record record;

    public Goat(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Goat multiply() {
        return new Goat(record, getLocation());
    }
}