/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheProject;

/**
 *
 * @author haneen
 */
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FreeHandDrawing implements MouseMotionListener{
	JFrame frame = new JFrame();
	JPanel canvasPanel, toolBar;
	JButton btn;
	JTextField col, sz;
	String prev, back;
	
	FreeHandDrawing() {
		canvasPanel = new JPanel();
		canvasPanel.addMouseMotionListener(this);
	}

	public void CanvasView() {
		
		//-----------CANVAS-------------
		canvasPanel.setBounds(320, 30, 850, 600);
		canvasPanel.setBackground(Color.white);
		canvasPanel.setLayout(null);
		frame.add(canvasPanel);
		canvasPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cl = col.getText();
				int rad = Integer.parseInt(sz.getText());
				Graphics grap = canvasPanel.getGraphics();
				grap.setColor(Color.decode(cl));
				grap.fillRect(e.getX(), e.getY(), rad, rad);
			}
		});
		//-----------------------------
		
		//-----------TOOLBAR-------------
		toolBar = new JPanel();
		toolBar.setBounds(30, 30, 250, 600);
		toolBar.setBackground(Color.white);
		toolBar.setLayout(null);
		frame.add(toolBar);
		addButton(32, 30, "#000000");
		addButton(105, 30, "#FFFFFF");
		addButton(177, 30, "#808080");
		addButton(32, 90, "#FF0000");
		addButton(105, 90, "#00FF00");
		addButton(177, 90, "#0000FF");
		addButton(32, 150, "#FFFF00");
		addButton(105, 150, "#FFA500");
		addButton(177, 150, "#A020F0");
		addButton(32, 210, "#FFC0CB");
		addButton(105, 210, "#964B00");
		addButton(177, 210, "#C32148");
		//-----------------------------
		
		//---------CUSTOM---------------
		JLabel cllabel = new JLabel("Custom Colors : ");
		cllabel.setBounds(30, 260, 100, 50);
		toolBar.add(cllabel);
		col = new JTextField();
		col.setBounds(30, 310, 190, 30);
		col.setText("#000000");
		toolBar.add(col);
		//------------------------------
		
		//-----------BACKGROUNDCOLOUR-------------
		back = "#FFFFFF";
		JButton bc = new JButton("SET BACKGROUND");
		bc.setBounds(30, 350, 190, 30);
		toolBar.add(bc);
		bc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back = col.getText();
				canvasPanel.setBackground(Color.decode(back));
			}
		});
		//---------------------------------

		//---------SIZE------------------
		JLabel szlabel = new JLabel("Size : ");
		szlabel.setBounds(30, 380, 100, 50);
		toolBar.add(szlabel);
		JButton sub = new JButton("-");
		sub.setBounds(30, 415, 50, 30);
		toolBar.add(sub);
		sz = new JTextField();
		sz.setBounds(100, 415, 50, 30);
		sz.setText("5");
		toolBar.add(sz);
		JButton add = new JButton("+");
		add.setBounds(170, 415, 50, 30);
		toolBar.add(add);
		sub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sz.setText(Integer.parseInt(sz.getText())-1+"");
			}
		});
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sz.setText(Integer.parseInt(sz.getText())+1+"");
			}
		});
		//-------------------------------
		
		//---------PAINT&ERASE-----------------
		JButton brush = new JButton("BRUSH");
		brush.setBounds(30, 465, 80, 30);
		toolBar.add(brush);
		JButton erase = new JButton("ERASE");
		erase.setBounds(140, 465, 80, 30);
		toolBar.add(erase);
		prev = "#000000";
		brush.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col.setText(prev);
			}
		});
		erase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!col.getText().equals(back))
					prev = col.getText();
				col.setText(back);
			}
		});
		//--------------------------------
		
		//-----------CLEAN--------------
		JButton clean = new JButton("CLEAN");
		clean.setBounds(30, 515, 190, 30);
		toolBar.add(clean);
		clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvasPanel.removeAll();
				canvasPanel.repaint();
			}
		});
		//-------------------------------
		
		//-----------SAVE---------------
		JButton save = new JButton("SAVE");
		save.setBounds(30, 555, 190, 30);
		toolBar.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tm = java.time.LocalTime.now()+"";
				tm = tm.substring(0, 2) + tm.substring(3, 5) + tm.substring(6, 8) + tm.substring(9, 12) + ".png";
				try {
				BufferedImage image = getImg(canvasPanel);
				ImageIO.write(image, "png", new File(tm));
				}
				catch(Exception ex) {
					
				}
			}
		});
                
		//-------------------------------
		
		//------FRAME------------------
		frame.setSize(1250, 700);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#001122"));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//---------------------------------
	}
	//---------MOUSE FUNCTIONS---------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		String cl = col.getText();
		int rad = Integer.parseInt(sz.getText());
		Graphics grap = canvasPanel.getGraphics();
		grap.setColor(Color.decode(cl));
		grap.fillRect(e.getX(), e.getY(), rad, rad);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	//-------------------------------------------------

	//---------FUNCTIONS TO CREATE SIMILAR BUTTONS---------
	public void addButton(int x, int y, String clr) {
		this.btn = new JButton();
		btn.setBounds(x, y, 40, 40);
		btn.setBackground(Color.decode(clr));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col.setText(clr);
			}
		});
		toolBar.add(btn);
	}
	//-------------------------------------------------------
	
	//----------FUNCTION TO CREATE IMAGE TO SAVE-----------------------------
	public BufferedImage getImg(Component comp) throws AWTException {
	    Point p = comp.getLocationOnScreen();
	    Dimension dim = comp.getSize();
	    BufferedImage capture = new Robot().createScreenCapture(new Rectangle(p, dim));
	    return capture;
	}
	//--------------------------------------------------------------------------
}