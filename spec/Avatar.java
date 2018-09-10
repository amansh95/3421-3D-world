package ass2.spec;

import com.jogamp.opengl.GL2;

public class Avatar {
	
	//TODO Fix Textures and Make a respectable Avatar
	//TODO Make an enemy 
	
	//String texture;
	//String texExtension;
	private double height;
	public Avatar(double height) {
		this.height=height;
	}
	
	/**
	public void loadTexture(GL2 gl, boolean mipmaps){
        if(myTexture == null) {
            myTexture = new MyTexture(gl, texture, textureExt, mipmaps);
        }
    }
    **/
	
	final static double[] backTex =  {4/16, 0/8, 3/16, 3/8};
    final static double[] insideTex ={3/16, 0/8, 2/16, 3/8};
    final static double[] outsideTex={1/16, 0/8, 0/16, 3/8};
    final static double[] topTex =   {2/16, 3/8, 1/16, 4/8};
    final static double[] topBottom ={2/16, 3/8, 3/16, 4/8};
    final static double[] frontTex = {2/16, 0/8, 1/16, 3/8};
	
	public void drawAvatar(GL2 gl) {
		
		double[] cPos =Camera.getPos();
		gl.glPushMatrix(); {
			gl.glTranslated(cPos[Game.X], cPos[Game.Y]-height, cPos[Game.Z]);
            gl.glRotated(-90, 0, 1, 0);
            gl.glScaled(0.15,0.15,0.15);
            //gl.glScaled(0.1,0.1,0.1);
            gl.glTranslated(-4, 0, 0);
            //setLight.avatarLight(gl);
            
            gl.glPushMatrix(); {
                // Rotate leg 10 deg around correct pivot
                gl.glTranslated(0, 10, 0);
                gl.glRotated(0, 0, 0, 1);
                gl.glTranslated(0, -10, 0);
                drawCube(gl, 5, 10, 2, 
                    backTex, frontTex, outsideTex,
                    insideTex, topTex, topBottom);
            }gl.glPopMatrix();
       } gl.glPopMatrix();
	}
	
	public static void drawCube(GL2 gl, double w, double h, double d, 
	        double[] texFront, double[] texBack, double[] texLeft,
	        double[] texRight, double[] texTop, double[] texBottom) {
	        
	        //double[] texCoords = {0,0,1,1};
	        
	        gl.glBegin(GL2.GL_QUADS); {
	            
	            // FRONT FACE
	            drawSquare(gl, w, h, texFront);
	            // LEFT FACE
	            gl.glPushMatrix(); {
	                gl.glTranslated(0, 0, -d);
	                gl.glRotated(-90, 0, 1, 0);
	                drawSquare(gl, d, h, texLeft);
	            } gl.glPopMatrix();
	            // BACK FACE
	            gl.glPushMatrix(); {
	                gl.glTranslated(w, 0, -d);
	                gl.glRotated(180, 0, 1, 0);
	                drawSquare(gl, w, h, texBack);
	            } gl.glPopMatrix();
	            // RIGHT FACE
	            gl.glPushMatrix(); {
	                gl.glTranslated(w, 0, 0);
	                gl.glRotated(90, 0, 1, 0);
	                drawSquare(gl, d, h, texRight);
	            } gl.glPopMatrix();
	            // BOTTOM FACE
	            gl.glPushMatrix(); {
	                gl.glTranslated(0, 0, -d);
	                gl.glRotated(90, 1, 0, 0);
	                drawSquare(gl, w, d, texBottom);
	            } gl.glPopMatrix();
	            // TOP FACE
	            gl.glPushMatrix(); {
	                gl.glTranslated(0, h, 0);
	                gl.glRotated(-90, 1, 0, 0);
	                drawSquare(gl, w, d, texTop);
	            } gl.glPopMatrix();
	        }gl.glEnd();
	        
	    }
	
public static void drawSquare(GL2 gl, double w, double h, double[] texCoords) {
        
        // Store normal and 4 points for face
        double n[];
        double p0[] = new double[3];
        double p1[] = new double[3];
        double p2[] = new double[3];
        double p3[] = new double[3];
        
        gl.glBegin(GL2.GL_QUADS); {
            p0[Game.X] = 0;
            p0[Game.Y] = 0;
            p0[Game.Z] = 0;
            
            p1[Game.X] = w;
            p1[Game.Y] = 0;
            p1[Game.Z] = 0;
            
            p2[Game.X] = w;
            p2[Game.Y] = h;
            p2[Game.Z] = 0;
            
            p3[Game.X] = 0;
            p3[Game.Y] = h;
            p3[Game.Z] = 0;
            
            n = MathUtil.normal(p0, p1, p2);
            gl.glNormal3dv(n, 0);
            gl.glTexCoord2d(texCoords[0], texCoords[1]);
            gl.glVertex3dv(p0, 0);
            gl.glTexCoord2d(texCoords[2], texCoords[1]);
            gl.glVertex3dv(p1, 0);
            gl.glTexCoord2d(texCoords[2], texCoords[3]);
            gl.glVertex3dv(p2, 0);
            gl.glTexCoord2d(texCoords[0], texCoords[3]);
            gl.glVertex3dv(p3, 0);
        }gl.glEnd();
    }
}


