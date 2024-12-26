package org.application.objects.plants;

import lombok.Getter;
import org.application.config.database.Record;
import org.application.island.Location;
import org.application.objects.Organism;

@Getter
public abstract class Plant extends Organism {

    public Plant(Record record, Location location) {
        super(record, location);
    }
}