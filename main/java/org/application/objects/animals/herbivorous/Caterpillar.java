package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.animals.Herbivorous;

@Config(filePath = "animals/caterpillar.yaml")
public class Caterpillar extends Herbivorous {

    private final Record record;

    public Caterpillar(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Caterpillar multiply() {
        return new Caterpillar(record, getLocation());
    }
}