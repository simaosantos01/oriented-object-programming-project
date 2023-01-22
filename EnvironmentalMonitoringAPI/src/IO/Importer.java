/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package IO;

import Core.CartesianCoordinates;
import Core.GeographicCoordinates;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author simao
 */


public class Importer implements IImporter {

    private ImporterStatistics importerStats = new ImporterStatistics();

    private CartesianCoordinates getCCFromJSONObject(JSONObject coordinates) {

        double x = Double.parseDouble((String) coordinates.get("x").toString());
        double y = Double.parseDouble((String) coordinates.get("y").toString());
        double z = Double.parseDouble((String) coordinates.get("z").toString());

        CartesianCoordinates icc = new CartesianCoordinates(x, y, z);

        return icc;
    }

    private GeographicCoordinates getGCFromJSONObject(JSONObject coordinates) {

        double lat
                = Double.parseDouble((String) coordinates.get("lat").toString());
        double lng
                = Double.parseDouble((String) coordinates.get("lng").toString());

        GeographicCoordinates igc = new GeographicCoordinates(lat, lng);

        return igc;
    }

    private LocalDateTime getDateTimeFromString(String dateString) {

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime date = LocalDateTime.parse(dateString,
                formatter);
        return date;
    }

    private boolean objectVerifier(String adress, String id,
            JSONObject coordinates, String value, String unit, String date) {

        if (adress != null && id != null && coordinates != null
                && coordinates.get("x") != null
                && coordinates.get("y") != null
                && coordinates.get("z") != null
                && coordinates.get("lat") != null
                && coordinates.get("lng") != null && value != null
                && unit != null && date != null) {

            return true;
        }
        return false;
    }

    private void addSensor(ICity icity, String address, String id,
            JSONObject coordinates) {

        try {
            if (icity.addSensor(address, id,
                    this.getCCFromJSONObject(coordinates),
                    this.getGCFromJSONObject(coordinates)) == true) {

                this.importerStats.incrementNumberOfNewSensorsRead();
            }
            this.importerStats.incrementNumberOfSensorsRead();

        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
            this.importerStats.addException(ex.getMessage());
        }
    }

    private void addMeasurement(ICity icity, String address, String id,
            double value, String unit, LocalDateTime date) {

        try {
            if (icity.addMeasurement(address, id, value, unit, date) == true) {
                this.importerStats.incrementNumberOfNewMeasurementsRead();
            }
            this.importerStats.incrementNumberOfReadMeasurements();

        } catch (CityException | StationException | SensorException
                | MeasurementException ex) {
            System.out.println(ex.getMessage());
            this.importerStats.addException(ex.getMessage());
        }
    }

    private void addStation(ICity icity, String address) {

        try {
            if (icity.addStation(address) == true) {
                this.importerStats.incrementNumberOfNewStationsRead();
            }
            this.importerStats.incrementNumberOfStationsRead();

        } catch (CityException ex) {
            System.out.println(ex.getMessage());
            this.importerStats.addException(ex.getMessage());
        }

    }

    /**
     * Reads the input JSON file
     *
     * @param icity The city in which the file data will be read
     * @param string The file path
     * @return The IO statistics for the data read
     * @throws FileNotFoundException If the file is not found
     * @throws IOException If the file cannot be read
     * @throws CityException If the city is null
     */
    @Override
    public IOStatistics importData(ICity icity, String string)
            throws FileNotFoundException, IOException, CityException {

        if (icity == null) {
            throw new CityException("The city is null");
        }

        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(string);

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);

                if (this.objectVerifier((String) data.get("address"),
                        (String) data.get("id"),
                        (JSONObject) data.get("coordinates"),
                        (String) data.get("value").toString(),
                        (String) data.get("unit"),
                        (String) data.get("date")) == true) {

                    //------------------GETTING DATA FROM JSON------------------
                    String address = (String) data.get("address");
                    String id = (String) data.get("id");
                    JSONObject coordinates = (JSONObject) data.get("coordinates"
                    );
                    double value
                            = Double.parseDouble((String) data.get("value"
                            ).toString());
                    String unit = (String) data.get("unit");
                    LocalDateTime date
                            = this.getDateTimeFromString((String) data.get("date"
                            ));

                    //------------------ADDING DATA------------------
                    this.addStation(icity, address);
                    this.addSensor(icity, address, id, coordinates);
                    this.addMeasurement(icity, address, id, value, unit, date);
                }
            }
            return this.importerStats;

        } catch (ParseException ex) {
            System.out.println("Failure during parse");
        }
        return null;
    }

}
