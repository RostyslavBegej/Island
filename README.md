# Animal Life Simulator

@Author: Rostyslav Begej

## Content

1. [Introduction](#introduction)
2. [Multithreading](#multithreading)
3. [Statistic](#statistic)
4. [Realization](#realization)
    - [Island](#island)
    - [Database](#database)
    - [Initialization](#initialization)
    - [Life](#life)
    - [Used resources](#used-resources)
5. [Conclusion](#conclusion)

### Introduction

This project is about the life of animals on the island.<br>
Animals can run around the island, reproduce, eat plants, or other animals, depending on whether the animal is a
herbivore or a carnivore, and some animals are omnivorous.

### Multithreading

Multithreading is implemented in the project.<br>
There are only 3 streams in the project.<br>
Animals live in one stream, plants grow in another, and the third stream outputs statistics on the island to the
console.

### Statistic

In statistics, you can see statistics for every type of organism on the island, including plants.

**Each organism shows**:

1. How much of this organism was on the island.
2. How many living organisms of this species are currently left.
3. How many organisms of this species were created.
4. How many organisms of this species ate other organisms.
5. How many organisms of this species died of starvation.
6. How many organisms of this species were eaten by other organisms.
7. How many organisms died in total.
   Also, the last row displays information on all organisms in general.

# Realization

## Island

Each island consists of an array of locations.<br>
The location contains a variety of animals and plants.

## Database

All initial data for each organism are stored in a separate "DataBase" class. It is able to download data from a yaml
file and store them in a separate "Record" class. This class stores the data of each organism.

## Initialization

Life on an island begins with island initialization.

First, locations are created.<br>
When creating a location, it is immediately filled with organisms in a random amount.

## Life

### Animal

Controllers are responsible for the lives of animals.
Each controller is an action. It iterates through all the animals, and forces the animal to perform the action for which
this controller is responsible.

- #### **Move**

After the initialization of the island, the starting animals are first moved around the island. They randomly choose a
path and move along it.

- #### **Reproduce**

After movement, the animal begins to reproduce. She chooses a mate and reproduces.
Reproduction occurs with some chance, as well as a random number of cubs.

- #### **Eat**

After reproduction, the animal goes to eat.
Each animal has a matrix that indicates which organism and with what chance the animal can eat. So the animal looks for
organisms according to this matrix, and tries to eat it.

- #### **Starving**

For the simulation to work correctly, animals must die, so each animal has a hunger scale that decreases every cycle,
and when it reaches 0, the animal dies.

**After these actions, the cycle repeats itself.**

### Plant

The growth of plants is implemented in a separate flow, so they grow independently of the actions of animals.

## Used resources

In the project, almost everything is done through the **Stream API**.
All iterations on organisms are done via stream.
A reflection from the **reflections** library is also used to initialize the project.

In order to display all data in one place, **yaml** files are used. Each organization has them, as well as the island,
in
order to conveniently set the simulation parameters.
**Jackson's libraries** are used to read yaml files.

**Annotations** are used to link yaml files and classes.

To reduce the volume of methods, the **lombok** library is used, which has very convenient standard class methods.

Many **SOLID** principles were followed through reflection, annotations, and abstraction.

**New organisms, implementations of actions, and more can be added with minimal changes to the program.**

## Conclusion

The idea of the project is quite interesting. There are many ways to implement this or that method, a separate action or
even part of a project.

In the process of implementation, almost all logic was rewritten several times, which gave quite a lot of skills in
using many libraries and classes.

The project is not yet finished, but will most likely change to optimize the simulation process.

### **Thanks for watching!**
