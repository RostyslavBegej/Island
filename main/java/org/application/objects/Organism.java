package org.application.objects;

import lombok.Getter;
import lombok.Setter;
import org.application.config.database.Record;
import org.application.enums.ObjectType;
import org.application.interfaces.Reproducible;
import org.application.island.Location;

@Getter
@Setter
public abstract class Organism implements Reproducible {

    private double weight;
    private int maxCountOnCell;
    private ObjectType objectType;
    private int chanceToReproduce;

    private boolean canReproduce = true;

    private Location location;
    private Location.Statistic statistic;

    public Organism(Record record, Location location) {
        weight = record.getWeight();
        maxCountOnCell = record.getMaxCountOnCell();
        objectType = record.getObjectType();
        chanceToReproduce = record.getChanceToReproduce();

        this.location = location;

        statistic = Location.getOrganismStatistic(this.getClass());
        statistic.logAllOrganisms();
        statistic.logBornOrganisms();
        statistic.logAliveOrganisms();
    }

    public abstract Organism multiply();

}