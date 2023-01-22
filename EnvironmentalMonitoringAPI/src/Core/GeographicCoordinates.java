/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.interfaces.IGeographicCoordinates;

/**
 *
 * @author simao
 */
public class GeographicCoordinates implements IGeographicCoordinates {

    private final double latitude;
    private final double longitude;

    /**
     * The GeographicCoordinates class constructor
     *
     * @param latitude The longitude geographic coordinate
     * @param longitude The latitude geographic coordinate
     */
    public GeographicCoordinates(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter for the latitude geographic coordinate
     *
     * @return The latitude geographic coordinate
     */
    @Override
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Getter for the longitude geographic coordinate
     *
     * @return The longitude geographic coordinate
     */
    @Override
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Returns the object in string format
     *
     * @return The object in string format
     */
    @Override
    public String toString() {
        return "GeographicCoordinates{" + "latitude=" + latitude + ", "
                + "longitude=" + longitude + '}';
    }

}
