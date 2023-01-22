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
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import java.time.LocalDateTime;

/**
 *
 * @author simao
 */
public class Station implements IStation {

    private static final int ARRAY_SIZE = 1;
    private final String name;
    private Sensor[] sensorsList;
    private int sensorCounter;

    /**
     * The Station class constructor
     *
     * @param name The station name
     */
    public Station(String name) {

        this.name = name;
        this.sensorsList = new Sensor[ARRAY_SIZE];
        this.sensorCounter = 0;
    }

    /**
     * Getter for the station name
     *
     * @return The station name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Return a copy of the existing sensors
     *
     * @return A copy of the existing sensors
     */
    @Override
    public ISensor[] getSensors() {
        return this.sensorsList;
    }

    /**
     * Return a copy of a existing sensor from a @param id
     *
     * @param string The sensor ID
     * @return A copy of a existing sensor
     */
    @Override
    public ISensor getSensor(String string) {

        int index = this.findSensor(string);

        if (index != -1) {
            return this.sensorsList[index];
        }
        return null;
    }

    /**
     * Return the number of sensors
     *
     * @return the number of sensors
     */
    public int getSensorCounter() {
        return sensorCounter;
    }

    private void expandArray() {

        Sensor[] temp = new Sensor[this.sensorsList.length];

        System.arraycopy(this.sensorsList, 0, temp, 0,
                this.sensorsList.length);

        this.sensorsList = new Sensor[this.sensorsList.length
                + this.sensorsList.length];

        System.arraycopy(temp, 0, this.sensorsList, 0, temp.length);
    }

    private boolean verifyIfParameterMatchesSensorType(Parameter parameter,
            SensorType sensorType) {

        Parameter[] tmp = sensorType.getParameters();

        for (int i = 0; i < tmp.length; i++) {

            if (tmp[i] == parameter) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyIfSensorTypeAndParameterMatchesId(String id,
            SensorType sensorType, Parameter parameter) {

        if ((id.indexOf("QA") == 0 && sensorType == SensorType.AIR
                || (id.indexOf("RU") == 0 && sensorType == SensorType.NOISE)
                || id.indexOf("ME") == 0 && sensorType == SensorType.WEATHER)
                && id.contains(parameter.toString()) == true) {

            return true;
        }
        return false;
    }

    private boolean verifyId(String id, Parameter parameter,
            SensorType sensorType) {

        if (id.length() != 10) {
            return false;
        }

        if (this.verifyIfSensorTypeAndParameterMatchesId(id, sensorType,
                parameter) == false
                || this.verifyIfParameterMatchesSensorType(parameter,
                        sensorType) == false) {

            return false;
        }
        return true;
    }

    private int findSensor(String id) {

        int index = -1;

        for (int i = 0; i < this.sensorCounter; i++) {

            if (this.sensorsList[i].getId().equals(id)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Adds a new sensor to the station
     *
     * @param is The sensor instance to be inserted
     * @return true if the sensor was inserted in the collection storing all
     * sensors otherwise false
     * @throws StationException if the @param sensor is null
     * @throws SensorException if the sensorId is invalid
     */
    @Override
    public boolean addSensor(ISensor is) throws StationException,
            SensorException {

        if (is == null) {
            throw new StationException("The param 'is' is null");
        }

        if (is.getParameter() == null || is.getType() == null) {
            throw new SensorException("ID doesn't match the requirements");
        }

        if (verifyId(is.getId(), is.getParameter(), is.getType()) == false) {
            throw new SensorException("ID doesn't match the requirements");
        }

        if (this.findSensor(is.getId()) != -1) {
            return false;
        }

        if (this.sensorCounter == this.sensorsList.length) {
            this.expandArray();
        }

        this.sensorsList[this.sensorCounter] = (Sensor) is;

        this.sensorCounter++;

        return true;
    }

    private boolean verifyUnit(Parameter parameter, Unit unit) {

        if (parameter.getUnit() == unit) {
            return true;
        }
        return false;
    }

    /**
     * Adds a new measurement to a sensor
     *
     * @param string The sensor ID
     * @param d The measurement value
     * @param ldt The measurement date
     * @param string1 The measurement unit
     * @return true if the measurement was inserted in the collection storing
     * all sensors otherwise false
     * @throws StationException If the sensor doesn't exists or any parameter is
     * null
     * @throws SensorException if the @param unit is not compatible with the
     * unit pre-defined for the sensor
     * @throws MeasurementException uncatched from measurement if the value is
     * -99
     */
    @Override
    public boolean addMeasurement(String string, double d, LocalDateTime ldt,
            String string1) throws StationException, SensorException,
            MeasurementException {

        if (string == null || ldt == null || string1 == null) {
            throw new StationException("Ate least one of the parameters is "
                    + "null");
        }

        int index = this.findSensor(string);

        if (index == -1) {
            throw new StationException("The id does not correspond to any of the"
                    + " sensors of the list");
        }

        Unit unit = Unit.getUnitFromString(string1);

        if (unit == null) {
            throw new SensorException("Unit is not compatible to the unit "
                    + "pre-defined for the sensor");
        }

        if (this.verifyUnit(this.sensorsList[index].getParameter(), unit)
                == false) {

            throw new SensorException("Unit is not compatible to the unit "
                    + "pre-defined for the sensor");
        }
        return this.sensorsList[index].addMeasurement(d, ldt, string1);
    }

    private String sensorsToString() {

        String tmp = "";

        for (int i = 0; i < this.sensorCounter; i++) {
            tmp += this.sensorsList[i].toString();
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
        return "Station " + this.name + "\n"
                + "sensorCounter = " + sensorCounter + "\n"
                + "sensorList:\n\n" + this.sensorsToString();
    }

}
