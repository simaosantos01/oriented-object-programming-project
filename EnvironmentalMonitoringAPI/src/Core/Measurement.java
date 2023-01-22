/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Core;

import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.interfaces.IMeasurement;

import java.time.LocalDateTime;

/**
 *
 * @author simao
 */
public class Measurement implements IMeasurement {

    private final LocalDateTime time;
    private final double value;
    private final Unit unit;

    /**
     * The Measurement class constructor
     *
     * @param time The measurement date
     * @param value The measurement value
     * @param unit The measurement unit
     */
    public Measurement(LocalDateTime time, double value, Unit unit) {

        this.time = time;
        this.value = value;
        this.unit = unit;
    }

    /**
     * Getter for the measurement unit
     *
     * @return The measurement measure unit
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * Getter for the measurement date
     *
     * @return The measurement date
     */
    @Override
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Getter for the measurement value
     *
     * @return The measurement value
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Two Measurements are equal if they have been captured at the same time
     * and have the same value
     *
     * @param obj object to compare
     * @return True is equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Measurement)) {
            return false;
        }

        Measurement tmp = (Measurement) obj;

        if (this.time.toString().equals(tmp.time.toString())
                && this.value == tmp.value) {
            return true;
        }
        return false;
    }

    /**
     * Returns the object in string format
     *
     * @return The object in string format
     */
    @Override
    public String toString() {
        return "time  = " + this.time + "\n"
                + "value = " + this.value + "\n"
                + "measureUnit = " + Unit.getUnitString(this.unit) + "\n\n";
    }

}
