package fileManager;

import Testing.Main;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Testing.Main.terrainChoice;

/**
 * Created by Jeroen on 16-3-2016.
 */
public class gameLoader {

    public static void loadEntities(String fileName) throws FileNotFoundException {
        fileName = "crs/" + fileName + ".txt";
        Scanner s = new Scanner(new File(fileName));

        String line = s.nextLine();
        terrainChoice = Main.getTerrain(line);
        System.out.println(terrainChoice);
        System.out.println("line" +line);

        line = s.nextLine();
        System.out.println("Loading");
        Main.entities.clear();
        //TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize
        while (s.hasNextLine() && !line.substring(0,7).equals("HEIGHTS")) {
            Scanner s2 = new Scanner(line);
            TexturedModel model = Main.getModel(Integer.parseInt(s2.next()));

            Vector3f position = new Vector3f(Float.parseFloat(s2.next()), Float.parseFloat(s2.next()), Float.parseFloat(s2.next()));
            float rotX = Float.parseFloat(s2.next());
            float rotY = Float.parseFloat(s2.next());
            float rotZ = Float.parseFloat(s2.next());
            float scale = Float.parseFloat(s2.next());
            boolean isObstacle = Boolean.parseBoolean(s2.next());
            Vector3f collisionSize = new Vector3f(Float.parseFloat(s2.next()), Float.parseFloat(s2.next()), Float.parseFloat(s2.next()));
            Main.entities.add(new Entity(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize));
            System.out.println(Main.entities);
            line = s.nextLine();
        }
        line = s.nextLine();
        float[][] heights = terrainChoice.getHeights();
        int i = 0;
        int j = 0;
        int stopCondition = (terrainChoice.getHeights().length) * 2;
        boolean row = true;
        System.out.println("line" +line);
        while (s.hasNextLine() ) {

            boolean finalLine = false;
            while ( s.hasNextLine()) {
                Scanner s2 = new Scanner(line);

                while (s2.hasNext()) {

                    heights[i][j] = Float.parseFloat(s2.next());
//                    System.out.print(heights[i][j]);
                    j++;
                }
                if(!s2.hasNext())finalLine=true;
//                System.out.println("");
                i++;
                j=0;
                line = s.nextLine();

            }




        }
//
        terrainChoice.setHeights(heights);
        System.out.println("FINISHED");
    }
/*
        public void loadEntities(String fileName) throws FileNotFoundException {
            fileName ="crs/" +  fileName + ".txt";
            Scanner s = new Scanner(new File(fileName));

            String line = s.nextLine();
            System.out.println("Loading...");
            while(s.hasNextLine()){
                System.out.println(line);
                Scanner s2 = new Scanner(line);

                TexturedModel model = model[s2.next()];

                Vector3f position = new Vector3f(Float.parseFloat(s2.next()),Float.parseFloat(s2.next()),Float.parseFloat(s2.next()));
                float rotX = Float.parseFloat(s2.next());
                float rotY = Float.parseFloat(s2.next());
                float rotZ = Float.parseFloat(s2.next());
                float scale = Float.parseFloat(s2.next());
                boolean isObstacle = Boolean.parseBoolean(s2.next());
                Vector3f collisionSize = new Vector3f(Float.parseFloat(s2.next()),Float.parseFloat(s2.next()),Float.parseFloat(s2.next()));

           *//* System.out.println("MODEL" +model);
            System.out.println("position" +position);
            System.out.println("rotx" +rotX);
            System.out.println("rotY" +rotY);
            System.out.println("rotZ" +rotZ);
            System.out.println("scale" +scale);
            System.out.println("isOBstacle" +isObstacle);
            System.out.println("collisionsize" +collisionSize);*//*

                entities.add(new Entity(model,position,rotX,rotY,rotZ,scale,isObstacle,collisionSize));
                Main.terrainChoice = Main.terrains.get(Integer.parseInt(s2.next()));
                if(s.hasNext())line= s.nextLine();
                //TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize
            }System.out.println("LOADING FINISHED" +entities.size());

            Main.entities=entities;
        }
    }
    public static void loadEntities(){
        while()
       Entity e = new Entity();
        Main.entities.add(e);


    }

    public static void loadGame() {
    }*/
}