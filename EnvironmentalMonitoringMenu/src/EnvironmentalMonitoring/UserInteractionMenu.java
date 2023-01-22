/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package EnvironmentalMonitoring;

import Core.City;
import Core.Statistics;
import IO.ExporterBarChart;
import IO.Importer;
import IO.ImporterStatistics;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.dashboards.Dashboard;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author simao
 */
public class UserInteractionMenu {

    private static String readFilePath() {

        Scanner sc = new Scanner(System.in);

        String filePath = "";

        do {
            System.out.println("Insert the file path to read: ");
            filePath = sc.nextLine();

        } while (filePath.equals(""));
        return filePath;
    }

    private static City readCityName() {

        Scanner sc = new Scanner(System.in);

        String cityName = "";
        String cityId = "";

        do {
            System.out.println("Insert the city name: ");
            cityName = sc.nextLine();

        } while (cityName.equals(""));

        do {
            System.out.println("Insert the city ID: ");
            cityId = sc.nextLine();

        } while (cityId.equals(""));

        City city = new City(cityId, cityName);

        return city;
    }

    private static String[] expandArray(String[] json) {

        String[] temp = new String[json.length];

        System.arraycopy(json, 0, temp, 0, json.length);

        json = new String[json.length + 1];

        System.arraycopy(temp, 0, json, 0, temp.length);

        return json;
    }

    private static void menu(City city) {

        Scanner sc = new Scanner(System.in);
        String choice;
        String[] json = new String[1];
        int jsonIndex = 0;
        int keeps;

        do {
            keeps = 1;
            System.out.println("-------------MENU-------------");
            System.out.println(" 1- Visualizations by Stations");
            System.out.println(" 2- Visualizations by Sensor");
            System.out.println(" 3- Exit");
            System.out.println("Insert your choice value: ");

            choice = sc.nextLine();
            int visualizationBy = Integer.parseInt(choice);

            if (choice.equals("3")) {
                keeps = 0;
            } else if (choice.equals("2") || choice.equals("1")) {
                
                if (displayChart(city, visualizationBy, json, jsonIndex)
                        == true) {
                    jsonIndex++;
                    json = expandArray(json);
                } else {
                    System.out.println("The station does not exists");
                }
            }
        } while (keeps != 0);
    }

    private static LocalDateTime[] chooseBeteweenTimes() {

        LocalDateTime[] time = new LocalDateTime[2];
        LocalDateTime date;
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        Scanner sc = new Scanner(System.in);
        String choice;
        boolean bError;

        do {
            System.out.println("Display by time frames? (Y/N)");
            choice = sc.nextLine();
        } while (!(choice.equals("Y") || (choice.equals("N"))));

        if (choice.equals("N")) {
            time[0] = null;
            time[1] = null;

            return time;
        }

        do {
            bError = true;
            System.out.println("Insert startDate (yyyyMMddHHmm): ");
            choice = sc.nextLine();

            try {
                date = LocalDateTime.parse(choice, formatter);
                time[0] = date;
            } catch (java.time.format.DateTimeParseException ex) {
                System.out.println("Invalid date");
                bError = false;
            }
        } while (bError == false);

        do {
            bError = true;
            System.out.println("Insert endDate (yyyyMMddHHmm): ");
            choice = sc.nextLine();

            try {
                date = LocalDateTime.parse(choice, formatter);
                time[1] = date;
            } catch (java.time.format.DateTimeParseException ex) {
                System.out.println("Invalid date");
                bError = false;
            }
        } while (bError == false);

        return time;
    }

    private static int chooseParameter() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("------------PARAM-------------");
            System.out.println(" 1- NO2");
            System.out.println(" 2- O3");
            System.out.println(" 3- PM2.5");
            System.out.println(" 4- PM10");
            System.out.println(" 5- SO2");
            System.out.println(" 6- C6H6");
            System.out.println(" 7- CO");
            System.out.println(" 8- LAEQ");
            System.out.println(" 9- PA");
            System.out.println(" 10- TEMP");
            System.out.println(" 11- RU");
            System.out.println(" 12- VD");
            System.out.println(" 13- VI");
            System.out.println(" 14- HM");
            System.out.println(" 15- PC");
            System.out.println(" 16- RG");
            System.out.println("Insert your choice value: ");

            choice = sc.nextLine();
        } while (!(choice.equals("1") || choice.equals("2")
                || choice.equals("3") || choice.equals("4")
                || choice.equals("5") || choice.equals("6")
                || choice.equals("7") || choice.equals("8")
                || choice.equals("9") || choice.equals("10")
                || choice.equals("11") || choice.equals("12")
                || choice.equals("13") || choice.equals("14")
                || choice.equals("15") || choice.equals("16")));
        return Integer.parseInt(choice);
    }

    private static int chooseOperator() {

        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("-----------OPERATOR-----------");
            System.out.println(" 1- Average");
            System.out.println(" 2- Counter");
            System.out.println(" 3- Max value");
            System.out.println(" 4- Min value");
            System.out.println("Insert your choice value: ");

            choice = sc.nextLine();
        } while (!(choice.equals("1") || choice.equals("2")
                || choice.equals("3") || choice.equals("4")));
        return Integer.parseInt(choice);
    }

    private static String readStationName() {

        Scanner sc = new Scanner(System.in);
        String stationName;

        do {
            System.out.println("Insert the station name: ");
            stationName = sc.nextLine();
        } while (stationName.equals(""));
        return stationName;
    }

    private static Parameter getParam(int chooseParameter) {

        switch (chooseParameter) {
            case 1:
                return Parameter.NO2;
            case 2:
                return Parameter.O3;
            case 3:
                return Parameter.PM2_5;
            case 4:
                return Parameter.PM10;
            case 5:
                return Parameter.SO2;
            case 6:
                return Parameter.C6H6;
            case 7:
                return Parameter.CO;
            case 8:
                return Parameter.LAEQ;
            case 9:
                return Parameter.PA;
            case 10:
                return Parameter.TEMP;
            case 11:
                return Parameter.RU;
            case 12:
                return Parameter.VD;
            case 13:
                return Parameter.VI;
            case 14:
                return Parameter.HM;
            case 15:
                return Parameter.PC;
            case 16:
                return Parameter.RG;
        }
        return null;
    }

    private static Statistics[] getStatsBySensor(City city,
            String stationName, int chooseOperator, int chooseParam,
            LocalDateTime[] time) {

        switch (chooseOperator) {
            case 1:
                return (Statistics[]) city.getMeasurementsBySensor(stationName,
                        AggregationOperator.AVG, getParam(chooseParam), time[0],
                        time[1]);
            case 2:
                return (Statistics[]) city.getMeasurementsBySensor(stationName,
                        AggregationOperator.COUNT, getParam(chooseParam),
                        time[0], time[1]);
            case 3:
                return (Statistics[]) city.getMeasurementsBySensor(stationName,
                        AggregationOperator.MAX, getParam(chooseParam), time[0],
                        time[1]);
            case 4:
                return (Statistics[]) city.getMeasurementsBySensor(stationName,
                        AggregationOperator.MIN, getParam(chooseParam), time[0],
                        time[1]);
        }
        return null;
    }

    private static Statistics[] getStatsByStation(City city,
            int chooseOperator, int chooseParam, LocalDateTime[] time) {

        switch (chooseOperator) {
            case 1:
                return (Statistics[]) city.getMeasurementsByStation(
                        AggregationOperator.AVG, getParam(chooseParam), time[0],
                        time[1]);
            case 2:
                return (Statistics[]) city.getMeasurementsByStation(
                        AggregationOperator.COUNT, getParam(chooseParam),
                        time[0], time[1]);
            case 3:
                return (Statistics[]) city.getMeasurementsByStation(
                        AggregationOperator.MAX, getParam(chooseParam), time[0],
                        time[1]);
            case 4:
                return (Statistics[]) city.getMeasurementsByStation(
                        AggregationOperator.MIN, getParam(chooseParam), time[0],
                        time[1]);
        }
        return null;
    }

    private static Statistics[] getStats(City city, int VisualizationBy) {

        String stationName = "";

        if (VisualizationBy == 2) {
            stationName = readStationName();
        }

        switch (VisualizationBy) {
            case 1:
                return getStatsByStation(city, chooseOperator(),
                        chooseParameter(), chooseBeteweenTimes());
            case 2:
                return getStatsBySensor(city, stationName, chooseOperator(),
                        chooseParameter(), chooseBeteweenTimes());
        }
        return null;
    }

    private static boolean displayChart(City city, int visualizationBy,
            String[] json, int jsonIndex) {

        Statistics[] stats = getStats(city, visualizationBy);
        ExporterBarChart exp = new ExporterBarChart(stats);

        if (stats != null) {
            try {
                json[jsonIndex] = exp.export();
            } catch (IOException ex) {
                System.out.println("I/O exception");
            }

            try {                
                Dashboard.render(json);
            } catch (IOException ex) {
                System.out.println("I/O exception");
            }
            return true;

        }
        return false;
    }

    /**
     * The user interaction menu
     *
     * @param args
     */
    public static void main(String[] args) {

        City city = readCityName();
        String filePath = readFilePath();

        Importer imp = new Importer();
        try {
            ImporterStatistics impstats
                    = (ImporterStatistics) imp.importData(city, filePath);

            System.out.println(impstats.toString());
                                   
            menu(city);

        } catch (IOException | CityException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
