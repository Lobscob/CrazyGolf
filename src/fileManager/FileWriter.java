package fileManager;

import entities.Entity;
import testing.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 16-3-2016.
 */
public class FileWriter {

    public void writer(String courseName, List<Entity> entitiesArray, int terrainChoice) throws FileNotFoundException {
        courseName = "crs/" + courseName + ".txt";
        entitiesArray = entitiesArray;
        File inputFile = new File(courseName);
        PrintWriter out = new PrintWriter(courseName);
        ArrayList<String> entitiesString = new ArrayList<String>();

        for (int i = 0; i < Main.entities.size(); i++) {
            Entity t = Main.entities.get(i);
            String k = " ";

            String s = t.getModel().getIndex() + k + t.getPosition().x + k + t.getPosition().y + k + t.getPosition().z + k + t.getRotX() + k + t.getRotY() + k + t.getRotZ() +
                    k + t.getScale() + k + t.isEntityObstacle() + k + +t.getCollisionZone().x + k + t.getCollisionZone().y + k + t.getCollisionZone().z + k + terrainChoice;
            entitiesString.add(s);
//            System.out.println(s);

            out.println(s);
            out.flush();
            //TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize
        }
        System.out.println("SAVING FINISHED");
    }
}

