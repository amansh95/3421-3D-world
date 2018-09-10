package ass2.spec;
/**
 * COMMENT: Comment Tree 
 *
 * @author malcolmr
 */
import com.jogamp.opengl.GL2;
/**
 * Draw a tree
 * @author Aman
 *
 */
public class Tree {

    private double[] myPos;
    private double treeHeight=6.6 ;
    
    //Tree
    private MyTexture myTree=null;
    private String treeTex = "img/tree.jpg";
    private String treeTexExt = "jpg";
    private static final int treeSlices=128;
    private double Xangle = -90;
    private double Yangle = 0;
    private double Zangle = 0;
    
    //Leaf
    private MyTexture myLeaves=null;
    private String leafTex = "img/leaf.jpg";
    private String leafTexExt = "jpg";
    private static final double leafRadius=2;
    private static final int leafStacks=24;
    private static final int leafSlices=24;
    
    
    
    
    public Tree(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public double[] getPosition() {
        return myPos;
    }
    
    //in lecture code
    public void normalize(double v[])
    {
    	double d = Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);
    	if ( d!=0) {
    		v[0]=v[0]/d;
    		v[2]=v[2]/d;
    		v[1]=v[1]/d;
    	}
    }
    
    double getY(double r) {
    	double y = Math.sin(2*Math.PI*r);
    	return y; 	
    }
    
    double r(double t) {
    	double x = Math.cos(2*Math.PI*t);;
    	return x;
    }
    
    public void loadTexture(GL2 gl, boolean mipmaps) {
    	if(myTree == null) {
    		myTree= new MyTexture(gl, treeTex, treeTexExt, mipmaps);
    	}
    	if(myLeaves == null) {
    		myTree= new MyTexture(gl, leafTex, leafTexExt, mipmaps);
    	}
    }
    
    public void drawTree(GL2 gl) {
    	double[] t = getPosition();
    	/*
    	 * Drawing the tree top
    	 * Taken from lecture slides week 8
    	 * Draw a tree top by rotating around Y-Axis
    	 * 
    	 */
    	setLight.treeTopLight(gl);
    	gl.glPushMatrix();
    	{
    		gl.glTranslated(t[0], t[1]+treeHeight, t[2]);
            double deltaT = 0.5/leafStacks;
            int ang;  
            int delang = 360/leafSlices;
            double x1,x2,z1,z2,y1,y2;
            for (int i = 0; i < leafStacks; i++) { 
                double td = -0.25 + i*deltaT;
                
                gl.glBegin(GL2.GL_TRIANGLE_STRIP); 
                
                for(int j = 0; j <= leafSlices; j++) {  
                    ang = j*delang;
                    x1 = leafRadius * r(td)*Math.cos((double)ang*2.0*Math.PI/360.0); 
                    x2 = leafRadius * r(td+deltaT)*Math.cos((double)ang*2.0*Math.PI/360.0); 
                    y1 = leafRadius * getY(td);
    
                    z1 = leafRadius * r(td)*Math.sin((double)ang*2.0*Math.PI/360.0);  
                    z2 = leafRadius * r(td+deltaT)*Math.sin((double)ang*2.0*Math.PI/360.0);  
                    y2 = leafRadius * getY(td+deltaT);
    
                    double normal[] = {x1,y1,z1};
                    normalize(normal);    
    
                    gl.glNormal3dv(normal,0);  
                    double tCoord = 1.0/leafStacks * i; 
                    double sCoord = 1.0/leafSlices * j;
                    gl.glTexCoord2d(sCoord,tCoord);
                    gl.glVertex3d(x1,y1,z1);
                    normal[0] = x2;
                    normal[1] = y2;
                    normal[2] = z2;
    
                    normalize(normal);    
                    gl.glNormal3dv(normal,0); 
                    tCoord = 1.0/leafStacks * (2);
                    gl.glTexCoord2d(sCoord,tCoord);
                    gl.glVertex3d(x2,y2,z2); 
    
                }; 
                gl.glEnd();  
            }
        }gl.glPopMatrix();
    	
        
        /**
         * Drawing the Tree Trunk
         * Code from week 8
         */
        
        double angleIncrement = (Math.PI * 2.0) / treeSlices;
        double top = t[1]+treeHeight;
        double bottom = t[1];
        double thickness = 0.3;
        
        setLight.treeTrunkLight(gl);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, myTree.getTextureId());

        gl.glPushMatrix();
        {
            gl.glTranslated(t[0], 0, t[2]);
            gl.glRotated(Zangle, 0.0, 0.0, 1.0);
            gl.glRotated(Yangle, 0.0, 1.0, 0.0);
            gl.glRotated(Xangle, 1.0, 0.0, 0.0);
           //Upper Half
            gl.glBegin(GL2.GL_POLYGON);{
            	for(int i = 0; i <treeSlices; i++) {
            		double angle = i*angleIncrement;
            		gl.glNormal3d(0.0, 0.0, 1);
            		gl.glVertex3d(Math.cos(angle)*thickness, Math.sin(angle)*thickness, top);
            	}
            }
            gl.glEnd();
          
            gl.glBegin(GL2.GL_QUAD_STRIP);{
                for(int i=0; i<= treeSlices; i++){
                    double angle = i*angleIncrement;
                    double xPos = Math.cos(angle)*thickness;
                    double yPos = Math.sin(angle)*thickness;
                    double sCoord = 1.0/treeSlices * i;
                    
                    gl.glNormal3d(xPos, yPos, 0);
                    gl.glTexCoord2d(sCoord,1);
                    gl.glVertex3d(xPos,yPos,top);
                    gl.glTexCoord2d(sCoord,0);
                    gl.glVertex3d(xPos,yPos,bottom);
                    
                }
            }gl.glEnd();
            
            //Lower Half
            gl.glBegin(GL2.GL_POLYGON);{
               
                for(int i = 0; i < treeSlices; i++)
                {
                    double angle = -i*angleIncrement;
                    gl.glNormal3d(0.0, 0.0, -1);
                    gl.glVertex3d(Math.cos(angle)*thickness, Math.sin(angle)*thickness,bottom);
                }
            }gl.glEnd();
        }gl.glPopMatrix();
        
    }
}
   
   
