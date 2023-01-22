/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import java.time.LocalDateTime;

/**
 *
 * @author simao
 */
public class Sensor implements ISensor {

    private static final int ARRAY_SIZE = 2;
    private final String id;
    private final SensorType sensorType;
    private final Parameter parameter;
    private Measurement[] measurementsList;
    private int measurementCounter;
    private final CartesianCoordinates cartesianCoordniates;
    private final GeographicCoordinates geographicCoordinates;

    /**
     * The Sensor class constructor
     *
     * @param id The sensor ID
     * @param sensorType The sensor type
     * @param parameter The sensor parameter
     * @param cartesianCoordniates The sensor cartesian coordinates
     * @param geographicCoordinates The sensor geographic coordinates
     */
    public Sensor(String id, SensorType sensorType, Parameter parameter,
            CartesianCoordinates cartesianCoordniates,
            GeographicCoordinates geographicCoordinates) {

        this.id = id;
        this.sensorType = sensorType;
        this.parameter = parameter;
        this.cartesianCoordniates = cartesianCoordniates;
        this.geographicCoordinates = geographicCoordinates;
        this.measurementsList = new Measurement[ARRAY_SIZE];
        this.measurementCounter = 0;
    }

    /**
     * Getter for the sensor ID
     *
     * @return The sensor id
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Getter for sensor type
     *
     * @return The sensor type
     */
    @Override
    public SensorType getType() {
        return this.sensorType;
    }

    /**
     * Getter for the sensor parameter
     *
     * @return The sensor parameter
     */
    @Override
    public Parameter getParameter() {
        return this.parameter;
    }

    /**
     * Getter for the sensor cartesian coordinates
     *
     * @return The sensor cartesian coordinates
     */
    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return this.cartesianCoordniates;
    }

    /**
     * Getter for the number of measurement
     *
     * @return The number of measurement
     */
    @Override
    public int getNumMeasurements() {
        return this.measurementCounter;
    }

    /**
     * Return a copy of the existing measurements
     *
     * @return A copy of the existing measurements
     */
    @Override
    public IMeasurement[] getMeasurements() {
        return this.measurementsList;
    }

    /**
     * Getter for the sensor geographic coordinates
     *
     * @return The sensor geographic coordinates
     */
    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return this.geographicCoordinates;
    }

    private void expandArray() {

        Measurement[] temp = new Measurement[this.measurementsList.length];

        System.arraycopy(this.measurementsList, 0, temp, 0,
                this.measurementsList.length);

        this.measurementsList = new Measurement[this.measurementsList.length
                + this.measurementsList.length];

        System.arraycopy(temp, 0, this.measurementsList, 0, temp.length);
    }

    private boolean verifyUnit(Unit unit) {

        if (this.parameter.getUnit() == unit) {
            return true;
        }
        return false;
    }

    private boolean measurementExists(Measurement tmp) {

        for (int i = 0; i < this.measurementCounter; i++) {

            if (this.measurementsList[i].equals(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new measurement
     *
     * @param d The measurement value
     * @param ldt The measurement date
     * @param string The measurement unit
     *
     * @return true if the measurement was correctly added otherwise false
     *
     * @throws SensorException if the @param unit is not compatible with the
     * unit pre-defined for the sensor
     * @throws MeasurementException if the value is -99 or any parameter is
     * invalid
     */
    @Override
    public boolean addMeasurement(double d, LocalDateTime ldt, String string)
            throws SensorException, MeasurementException {

        if (string == null) {
            throw new MeasurementException("Unit is not valid");
        }

        if (ldt == null) {
            throw new MeasurementException("Date is not valid");
        }

        if (d == -99) {
            throw new MeasurementException("Value is not valid");
        }

        Unit unit = Unit.getUnitFromString(string);

        if (unit == null) {
            throw new SensorException("Unit is not compatible to the unit "
                    + "pre-defined for the sensor");
        }

        if (this.verifyUnit(unit) == false) {
            throw new SensorException("Unit is not compatible to the "
                    + "unit pre-defined for the sensor");
        }

        Measurement tmp = new Measurement(ldt, d, unit);

        if (this.measurementExists(tmp) == true) {
            return false;
        }

        if (this.measurementCounter == this.measurementsList.length) {
            this.expandArray();
        }

        this.measurementsList[this.measurementCounter] = tmp;
        this.measurementCounter++;

        return true;
    }

    private String measurementsToString() {

        String tmp = "";

        for (int i = 0; i < this.measurementCounter; i++) {
            tmp += this.measurementsList[i].toString();
        }
        return tmp;
    }

    /**
     * Returns the object in string format
     *
     * @return The object in string format
     */
    @Override
    public String toString() {
        return "id = " + this.id + "\n"
                + "measurementsCounter = " + this.measurementCounter + "\n"
                + "sensorType = " + this.sensorType + "\n"
                + "parameter = " + this.parameter + "\n"
                + "cartesianCoordniates = " + this.cartesianCoordniates + "\n"
                + "geographicCoordinates = " + this.geographicCoordinates + "\n"
                + "measurementList:\n\n" + this.measurementsToString();
    }

}
