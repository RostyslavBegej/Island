package org.application.objects.animals;

import lombok.Getter;
import lombok.Setter;
import org.application.config.database.Record;
import org.application.enums.Direction;
import org.application.enums.ObjectType;
import org.application.global.GlobalVariables;
import org.application.interfaces.Eatable;
import org.application.interfaces.Movable;
import org.application.interfaces.Reproducible;
import org.application.island.Location;
import org.application.objects.Organism;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public abstract class Animal extends Organism implements Movable, Reproducible, Eatable {

    private ThreadLocalRandom random = GlobalVariables.random;

    private int speed;
    private double maxSatiatingFood;
    private double saturation;
    private int maxChild;

    private boolean canMove = true;

    private Map<ObjectType, Integer> targetMatrix;

    public Animal(Record record, Location location) {
        super(record, location);
        this.speed = record.getSpeed();
        this.maxSatiatingFood = record.getMaxSatiatingFood();
        this.saturation = maxSatiatingFood * 0.85;
        this.targetMatrix = record.getTargetMatrix();
        this.maxChild = record.getMaxChild();
    }

    @Override
    public Direction[] move() {
        int countSteps = random.nextInt(speed + 1);
        Direction[] steps = new Direction[countSteps];
        Direction[] directions = Direction.values();
        for (int i = 0; i < countSteps; i++) {
            steps[i] = directions[random.nextInt(directions.length)];
        }
        return steps;
    }

    @Override
    public void eat(Organism organism) {
        Integer chanceToEat = targetMatrix.get(organism.getObjectType());
        if (GlobalVariables.random.nextInt(100) + 1 <= chanceToEat) {
            this.saturation += organism.getWeight();
            getLocation().removeOrganism(organism);
            Location.getOrganismStatistic(this.getClass()).logAteOrganisms();
            Location.getOrganismStatistic(organism.getClass()).logKilledOrganisms();
            Location.getOrganismStatistic(organism.getClass()).logDeadOrganisms();
        }
    }

    public boolean canEat() {
        return saturation < maxSatiatingFood || (!targetMatrix.keySet().isEmpty() && maxSatiatingFood == 0);
    }
}