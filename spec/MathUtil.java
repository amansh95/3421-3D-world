package ass2.spec;


/**
 * A collection of useful math methods 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class MathUtil {
	
	//array indexes
    private final static int X = 0, Y = 1, Z = 2;
    // Define axes used in interpolation
    final static int X_AXIS = 0, Y_AXIS = 1, Z_AXIS = 2;

    /**
     * Normalise an angle to the range [-180, 180)
     * 
     * @param angle 
     * @return
     */
    static public double normaliseAngle(double angle) {
        return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
    }

    /**
     * Clamp a value to the given range
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Multiply two matrices
     * 
     * @param p A 3x3 matrix
     * @param q A 3x3 matrix
     * @return
     */
    public static double[][] multiply(double[][] p, double[][] q) {

        double[][] m = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                   m[i][j] += p[i][k] * q[k][j]; 
                }
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a matrix
     * 
     * @param m A 4x4 matrix
     * @param v A 4x1 vector
     * @return
     */
    public static double[] multiply(double[][] m, double[] v) {

        double[] u = new double[4];

        for (int i = 0; i < 4; i++) {
            u[i] = 0;
            for (int j = 0; j < 4; j++) {
                u[i] += m[i][j] * v[j];
            }
        }

        return u;
    }



    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: A 3D translation matrix for the given offset vector
     * 
     * @param pos
     * @return
     */
    public static double[][] translationMatrix(double[] v) {

    	double[][] matrix = new double[4][4];
        matrix[0] = new double[] {1, 0, 0, v[0]};
        matrix[1] = new double[] {0, 1,0, v[1]};
        matrix[2] = new double[] {0, 0, 1,v[3]};
        matrix[3] = new double[] {0, 0, 0, 1};
        return matrix;
    }

    /**
     * TODO: A 3D rotation matrix for the given angle along x axis
     * 
     * @param angle in degrees
     * @return
     */
    public static double[][] rotationMatrixX(double angle) {
    	double sin = Math.sin(angle * Math.PI / 180);
        double cos = Math.cos(angle * Math.PI / 180);

        double[][] matrix = new double[4][4];
        matrix[0] = new double[] {1, 0, 0, 0};
        matrix[1] = new double[] {0, cos, -sin, 0};
        matrix[2] = new double[] {0, sin, cos, 0};
        matrix[3] = new double[] { 0, 0, 0, 1 };
        return matrix;
    }
    
    /**
     * TODO: A 3D rotation matrix for the given angle along y axis
     * 
     * @param angle in degrees
     * @return
     */
    public static double[][] rotationMatrixY(double angle) {
    	double sin = Math.sin(angle * Math.PI / 180);
        double cos = Math.cos(angle * Math.PI / 180);

        double[][] matrix = new double[4][4];
        matrix[0] = new double[] {cos, 0, sin, 0};
        matrix[1] = new double[] {0, 1, 0, 0};
        matrix[2] = new double[] {-sin, 0, cos, 0};
        matrix[3] = new double[] { 0, 0, 0, 1 };
        return matrix;
    }
    
    /**
     * TODO: A 3D rotation matrix for the given angle along z axis
     * 
     * @param angle in degrees
     * @return
     */
    public static double[][] rotationMatrixZ(double angle) {
    	double sin = Math.sin(angle * Math.PI / 180);
        double cos = Math.cos(angle * Math.PI / 180);

        double[][] matrix = new double[4][4];
        matrix[0] = new double[] {cos,-sin, 0, 0};
        matrix[1] = new double[] {sin, cos, 0, 0};
        matrix[2] = new double[] {0, 0, 1, 0};
        matrix[3] = new double[] { 0, 0, 0, 1 };
        return matrix;
    }
    
    /**
     * TODO: A 3D scale matrix that scales all three axes by the same factor
     * 
     * @param scale
     * @return
     */
    public static double[][] scaleMatrix(double scale) {

    	   
    	double[][] matrix = new double[4][4];
        matrix[0] = new double[] {scale, 0, 0, 0};
        matrix[1] = new double[] {0, scale, 0, 0};
        matrix[2] = new double[] {0, 0, scale, 0};
        matrix[3] = new double[] {0,0,0,1};
        return matrix;
    }    
    /**
     * Subtract 2 vectors a and b such that r = (b - a)
     * @param a vector
     * @param b vector
     * @return r vector
     */
    public static double[] vectorMinus(double[] a, double[] b) {
        double[] r = new double[a.length];
        for(int i = 0; i < a.length; i++) {
            r[i] = b[i] - a[i];
        }
        return r;
    }
    public static float[] vectorMinus(float[] a, float[] b) {
        float[] r = new float[a.length];
        for(int i = 0; i < a.length; i++) {
            r[i] = b[i] - a[i];
        }
        return r;
    }
    
    /**
     * Calculate the normal vector given 3 points (counter-clockwise)
     * @param p0 point
     * @param p1 point
     * @param p2 point
     * @return n normal vector
     */
    public static double[] normal(double[] p0, double[] p1, double[] p2) {
        // Vectors
        double[] u = vectorMinus(p0, p1);
        double[] v = vectorMinus(p0, p2);
        // Normal calculation
        double[] n = {
            u[Y]*v[Z] - u[Z]*v[Y],
            u[Z]*v[X] - u[X]*v[Z],
            u[X]*v[Y] - u[Y]*v[X]
        };
        return n;
    }
    public static float[] normal(float[] p0, float[] p1, float[] p2) {
        // Vectors
        float[] u = vectorMinus(p0, p1);
        float[] v = vectorMinus(p0, p2);
        // Normal calculation
        float[] n = {
            u[Y]*v[Z] - u[Z]*v[Y],
            u[Z]*v[X] - u[X]*v[Z],
            u[X]*v[Y] - u[Y]*v[X]
        };
        return n;
    }
    /**
     * Linear Interpolation formula
     * 
     * @param target point we are interpolating (between p0 - p1)
     * @param axis which axis are we interpolating for i.e. X or Z interpolation
     * @param targetAxis which axis are we deriving our answer from, i.e. depth on Y-axis
     * @param p0 first point
     * @param p1 second point
     * @return result 
     */
    public static double linearInterpolate(double[] target, int axis, int targetAxis, double[] p0, double[] p1) {
        return (target[axis] - p0[axis]) / (p1[axis] - p0[axis]) * p1[targetAxis] +
               (p1[axis] - target[axis]) / (p1[axis] - p0[axis]) * p0[targetAxis];
    }
    /**
     * Bilinear Interpolation formula to find altitude (y-axis) of target given 3 points 
     * 
     * @param target point
     * @param p0 point
     * @param p1 point
     * @param p2 point 
     * @return altitude
     */
    public static double bilinearInterpolate(double[] target, double[] p0, double[] p1, double[] p2) {

        double depthAy = MathUtil.linearInterpolate(target, MathUtil.Z_AXIS, MathUtil.Y_AXIS, p0, p1);
        double depthBy = MathUtil.linearInterpolate(target, MathUtil.Z_AXIS, MathUtil.Y_AXIS, p1, p2);
        double depthAx = MathUtil.linearInterpolate(target, MathUtil.Z_AXIS, MathUtil.X_AXIS, p0, p1);
        double depthBx = MathUtil.linearInterpolate(target, MathUtil.Z_AXIS, MathUtil.X_AXIS, p1, p2);
        // Now we have the 2 points, we can linear interpolate A and B
        double[] A = {depthAx, depthAy, target[2]};
        double[] B = {depthBx, depthBy, target[2]};
        return MathUtil.linearInterpolate(target, MathUtil.X_AXIS, MathUtil.Y_AXIS, A, B);
        
    }
    
}
