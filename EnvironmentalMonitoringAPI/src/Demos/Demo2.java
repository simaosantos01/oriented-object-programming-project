/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Demos;

import Core.City;
import Core.Statistics;
import IO.ExporterBarChart;
import IO.Importer;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.dashboards.Dashboard;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simao
 */
public class Demo2 {

    /**
     * Testing the core and bar chart visualizaton
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        City cityA = new City("aa-11", "Santo Tirso");

        Importer imp = new Importer();

        try {
            imp.importData(cityA, "files/teste.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (CityException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------------------------");

        Statistics[] stats = (Statistics[]) cityA.getMeasurementsBySensor(
                "Calcada da Ajuda", AggregationOperator.AVG, Parameter.LAEQ);

        for (int i = 0; i < stats.length; i++) {
            System.out.println(stats[i].getDescription() + ": "
                    + stats[i].getValue());
        }

        ExporterBarChart exp = new ExporterBarChart(stats);
        String[] json = new String[1];

        try {
            System.out.println(exp.export());

            json[0] = exp.export();
        } catch (IOException ex) {
            Logger.getLogger(Demo2.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Dashboard.render(json);
        } catch (IOException ex) {
            Logger.getLogger(Demo3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
