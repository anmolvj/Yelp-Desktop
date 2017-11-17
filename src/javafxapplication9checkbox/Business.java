/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication9checkbox;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Anmol
 */
public class Business {
    private final SimpleStringProperty bid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty city;
    private final SimpleStringProperty state;
    private final SimpleStringProperty stars;

    public Business(String bid, String name, String city, String state, String stars) {
        this.bid = new SimpleStringProperty(bid);
        this.name = new SimpleStringProperty(name);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.stars = new SimpleStringProperty(stars);
        
    }
    
    public String getBid() {
        return bid.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public String getStars() {
        return stars.get();
    }
    
    
}
