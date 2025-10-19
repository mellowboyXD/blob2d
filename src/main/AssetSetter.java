package main;

import objects.OBJ_Banana;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Key;

import java.util.HashSet;
import java.util.Random;

public class AssetSetter {
    GamePanel gp;
    public static int objCount;
    static Point[] possiblePos = {
            new Point(25, 2),
            new Point(38, 15),
            new Point(43, 43),
            new Point(39, 30),
            new Point(1, 48),
            new Point(25, 23),
            new Point(27, 45),
            new Point(19, 21),
            new Point(22, 27),
            new Point(7, 43),
            new Point(1, 25),
            new Point(29, 27),
            new Point(36, 42),
            new Point(45, 36),
            new Point(45, 4),
            new Point(27, 8),
            new Point(15, 4),
            new Point(1, 10),
    };

    static HashSet<Integer> chosen = HashSet.newHashSet(3);

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    private void init() {
        chosen.clear();
        objCount = 0;
    }

    int getKeyIdx() {
        Random r = new Random(System.currentTimeMillis());

        int i = r.nextInt(possiblePos.length);
        while(chosen.contains(i)){
            i = r.nextInt(possiblePos.length);
        }
        chosen.add(i);
        return i;
    }

    public void setObject() {
        init();
        for (int i =0; i < 3; ++i) {
            Point p = possiblePos[getKeyIdx()];
            gp.obj[objCount++] = new OBJ_Key(gp, "/objects/key.png", p.x(), p.y());
        }

        for (int i = 0; i < 2; ++i) {
            Point p = possiblePos[getKeyIdx()];
            gp.obj[objCount++] = new OBJ_Banana(gp, "/objects/banana_0.png", p.x(), p.y());
        }

        for (int i = 0; i < 6; ++i) {
            Point p = possiblePos[getKeyIdx()];
            gp.obj[objCount] = new OBJ_Banana(gp, "/objects/banana_1.png", p.x(), p.y());
            gp.obj[objCount++].name = "Banana Peel";
        }

        gp.obj[objCount++] = new OBJ_Door(gp, "/objects/door.png", 13, 21);
        gp.obj[objCount++] = new OBJ_Door(gp, "/objects/door.png", 8, 10);
        gp.obj[objCount++] = new OBJ_Door(gp, "/objects/door.png", 10, 16);

        gp.obj[objCount++] = new OBJ_Chest(gp, "/objects/chest.png", 8, 4);
    }
}
