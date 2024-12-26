package org.application.objects.animals.predators;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Predator;

@Config(filePath = "animals/eagle.yaml")
public class Eagle extends Predator {

    private final Record record;

    public Eagle(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Eagle multiply() {
        return new Eagle(record, getLocation());
    }
}