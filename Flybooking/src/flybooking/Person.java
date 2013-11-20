package flybooking;

/**
 * A person with a name, and ID, an address and a group (elderly, child etc.)
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Person
{

    private String firstName;
    private String lastName;
    private final int ID;
    private String address;
    private int groupID;

    public Person(String firstName, String lastName, int ID, String address, int groupID)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.address = address;
        this.groupID = groupID;
    }

    /**
     * Return the firstname of the person.
     *
     * @return The firstname of the person.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the firstname of this person to the parameter firstName
     *
     * @param firstName the firstname to change to.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Return the firstname of the person.
     *
     * @return The firstname of the person.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the firstname of this person to the parameter firstName
     *
     * @param LastName the firstname to change to.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Return the ID of the person.
     *
     * @return The ID of the person.
     */
    public int getID()
    {
        return ID;
    }

    /**
     * Return the address of the person.
     *
     * @return The address of the person.
     */
    public String getAdress()
    {
        return address;
    }

    /**
     * Return the group the person belongs to (elderly, child etc.)
     *
     * @return The group the person belongs to (elderly, child, etc.).
     */
    public int getGroupID()
    {
        return groupID;
    }
    
    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }
}
