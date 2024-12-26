package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.console.Console;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;
import org.application.config.database.Record;

@Config(filePath = "animals/sheep.yaml")
public class Sheep extends Herbivorous {

    private final Record record;

    public Sheep(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Sheep multiply() {
        return new Sheep(record, getLocation());
    }
}