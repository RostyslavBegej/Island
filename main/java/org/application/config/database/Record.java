package org.application.config.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.application.enums.ObjectType;

import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Record {

    private double weight;
    private int maxCountOnCell;
    private int speed;
    private double maxSatiatingFood;
    private int chanceToReproduce;
    private int maxChild;
    private ObjectType objectType;
    private Map<ObjectType, Integer> targetMatrix;

}