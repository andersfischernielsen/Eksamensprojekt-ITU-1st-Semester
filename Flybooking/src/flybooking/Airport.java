
package flybooking;

/**
 * An airport with a name and location.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Airport {
    private final String id;
    private final String country;
    private final String city;

    /**
     * 
     * @param id The three-letter ID for the airport (ex. "CPH")
     * @param country The name of the country
     * @param city The name of the city
     */
    public Airport(String id, String country, String city)
    {
        this.id = id;
        this.country = country;
        this.city = city;
    }
    
    
    /**
     * Return the three-letter ID of the airport.
     * @return name The name of the airport.
     */
    public String getID() {
        return id;
    }
    
    /**
     * Return the country the airport is in.
     * @return country The country the airport is in.
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Return the city the airport is in.
     * @return city The city the airport is in.
     */
    public String getCity() {
        return city;
    }
}
