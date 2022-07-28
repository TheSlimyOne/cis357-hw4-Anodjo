/**
 * The <code>ProductSpecification</code> class represents a product and the
 * information for that product.
 * 
 * @author Jordan Anodjo
 */
public class ProductSpecification {

    /**
     * Member variable -
     * The identification for a <code>ProductSpecification</code>.
     */
    private ItemId id;

    /**
     * Member variable -
     * The name for a <code>ProductSpecification</code>.
     */
    private String name;

    /**
     * Member variable -
     * The price for a <code>ProductSpecification</code>.
     */
    private float price;

    /**
     * Member variable -
     * The tax for a <code>ProductSpecification</code>.
     */
    private double tax;

    /**
     * Constructor -
     * Constructs a <code>ProductSpecification</code> with all member variables
     * populated.
     * 
     * @param id    - <code>ItemId</code> of the <code>ProductSpecification</code>
     * @param name    the name of the <code>ProductSpecification</code>
     * @param price - the cost of the <code>ProductSpecification</code>
     */
    public ProductSpecification(ItemId id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tax = calcTax();
    }

    /**
     * Copy Constructor -
     * Constructs a copy of a <code>ProductSpecification</code> from a
     * <code>ProductSpecification</code>.
     * 
     * @param product <code>ProductSpecification</code> that will be copied
     */
    public ProductSpecification(ProductSpecification product) {
        this.id = new ItemId(product.getId());
        this.name = new String(product.getName());
        this.price = product.getPrice();
        this.tax = product.getTax();
    }

    /**
     * Member Function -
     * Getter for the <code>ItemId</code>.
     * 
     * @return <code>ItemId</code>
     */
    public ItemId getId() {
        return id;
    }

    /**
     * Member Function -
     * Getter for the <code>ProductSpecification</code> name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Member Function -
     * Getter for the <code>ProductSpecification</code> price.
     * 
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Member Function -
     * Getter for the <code>ProductSpecification</code> tax.
     * 
    * @return tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * Member Function -
     * Calculate what the tax of the <code>ProductSpecification</code> should have
     * from its <code>ItemId</code>.
     * 
     * @return tax
     */
    public double calcTax() {
        // Check if the leading matches a specified character
        if (Character.compare(id.getLeadChar(), 'B') == 0)
            return 0;
        else
            return 0.06;
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints <code>ProductSpecification</code> in the format of: id, name, and
     * price.
     */
    @Override
    public String toString() {
        return String.format("%5s %-13s $ %6.2f", id, name, price);
    }
}
