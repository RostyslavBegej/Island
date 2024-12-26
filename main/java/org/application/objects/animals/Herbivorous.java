package org.application.objects.animals;

import org.application.config.database.Record;
import org.application.global.GlobalVariables;
import org.application.island.Location;
import org.application.objects.Organism;

import java.util.HashSet;
import java.util.Set;

public abstract class Herbivorous extends Animal {

    public Herbivorous(Record record, Location location) {
        super(record, location);
    }

    @Override
    public void eat(Organism organism) {
        Integer chanceToEat = getTargetMatrix().get(organism.getObjectType());
        if (GlobalVariables.random.nextInt(100) + 1 <= chanceToEat) {
            eatPlant(organism);
        }
    }

    protected void eatPlant(Organism organism) {
        double organismWeight = organism.getWeight();
        int countEatOrganisms = (int) Math.max(Math.ceil(getMaxSatiatingFood() * 0.5 / organismWeight), 1);
        Set<Organism> copy = new HashSet<>(getLocation().getSetOrganismsOnLocation());
        copy.stream()
                .filter(org -> org.getClass() == organism.getClass())
                .limit(countEatOrganisms)
                .peek(org -> this.setSaturation(this.getSaturation() + org.getWeight()))
                .peek(org -> {
                    Location.getOrganismStatistic(this.getClass()).logAteOrganisms();
                    Location.getOrganismStatistic(organism.getClass()).logKilledOrganisms();
                    Location.getOrganismStatistic(organism.getClass()).logDeadOrganisms();
                })
                .forEach(getLocation()::removeOrganism);
    }
}