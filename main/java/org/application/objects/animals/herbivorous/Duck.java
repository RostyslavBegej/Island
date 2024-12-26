package org.application.objects.animals.herbivorous;

import org.application.annotations.Config;
import org.application.config.database.Record;
import org.application.global.GlobalVariables;
import org.application.island.Location;
import org.application.objects.Organism;
import org.application.objects.animals.Herbivorous;
import org.application.objects.plants.Plant;

@Config(filePath = "animals/duck.yaml")
public class Duck extends Herbivorous {

    private final Record record;

    public Duck(Record record, Location location) {
        super(record, location);
        this.record = record;
    }

    @Override
    public Duck multiply() {
        return new Duck(record, getLocation());
    }

    @Override
    public void eat(Organism organism) {
        Integer chanceToEat = getTargetMatrix().get(organism.getObjectType());
        if (GlobalVariables.random.nextInt(100) + 1 <= chanceToEat) {
            if (organism instanceof Plant) {
                eatPlant(organism);
                return;
            }
            this.setSaturation(this.getSaturation() + organism.getWeight());
            getLocation().removeOrganism(organism);
            Location.getOrganismStatistic(this.getClass()).logAteOrganisms();
            Location.getOrganismStatistic(organism.getClass()).logKilledOrganisms();
            Location.getOrganismStatistic(organism.getClass()).logDeadOrganisms();
        }
    }
}