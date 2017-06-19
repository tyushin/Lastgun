package com.TGame.Game;

import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class Helper {

    public static boolean doAreasCollide(Area area1, Area area2) {

        if(area1 == null || area2 == null) {
            return  false;
        }

        boolean collide = false;

        Area collide1 = new Area(area1);
        collide1.subtract(area2);
        if (!collide1.equals(area1)) {
            collide = true;
        }

        Area collide2 = new Area(area2);
        collide2.subtract(area1);
        if (!collide2.equals(area2)) {
            collide = true;
        }

        return collide;
    }

    public static double CalculateAngle(Point2D point1, Point2D point2) {
        double dx = point1.getX() - point2.getX(),
               dy = point1.getY() - point2.getY();

        return  Math.atan2(dy, dx);
    }

}
