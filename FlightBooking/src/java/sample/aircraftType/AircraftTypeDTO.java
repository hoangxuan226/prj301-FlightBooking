/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.aircraftType;

import java.io.Serializable;

/**
 *
 * @author phamx
 */
public class AircraftTypeDTO implements Serializable {

    private int typeID;
    private String name;
    private int numberBusinessSeats;
    private int numberEconomySeats;

    public AircraftTypeDTO() {
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberBusinessSeats() {
        return numberBusinessSeats;
    }

    public void setNumberBusinessSeats(int numberBusinessSeats) {
        this.numberBusinessSeats = numberBusinessSeats;
    }

    public int getNumberEconomySeats() {
        return numberEconomySeats;
    }

    public void setNumberEconomySeats(int numberEconomySeats) {
        this.numberEconomySeats = numberEconomySeats;
    }


}
