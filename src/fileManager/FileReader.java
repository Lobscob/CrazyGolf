package fileManager;

import Testing.Main;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jeroen on 16-3-2016.
 */
public class FileReader {

    List<Entity> entities = new ArrayList<>();
    public void reader(String fileName, List<Entity> entities, List<TexturedModel> texturedModels) throws FileNotFoundException {
        fileName ="crs/" +  fileName + ".txt";
        Scanner s = new Scanner(new File(fileName));

        String line = s.nextLine();
        while(s.hasNextLine()){
            System.out.println(line);
            Scanner s2 = new Scanner(line);

            TexturedModel model = texturedModels.get(Integer.parseInt(s2.next()));

            Vector3f position = new Vector3f(Float.parseFloat(s2.next()),Float.parseFloat(s2.next()),Float.parseFloat(s2.next()));
            float rotX = Float.parseFloat(s2.next());
            float rotY = Float.parseFloat(s2.next());
            float rotZ = Float.parseFloat(s2.next());
            float scale = Float.parseFloat(s2.next());
            boolean isObstacle = Boolean.parseBoolean(s2.next());
            Vector3f collisionSize = new Vector3f(Float.parseFloat(s2.next()),Float.parseFloat(s2.next()),Float.parseFloat(s2.next()));

           /* System.out.println("MODEL" +model);
            System.out.println("position" +position);
            System.out.println("rotx" +rotX);
            System.out.println("rotY" +rotY);
            System.out.println("rotZ" +rotZ);
            System.out.println("scale" +scale);
            System.out.println("isOBstacle" +isObstacle);
            System.out.println("collisionsize" +collisionSize);*/

            entities.add(new Entity(model,position,rotX,rotY,rotZ,scale,isObstacle,collisionSize));
            Main.terrainChoice = Main.terrains.get(s2.nextInt());
            if(s.hasNext())line= s.nextLine();
            //TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize

        }
//     Main.entities=entities;
    }
}