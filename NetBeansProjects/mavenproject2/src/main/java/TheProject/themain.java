/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheProject;

import theButton.TextMoving;
import javax.swing.JFrame;

/**
 *
 * @author haneen
 */
public class themain {

    public static  void main(String[] args) {

        JFrame f = new JFrame();
        TextMoving panel = new TextMoving();
        f.setDefaultCloseOperation(3);
        f.setSize(400, 500);
        f.setTitle("hannon");
 //    panel paint = new panel();
 PaintBrush paint = new PaintBrush();
        f.setContentPane(paint);
        f.setVisible(true);
   		//FreeHandDrawing canvas = new FreeHandDrawing();
		//canvas.CanvasView();
	}
    
    }


