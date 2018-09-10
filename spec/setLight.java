package ass2.spec;

import com.jogamp.opengl.GL2;
/**
 * MAnualy Set light properties for the objects designed in the world
 * @author Aman
 *
 */
public class setLight {
	private static float lightAmbAndDif[] = null;
    private static float lightSpec[] = null;
    private static float lightShine[] = null;
    private static float col[] = null;
    
    public static void roadLightProp(GL2 gl) {
        lightAmbAndDif = new float[]{1f, 1f, 1f, 1.0f};
        lightSpec = new float[]{1f, 1f, 1f, 1.0f};
        lightShine = new float[]{100.0f};
        col = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        applyLight(gl);
    }
    
    public static void terrainLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.00f, 0.60f, 0.00f, 1.0f};
        lightSpec = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        lightShine = new float[]{0.0f};
        col = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        applyLight(gl);
    }
    
    public static void treeTopLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.50f, 1.00f, 0.50f, 1.0f};
        lightSpec = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        lightShine = new float[]{0.0f};
        col = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        applyLight(gl);
    }
    
    public static void treeTrunkLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.50f, 0.33f, 0.22f, 1.0f};
        lightSpec = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        lightShine = new float[]{0.0f};
        col = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        applyLight(gl);
    }
    
    /**
    public static void avatarLight(GL2 gl) {
        lightAmbAndDif = new float[]{1.00f, 1.00f, 1.00f, 1.0f};
        lightSpec = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        lightShine = new float[]{0.0f};
        col = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        applyLight(gl);
    }
    **/
    
    public static void redLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.00f, 0.00f, 0.00f, 1.0f};
        lightSpec = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        lightShine = new float[]{50.0f};
        col = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
        applyLight(gl);
    }
    
    public static void greenLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.00f, 0.00f, 0.00f, 1.0f};
        lightSpec = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        lightShine = new float[]{50.0f};
        col = new float[]{0.0f, 0.0f, 1.0f, 1.0f};
        applyLight(gl);
    }
    
    public static void blueLight(GL2 gl) {
        lightAmbAndDif = new float[]{0.00f, 0.00f, 0.00f, 1.0f};
        lightSpec = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        lightShine = new float[]{50.0f};
        col = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        applyLight(gl);
    }
    
    
    
    public static void applyLight(GL2 gl) {
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, lightAmbAndDif,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, lightSpec,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, lightShine,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, col,0);
    }
   

}
