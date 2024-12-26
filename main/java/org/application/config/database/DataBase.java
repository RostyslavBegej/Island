package org.application.config.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Getter;
import org.application.annotations.Config;
import org.application.exception.DataBaseLoadException;
import org.application.objects.Organism;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class DataBase {

    private static DataBase instance;
    private final Map<Class<? extends Organism>, Record> dataBase;
    private static final Reflections reflections = new Reflections("org.application");

    private DataBase() {
        dataBase = loadDataBase();
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public static Set<Class<? extends Organism>> setObjects() {
        return reflections.getSubTypesOf(Organism.class)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(Config.class))
                .collect(Collectors.toSet());
    }

    private Map<Class<? extends Organism>, Record> loadDataBase() {
        return setObjects()
                .stream()
                .collect(Collectors.toMap(k -> k, this::loadObject));
    }

    private Record loadObject(Class<? extends Organism> organism) {
        ObjectMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(getFilePath(organism), Record.class);
        } catch (IOException e) {
            throw new DataBaseLoadException(e);
        }
    }

    private URL getFilePath(Class<? extends Organism> organism) {
        Config config = organism.getAnnotation(Config.class);
        return organism.getClassLoader().getResource(config.filePath());
    }
}