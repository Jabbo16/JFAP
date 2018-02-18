# JFAP

## Description

JFAP is a Java port of FAP (Fast APproximation).
Its designed and test to work with BWMirror 2.5+ and BWAPI 4.1.2.

FAP is a combat simulator for Starcraft:Brood War C++ bots originally made by Hannes Bredberg <https://github.com/N00byEdge/Neohuman>

## How to use JFAP?

1. Clone this project

    ```cmd
    git clone https://github.com/Jabbo16/JFAP.git
    ```

2. Copy the source code into your Eclipse/IntelliJ Java project.

3. This is an example code of how to start simulating combats:

    ```java
    JFAP simulator = new JFAP(bwapi.getGame()); // Requires a 'Game' object to be passed by parameter
    simulator.clear(); // Before starting the simulations we need to clear the simulator
    simulator.addUnitPlayer1(new JFAPUnit(myUnit)); // Adds a friendly unit to the simulator
    simulator.addUnitPlayer2(new JFAPUnit(enemyUnit)); // Adds an enemy unit to the simulator
    Pair<Integer, Integer> preSimScores = simulator.playerScores(); // We can get each player scores before the simulation starts
    int preSimFriendlyUnitCount = simulator.getState().first.size(); // Friendly unit count introduced to JFAP before the simulation starts
    simulator.simulate(50); // Starts simulating the combat, number of frames to simulate is passed by parameters, default is 96 frames

    // After the simulation we can get the post battle score and the number of units that died for each player
    // You can use this info to know if the combat will be favorable for you or not
    Pair<Integer, Integer> postSimScores = simulator.playerScores();
    int postSimFriendlyUnitCount = simulator.getState().first.size();
    int myLosses = preSimFriendlyUnitCount - postSimFriendlyUnitCount;
    int myScoreDiff = preSimScores.first - postSimScores.first;
    int enemyScoreDiff = preSimScores.second - postSimScores.second;
    ```
