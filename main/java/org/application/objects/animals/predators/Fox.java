package org.application.objects.animals.predators;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Predator;

@Config(filePath = "animals/fox.yaml")
public class Fox extends Predator {

    private final Record record;

    public Fox(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Fox multiply() {
        return new Fox(record, getLocation());
    }
}