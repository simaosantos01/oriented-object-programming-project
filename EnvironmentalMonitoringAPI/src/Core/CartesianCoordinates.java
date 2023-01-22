/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.interfaces.ICartesianCoordinates;

/**
 *
 * @author simao
 */
public class CartesianCoordinates implements ICartesianCoordinates {

    private final double x;
    private final double y;
    private final double z;

    /**
     * The CartesianCoordinates class constructor
     *
     * @param x The x cartesian coordinate
     * @param y The y cartesian coordinate
     * @param z The z cartesian coordinate
     */
    public CartesianCoordinates(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Getter for the x cartesian coordinate
     * 
     * @return The x cartesian coordinate
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * Getter for the y cartesian coordinate
     *
     * @return The y cartesian coordinate
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Getter for the z cartesian coordinate
     *
     * @return The z cartesian coordinate
     */
    @Override
    public double getZ() {
        return this.z;
    }

    /**
     * Returns the object in string format
     *
     * @return The object in string format
     */
    @Override
    public String toString() {
        return "CartesianCoordinates{" + "x=" + x + ", y=" + y + ", z="
                + z + '}';
    }

}
