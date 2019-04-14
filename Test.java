
import java.awt.*;
import java.awt.event.*;

public class Test extends Frame implements KeyListener{

    private double[] noise;
    private double[] rand;

    private int scale = 10;
    private int height = 600;
    private int width = 800;
    private int size = 500;
    
    private boolean fullscreen;
    
    public static void main(String[] args){
        new Test();
    }

    public Test(int s){
        this.size = s;
        scale = width / size;
        setSize(width, height);
        setTitle("NOISE TESTER");
        init();
        fullscreen = false;
        addKeyListener(this);
        addWindowListener( new WindowAdapter() {
                @Override
                public void windowClosing ( WindowEvent e ) { System.exit( 0 ); }
            } );
        setVisible(true);
    }
    
    public Test(){
        this(256);
    }

    public void paint(Graphics g){
        scale = getWidth() / size;
        
        g.setColor(Color.BLACK);
        int offset = Math.min(getHeight(), getWidth())/10;
        g.setFont(new Font(getFont().getName(), getFont().getStyle(), offset/3));
        g.drawString("Press 'SPACE' to reload, 'UP' or 'DOWN' for size/currently: " + size + " | 'f' toggle fullscreen", offset, offset);
        
        int n = noise.length;
        //draw random data
        g.setColor(new Color(255, 0, 0, 128));
        for(int i = 0; i < n-1; i++){
            // g.fillRect(i*scale, 0, scale, (int)(noise[i]*height*0.5));
            g.drawLine(i*scale, (int)((1-rand[i])*getHeight()), 
                (i+1)*scale, (int)((1-rand[i+1])*getHeight()));
        }
        
        //draw noise data
        g.setColor(Color.GREEN);
        for(int i = 0; i < n-1; i++){
            // g.fillRect(i*scale, 0, scale, (int)(noise[i]*height*0.5));
            g.drawLine(i*scale, (int)((1-noise[i])*getHeight()), 
                (i+1)*scale, (int)((1-noise[i+1])*getHeight()));
        }
    }
    
    public void keyReleased(KeyEvent e){}
    
    private void init(){
        NoiseGenerator n = new NoiseGenerator(size);
        noise = n.getNoise();
        rand = n.getRand();
    }
    
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            init();
            repaint();
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            size++;
            init();
            repaint();
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(size > 1) size--;
            init();
            repaint();
        }else if(e.getKeyCode() == KeyEvent.VK_F){
            if(!fullscreen){
                GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                device.setFullScreenWindow(this);
            }else{
                GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                device.setFullScreenWindow(null);
            }
            fullscreen = !fullscreen;
        }
    }
    
    public void keyTyped(KeyEvent e){}

    public static void printNoise(int size){
        NoiseGenerator n = new NoiseGenerator(size);
        double[] noise = n.getNoise();
        System.out.println("NOISE");
        for(double d : noise){
            System.out.print("|" + d + "\t");
        }
        System.out.println("");
        double[] rand = n.getRand();
        System.out.println("RANDOM");
        for(double d : rand){
            System.out.print("|" + d + "\t");
        }
    }
}
