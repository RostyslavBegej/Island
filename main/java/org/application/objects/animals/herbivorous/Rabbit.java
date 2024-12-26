package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/rabbit.yaml")
public class Rabbit extends Herbivorous {

    private final Record record;

    public Rabbit(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Rabbit multiply() {
        return new Rabbit(record, getLocation());
    }
}