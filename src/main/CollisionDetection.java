package main;

import entity.Entity;
import objects.OBJ;

public class CollisionDetection {
    GamePanel gp;

    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }

    public int checkObj(OBJ[] obj, Entity entity) {
        int entityDefaultX = entity.collisionArea.x;
        int entityDefaultY = entity.collisionArea.y;
        for(int i = 0; i < obj.length; ++i) {
            if (obj[i] != null) {
                int objDefaultX = obj[i].collisionArea.x;
                int objDefaultY = obj[i].collisionArea.y;
                int objIdx = -1;

                entity.collisionArea.x = entity.worldX;
                entity.collisionArea.y = entity.worldY;

                obj[i].collisionArea.x = obj[i].worldX;
                obj[i].collisionArea.y = obj[i].worldY;

                switch (entity.direction) {
                    case "up" -> {
                        entity.collisionArea.y -= entity.speed;
                        if(entity.collisionArea.intersects(obj[i].collisionArea)) {
                            objIdx = i;
                        }
                    }
                    case "right" -> {
                        entity.collisionArea.x += entity.speed;
                        if(entity.collisionArea.intersects(obj[i].collisionArea)) {
                            objIdx = i;
                        }
                    }
                    case "down" -> {
                        entity.collisionArea.y += entity.speed;
                        if(entity.collisionArea.intersects(obj[i].collisionArea)) {
                            objIdx = i;
                        }
                    }
                    case "left" -> {
                        entity.collisionArea.x -= entity.speed;
                        if(entity.collisionArea.intersects(obj[i].collisionArea)) {
                            objIdx = i;
                        }
                    }
                }

                entity.collisionArea.x = entityDefaultX;
                entity.collisionArea.y = entityDefaultY;
                obj[i].collisionArea.x = objDefaultX;
                obj[i].collisionArea.y = objDefaultY;
                if(objIdx != -1)
                    return objIdx;
            }
        }
        return -1;
    }

    public void checkTile(Entity entity) {
        int offsetX = entity.collisionArea.x - entity.getScreenX();
        int offsetY = entity.collisionArea.y - entity.getScreenY();

        int leftWorldX = offsetX + entity.worldX;
        int rightWorldX = offsetX + entity.worldX + entity.collisionArea.width - 2;
        int topWorldY = offsetY + entity.worldY;
        int bottomWorldY = offsetY + entity.worldY + entity.collisionArea.height - 2;

        int leftCol = leftWorldX / gp.tileSize;
        int rightCol = rightWorldX / gp.tileSize;
        int topRow = topWorldY / gp.tileSize;
        int bottomRow = bottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up" -> {
                topRow = (topWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTilesNum[leftCol][topRow];
                tileNum2 = gp.tileManager.mapTilesNum[rightCol][topRow];
                if(gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                rightCol = (rightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTilesNum[rightCol][topRow];
                tileNum2 = gp.tileManager.mapTilesNum[rightCol][bottomRow];
                if(gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                bottomRow = (bottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTilesNum[rightCol][bottomRow];
                tileNum2 = gp.tileManager.mapTilesNum[leftCol][bottomRow];
                if(gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                leftCol = (leftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTilesNum[leftCol][topRow];
                tileNum2 = gp.tileManager.mapTilesNum[leftCol][bottomRow];
                if(gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }
}
