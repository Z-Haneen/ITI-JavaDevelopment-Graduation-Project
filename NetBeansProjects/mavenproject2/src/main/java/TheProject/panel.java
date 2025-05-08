package TheProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class panel extends JPanel {

    private static final int RECTANGLE = 0;
    private static final int OVAL = 1;
    private static final int LINE = 2;
    private final int ERASER_SIZE = 20;

    private Color currentColor = Color.BLACK;
    private int currentShape = RECTANGLE;
    private boolean freeHand = false;
    private boolean erasing = false;
    private boolean dotted = false;
    private boolean filled = false;
    private Shape[] shapes = new Shape[100];
    private int shapeCount = 0;
    private Point startPoint;
    private String prev = "#000000"; // Store previous color
    private final String back = "#FFFFFF"; // Background color

    public panel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        add(toolbar, BorderLayout.NORTH);

        JPanel toolbar2 = new JPanel();
        toolbar2.setLayout(new BoxLayout(toolbar2, BoxLayout.Y_AXIS));
        add(toolbar2, BorderLayout.WEST);

        JPanel toolbar3 = new JPanel();
        toolbar3.setLayout(new BoxLayout(toolbar3, BoxLayout.Y_AXIS));
        add(toolbar3, BorderLayout.EAST);

        // Color buttons
        JButton blackButton = new JButton("");
        blackButton.addActionListener(e -> currentColor = Color.BLACK);
        blackButton.setPreferredSize(new Dimension(30, 30));
        blackButton.setBackground(Color.BLACK);
        toolbar.add(blackButton);

        JButton greyDButton = new JButton("");
        greyDButton.addActionListener(e -> currentColor = Color.DARK_GRAY);
        greyDButton.setPreferredSize(new Dimension(30, 30));
        greyDButton.setBackground(Color.DARK_GRAY);
        toolbar.add(greyDButton);

        JButton greyButton = new JButton("");
        greyButton.addActionListener(e -> currentColor = Color.GRAY);
        greyButton.setPreferredSize(new Dimension(30, 30));
        greyButton.setBackground(Color.GRAY);
        toolbar.add(greyButton);

        JButton greyLButton = new JButton("");
        greyLButton.addActionListener(e -> currentColor = Color.LIGHT_GRAY);
        greyLButton.setPreferredSize(new Dimension(30, 30));
        greyLButton.setBackground(Color.LIGHT_GRAY);
        toolbar.add(greyLButton);

        JButton pinkButton = new JButton("");
        pinkButton.addActionListener(e -> currentColor = Color.PINK);
        pinkButton.setPreferredSize(new Dimension(30, 30));
        pinkButton.setBackground(Color.PINK);
        toolbar.add(pinkButton);

        JButton marButton = new JButton("");
        marButton.addActionListener(e -> currentColor = Color.MAGENTA);
        marButton.setPreferredSize(new Dimension(30, 30));
        marButton.setBackground(Color.MAGENTA);
        toolbar.add(marButton);

        JButton blueButton = new JButton("");
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        blueButton.setPreferredSize(new Dimension(30, 30));
        blueButton.setBackground(Color.BLUE);
        toolbar.add(blueButton);

        JButton cyanButton = new JButton("");
        cyanButton.addActionListener(e -> currentColor = Color.CYAN);
        cyanButton.setPreferredSize(new Dimension(30, 30));
        cyanButton.setBackground(Color.CYAN);
        toolbar.add(cyanButton);

        JButton greenButton = new JButton("");
        greenButton.addActionListener(e -> currentColor = Color.GREEN);
        greenButton.setPreferredSize(new Dimension(30, 30));
        greenButton.setBackground(Color.GREEN);
        toolbar.add(greenButton);

        JButton yellow2Button = new JButton("");
        yellow2Button.addActionListener(e -> currentColor = Color.YELLOW);
        yellow2Button.setPreferredSize(new Dimension(30, 30));
        yellow2Button.setBackground(Color.YELLOW);
        toolbar.add(yellow2Button);

        JButton orangeButton = new JButton("");
        orangeButton.addActionListener(e -> currentColor = Color.ORANGE);
        orangeButton.setPreferredSize(new Dimension(30, 30));
        orangeButton.setBackground(Color.ORANGE);
        toolbar.add(orangeButton);

        JButton redButton = new JButton("");
        redButton.addActionListener(e -> currentColor = Color.RED);
        redButton.setPreferredSize(new Dimension(30, 30));
        redButton.setBackground(Color.RED);
        toolbar.add(redButton);

        Dimension commonButtonSize = new Dimension(100, 50);

        JButton rectButton = new JButton("Rectangle");
        rectButton.addActionListener(e -> currentShape = RECTANGLE);
        rectButton.setPreferredSize(new Dimension(commonButtonSize));
        toolbar2.add(rectButton);

        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(e -> currentShape = OVAL);
        ovalButton.setPreferredSize(new Dimension(commonButtonSize));
        toolbar2.add(ovalButton);

        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> currentShape = LINE);
        toolbar2.add(lineButton);

        JButton freeHandButton = new JButton("Free Hand");
        freeHandButton.addActionListener(e -> {
            freeHand = !freeHand;
            freeHandButton.setText(freeHand ? "Drawing Free Hand" : "Free Hand");
        });
        toolbar2.add(freeHandButton);

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> {
            shapeCount = 0;
            repaint();
        });
        toolbar3.add(clearButton);
//
//        JButton eraserButton = new JButton("Eraser");
//        eraserButton.addActionListener(e -> erasing = !erasing);
//        toolbar3.add(eraserButton);

        JButton brushButton = new JButton("BRUSH");
        brushButton.setPreferredSize(new Dimension(80, 30));
        toolbar3.add(brushButton);

        JButton eraseButton = new JButton("ERASE");
        eraseButton.setPreferredSize(new Dimension(80, 30));
        toolbar3.add(eraseButton);

        brushButton.addActionListener(e -> {
            erasing = false;
            currentColor = Color.decode(prev);
        });

        eraseButton.addActionListener(e -> {
            if (!currentColor.equals(Color.decode(back))) {
                prev = String.format("#%02x%02x%02x", currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            }
            currentColor = Color.decode(back);
            erasing = true;
        });

        // Add the dotted checkbox
        DottedCheckBox dottedCheckBox = new DottedCheckBox("Dotted");
        dottedCheckBox.addItemListener(e -> dotted = dottedCheckBox.isSelected());
        toolbar.add(dottedCheckBox);

        // Add the filled checkbox
        FilledCheckBox filledCheckBox = new FilledCheckBox("Filled");
        filledCheckBox.addItemListener(e -> filled = filledCheckBox.isSelected());
        toolbar.add(filledCheckBox);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (freeHand) {
                    if (shapeCount < shapes.length) {
                        shapes[shapeCount++] = new FreeHandShape(startPoint, currentColor, dotted, filled);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!freeHand) {
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
                    Graphics g = getGraphics();
                    g.setColor(getBackground());
                    g.fillOval(e.getX() - ERASER_SIZE / 2, e.getY() - ERASER_SIZE / 2, ERASER_SIZE, ERASER_SIZE);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < shapeCount; i++) {
            shapes[i].draw(g);
        }
    }

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
        private final Point start;
        private final Point end;

        RectangleShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);
            if (filled) {
                g.fillRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
            if (dotted) {
                Graphics2D g2d = (Graphics2D) g;
                Stroke dottedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5}, 0);
                g2d.setStroke(dottedStroke);
                g2d.drawRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
        }
    }

    private class OvalShape extends Shape {
        private final Point start;
        private final Point end;

        OvalShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);
            if (filled) {
                g.fillOval(x, y, width, height);
            } else {
                g.drawOval(x, y, width, height);
            }
            if (dotted) {
                Graphics2D g2d = (Graphics2D) g;
                Stroke dottedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5}, 0);
                g2d.setStroke(dottedStroke);
                g2d.drawOval(x, y, width, height);
            } else {
                g.drawOval(x, y, width, height);
            }
        }
    }

    private class LineShape extends Shape {
        private final Point start;
        private final Point end;

        LineShape(Point start, Point end, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            if (dotted) {
                Graphics2D g2d = (Graphics2D) g;
                Stroke dottedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5}, 0);
                g2d.setStroke(dottedStroke);
                g2d.drawLine(start.x, start.y, end.x, end.y);
            } else {
                g.drawLine(start.x, start.y, end.x, end.y);
            }
        }
    }

    private class FreeHandShape extends Shape {
        private final List<Point> points;

        FreeHandShape(Point startPoint, Color color, boolean dotted, boolean filled) {
            super(color, dotted, filled);
            points = new ArrayList<>();
            points.add(startPoint);
        }

        void addPoint(Point point) {
            points.add(point);
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}

/**
 
    private static final int RECTANGLE = 0;
    private static final int OVAL = 1;
    private static final int LINE = 2;
    private static final int ERASER = 3; // Added ERASER tool type

    private Color currentColor = Color.RED;
    private int currentShape = RECTANGLE;
    private boolean freeHand = false;
    private Shape[] shapes = new Shape[100];
    private int shapeCount = 0;
    private Point startPoint;

    public panel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        add(toolbar, BorderLayout.NORTH);

        JButton yellowButton = new JButton("Yellow");
        yellowButton.addActionListener(e -> currentColor = Color.YELLOW);
        yellowButton.setBackground(Color.YELLOW);
        toolbar.add(yellowButton);

        JButton greenButton = new JButton("Green");
        greenButton.addActionListener(e -> currentColor = Color.GREEN);
        greenButton.setBackground(Color.GREEN);
        greenButton.setForeground(Color.BLACK);
        toolbar.add(greenButton);

        JButton blueButton = new JButton("Blue");
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        blueButton.setBackground(Color.BLUE);
        blueButton.setForeground(Color.WHITE);
        toolbar.add(blueButton);

        JButton rectButton = new JButton("Rectangle");
        rectButton.addActionListener(e -> currentShape = RECTANGLE);
        toolbar.add(rectButton);

        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(e -> currentShape = OVAL);
        toolbar.add(ovalButton);

        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> currentShape = LINE);
        toolbar.add(lineButton);

        JButton freeHandButton = new JButton("Free Hand");
        freeHandButton.addActionListener(e -> {
            freeHand = !freeHand;
            freeHandButton.setText(freeHand ? "Drawing Free Hand" : "Free Hand");
        });
        toolbar.add(freeHandButton);
----------------------------------------------------------------------------------------
        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> {
            currentShape = ERASER; // Set current shape to ERASER
            freeHand = true; // Enable free hand mode for eraser
        });
        toolbar.add(eraserButton);
----------------------------------------------------------------------------------------

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> {
            shapeCount = 0;
            repaint();
        });
        toolbar.add(clearButton);
----------------------------------------------------------------------------------------

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (shapeCount < shapes.length) {
                    Color color = currentShape == ERASER ? getBackground() : currentColor; // Use background color for eraser
                    shapes[shapeCount++] = new FreeHandShape(startPoint, color);
                }
            }
----------------------------------------------------------------------------------------

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!freeHand) {
                    Shape newShape = null;
                    Point endPoint = e.getPoint();
                    switch (currentShape) {
                        case RECTANGLE:
                            newShape = new RectangleShape(startPoint, endPoint, currentColor);
                            break;
                        case OVAL:
                            newShape = new OvalShape(startPoint, endPoint, currentColor);
                            break;
                        case LINE:
                            newShape = new LineShape(startPoint, endPoint, currentColor);
                            break;
                    }
                    if (newShape != null && shapeCount < shapes.length) {
                        shapes[shapeCount++] = newShape;
                    }
                    repaint();
                }
            }
        });
----------------------------------------------------------------------------------------

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (shapeCount > 0) {
                    ((FreeHandShape) shapes[shapeCount - 1]).addPoint(e.getPoint());
                    repaint();
                }
            }
        });
    }
----------------------------------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < shapeCount; i++) {
            shapes[i].draw(g);
        }
    }

    private abstract class Shape {
        Color color;

        Shape(Color color) {
            this.color = color;
        }

        abstract void draw(Graphics g);
    }

    private class RectangleShape extends Shape {
        Point start, end;

        RectangleShape(Point start, Point end, Color color) {
            super(color);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);
            g.drawRect(x, y, width, height);
        }
    }

    private class OvalShape extends Shape {
        Point start, end;

        OvalShape(Point start, Point end, Color color) {
            super(color);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);
            g.drawOval(x, y, width, height);
        }
    }

    private class LineShape extends Shape {
        Point start, end;

        LineShape(Point start, Point end, Color color) {
            super(color);
            this.start = start;
            this.end = end;
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    private class FreeHandShape extends Shape {
        private List<Point> points = new ArrayList<>();

        FreeHandShape(Point start, Color color) {
            super(color);
            points.add(start);
        }

        void addPoint(Point p) {
            points.add(p);
        }

        @Override
        void draw(Graphics g) {
            g.setColor(color);
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
 */









/********************************************************/

/*
//
//    private static final int RECTANGLE = 0;
//    private static final int OVAL = 1;
//    private static final int LINE = 2;
//    private static final int ERASER = 3;
//    private static final int FREEHAND  = 4;
//
//    private Color currentColor = Color.RED;
//    private int currentShape = RECTANGLE;
//    private boolean freeHand = false;
//    private boolean erasing = false; // Fixed initialization
//    private Shape[] shapes = new Shape[100];
//    private int shapeCount = 0;
//    private int pointCount =0;
//    private Point startPoint;
//
//    public panel() {
//        setBackground(Color.WHITE);
//
//        JPanel toolbar = new JPanel();
//        add(toolbar, BorderLayout.NORTH);
//        
//        JButton blackbutton = new JButton("");
//        blackbutton.addActionListener(e -> currentColor = Color.black);
//        blackbutton.setBackground(Color.black);
//        blackbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(blackbutton);
//
//        JButton GREYDbutton = new JButton("");
//        GREYDbutton.addActionListener(e -> currentColor = Color.DARK_GRAY);
//        GREYDbutton.setBackground(Color.DARK_GRAY);
//        GREYDbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(GREYDbutton);
//
//        JButton GREYbutton = new JButton("");
//        GREYbutton.addActionListener(e -> currentColor = Color.GRAY);
//        GREYbutton.setBackground(Color.GRAY);
//        GREYbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(GREYbutton);
//
//        JButton GREYLbutton = new JButton("");
//        GREYLbutton.addActionListener(e -> currentColor = Color.LIGHT_GRAY);
//        GREYLbutton.setBackground(Color.LIGHT_GRAY);
//        GREYLbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(GREYLbutton);
//
//        JButton PINKbutton = new JButton("");
//        PINKbutton.addActionListener(e -> currentColor = Color.PINK);
//        PINKbutton.setBackground(Color.PINK);
//        PINKbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(PINKbutton);
//
//        JButton magentabutton = new JButton("");
//        magentabutton.addActionListener(e -> currentColor = Color.MAGENTA);
//        magentabutton.setBackground(Color.MAGENTA);
//        magentabutton.setPreferredSize(new Dimension(30, 30));
//
//        toolbar.add(magentabutton);
//
//        JButton bluebutton = new JButton("");
//        bluebutton.addActionListener(e -> currentColor = Color.BLUE);
//        bluebutton.setBackground(Color.BLUE);
//        bluebutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(bluebutton);
//
//        JButton cyanbutton = new JButton("");
//        cyanbutton.addActionListener(e -> currentColor = Color.cyan);
//        cyanbutton.setBackground(Color.CYAN);
//        cyanbutton.setPreferredSize(new Dimension(30, 30));
//
//        toolbar.add(cyanbutton);
//
//        JButton greenbutton = new JButton("");
//        greenbutton.addActionListener(e -> currentColor = Color.GREEN);
//        greenbutton.setBackground(Color.GREEN);
//        greenbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(greenbutton);
//
//        JButton yellowButton = new JButton("");
//        yellowButton.addActionListener(e -> currentColor = Color.yellow);
//        yellowButton.setBackground(Color.yellow);
//        yellowButton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(yellowButton);
//
//        JButton orangebutton = new JButton("");
//        orangebutton.addActionListener(e -> currentColor = Color.ORANGE);
//        orangebutton.setBackground(Color.ORANGE);
//        orangebutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(orangebutton);
//
//        JButton redbutton = new JButton("");
//        redbutton.addActionListener(e -> currentColor = Color.RED);
//        redbutton.setBackground(Color.RED);
//        redbutton.setPreferredSize(new Dimension(30, 30));
//        toolbar.add(redbutton);
//        /***********************************************/
//        
//        
//        JButton rectButton = new JButton("Rectangle");
//        rectButton.addActionListener(e -> currentShape = RECTANGLE);
//        toolbar.add(rectButton);
//
//        JButton ovalButton = new JButton("Oval");
//        ovalButton.addActionListener(e -> currentShape = OVAL);
//        toolbar.add(ovalButton);
//
//        JButton lineButton = new JButton("Line");
//        lineButton.addActionListener(e -> currentShape = LINE);
//        toolbar.add(lineButton);
//
//        JButton freeHandButton = new JButton("Free Hand");
//        freeHandButton.addActionListener(e -> {
//            freeHand = !freeHand;
//            freeHandButton.setText(freeHand ? "Drawing Free Hand" : "Free Hand");
//        });
//        toolbar.add(freeHandButton);
//        
//        
//        
//        
//        
//        JButton freeHandButton2 = new JButton("Free Hand2");
//        freeHandButton2.addActionListener(e -> currentShape= FREEHAND);
//        toolbar.add(freeHandButton2);
//        
//        
//        
//
//        JButton eraserButton = new JButton("Eraser");
//        eraserButton.addActionListener(e -> {
//            currentShape = ERASER;
//            freeHand = true; // Enable free hand mode for eraser
//            erasing = !erasing; // Toggle erasing mode
//        });
//        toolbar.add(eraserButton);
//
//        
//        
//        
//        
//        JButton clearButton = new JButton("Clear All");
//        clearButton.addActionListener(e -> {
//            shapeCount = 0;
//            repaint();
//        });
//        toolbar.add(clearButton);
///****************************************************************/
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                startPoint = e.getPoint();
//                if (currentShape == ERASER) {
//                    if (shapeCount < shapes.length) {
//                        shapes[shapeCount++] = new FreeHandShape(startPoint, getBackground());
//                    }
//                } else if (shapeCount < shapes.length) {
//                    shapes[shapeCount++] = new FreeHandShape(startPoint, currentColor);
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                Shape newShape = null;
//                Point endPoint = e.getPoint();
//                switch (currentShape) {
//                    case RECTANGLE:
//                        newShape = new RectangleShape(startPoint, endPoint, currentColor);
//                        break;
//                    case OVAL:
//                        newShape = new OvalShape(startPoint, endPoint, currentColor);
//                        break;
//                    case LINE:
//                        newShape = new LineShape(startPoint, endPoint, currentColor);
//                        break;
//                    case ERASER:
//                        newShape = new FreeHandShape(startPoint, getBackground());
//                        break;
//                    case FREEHAND :
//                          newShape = new FreeHandShape2(startPoint, getBackground());
//                }
//                if (newShape != null && shapeCount < shapes.length) {
//                    shapes[shapeCount++] = newShape;
//                }
//                repaint();
//            }
//        });
//
//        addMouseMotionListener(new MouseMotionAdapter() {//anymous inner class
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if (freeHand) {
//                    if (shapeCount > 0) {
//                        ((FreeHandShape) shapes[shapeCount - 1]).addPoint(e.getPoint());
//                        repaint();
//                    }
//                }             }
//        });
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        for (int i = 0; i < shapeCount; i++) {
//            shapes[i].draw(g);
//        }
//    }
//
//    private abstract class Shape {
//        Color color;
//
//        Shape(Color color) {
//            this.color = color;
//        }
//
//        abstract void draw(Graphics g);
//    }
//
//    private class RectangleShape extends Shape {
//        Point start, end;
//
//        RectangleShape(Point start, Point end, Color color) {
//            super(color);
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        void draw(Graphics g) {
//            g.setColor(color);
//            int x = Math.min(start.x, end.x);
//            int y = Math.min(start.y, end.y);
//            int width = Math.abs(start.x - end.x);
//            int height = Math.abs(start.y - end.y);
//            g.drawRect(x, y, width, height);
//        }
//    }
//
//    private class OvalShape extends Shape {
//        Point start, end;
//
//        OvalShape(Point start, Point end, Color color) {
//            super(color);
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        void draw(Graphics g) {
//            g.setColor(color);
//            int x = Math.min(start.x, end.x);
//            int y = Math.min(start.y, end.y);
//            int width = Math.abs(start.x - end.x);
//            int height = Math.abs(start.y - end.y);
//            g.drawOval(x, y, width, height);
//        }
//    }
//
//    private class LineShape extends Shape {
//        Point start, end;
//
//        LineShape(Point start, Point end, Color color) {
//            super(color);
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        void draw(Graphics g) {
//            g.setColor(color);
//            g.drawLine(start.x, start.y, end.x, end.y);
//        }
//    }
//
//    private class FreeHandShape extends Shape {
//        private Point[] points = new Point[100];
//
//        FreeHandShape(Point start, Color color) {
//            super(color);
//          points[0]=start;
//        }
//
//        void addPoint(Point p) {
//          if (pointCount < points.length){
//              points [pointCount ++]=p;
//          }
//        }
//
//        @Override
//        void draw(Graphics g) {
//            g.setColor(color);
//            for (int i = 0; i < points.length - 1; i++) {
//                Point p1 = points[i];
//                Point p2 = points[i+1];
//                g.drawLine(p1.x, p1.y, p2.x, p2.y);
//            }
//        }
//    }
//    
//    
//    
//    
//    
//    
//    
//    private class FreeHandShape2 extends Shape {
//        private Point[] points = new Point[100];
//
//        FreeHandShape2(Point start, Color color) {
//            super(color);
//          points[0]=start;
//        }
//
//        void addPoint(Point p) {
//             if (pointCount < points.length){
//              points [pointCount ++]=p;
//          }
//        }
//
//       
//    @Override
//    protected void draw(Graphics g) {
//      
//        g.setColor(color);
//            for (int i = 0; i < points.length - 1; i++) {
//                Point p1 = points[i];
//                Point p2 = points[i+1];
//                g.drawLine(p1.x, p1.y, p2.x, p2.y);
//            }
//       
//        }
//    }
//    
//    
//    
//    
//    
//    
//    
//*/
