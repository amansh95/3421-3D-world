package ass2.spec;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class Camera {
    // Static Fields
    private static final double[] angle = {0,0,0};
    private static final double[] look = {0,0,0};
    private static final double[] orientation = {0,0,0};
    private static final double fieldOfView = 80;
    private static final double zNEAR = 0.1;
    private static final double zFAR = 300;

    // Camera Settings
    private static double[] cameraPos;
    private static double[] cameraAngle;
    private static double[] cameraLook;
    private static double[] cameraOrientation;
    private static double fov;
    private static double zNear;
    private static double zFar;

    // Input Settings
    private double viewDistance;
    private double height;
    
    // Mode Settings
    private static boolean thirdPerson;
    private static boolean collision;
    private static boolean gravity;
    private static Terrain myTerrain;
    
    // Constructor
    public Camera(Terrain myTerrain, double height, double viewDistance) {
        Camera.myTerrain = myTerrain;
        this.height = height;
        this.viewDistance = viewDistance;
        gravity = true;
        collision = true;
        cameraPos = new double[3]; //x,y,z
        cameraPos[Game.X] = myTerrain.size().width / 2;
        cameraPos[Game.Y] = 0;
        cameraPos[Game.Z] = myTerrain.size().height / 3;
        cameraAngle = angle;
        cameraLook = look;
        cameraOrientation = orientation;
        fov = fieldOfView;
        zNear = zNEAR;
        zFar = zFAR;
    }
    
    // All the getters
    public static double[] getPos() {
    	return cameraPos;
    }
    public static double[] getAngle() {
    	return cameraAngle;
    }
    public static double[] getLook() {
    	return cameraLook;
    }
    public static double[] getOrien() {
    	return cameraOrientation;
    }
    public static double getFov() {
    	return fov;
    }
    public static double getZNear() {
    	return zNear;
    }
    public static double getZFar() {
    	return zFar;
    }
    public static boolean getThirdPerson() {
    	return thirdPerson;
    }
    public static boolean getGravity() {
    	return gravity;
    }
    public static boolean getCollision() {
    	return collision;
    }
    
    // All the setters
    public static void setThirdPerson(boolean b) {
    	thirdPerson = b;
    }
    public static void setGravity(boolean b) {
    	gravity = b;
    }
    public static void setCollision(boolean b) {
    	collision = b;
    }
    public static void setCameraPos(double[] pos) {
    	cameraPos = pos;
    }
    
    public void setView(GL2 gl) {
        GLU glu = new GLU();
        
        if(thirdPerson) {
            glu.gluLookAt(
                    cameraPos[Game.X] - Math.cos(Math.toRadians(cameraAngle[Game.Y]))*Math.cos(Math.toRadians(cameraAngle[Game.Z]))*viewDistance,
                    cameraPos[Game.Y] - Math.sin(Math.toRadians(cameraAngle[Game.Z]))*viewDistance,
                    cameraPos[Game.Z] + Math.sin(Math.toRadians(cameraAngle[Game.Y]))*viewDistance,
                    
                    cameraPos[Game.X],
                    cameraPos[Game.Y],
                    cameraPos[Game.Z],
                    
                    0, 1, 0);
        } else {
            gl.glRotated(-cameraAngle[Game.Z], 0,0,1);
            gl.glRotated(-cameraAngle[Game.Y], 0,1,0);
            gl.glTranslated(-cameraPos[Game.X], -cameraPos[Game.Y], -cameraPos[Game.Z]);
        }
    }
    
    public void update() {
        if(collision) {
            double altitude = myTerrain.altitude(cameraPos[Game.X], cameraPos[Game.Z]);
            if(cameraPos[Game.Y] <= altitude) {
                cameraPos[Game.Y] = altitude+0.5;
            }
        }
        
        if(gravity) {
            cameraPos[Game.Y] = myTerrain.altitude(cameraPos[Game.X], cameraPos[Game.Z]) + height;
        }
    }    
}