import java.util.Objects;

/**
 * The <code>ItemId</code> class represents the unique identification for an
 * <code>Object</code>.
 * 
 * @author Jordan Anodjo
 */
public class ItemId {

    /**
     * Member variable -
     * Format is a regex.
     * Used to set a specific format that all codes must follow.
     */
    private static String format = "^(?i)[A-Z].*";
    /**
     * Member variable -
     * Id is a <code>String</code> value that is used to represent the
     * identification of an <code>Object</code>.
     */
    private String id;

    /**
     * Member variable -
     * Hashcode is an <code>int</code> value calcuated from the id.
     */
    private int hashCode;

    /**
     * Contructor -
     * Contructs an <code>ItemID</code> with passed <code>String</code>.
     * 
     * @param code - <code>String</code> that will be used for an
     *             <code>ItemId</code>
     * @throws IllegalArgumentException if format is not valid
     */
    public ItemId(String code) {
        if (!code.matches(format))
            throw new IllegalArgumentException("Code format is not valid");
        this.id = code;
        this.hashCode = convertCodetoHash();
    }

    /**
     * Copy Constructor -
     * Constructs a copy of an <code>ItemId</code> from a <code>Code</code>
     * 
     * @param code - <code>ItemId</code> that will be copied
     */
    public ItemId(ItemId code) {
        this.id = new String(code.getId());
        this.hashCode = convertCodetoHash();
    }

    /**
     * Member Function -
     * This function returns the <code>String</code> value of the code.
     * 
     * @return a value for this <code>ItemId</code>
     */
    public String getId() {
        return id;
    }

    /**
     * Member function -
     * This function returns the <code>char</code> at the beginning of an
     * <code>ItemId</code>.
     * 
     * @return <code>char</code> at the first index of an <code>ItemId</code>
     */
    public char getLeadChar() {
        return id.charAt(0);
    }
    
    /**
     * {@inheritDoc}
     * Member Function -
     * This function returns a string version of <code>ItemId</code>.
     * 
     * @return the string version of <code>ItemId</code>
     */
    @Override
    public String toString() {
        return getId();
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * This function compares <code>itemId</code> ids.
     * 
     * @param o - <code>ItemId</code> to be compared with
     * @return if the ItemId are equal
     */
    @Override
    public boolean equals(Object o) {
        // if this object is the same thing return true
        if (o == this)
            return true;

        // if object is not an instance of the ItemId return false
        if (!(o instanceof ItemId))
            return false;

        // Cast downwards
        ItemId id = (ItemId) o;

        // Return if the ids are the same
        return this.getId().equals(id.getId());
    }

    /**
     * Member Function -
     * This function converts the <code>ItemId</code> to a hash code value.
     * 
     * @return a hash code
     */
    private int convertCodetoHash() {
        int hash = 0;
        // for each character in the id add their ascii values
        for (char ch : id.toCharArray())
            hash += (int) ch;

        // return the hash value of that number
        return Objects.hashCode(hash);
    }

    /**
     * This function retrieves the hash code of <code>ItemId</code>
     */
    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
