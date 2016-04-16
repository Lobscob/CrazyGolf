package actionListeners;

import Testing.Main;
import terrains.Terrain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Testing.Main.lightChoice;
import static Testing.Main.terrainChoice;

public class TerrainListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Terrain 1 - Plain") {
            if (terrainChoice.getID() != Main.getTerrain(1).getID()) {
                Main.entities.clear();
            }
            terrainChoice = Main.getTerrain(1);
            lightChoice = Main.getLight(1);
        } else if (e.getActionCommand() == "Terrain 2 - Luna") {
            if (terrainChoice.getID() != Main.getTerrain(2).getID()) {
                Main.entities.clear();
            }
            terrainChoice = Main.getTerrain(2);
            lightChoice = Main.getLight(2);
        } else if (e.getActionCommand() == "Terrain 3 - Hills") {
            if (terrainChoice.getID() != Main.getTerrain(3).getID()) {
                Main.entities.clear();
            }
            terrainChoice = Main.getTerrain(3);
            lightChoice = Main.getLight(3);
        } else if (e.getActionCommand() == "Terrain 4 - Grid") {
            if (terrainChoice.getID() != Main.getTerrain(4).getID()) {
                Main.entities.clear();
            }
            terrainChoice = Main.getTerrain(4);
            lightChoice = Main.getLight(4);
        } else if (e.getActionCommand() == "Reset Terrains") {
            Main.entities.clear();
            terrainChoice.resetTerrain();
        }
    }
}