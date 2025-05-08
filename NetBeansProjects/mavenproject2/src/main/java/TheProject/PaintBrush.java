/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package TheProject;

/**
 *
 * @author haneen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class PaintBrush extends JPanel {

    private static final int RECTANGLE = 0;
    private static final int OVAL = 1;
    private static final int LINE = 2;
    private static final int CIRCLE = 3;
    private static final int SQUARE = 4;
    private final int ERASER_SIZE = 20;
    private Color currentColor = Color.BLACK;
    private int currentShape = RECTANGLE;
    private boolean freeHand = false;
    private boolean erasing = false;
    private boolean dotted = false;
    private boolean filled = false;
    private Shape[] shapes = new Shape[3000];
    private int shapeCount = 0;
    private Point startPoint;
    private Image image; 
    private Image bufferedImage;
    
    public PaintBrush() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        add(toolbar, BorderLayout.NORTH);

        JPanel toolbar2 = new JPanel();
        toolbar2.setLayout(new BoxLayout(toolbar2, BoxLayout.Y_AXIS));
        add(toolbar2, BorderLayout.WEST);

        JButton clearButton = new JButton("ClearAll");
        clearButton.addActionListener(e -> {
            shapeCount = 0;
            if (image != null){
                image =null;
                
            }
            repaint();
        });
        toolbar.add(clearButton);

        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> erasing = !erasing);
        toolbar.add(eraserButton);

       
        JButton undoButton = new JButton("Undo");
        toolbar.add(undoButton);
        
        JButton blackButton = new JButton("");
        blackButton.addActionListener(e -> currentColor = Color.BLACK);
        blackButton.setPreferredSize(new Dimension(30, 30));
        blackButton.setBackground(Color.BLACK);
        toolbar.add(blackButton);

        JButton GREYDButton = new JButton("");
        GREYDButton.addActionListener(e -> currentColor = Color.DARK_GRAY);
        GREYDButton.setPreferredSize(new Dimension(30, 30));
        GREYDButton.setBackground(Color.DARK_GRAY);
        toolbar.add(GREYDButton);

        JButton GREYButton = new JButton("");
        GREYButton.addActionListener(e -> currentColor = Color.GRAY);
        GREYButton.setPreferredSize(new Dimension(30, 30));
        GREYButton.setBackground(Color.GRAY);
        toolbar.add(GREYButton);

        JButton GREYLButton = new JButton("");
        GREYLButton.addActionListener(e -> currentColor = Color.LIGHT_GRAY);
        GREYLButton.setPreferredSize(new Dimension(30, 30));
        GREYLButton.setBackground(Color.LIGHT_GRAY);
        toolbar.add(GREYLButton);

        JButton PINKButton = new JButton("");
        PINKButton.addActionListener(e -> currentColor = Color.PINK);
        PINKButton.setPreferredSize(new Dimension(30, 30));
        PINKButton.setBackground(Color.PINK);
        toolbar.add(PINKButton);

        JButton MARButton = new JButton("");
        MARButton.addActionListener(e -> currentColor = Color.MAGENTA);
        MARButton.setPreferredSize(new Dimension(30, 30));
        MARButton.setBackground(Color.MAGENTA);
        toolbar.add(MARButton);

        JButton blueButton = new JButton("");
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        blueButton.setPreferredSize(new Dimension(30, 30));
        blueButton.setBackground(Color.BLUE);
        toolbar.add(blueButton);

        JButton CYANButton = new JButton("");
        CYANButton.addActionListener(e -> currentColor = Color.CYAN);
        CYANButton.setPreferredSize(new Dimension(30, 30));
        CYANButton.setBackground(Color.CYAN);
        toolbar.add(CYANButton);

        JButton GREENButton = new JButton("");
        GREENButton.addActionListener(e -> currentColor = Color.GREEN);
        GREENButton.setPreferredSize(new Dimension(30, 30));
        GREENButton.setBackground(Color.GREEN);
        toolbar.add(GREENButton);

        JButton yellow2Button = new JButton("");
        yellow2Button.addActionListener(e -> currentColor = Color.YELLOW);
        yellow2Button.setPreferredSize(new Dimension(30, 30));
        yellow2Button.setBackground(Color.YELLOW);
        toolbar.add(yellow2Button);

        JButton ORANGEButton = new JButton("");
        ORANGEButton.addActionListener(e -> currentColor = Color.ORANGE);
        ORANGEButton.setPreferredSize(new Dimension(30, 30));
        ORANGEButton.setBackground(Color.ORANGE);
        toolbar.add(ORANGEButton);

        JButton REDButton = new JButton("");
        REDButton.addActionListener(e -> currentColor = Color.RED);
        REDButton.setPreferredSize(new Dimension(30, 30));
        REDButton.setBackground(Color.RED);
        toolbar.add(REDButton);

        Dimension commonButtonSize = new Dimension(100, 50);

        JButton freeHandButton = new JButton("FreeHand");
        freeHandButton.addActionListener(e -> {
            freeHand = !freeHand;
            freeHandButton.setText(freeHand ? " FreeHand on" : "FreeHand off");
        });
        toolbar2.add(freeHandButton);

        JButton rectButton = new JButton("Rectangle");
        rectButton.addActionListener(e -> currentShape = RECTANGLE);
        rectButton.setPreferredSize(new Dimension(commonButtonSize));
        toolbar2.add(rectButton);

        JButton squareButton = new JButton("Square");
        squareButton.addActionListener(e -> currentShape = SQUARE);
        toolbar2.add(squareButton);

        JButton circleButton = new JButton("Circle");
        circleButton.addActionListener(e -> currentShape = CIRCLE);
        toolbar2.add(circleButton);

        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(e -> currentShape = OVAL);
        ovalButton.setPreferredSize(new Dimension(commonButtonSize));
        toolbar2.add(ovalButton);

        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> currentShape = LINE);
        toolbar2.add(lineButton);

        JButton saveButton = new JButton("SAVE");
        saveButton.setBounds(30, 555, 190, 30);
        toolbar.add(saveButton);
        saveButton.addActionListener(e -> {
            String tm = java.time.LocalTime.now().toString().replace(":", "").replace(".", "") + ".png";
           
            try {
                BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                paint(g2d);
                g2d.dispose();
                ImageIO.write(image, "png", new File(tm));
                JOptionPane.showMessageDialog(this, "Image saved as " + tm);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
            }
        });
        
        

        DottedCheckBox dottedCheckBox = new DottedCheckBox("Dotted");
        dottedCheckBox.addItemListener(e -> {dotted = dottedCheckBox.isSelected();
        repaint();
        });
        toolbar.add(dottedCheckBox);

        FilledCheckBox filledCheckBox = new FilledCheckBox("Filled");
        filledCheckBox.addItemListener(e -> filled = filledCheckBox.isSelected());
        toolbar.add(filledCheckBox);
 JButton openButton = new JButton("Open");
        toolbar.add(openButton);
        
        openButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Open button clicked");  // تأكد من أن هذا السطر ينفذ
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                bufferedImage = ImageIO.read(file);
                image = bufferedImage; // Update image to the loaded image
                repaint(); // Ensure repaint is called after loading the image
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});

        
//     openButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                int result = fileChooser.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile();
//                    try {
//                        bufferedImage = ImageIO.read(file);
//                        image = bufferedImage; // Update image to the loaded image
//                        repaint(); // Ensure repaint is called after loading the image
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shapeCount > 0) {
                    shapeCount--;
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (freeHand) {
                    if (shapeCount < shapes.length) {
                        shapes[shapeCount++] = new FreeHandShape(startPoint, currentColor, dotted, filled);
                    }
                } else if (erasing) {
                    if (shapeCount < shapes.length) {
                        shapes[shapeCount++] = new EraserShape(startPoint);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!freeHand && !erasing) {
                    Shape newShape = null;
                    Point endPoint = e.getPoint();
                    switch (currentShape) {
                        case RECTANGLE:
                            newShape = new RectangleShape(startPoint, endPoint, currentColor, dotted, filled);
                            break;
                        case OVAL:
                            newShape = new OvalShape(startPoint, endPoint, currentColor, dotted, filled);
                            break;
                        case LINE:
                            newShape = new LineShape(startPoint, endPoint, currentColor, dotted, filled);
                            break;
                        case SQUARE:
                            newShape = new SquareShape(startPoint, endPoint, currentColor, dotted, filled);
                            break;
                        case CIRCLE:
                            newShape = new CircleShape(startPoint, endPoint, currentColor, dotted, filled);
                            break;
                    }
                    if (newShape != null && shapeCount < shapes.length) {
                        shapes[shapeCount++] = newShape;
                    }
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (freeHand) {
                    if (shapeCount > 0) {
                        ((FreeHandShape) shapes[shapeCount - 1]).addPoint(e.getPoint());
                        repaint();
                    }
                } else if (erasing) {
                    if (shapeCount < shapes.length) {
                        shapes[shapeCount++] = new EraserShape(e.getPoint());
                        repaint();
                    }
                }
            }
        });
    }
    
    
    
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image != null) {
        g.drawImage(image, getWidth()/2, getHeight()/2, this);
    }
    for (int i = 0; i < shapeCount; i++) {
        shapes[i].draw(g);
    }
}


//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        for (int i = 0; i < shapeCount; i++) {
//            shapes[i].draw(g);
//              if (image != null) {
//        g.drawImage(image, 0, 0, this);
//    }
//  
//    }
//    }

    public class DottedCheckBox extends JCheckBox {
        public DottedCheckBox(String text) {
            super(text);
        }
    }

    public class FilledCheckBox extends JCheckBox {
        public FilledCheckBox(String text) {
            super(text);
        }
    }

    private abstract class Shape {
        Color color;
        boolean dotted;
        boolean filled;

        Shape(Color color, boolean dotted, boolean filled) {
            this.color = color;
            this.dotted = dotted;
            this.filled = filled;
        }

        abstract void draw(Graphics g);
    }
private class RectangleShape extends Shape {
    Point start;
    Point end;

    RectangleShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
        super(color, dotted, filled);
        this.start = start;
        this.end = end;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        if (dotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke(1)); // Reset to default stroke
        }
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        if (filled) {
            g2d.fillRect(x, y, width, height);
        } else {
            g2d.drawRect(x, y, width, height);
        }
    }
}

private class OvalShape extends Shape {
    Point start;
    Point end;

    OvalShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
        super(color, dotted, filled);
        this.start = start;
        this.end = end;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        if (dotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke(1)); // Reset to default stroke
        }
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        if (filled) {
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.drawOval(x, y, width, height);
        }
    }
}
private class LineShape extends Shape {
    Point start;
    Point end;

    LineShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
        super(color, dotted, filled);
        this.start = start;
        this.end = end;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        
        if (dotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke());
        }
        
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }
}
private class FreeHandShape extends Shape {
    List<Point> points = new ArrayList<>();

    FreeHandShape(Point start, Color color, boolean dotted, boolean filled) {
        super(color, dotted, filled);
        points.add(start);
    }

    void addPoint(Point point) {
        points.add(point);
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        
        if (dotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke());
        }
        
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}

    private class SquareShape extends Shape {
        Point start;
        Point end;

        SquareShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
              Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            if (dotted) {
                float[] dashPattern = {5, 5};
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
            }else {
            g2d.setStroke(new BasicStroke());
        }
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int size = Math.min(Math.abs(start.x - end.x), Math.abs(start.y - end.y));
            if (filled) {
                g.fillRect(x, y, size, size);
            } else {
                g.drawRect(x, y, size, size);
            }
        }
    }

    private class CircleShape extends Shape {
        Point start;
        Point end;

        CircleShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
          Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(color);
            if (dotted) {
                float[] dashPattern = {5, 5};
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
            }else {
            g2d.setStroke(new BasicStroke());
        }
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int size = Math.min(Math.abs(start.x - end.x), Math.abs(start.y - end.y));
            if (filled) {
                g.fillOval(x, y, size, size);
            } else {
                g.drawOval(x, y, size, size);
            }
        }
    }

    private class EraserShape extends Shape {
        Point point;

        EraserShape(Point point) {
            super(Color.WHITE, false, false);
            this.point = point;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(point.x - ERASER_SIZE / 2, point.y - ERASER_SIZE / 2, ERASER_SIZE, ERASER_SIZE);
        }
    }

}