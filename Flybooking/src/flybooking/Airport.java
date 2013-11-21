
package flybooking;

/**
 * An airport with a name and loca. 
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Airport {
    private final String name;
    private final String country;
    private final String city;

    /**
     * 
     * @param name The name of the airport
     * @param country The name of the country
     * @param city The name of the city
     */
    public Airport(String name, String country, String city)
    {
        this.name = name;
        this.country = country;
        this.city = city;
    }
    
    
    /**
     * Return the name of the airport.
     * @return name The name of the airport.
     */
    public String getName() {
        return name;
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
