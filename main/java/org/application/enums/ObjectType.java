package org.application.enums;

import lombok.Getter;
import org.application.objects.Organism;
import org.application.objects.animals.herbivorous.*;
import org.application.objects.animals.predators.*;
import org.application.objects.plants.*;

@Getter
public enum ObjectType {

    Boar(Boar.class),
    Buffalo(Buffalo.class),
    Caterpillar(Caterpillar.class),
    Deer(Deer.class),
    Duck(Duck.class),
    Goat(Goat.class),
    Horse(Horse.class),
    Mouse(Mouse.class),
    Rabbit(Rabbit.class),
    Sheep(Sheep.class),
    Bear(Bear.class),
    Boa(Boa.class),
    Eagle(Eagle.class),
    Fox(Fox.class),
    Wolf(Wolf.class),
    Grass(Grass.class);

    private final Class<? extends Organism> clazz;

    ObjectType(Class<? extends Organism> clazz) {
        this.clazz = clazz;
    }
}