package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/buffalo.yaml")
public class Buffalo extends Herbivorous {

    private final Record record;

    public Buffalo(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Buffalo multiply() {
        return new Buffalo(record, getLocation());
    }
}