/**
 * The <code>SalesLineItem</code> represents functionality of a purchase.
 * 
 * @author Jordan Anodjo
 */
public class SalesLineItem {

    /**
     * Member variable -
     * The quantity of the <code>SalesLineItem's</code> immitation
     */
    private int quantity;

    /**
     * Member variable -
     * The identity is the description of the product desired.
     */
    private ProductSpecification identity;

    /**
     * Constructor -
     * Creates a new SalesLineItem b
     * 
     * @param product the <code>ProductSpecification</code> being used as an
     *                identity
     */
    public SalesLineItem(ProductSpecification product) {
        // Create a copy of the product to use for the identity
        this.identity = new ProductSpecification(product);

        // Set quantity to 0 so it may be added to
        quantity = 0;
    }

    /**
     * Member Function -
     * This function adds to quantity.
     * 
     * @param quantity amount to add by
     * @throws IllegalArgumentException if the results of adding is less than 1
     */
    public void addQuantity(int quantity) throws IllegalArgumentException {
        if (this.quantity + quantity <= 0)
            throw new IllegalArgumentException("Cannot have non positive quantity of items.");
        this.quantity += quantity;
    }

    /**
     * Member Function -
     * This function calculates the total cost of the product with the quantity of
     * the product.
     * (without tax)
     * 
     * @return the total cost
     */
    public double getSubtotal() {
        return (identity.getPrice() * quantity);
    }

    /**
     * Member Function -
     * This function calculates the total cost of the product with the quantity of
     * the product.
     * (with tax)
     * 
     * @return the total cost with tax
     */
    public double getSubtotalWithTax() {
        return ((getSubtotal() * identity.getTax()) + getSubtotal());
    }

    /**
     * Member Function -
     * Getter for the name of the product being imitated.
     * 
     * @return name
     */
    public String getName() {
        return identity.getName();
    }

    /**
     * Member Function -
     * Getter for the quantity.
     * 
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Member Function -
     * Getter for the price of the product being imitated.
     * 
     * @return price
     */
    public double getPrice() {
        return identity.getPrice();
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints <code>Item</code> in the format of: amount, name, and total price.
     */
    @Override
    public String toString() {
        return String.format("%5d %-13s $ %6.2f", quantity, identity.getName(), getSubtotal());
    }
}
