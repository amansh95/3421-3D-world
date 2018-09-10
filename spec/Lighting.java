package ass2.spec;

import com.jogamp.opengl.GL2;

/**
 * Set up for the main light in the game
 * @author Aman
 *
 */
public class Lighting {
	
	// Ambient, Diffuse, Specular and Global ambient properties of light
    private float amb, diff, spec, glo;
    // Position of light
    private float x, y, z;
    private int localView = 0;

    public void setProp(float amb, float diff, float spec, float glo) {
        this.amb = amb;
        this.diff = diff;
        this.spec = spec;
        this.glo = glo;
    }
    
    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setLighting(GL2 gl) {
        
        gl.glEnable(GL2.GL_LIGHT0);
        
        float lightAmb[] = { amb, amb, amb, 1.0f };
        float lightDiff[] = { diff, diff, diff, 1.0f };
        float lightSpec[] = { spec, spec, spec, 1.0f };
        float gloAmb[] = { glo, glo, glo, 1.0f };

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmb,0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightDiff,0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightSpec,0);

        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, gloAmb,0); 
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, localView);
        positionLighting(gl);
    }
    
    private void positionLighting(GL2 gl){
        // Directional light
        float lightPos[] = {x, y, z, 0};

        //Transformations to move lights
        gl.glPushMatrix();{
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        }gl.glPopMatrix();
    }
 
    public void setup(GL2 gl) {
        setLighting(gl);
        positionLighting(gl);
    }
}
