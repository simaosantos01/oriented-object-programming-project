/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package IO;

import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import java.io.IOException;

/**
 *
 * @author simao
 */
public class ExporterBarChart implements IExporter {

    private final IStatistics[] statistics;

    /**
     * The ExporterBarChart class contructor
     *
     * @param statistics The statistics array
     */
    public ExporterBarChart(IStatistics[] statistics) {
        this.statistics = statistics;
    }

    private String convertValuesToString() {
        String string = "";

        for (int i = 0; i < this.statistics.length; i++) {
            string += this.statistics[i].getValue() + ",";
        }
        return string;
    }

    private String getParameterFromDescription() {

        String stringTmp;

        stringTmp = this.statistics[0].getDescription();
       
        String string = stringTmp.substring(stringTmp.indexOf("("));

        return string;
    }

    private String getLabelsFromDescription() {

        String string = "";

        for (int i = 0; i < this.statistics.length; i++) {
            String tmp = this.statistics[i].getDescription();
            tmp = tmp.substring(0, tmp.indexOf("("));

            string += "\"" + tmp + "\"" + ',';
        }
        return string;
    }

    /**
     * Serialize an object to a specific format that can be stored
     *
     * @return the JSON representation
     * @throws IOException java.io.IOException Signals that an I/O exception of 
     * some sort has occurred. This class is the general class of exceptions 
     * produced by failed or interrupted I/O operations.
     */
    @Override
    public String export() throws IOException {
        return '{' + "\"type\"" + ':' + "\"bar\"" + ',' + "\"data\"" + ':' + '{'
                + "\"labels\"" + ':' + '[' + this.getLabelsFromDescription()
                + ']' + ',' + "\"datasets\"" + ":" + '[' + '{' + "\"label\""
                + ':' + "\"" + this.getParameterFromDescription() + "\"" + ','
                + "\"data\"" + ':' + '[' + this.convertValuesToString()
                + ']' + '}' + ']' + '}' + '}';
    }
}
