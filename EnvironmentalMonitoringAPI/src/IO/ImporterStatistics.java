/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package IO;

import edu.ma02.io.interfaces.IOStatistics;

/**
 *
 * @author simao
 */
public class ImporterStatistics implements IOStatistics {

    private int numberOfNewStationsRead;
    private int numberOfNewSensorsRead;
    private int numberOfNewMeasurementsRead;
    private int numberOfStationsRead;
    private int numberOfSensorsRead;
    private int numberOfReadMeasurements;
    private String[] exceptions;
    private static final int ARRAY_SIZE = 1;
    private int exceptionsCounter;

    /**
     * The ImporterStatistics class constructor
     *
     */
    public ImporterStatistics() {

        this.numberOfNewStationsRead = 0;
        this.numberOfNewSensorsRead = 0;
        this.numberOfNewMeasurementsRead = 0;
        this.numberOfStationsRead = 0;
        this.numberOfSensorsRead = 0;
        this.numberOfReadMeasurements = 0;
        this.exceptions = new String[ARRAY_SIZE];
        this.exceptionsCounter = 0;
    }

    private void expandArray() {

        String[] temp = new String[this.exceptions.length];

        System.arraycopy(this.exceptions, 0, temp, 0,
                this.exceptions.length);

        this.exceptions = new String[this.exceptions.length
                + this.exceptions.length];

        System.arraycopy(temp, 0, this.exceptions, 0, temp.length);
    }

    /**
     * Getter for the number of measurements read
     *
     * @return the number of measurements read
     */
    @Override
    public int getNumberOfReadMeasurements() {
        return this.numberOfReadMeasurements;
    }

    /**
     * Incrementer for the number of measurements read
     *
     */
    public void incrementNumberOfReadMeasurements() {
        this.numberOfReadMeasurements++;
    }

    /**
     * Getter for the number of new stations read
     *
     * @return the number of new stations read
     */
    @Override
    public int getNumberOfNewStationsRead() {
        return this.numberOfNewStationsRead;
    }

    /**
     * Incrementer for the number of new stations read
     *
     */
    public void incrementNumberOfNewStationsRead() {
        this.numberOfNewStationsRead++;
    }

    /**
     * Getter for the number of stations read
     *
     * @return the number of stations read
     */
    @Override
    public int getNumberOfStationsRead() {
        return this.numberOfStationsRead;
    }

    /**
     * Incrementer for the number of stations read
     *
     */
    public void incrementNumberOfStationsRead() {
        this.numberOfStationsRead++;
    }

    /**
     * Getter for the number of sensors read
     *
     * @return the number of sensors read
     */
    @Override
    public int getNumberOfSensorsRead() {
        return this.numberOfSensorsRead;
    }

    /**
     * Incrementer for the number of sensors read
     *
     */
    public void incrementNumberOfSensorsRead() {
        this.numberOfSensorsRead++;
    }

    /**
     * Getter for the number of new sensors read
     *
     * @return the number of new sensors read
     */
    @Override
    public int getNumberOfNewSensorsRead() {
        return this.numberOfNewSensorsRead;
    }

    /**
     * Incrementer for number of new sensors read
     */
    public void incrementNumberOfNewSensorsRead() {
        this.numberOfNewSensorsRead++;
    }

    /**
     * Getter for the new measurements read
     *
     * @return the new measurements read
     */
    @Override
    public int getNumberOfNewMeasurementsRead() {
        return this.numberOfNewMeasurementsRead;
    }

    /**
     * Incrementer for the number of new measurements read
     *
     */
    public void incrementNumberOfNewMeasurementsRead() {
        this.numberOfNewMeasurementsRead++;
    }
    
    /**
     * Adds an exception
     *
     * @param string the exception to be added
     */
    public void addException(String string){
        
        if(this.exceptionsCounter == this.exceptions.length){
            this.expandArray();
        }
        
        this.exceptions[this.exceptionsCounter] = string;
        this.exceptionsCounter++;
    }
    
    /**
     * Returns the object in string format
     *
     * @return The object in string format
     */
    @Override
    public String toString(){
        return "-----------RELATORY-----------\n"  
                + "New measurements read: " + this.numberOfNewMeasurementsRead 
                + "\n" 
                + "New sensors read: " + this.numberOfNewSensorsRead + "\n" 
                +  "New stations read: " + this.numberOfNewStationsRead + "\n" 
                + "Total measurements read:" + this.numberOfReadMeasurements 
                + "\n" 
                + "Total sensors read: " + this.numberOfSensorsRead + "\n"
                + "Total stations read: " + this.numberOfStationsRead + "\n";
    }

    /**
     * Getter for the exceptions list
     *
     * @return the exceptions list
     */
    @Override
    public String[] getExceptions() {
        return this.exceptions;
    }

}
