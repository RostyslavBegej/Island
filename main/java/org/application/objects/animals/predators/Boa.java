package org.application.objects.animals.predators;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Predator;

@Config(filePath = "animals/boa.yaml")
public class Boa extends Predator {

    private final Record record;

    public Boa(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Boa multiply() {
        return new Boa(record, getLocation());
    }
}