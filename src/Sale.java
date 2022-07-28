
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The <code>Sale</code> class represents a literal transaction.
 * Containing processes that allows for the <code>Sale</code> to gain items,
 * calcuate the price of these items and finish transaction.
 * 
 * @author Jordan Anodjo
 */
public class Sale {

    /**
     * Member variable -
     * The date that represents the date the <code>Sale<code> begain.
     */
    private Date date;

    /**
     * Member variable -
     * The time that represents the time the <code>Sale<code> begain.
     */
    private Time time;

    /**
     * Member variable -
     * isComplete repsents when the sale is over or not <code>Sale<code>.
     */
    private Boolean isComplete;

    /**
     * Member variable -
     * isBuying represents if the <code>Sale<code> is still purchasing products.
     */
    private Boolean isBuying;

    /**
     * Memeber variable -
     * shoppingCart is the container used to hold all products the <code>Sale<code>
     * is purchasing.
     */
    private HashMap<ItemId, SalesLineItem> shoppingCart;

    /**
     * Constructer -
     * Constructs a <code>Sale</code>.
     */
    public Sale() {
        // Get the current time of the sale
        // date = new Date(System.currentTimeMillis());
        // time = new Time(System.currentTimeMillis());

        // Since this was created a user wants to buy products
        isBuying = true;

        // Since this is the start of the sale isComplete will be false
        isComplete = false;

        // Create the container for all products sold
        shoppingCart = new HashMap<>();
    }

    /**
     * Member Function -
     * Getter for isComplete.
     * 
     * @return isComplete
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Member Function -
     * Getter for isBuying.
     * 
     * @return isBuying
     */
    public boolean isBuying() {
        return isBuying;
    }

    /**
     * Member Function -
     * Getter for the <code>Date</code> date.
     * 
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Member Function -
     * Getter for the <code>Time</code> time.
     * 
     * @return time
     */
    public Time getTime() {
        return time;
    }

    /**
     * Member Function -
     * This function checks if the <code>SalesLineItem</code> exist within the
     * <code>Sale</code>.
     * 
     * @param id - the <code>ItemId</code> of the item requested
     * @return
     */
    public boolean contains(ItemId id) {
        return (shoppingCart.containsKey(id));
    }

    /**
     * Member Function -
     * This function calcuates the total price of each item requested without tax.
     * 
     * @return total
     */
    public double getTotal() {
        double total = 0;
        for (HashMap.Entry<ItemId, SalesLineItem> item : shoppingCart.entrySet())
            total += item.getValue().getSubtotal();
        return total;
    }

    /**
     * Member Function -
     * This function calcuates the total price of each item requested with tax.
     * 
     * @return total with tax
     */
    public double getTotalWithTax() {
        double total = 0;
        for (HashMap.Entry<ItemId, SalesLineItem> item : shoppingCart.entrySet())
            total += item.getValue().getSubtotalWithTax();
        return total;
    }

    /**
     * Member Function -
     * This function gets the <code>SalesLineItem</code> within the
     * <code>Sale</code>.
     * 
     * @param id - the <code>ItemId</code> of the item requested
     * @return the <code>SalesLineItem</code> requested
     * @throws NullPointerException if the <code>ItemId</code> is not in the
     *                              <code>Sale</code>
     */
    public SalesLineItem get(ItemId id) throws NullPointerException {
        if (!this.contains(id))
            throw new NullPointerException();
        return shoppingCart.get(id);
    }

    /**
     * Member Function -
     * Setter to set isComplete to true.
     */
    public void becomeComplete() {
        isComplete = true;
    }

    /**
     * Member Function -
     * Setter to set isBuying to true.
     */
    public void startBuying() {
        isBuying = true;
    }

    /**
     * Member Function -
     * Setter to set isBuying to false.
     */
    public void endBuying() {
        isBuying = false;
    }

    /**
     * Member Function -
     * Creates a new <code>SalesLineItem</code> from a
     * <code>ProductSpecification</code> and stores it in <code>Sale</code>.
     * 
     * @param productInfo the <code>ProductSpecification</code> that is being added
     */
    public void makeLineItem(ProductSpecification productInfo) {
        SalesLineItem salesLineItem = new SalesLineItem(productInfo);
        shoppingCart.put(productInfo.getId(), salesLineItem);

    }

    /**
     * Member Function -
     * This function validates payment and returns change for that payment.
     * 
     * @param payment the amount money being used
     * @return change
     * @throws IllegalArgumentException if the payment is not enough to cover cost
     */
    public double makePayment(double payment) throws IllegalArgumentException {
        if (payment - getTotalWithTax() < 0)
            throw new IllegalArgumentException("Insufficent funds");

        return payment - getTotalWithTax();
    }

    @Override
    /**
     * {@inheritDoc}
     * Member Function -
     * Turns <code>Sale</code> into a <code>String</code>.
     */
    public String toString() {

        ArrayList<SalesLineItem> arrayList = new ArrayList<>();

        // convert each value in the shoppingCart to an array
        for (HashMap.Entry<ItemId, SalesLineItem> item : shoppingCart.entrySet())
            arrayList.add(item.getValue());

        int length = arrayList.size();

        StringBuilder strbldr = new StringBuilder();
        // For each Item compare it with the other items and swap them if needed
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++)
                if (arrayList.get(i).getName().length() < arrayList.get(j).getName().length()) {
                    SalesLineItem temp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, temp);
                }

            strbldr.append(arrayList.get(i).toString());

            // Dont print the last newline.
            if (i != length - 1)
                strbldr.append("\n");
        }

        return strbldr.toString();
    }

}
