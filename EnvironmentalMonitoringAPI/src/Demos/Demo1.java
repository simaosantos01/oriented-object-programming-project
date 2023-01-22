/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Demos;

import Core.CartesianCoordinates;
import Core.GeographicCoordinates;
import Core.City;
import Core.Sensor;
import Core.Station;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import java.time.LocalDateTime;


/**
 *
 * @author simao
 */
public class Demo1 {

    /**
     * Testing the core
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SENSOR&MEASUREMENT----------------------------------------------------
        CartesianCoordinates ca = new CartesianCoordinates(5, -15, 20);
        GeographicCoordinates ga = new GeographicCoordinates(21, 56);

        Sensor sa = new Sensor("RU00LAEQ01", SensorType.NOISE, Parameter.LAEQ,
                ca, ga);
        Sensor sb = new Sensor("RU0LAEQ001", SensorType.NOISE, Parameter.LAEQ,
                ca, ga);

        LocalDateTime tmp = LocalDateTime.now();

        try {
            sa.addMeasurement(40, tmp, "dB(A)");
        } catch (SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        //STATION---------------------------------------------------------------
        Station station = new Station("Avenida da Républica");
        Station stationB = new Station("Avenida da Républica");

        try {
            station.addSensor(sa);
        } catch (StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            station.addSensor(sb);
        } catch (StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        //STATION ADDING MEASUREMENT--------------------------------------------
        try {
            station.addMeasurement("RU00LAEQ01", 55, tmp, "dB(A)");
        } catch (StationException | SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        //CITY------------------------------------------------------------------
        City cityA = new City("aa-11", "Santo Tirso");

        //ADD STATIONS
        try {
            cityA.addStation("Avenida da Républica");
        } catch (CityException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            cityA.addStation("Calçada da Ajuda");
        } catch (CityException ex) {
            System.out.println(ex.getMessage());
        }

        //ADD SENSORS
        try {
            cityA.addSensor("Calçada da Ajuda", "RU00LAEQ01", ca, ga);
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            cityA.addSensor("Calçada da Ajuda", "RU00LAEQ02", ca, ga);
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        //ADD MEASUREMENTS
        try {
            cityA.addMeasurement("Calçada da Ajuda", "RU00LAEQ02", 20,
                    "dB(A)", tmp);
        } catch (CityException | StationException | SensorException
                | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            cityA.addMeasurement("Calçada da Ajuda", "RU00LAEQ01", 20,
                    "dB(A)", tmp);
        } catch (CityException | StationException | SensorException
                | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(cityA.toString());   
    }

}
