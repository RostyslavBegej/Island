package org.application.objects.plants;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;

@Config(filePath = "plants/grass.yaml")
public class Grass extends Plant {

    private final Record record;

    public Grass(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Grass multiply() {
        return new Grass(record, getLocation());
    }
}