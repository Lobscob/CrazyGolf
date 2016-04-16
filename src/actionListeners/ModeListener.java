package actionListeners;

import testing.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "Editor") {
			Main.setEditorMode(true);
			Main.setTerrainEditorMode(false);
			Main.setPlayerMode(false);
			Main.setMenuChoiceMade(true);
		}else if(e.getActionCommand() == "Player") {
			Main.setPlayerMode(true);
			Main.setEditorMode(false);
			Main.setTerrainEditorMode(false);
			Main.setMenuChoiceMade(true);
		}else if(e.getActionCommand() == "Terrain Editor") {
			Main.setPlayerMode(false);
			Main.setEditorMode(false);
			Main.setTerrainEditorMode(true);
			Main.setMenuChoiceMade(true);
		}
	}
}  

