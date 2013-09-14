package com.dasatti.uhtml;

import java.awt.*;
import javax.swing.*;

public class ColoredBox implements Icon {
    Color color;
    int width=50,height=15;
    public ColoredBox (Color c) {
      color = c;
    }
    public ColoredBox (Color c,int w,int h) {
      color = c;
      width=w;
      height=h;
    }
    public void paintIcon (Component c, Graphics g, int x, int y) {
      g.setColor(color);
      g.fillRect (x, y, getIconWidth(), getIconHeight());
      //g.drawString(color.toString(),x+15,y+10);
    }
    public int getIconWidth() {
      return width;
    }
    public int getIconHeight() { 
      return height;
    }
}
