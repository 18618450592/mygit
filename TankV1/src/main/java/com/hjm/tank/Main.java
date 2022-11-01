package com.hjm.tank;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf =new TankFrame();

        JButton jButton=new JButton();
        jButton.setBounds(100,200,100,30);
        jButton.setVisible(true);
        jButton.setText("重新开始");
        jButton.setBackground(Color.blue);
        tf.add(jButton);

        System.out.println(Integer.valueOf(PropertyMgr.get("initTankCount").toString()));
        int tankCount=Integer.valueOf(PropertyMgr.get("initTankCount").toString());
        //初始化敌方tank
        for (int i = 0; i < tankCount; i++) {
            tf.tanks.add(new Tank(50+(i*160),200,Dir.DOWN,Group.BAD,tf));
        }
        while (true){
            Thread.sleep(50);
            tf.repaint();
        }

    }
}
