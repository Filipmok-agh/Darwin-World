# Darwin World

## Authors
- **[Filip Mokrzycki](https://github.com/Filipmok-agh)** 
- **[Jakub Fabia](https://github.com/jakub-fabia)**

## Description
The goal of the project was to create an application to simulate and visualize the behavior of animals based on the rules described [here](https://github.com/Soamid/obiektowe-lab/tree/a56b132b6f10a73c6df9e9040804f649a894cd3e/proj). 


## Technologies and Tools  
- **Programming Language**: Java 21  
- **Build Tool**: Gradle  
- **Libraries**: JavaFX  
- **Testing**: JUnit

### Features:

#### 1. **Starting Screen**:
![Starting Screen](screenshots/Starting%20Screen.png)

### 2. **Menu Screen**:
   - The user can specify simulation options.
   - Options can be imported or saved.
   - The user can choose whether statistics will be saved.
   - Multiple simulations can be started (options are validated).

![Menu Screen](screenshots/Menu%20Screen.png)

### 3. **Simulation Screen**:
   - The simulation displays animal movement.
   - The simulation shows statistics for all animals and for the observed one.
   - The simulation can be stopped.
   - The user can change jungle visibility and highlight animals with the most popular genome.
   - The user can observe an animal by simply clicking on it on the map.
   - The observed animal is highlighted, and its stats are updated in real time.

#### **Jungle Positions**  
![Simulation Screen1](screenshots/Simulation%20Screen1.png)  

#### **Observed Animal and Animals with the Most Popular Genome**  
![Simulation Screen2](screenshots/Simulation%20Screen2.png)  

### 4. **Multithreading**
   - The user can start multiple simulations at the same time thanks to the multithreading system.  
![Multithreading](screenshots/Multithreading.png)

### 5. **Statistics Saving**
![Statistics](screenshots/Statistics.png)

## **Explanation of Simulation Symbols**


### Animal

 - Observed <br>
    <img src="img-readme/Observed.png" width=50 height=50>
 - Most Popular Genome <br>
    <img src="img-readme/Popular-genome.png" width=50 height=50>
 - Low Energy <br>
    <img src="img-readme/Low-energy.png" width=50 height=50>
 - Medium Energy <br>
    <img src="img-readme/Medium-energy.png" width=50 height=50>
 - High Energy <br>
    <img src="img-readme/High-energy.png" width=50 height=50>
 - Very High Energy <br>
    <img src="img-readme/Very-high-energy.png" width=50 height=50>

### Grass <br>

<img src="img-readme/Grass.png" width=50 height=50>

### Fertile Land (Jungle) <br>

<img src="img-readme/Jungle.png" width=50 height=50>

### Standard Land (Steppe) <br>

<img src="img-readme/Steppe.png" width=50 height=50>

