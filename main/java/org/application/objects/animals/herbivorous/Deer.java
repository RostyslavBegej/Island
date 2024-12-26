package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/deer.yaml")
public class Deer extends Herbivorous {

    private final Record record;

    public Deer(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Deer multiply() {
        return new Deer(record, getLocation());
    }
}