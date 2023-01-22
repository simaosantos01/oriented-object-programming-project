/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.interfaces.IStatistics;

/**
 *
 * @author simao
 */
public class Statistics implements IStatistics {

    private final String description;
    private final double value;

    /**
     * The Statistics class contructor
     *
     * @param description The statistics description
     * @param value The statistics value
     */
    public Statistics(String description, double value) {
        this.description = description;
        this.value = value;
    }

    /**
     * Getter for the statistics description
     *
     * @return the statistics description
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter for the statistics value
     *
     * @return the statistics value
     */
    @Override
    public double getValue() {
        return this.value;
    }

}
