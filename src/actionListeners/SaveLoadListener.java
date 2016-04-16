package actionListeners;

import fileManager.gameLoader;
import fileManager.gameSaver;
import testing.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * Created by Jeroen on 16/04/2016.
 */
public class SaveLoadListener implements ActionListener {


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Save") {

            try {
                gameSaver.saveEntities(Main.saveTF.getText());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }


        } else if (e.getActionCommand() == "Load") {
            try {
                gameLoader.loadEntities(Main.loadTF.getText());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }


}
