package com.hjm.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    public Rectangle rect=new Rectangle();
    int x = 200, y = 200;

    private boolean moving = true;

    private boolean living = true;

    Dir dir = Dir.DOWN;
    private int SPEED = 3;
    private TankFrame tf;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group = Group.BAD;
    private Random random = new Random();

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }


    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        if (this.group == Group.GOOD) {
            this.SPEED = 10;
        }
        if (this.group == Group.BAD) {
            this.SPEED = 5;
        }

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;

    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
            return;
        }
        // Color c=g.getColor();
        /*g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);*/
        //tank图片
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }


        if (random.nextInt(10) > 8) this.fire();

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
        }
        boundsCheck();

        rect.x=this.x;
        rect.y=this.y;



    }

    //边界检测
    private void boundsCheck() {
        if(this.x<0) x=0;
        if(this.y<30)y=30;
        if(this.x>TankFrame.GAME_WIDTH-Tank.WIDTH) x=TankFrame.GAME_WIDTH-Tank.WIDTH;
        if(this.y>TankFrame.GAME_HEIGHT-Tank.HEIGHT) y=TankFrame.GAME_HEIGHT-Tank.HEIGHT;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));

        if (this.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();

    }

    public void die() {
        living = false;
    }
}
