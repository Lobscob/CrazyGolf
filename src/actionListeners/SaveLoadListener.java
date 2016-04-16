package actionListeners;

import fileManager.gameSaver;

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
                gameSaver.saveEntities("test");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }


        } else if (e.getActionCommand() == "Load") {
//            gameLoader.loadEntities();
        }
    }


}
