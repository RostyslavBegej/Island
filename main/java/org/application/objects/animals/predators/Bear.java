package org.application.objects.animals.predators;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Predator;

@Config(filePath = "animals/bear.yaml")
public class Bear extends Predator {

    private final Record record;

    public Bear(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Bear multiply() {
        return new Bear(record, getLocation());
    }
}