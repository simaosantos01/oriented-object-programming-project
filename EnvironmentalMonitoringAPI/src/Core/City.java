/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.ICityStatistics;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.IStatistics;
import java.time.LocalDateTime;

/**
 *
 * @author simao
 */
public class City implements ICity, ICityStatistics {

    private static final int ARRAY_SIZE = 1;
    private final String Id;
    private final String name;
    private Station[] stationsList;
    private int stationCounter;

    /**
     * The City class contructor
     *
     * @param Id The city ID
     * @param Name The city name
     */
    public City(String Id, String Name) {

        this.Id = Id;
        this.name = Name;
        this.stationsList = new Station[ARRAY_SIZE];
        this.stationCounter = 0;
    }

    /**
     * Getter for id that identifies each City
     *
     * @return The city ID
     */
    @Override
    public String getId() {
        return this.Id;
    }

    /**
     * Getter for city name
     *
     * @return The city name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the city stations
     *
     * @return A copy of the existing stations
     */
    @Override
    public IStation[] getStations() {
        return this.stationsList;
    }

    /**
     * Getter for a station by the station name
     *
     * @param string The station name
     * @return A copy of a existing station
     */
    @Override
    public IStation getStation(String string) {

        int index = this.findStation(string);

        if (index != -1) {

            return this.stationsList[index];
        }
        return null;
    }

    /**
     * Getter for an station sensors by station name
     *
     * @param string The station name
     * @return A copy of the existing sensors
     */
    @Override
    public ISensor[] getSensorsByStation(String string) {

        Station tmp = (Station) this.getStation(string);

        if (tmp != null) {

            if (tmp.getSensorCounter() > 0) {
                return tmp.getSensors();
            }
            return null;
        }
        return null;
    }

    /**
     * Getter for an sensor measurements by sensor ID
     *
     * @param string The sensor ID
     * @return A copy of the existing measurements
     */
    @Override
    public IMeasurement[] getMeasurementsBySensor(String string) {

        for (int i = 0; i < this.stationCounter; i++) {
            Sensor[] sensorsTmp = (Sensor[]) this.stationsList[i].getSensors();

            if (sensorsTmp[0] != null) {
                for (int j = 0; j < sensorsTmp.length; j++) {

                    if (sensorsTmp[j].getId().equals(string)) {
                        return sensorsTmp[j].getMeasurements();
                    }
                }
            }
        }
        return null;
    }

    private void expandArray() {

        Station[] temp = new Station[this.stationsList.length];

        System.arraycopy(this.stationsList, 0, temp, 0,
                this.stationsList.length);

        this.stationsList = new Station[this.stationsList.length
                + this.stationsList.length];

        System.arraycopy(temp, 0, this.stationsList, 0, temp.length);
    }

    /**
     * Adds a new station to the city
     *
     * @param string The station name
     * @return true if the station was inserted in the collection storing all
     * stations otherwise false
     * @throws CityException if the @param string is null
     */
    @Override
    public boolean addStation(String string) throws CityException {

        if (string == null) {
            throw new CityException("The param string is null");
        }

        if (this.findStation(string) != -1) {
            return false;
        }

        if (this.stationCounter == this.stationsList.length) {
            this.expandArray();
        }

        this.stationsList[this.stationCounter] = new Station(string);
        this.stationCounter++;

        return true;
    }

    private int findStation(String name) {

        int index = -1;

        for (int i = 0; i < this.stationCounter; i++) {

            if (this.stationsList[i].getName().equals(name)) {
                index = i;
            }
        }
        return index;
    }

    private SensorType atributesSensorType(String id) {

        if (id.indexOf("QA") == 0) {

            return SensorType.AIR;
        }

        if (id.indexOf("RU") == 0) {

            return SensorType.NOISE;
        }

        if (id.indexOf("ME") == 0) {

            return SensorType.WEATHER;
        }
        return null;
    }

    private Parameter atributeParameter(String id) {

        for (Parameter parameter : Parameter.values()) {
            if (id.contains(parameter.toString())) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Adds a new sensor to a station
     *
     * @param string The station name
     * @param string1 The sensor ID
     * @param icc The cartesian coordinates
     * @param igc The geographic coordinates
     * @return True if the sensor was inserted in the collection storing all
     * sensors otherwise false
     * @throws CityException if the station doesn't exists or if @param
     * stationName parameter is null
     * @throws StationException uncatched from station if the sensor doesn't
     * exists or the sensorId is invalid
     * @throws SensorException uncatched from sensor if the sensorId is invalid
     */
    @Override
    public boolean addSensor(String string, String string1,
            ICartesianCoordinates icc, IGeographicCoordinates igc)
            throws CityException, StationException, SensorException {

        if (string == null) {
            throw new CityException("The param string is null");
        }

        int stationIndex = this.findStation(string);

        if (stationIndex == -1) {
            throw new CityException("The name does not correspond to any of the"
                    + " stations list");
        }

        CartesianCoordinates cc = (CartesianCoordinates) icc;
        GeographicCoordinates gc = (GeographicCoordinates) igc;

        Sensor tmp = new Sensor(string1, this.atributesSensorType(string1),
                this.atributeParameter(string1), cc, gc);

        return this.stationsList[stationIndex].addSensor(tmp);
    }

    /**
     * Adds a new measurement to a sensor from a station
     *
     * @param string The station name
     * @param string1 The sensor ID
     * @param d The measurement value
     * @param string2 The measurement unit
     * @param ldt The measurement date
     * @return true if the measurement was inserted in the collection storing
     * all sensors otherwise false
     * @throws CityException if the station doesn't exists or if @param
     * stationName parameter is null
     * @throws StationException uncatched from station if the sensor doesn't
     * exists
     * @throws SensorException uncatched from sensor if the unit is not
     * compatible with the unit pre-defined for the sensor
     * @throws MeasurementException uncatched from measurement if the value is
     * -99 or any parameter is invalid
     */
    @Override
    public boolean addMeasurement(String string, String string1, double d,
            String string2, LocalDateTime ldt) throws CityException,
            StationException, SensorException, MeasurementException {

        if (string == null) {
            throw new CityException("The param string is null");
        }

        int stationIndex = this.findStation(string);

        if (stationIndex == -1) {
            throw new CityException("The city name does not correspond to any"
                    + "of the stations list");
        }
        return this.stationsList[stationIndex].addMeasurement(string1, d, ldt,
                string2);
    }

    private String stationsToString() {

        String tmp = "";

        for (int i = 0; i < this.stationCounter; i++) {
            tmp += this.stationsList[i].toString();
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
        return "City " + this.name + "\n"
                + "id = " + this.Id + "\n"
                + "stationCounter =" + this.stationCounter + "\n"
                + "stationsList:\n\n" + this.stationsToString();
    }

    private boolean verifyDate(Measurement measurement, LocalDateTime ldt,
            LocalDateTime ldt1) {

        if (measurement.getTime().isAfter(ldt)
                && measurement.getTime().isBefore(ldt1)) {

            return true;
        }
        return false;
    }

    private Statistics[] calculateMeasurementAverageBySensor(Sensor[] sensors,
            int sensorsNum, Parameter prmtr, LocalDateTime ldt,
            LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[sensorsNum];

        for (int i = 0; i < sensorsNum; i++) {
            double average = 0;
            int counter = 0;

            if (sensors[i].getParameter() == prmtr) {
                Measurement[] measurements
                        = (Measurement[]) sensors[i].getMeasurements();

                for (int j = 0; j < sensors[i].getNumMeasurements(); j++) {
                    if (ldt == null && ldt1 == null) {
                        average += measurements[j].getValue();
                        counter++;

                    } else if (this.verifyDate(measurements[j], ldt, ldt1)
                            == true) {

                        average += measurements[j].getValue();
                        counter++;
                    }
                }
                if (average > 0) {
                    average = (average / counter);
                }
            }
            stats[i] = new Statistics(sensors[i].getId() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.AVG.toString(), average);
        }
        return stats;
    }

    private Statistics[] calculateNumOfMeasurementsBySensor(Sensor[] sensors,
            int sensorsNum, Parameter prmtr, LocalDateTime ldt,
            LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[sensorsNum];

        for (int i = 0; i < sensorsNum; i++) {
            int counter = 0;

            if (sensors[i].getParameter() == prmtr) {
                if (ldt != null && ldt1 != null) {
                    Measurement[] measurements
                            = (Measurement[]) sensors[i].getMeasurements();

                    for (int j = 0; j < sensors[i].getNumMeasurements(); j++) {
                        if (this.verifyDate(measurements[j], ldt, ldt1)
                                == true) {

                            counter++;
                        }
                    }
                } else {
                    counter = sensors[i].getNumMeasurements();
                }
            }
            stats[i] = new Statistics(sensors[i].getId() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.COUNT.toString(), counter);
        }
        return stats;
    }

    private double getsMax(double max, Measurement[] measurements,
            int MeasurementsNum, LocalDateTime ldt, LocalDateTime ldt1) {

        for (int i = 0; i < MeasurementsNum; i++) {
            if (max < measurements[i].getValue() && ldt == null
                    && ldt1 == null) {
                max = measurements[i].getValue();
            }
            if (max < measurements[i].getValue() && ldt != null && ldt1 != null
                    && this.verifyDate(measurements[i], ldt, ldt1) == true) {
                max = measurements[i].getValue();
            }
        }
        return max;
    }

    private Statistics[] calculateMaxMeasurementBySensor(Sensor[] sensors,
            int sensorsNum, Parameter prmtr, LocalDateTime ldt,
            LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[sensorsNum];

        for (int i = 0; i < sensorsNum; i++) {
            double max = 0;

            if (sensors[i].getParameter() == prmtr) {
                Measurement[] measurements
                        = (Measurement[]) sensors[i].getMeasurements();

                if (ldt != null && ldt1 != null) {
                    for (int j = 0; j < sensors[i].getNumMeasurements(); j++) {
                        if (this.verifyDate(measurements[j], ldt, ldt1)
                                == true) {

                            max = measurements[j].getValue();
                            break;
                        }
                    }
                    max = this.getsMax(max, measurements,
                            sensors[i].getNumMeasurements(), ldt, ldt1);

                } else if (sensors[i].getNumMeasurements() > 0) {

                    max = this.getsMax(measurements[0].getValue(), measurements,
                            sensors[i].getNumMeasurements(), ldt, ldt1);
                }
            }
            stats[i] = new Statistics(sensors[i].getId() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.MAX.toString(), max);
        }
        return stats;
    }

    private double getsMin(double min, Measurement[] measurements,
            int MeasurementsNum, LocalDateTime ldt, LocalDateTime ldt1) {

        for (int i = 0; i < MeasurementsNum; i++) {
            if (min > measurements[i].getValue() && ldt == null
                    && ldt1 == null) {
                min = measurements[i].getValue();
            }
            if (min > measurements[i].getValue() && ldt != null && ldt1 != null
                    && this.verifyDate(measurements[i], ldt, ldt1) == true) {
                min = measurements[i].getValue();
            }
        }
        return min;
    }

    private Statistics[] calculateMinMeasurementBySensor(Sensor[] sensors,
            int sensorsNum, Parameter prmtr, LocalDateTime ldt,
            LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[sensorsNum];

        for (int i = 0; i < sensorsNum; i++) {
            double min = 0;

            if (sensors[i].getParameter() == prmtr) {
                Measurement[] measurements
                        = (Measurement[]) sensors[i].getMeasurements();

                if (ldt != null && ldt1 != null) {
                    for (int j = 0; j < sensors[i].getNumMeasurements(); j++) {
                        if (this.verifyDate(measurements[j], ldt, ldt1)
                                == true) {

                            min = measurements[j].getValue();
                            break;
                        }
                    }
                    min = this.getsMin(min, measurements,
                            sensors[i].getNumMeasurements(), ldt, ldt1);

                } else if (sensors[i].getNumMeasurements() > 0) {

                    min = this.getsMin(measurements[0].getValue(), measurements,
                            sensors[i].getNumMeasurements(), ldt, ldt1);
                }
            }
            stats[i] = new Statistics(sensors[i].getId() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.MAX.toString(), min);
        }
        return stats;
    }

    private Statistics[] calculateMeasurementAverageByStation(Parameter prmtr,
            LocalDateTime ldt, LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[this.stationCounter];

        for (int i = 0; i < this.stationCounter; i++) {
            double average = 0;
            double counter = 0;
            Sensor[] sensors = (Sensor[]) this.stationsList[i].getSensors();

            Statistics[] sensorStats = this.calculateMeasurementAverageBySensor(
                    sensors, this.stationsList[i].getSensorCounter(),
                    prmtr, ldt, ldt1);

            for (int j = 0; j < sensorStats.length; j++) {
                average += sensorStats[j].getValue();

                if (sensorStats[j].getValue() > 0) {
                    counter++;
                }
            }

            if (average > 0) {
                average = (average / counter);
            }

            stats[i] = new Statistics(this.stationsList[i].getName() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.AVG.toString(), average);

        }
        return stats;
    }

    private Statistics[] calculateNumOfMeasurementsByStation(Parameter prmtr,
            LocalDateTime ldt, LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[this.stationCounter];

        for (int i = 0; i < this.stationCounter; i++) {
            double counter = 0;

            Sensor[] sensors = (Sensor[]) this.stationsList[i].getSensors();

            Statistics[] sensorStats = this.calculateNumOfMeasurementsBySensor(
                    sensors, this.stationsList[i].getSensorCounter(),
                    prmtr, ldt, ldt1);

            for (int j = 0; j < sensorStats.length; j++) {
                counter += sensorStats[j].getValue();
            }
            stats[i] = new Statistics(this.stationsList[i].getName() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.COUNT.toString(), counter);

        }
        return stats;
    }

    private Statistics[] calculateMaxMeasurementByStation(Parameter prmtr,
            LocalDateTime ldt, LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[this.stationCounter];

        for (int i = 0; i < this.stationCounter; i++) {
            double max = 0;

            Sensor[] sensors = (Sensor[]) this.stationsList[i].getSensors();

            Statistics[] sensorStats = this.calculateMaxMeasurementBySensor(
                    sensors, this.stationsList[i].getSensorCounter(), prmtr,
                    ldt, ldt1);

            if (sensorStats.length > 0) {
                max = sensorStats[0].getValue();

                for (int j = 0; j < sensorStats.length; j++) {
                    if (max < sensorStats[j].getValue()) {
                        max = sensorStats[j].getValue();
                    }
                }
            }
            stats[i] = new Statistics(this.stationsList[i].getName() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.MAX.toString(), max);
        }
        return stats;
    }

    private Statistics[] calculateMinMeasurementByStation(Parameter prmtr,
            LocalDateTime ldt, LocalDateTime ldt1) {

        Statistics[] stats = new Statistics[this.stationCounter];

        for (int i = 0; i < this.stationCounter; i++) {
            double min = 0;

            Sensor[] sensors = (Sensor[]) this.stationsList[i].getSensors();

            Statistics[] sensorStats = this.calculateMinMeasurementBySensor(
                    sensors, this.stationsList[i].getSensorCounter(),
                    prmtr, ldt, ldt1);

            if (sensorStats.length > 0) {
                for (int j = 0; j < sensorStats.length; j++) {
                    if (sensorStats[j].getValue() != 0) {
                        min = sensorStats[j].getValue();
                    }
                }

                for (int j = 0; j < sensorStats.length; j++) {
                    if (min > sensorStats[j].getValue()
                            && sensorStats[j].getValue() != 0) {
                        min = sensorStats[j].getValue();
                    }
                }
            }
            stats[i] = new Statistics(this.stationsList[i].getName() + " ("
                    + prmtr.toString() + ") "
                    + AggregationOperator.MIN.toString(), min);
        }
        return stats;
    }

    /**
     * Returns a collection of statistics based on the measurements of a station 
     * and a date interval
     * 
     * @param ao The operator applied to the query
     * @param prmtr The parameter applied to the query
     * @param ldt The time interval start
     * @param ldt1 The time interval end
     * @return The collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao,
            Parameter prmtr, LocalDateTime ldt, LocalDateTime ldt1) {

        switch (ao) {
            case AVG:
                return this.calculateMeasurementAverageByStation(prmtr, ldt,
                        ldt1);
            case COUNT:
                return this.calculateNumOfMeasurementsByStation(prmtr, ldt,
                        ldt1);
            case MAX:
                return this.calculateMaxMeasurementByStation(prmtr, ldt, ldt1);
            case MIN:
                return this.calculateMinMeasurementByStation(prmtr, ldt, ldt1);
        }
        return null;
    }

    /**
     * Returns a collection of statistics based on the measurements of a station
     * 
     * @param ao The operator applied to the query
     * @param prmtr The parameter applied to the query
     * @return The collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao,
            Parameter prmtr) {

        switch (ao) {
            case AVG:
                return this.calculateMeasurementAverageByStation(prmtr, null,
                        null);
            case COUNT:
                return this.calculateNumOfMeasurementsByStation(prmtr, null,
                        null);
            case MAX:
                return this.calculateMaxMeasurementByStation(prmtr, null, null);
            case MIN:
                return this.calculateMinMeasurementByStation(prmtr, null, null);
        }
        return null;
    }

    /**
     * Returns a collection of statistics based on the measurements of a sensor 
     * and a date interval
     * 
     * @param string The selected station
     * @param ao The operator applied to the query
     * @param prmtr The parameter applied to the query
     * @param ldt The time interval start
     * @param ldt1 The time interval end
     * @return The collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string,
            AggregationOperator ao, Parameter prmtr,
            LocalDateTime ldt, LocalDateTime ldt1) {

        int stationIndex = this.findStation(string);

        if (stationIndex == -1) {
            return null;
        }

        int sensorsNum = this.stationsList[stationIndex].getSensorCounter();

        if (sensorsNum > 0) {
            Sensor[] sensors
                    = (Sensor[]) this.stationsList[stationIndex].getSensors();

            switch (ao) {
                case AVG:
                    return this.calculateMeasurementAverageBySensor(sensors,
                            sensorsNum, prmtr, ldt, ldt1);
                case COUNT:
                    return this.calculateNumOfMeasurementsBySensor(sensors,
                            sensorsNum, prmtr, ldt, ldt1);
                case MAX:
                    return this.calculateMaxMeasurementBySensor(sensors,
                            sensorsNum, prmtr, ldt, ldt1);
                case MIN:
                    return this.calculateMinMeasurementBySensor(sensors,
                            sensorsNum, prmtr, ldt, ldt1);
            }
        }
        return null;
    }

    /**
     * Returns a collection of statistics based on the measurements of a sensor
     * 
     * @param string The selected station
     * @param ao The operator applied to the query
     * @param prmtr The parameter applied to the query
     * @return The collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string,
            AggregationOperator ao, Parameter prmtr) {

        int stationIndex = this.findStation(string);

        if (stationIndex == -1) {
            return null;
        }

        int sensorsNum = this.stationsList[stationIndex].getSensorCounter();

        if (sensorsNum > 0) {

            Sensor[] sensors
                    = (Sensor[]) this.stationsList[stationIndex].getSensors();

            switch (ao) {
                case AVG:
                    return this.calculateMeasurementAverageBySensor(sensors,
                            sensorsNum, prmtr, null, null);
                case COUNT:
                    return this.calculateNumOfMeasurementsBySensor(sensors,
                            sensorsNum, prmtr, null, null);
                case MAX:
                    return this.calculateMaxMeasurementBySensor(sensors,
                            sensorsNum, prmtr, null, null);
                case MIN:
                    return this.calculateMinMeasurementBySensor(sensors,
                            sensorsNum, prmtr, null, null);
            }
        }
        return null;
    }

}
