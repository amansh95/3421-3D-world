
package ass2.spec;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class Game extends JFrame implements GLEventListener {
	
	private static final long serialVersionUID = 1L;

    // X Y Z
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    

    public static int getwidth(){ return width; }
    public static int getheight(){ return height; }

    
   //window dimension
    private static final int height = 360;
    private static final int width = 480;
    private static final int FPS = 60;
    
    private Avatar avatar;
    
    // Terrain
    private Terrain myTerrain;
    
    // Camera
    private Camera myCamera;
    
    //movement speed
    private double speed;
    
    private double cHeight = 3;
    private double cDist = 4;
    
    private KeyboardInput myKeyboard;
    
    boolean wireframeMode;
    boolean drawCoord;
    
    public Game(Terrain terrain) {
         
        super("Assignment 2");
        
        this.myTerrain = terrain;
        this.myCamera = new Camera(terrain, cHeight, cDist);
        this.speed=0.1;
        this.myKeyboard = new KeyboardInput();
        this.wireframeMode = false;
        this.drawCoord = false;
        
     }
     
     
     /************************************
      *         METHOD (RUN)             *
      ***********************************/
    public void run() {
        
        // Init Profile
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);

        // Create Panel and Add Listeners
        GLJPanel panel = new GLJPanel(caps);
        panel.addGLEventListener(this);
        panel.addKeyListener(myKeyboard);
        panel.setFocusable(true);
        
        // Add an animator to call 'display' at 60fps
        FPSAnimator animator = new FPSAnimator(FPS);
        animator.add(panel);
        animator.start();
        
        // Add Panel to this Frame
        getContentPane().add(panel);
        
        // Frame Settings
        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);     
        
    }
   
    private void update(GL2 gl) {
        
        
        double[] pos = Camera.getPos();
        double[] angle = Camera.getAngle();

        
        if(KeyboardInput.key(Controls.W)) {
            pos[Game.X] += speed * Math.cos(Math.toRadians(angle[Game.Y])) * Math.cos(Math.toRadians(angle[Game.Z]));
            if(Camera.getThirdPerson()){
                pos[Game.Y] += speed * Math.sin(Math.toRadians(angle[Game.Z]));
            }else{
                pos[Game.Y] += speed * Math.sin(Math.toRadians(angle[Game.Z]+45));
            }
            pos[Game.Z] -= speed * Math.sin(Math.toRadians(angle[Game.Y]));
        }
        if(KeyboardInput.key(Controls.S)) {
            pos[Game.X] -= speed * Math.cos(Math.toRadians(angle[Game.Y])) * Math.cos(Math.toRadians(angle[Game.Z]));
            if(Camera.getThirdPerson()){
                pos[Game.Y] -= speed * Math.sin(Math.toRadians(angle[Game.Z]));
            }else{
                pos[Game.Y] -= speed * Math.sin(Math.toRadians(angle[Game.Z]+45));
            }
            pos[Game.Z] += speed * Math.sin(Math.toRadians(angle[Game.Y]));
        }
        if(KeyboardInput.key(Controls.A)) {
            pos[Game.X] += speed * Math.cos(Math.toRadians(angle[Game.Y]+90));// * Math.cos(Math.toRadians(angle[Game.Z]));
            pos[Game.Z] -= speed * Math.sin(Math.toRadians(angle[Game.Y]+90));
        }
        if(KeyboardInput.key(Controls.D)) {
            pos[Game.X] -= speed * Math.cos(Math.toRadians(angle[Game.Y]+90));// * Math.cos(Math.toRadians(angle[Game.Z]));
            pos[Game.Z] += speed * Math.sin(Math.toRadians(angle[Game.Y]+90));
        }
            
    

        /**
         *  Rotate camera
         *  TODO - Fix camera positioning and angle
         */
        
        if(KeyboardInput.key(Controls.UP)) {
            angle[Game.Z] += 5;
        }
        if(KeyboardInput.key(Controls.DOWN)) {
            angle[Game.Z] += -5;
        }
        if(KeyboardInput.key(Controls.LEFT)) {
            angle[Game.Y] += 5;
        }
        if(KeyboardInput.key(Controls.RIGHT)) {
            angle[Game.Y] += -5;
        }
        
        
        // Toggle view
        if(KeyboardInput.keyToggle(Controls.C)) {
            Camera.setThirdPerson(!Camera.getThirdPerson());
            if(Camera.getThirdPerson()) {
                angle[Game.X] = 0;
                angle[Game.Y] = 0;
                angle[Game.Z] = -45;
            } else {
                angle[Game.X] = 0;
                angle[Game.Y] = 0;
                angle[Game.Z] = 0;
            }
        }
        
        
        //if map ends
        if(pos[Game.X] < 0) {
            pos[Game.X] = 0;
        } else if(pos[Game.X] > myTerrain.size().getHeight()-1.01) {
            pos[Game.X] = myTerrain.size().getHeight()-1.01;
        }
        if(pos[Game.Z] < 0) {
            pos[Game.Z] = 0;
        } else if(pos[Game.Z] > myTerrain.size().getWidth()-1.01) {
            pos[Game.Z] = myTerrain.size().getWidth()-1.01;
        }
    }
    
    private void render(GL2 gl) {
        
        // Load ModelView Matrix
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        // Set background
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        //TODO Set sky color to something good
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1); 
        

        // Setup Camera
        if(!Camera.getThirdPerson()) {
            gl.glRotated(90, 0, 1, 0);
            myCamera.setView(gl);
            myCamera.update();
        } else {
            myCamera.setView(gl);
            avatar.drawAvatar(gl);
            myCamera.update();
        }

        // Light Setting
        Lighting l = new Lighting();
        float[] sun = myTerrain.getSunlight();
        l.setProp(0.1f, 0.8f, 0.1f, 0.2f);
        l.setPos(sun[0], sun[1], sun[2]);
        l.setup(gl);
        
        //Draws the grass
        myTerrain.drawVBOTerrain(gl);
        
        // Draws the trees
        List<Tree> trees = myTerrain.trees();
        for(Tree tree : trees) {
            tree.drawTree(gl);
        }
        
        // Draws the roads
        List<Road> roads = myTerrain.roads();
        for(Road road : roads) {
            road.drawRoad(gl);
        }
        
        //TODO
        avatar = new Avatar(cHeight);
        //avatar.loadTexture(gl, true);
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        
        GL2 gl = drawable.getGL().getGL2();
        update(gl);
        render(gl);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        
        GL2 gl = drawable.getGL().getGL2();
        
        // Set up gl with depth test
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        // Set up gl with lighting
        gl.glEnable(GL2.GL_LIGHTING);
        
        // Normalize vectors1
        gl.glEnable(GL2.GL_NORMALIZE);
        
        // Cull back faces
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glCullFace(GL2.GL_BACK);
        
        // Turn on texturing
        gl.glEnable(GL2.GL_TEXTURE_2D);
        myTerrain.loadTerrain(gl, true);
       
        // Load Tree Texture
        List<Tree> trees = myTerrain.trees();
        for(Tree tree : trees) {
            tree.loadTexture(gl, true);
        }
        
        // Load Road Texture
        List<Road> roads = myTerrain.roads();
        for(Road road : roads) {
            road.loadTexture(gl, true);
        }
        
        // Setup terrain using VBO 
        myTerrain.setupVBO(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
   
        GLU glu = new GLU();
        glu.gluPerspective(Camera.getFov(), (float)width/(float)height, Camera.getZNear(), Camera.getZFar());
    }
    
    @Override
    public void dispose(GLAutoDrawable drawable) {}

    public static void main(String[] args) throws FileNotFoundException {
        Terrain terrain = LevelIO.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }
}