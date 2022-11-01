package com.hjm.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    Tank myTank = new Tank(700,600,Dir.UP,Group.GOOD,this);

    List<Bullet> bullets=new ArrayList<Bullet>();

    List<Tank> tanks =new ArrayList<>();

    List<Explode> explodes=new ArrayList<>();

    static final int GAME_WIDTH=1500,GAME_HEIGHT=1200;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLocation(500,200);
        setResizable(false);//是否改变大小
        setTitle("坦克大战游戏");
        setVisible(true);

        //增加按键监听事件
        this.addKeyListener(new MyKeyListener());
        //增加窗口监听事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    Image offScreenImage=null;
    @Override
    public void update(Graphics g){
        if(offScreenImage==null){
            offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen=offScreenImage.getGraphics();
        Color c=gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    @Override
    public void paint(Graphics g) {
        Color c=g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量："+bullets.size(),10,60);
        g.drawString("敌人的数量："+tanks.size(),10,80);
        g.drawString("爆炸的数量："+explodes.size(),10,100);


        g.setColor(c);
        myTank.paint(g);
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < tanks.size(); i++) {
           tanks.get(i).paint(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j <tanks.size() ; j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
           // bullets.get(i).collideWith(myTank);
        }


    }

    class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        //键盘按下事件
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();//按键的代码
            //System.out.println("key=" + key);
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }


        //键盘抬起事件
        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("keyReleased");
            int key = e.getKeyCode();//按键的代码
           // System.out.println("key=" + key);
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }
    }

}