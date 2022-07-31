import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <code>Register</code> class represents an object that only handles
 * transaction of items and currency or a <code>Sale</code>.
 * 
 * @author Jordan Anodjo
 */
public class Register {

    /**
     * Member variable -
     * The catalog that stores a <code>ProductCatalog</code>.
     */
    private ProductCatalog catalog;

    /**
     * Member variable -
     * The current <code>Sale</code> that is currently being handled by
     * <code>Register</code>.
     */
    private Sale curSale;

    /**
     * Member variable -
     * The grand total made from all sales on this <code>Register</code>.
     */
    private double grandTotal;

    /**
     * Constructor -
     * Constructs a <code>Register</code> with a <code>ProductCatalog</code>.
     * 
     * @param catalog - <code>ProductCatalog</code> that the <code>Register</code>
     *                will use
     */
    public Register(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Member Function -
     * Getter for the grand total of the <code>Register</code>.
     * 
     * @return the grandtotal
     */
    public double getGrandTotal() {
        return grandTotal;
    }

    /**
     * Member Function -
     * Start a new sale by creating <code>Sale</code> object
     */
    public void startSale() {
        // if the sale doesnt exist make a new one
        if (curSale == null)
            curSale = new Sale();
    }

    /**
     * Member function -
     * Starts a session in where the user must select an apporiate response.
     * In this session the user must select a product they wish to buy and the
     * quantity of items they wish to purchase.
     * 
     * @param input - The users input <code>Scanner</code>
     */
    public void selectProduct(Scanner input) {
        while (curSale.isBuying()) {

            // prompt the user to select items
            SalesLineItem userProduct = promptSelectProduct(input);

            if (userProduct == null)
                return;

            // prompt the amount of items the user wants to purchase
            promptQuantity(userProduct, input);
        }

    }

    /**
     * Starts a session in where the user must select an apporiate response.
     * In this session the user must select a product they wish to buy.
     * 
     * @param input - The users input <code>Scanner</code>
     * @return the product the user wanted to buy
     */
    private SalesLineItem promptSelectProduct(Scanner input) {
        // This session the user must select a valid product and purchase it
        session: while (curSale.isBuying()) {
            System.out.print("Enter product code: ");

            // record user's input for later use
            String userInput = input.nextLine();

            // check if the user wants to use other features
            if (userInput.equals("-1")) {
                // negative 1 is the key to continue to the next phase of the program
                // prompt the user with what they will be paying
                curSale.endBuying();
                break session;

            } else if (userInput.equals("0000")) {
                // display all the items in the catalog
                System.out.println("\n" + catalog + "\n");
                continue session;
            }

            ItemId productId;

            // This will be the information of the item the user wants to purchase
            // However this productSpecification needs to be cloned
            // If not the Sale will have a reference to the store's catalog
            ProductSpecification productInfo;

            // validate the user's input
            try {
                productId = new ItemId(userInput.toString());
                productInfo = catalog.getCopy(productId);

            } catch (IllegalArgumentException e) {
                // catches if the user input fails to match ItemId specifications
                System.out.println("!!! Invalid data type\n");
                continue session;

            } catch (NullPointerException e) {
                // catches if the user input an ItemId that does not exist within the catalog
                System.out.println("!!! Invalid product code\n");
                continue session;
            }

            // if the item doesnt already exist then add it
            if (!curSale.contains(productId))
                curSale.makeLineItem(productInfo);

            // retrieve selected item from currSale's shopping cart
            SalesLineItem userProduct = curSale.get(productInfo.getId());

            System.out.printf("%20s%s\n", "Item name: ", userProduct.getName());
            return userProduct;
        }

        return null;
    }

    /**
     * Member function -
     * This function starts to finalize and begin payment of all
     * <code>SaleLineItems</code> in <code>Sale</code>
     * 
     * @param input - The users input <code>Scanner</code>
     */
    public void makePayment(Scanner input) {
        if (!curSale.isBuying()) {
            // Print each item in the shopping cart
            System.out.println("----------------------------");
            System.out.println("Item list:");
            System.out.println(curSale);

            // Variable used to store the tax
            double tax = 0.06;
            double subTotal = curSale.getTotal();
            double taxedTotal = curSale.getTotalWithTax();

            System.out.printf("Subtotal %12s %6.2f\n", "$", subTotal);
            System.out.printf("Total with Tax (%.0f%%) %s %6.2f\n", tax * 100, "$", taxedTotal);
            promptValidTransaction(taxedTotal, input);
        }
    }

    /**
     * Member function -
     * Ends the current sale
     * 
     * @return the finished <code>Sale</code>
     */
    public Sale endSale() {
        // no sale to end
        if (curSale == null)
            return null;

        // Make this Sale complete
        curSale.becomeComplete();

        // Get a reference to that sale
        Sale temp = curSale;

        // remove the reference to current sale
        curSale = null;

        // return back that sale
        return temp;
    }

    /**
     * Member function -
     * Starts a session in where the user must select an apporiate response.
     * In this session the user must select the quantity of items they wish to
     * purchase.
     * 
     * @param userProduct - <code>Item</code> that will have its quantity raised
     * @param input        - The users input <code>Scanner</code>
     */
    private void promptQuantity(SalesLineItem userProduct, Scanner input) {
        while (true) {
            // prompt user
            System.out.printf("%-20s", "Enter quantity: ");

            int amount;

            // validate the user's input
            try {
                amount = input.nextInt();
                userProduct.addQuantity(amount);

            } catch (InputMismatchException e) {
                // catches if the user input is not an integer
                System.out.println("!!! Invalid data type\n");
                continue;

            } catch (IllegalArgumentException e) {
                // catches if the user input a number less than or equal to 0.
                System.out.println("!!! Invalid quantity\n");
                continue;

            } finally {
                // clear the input
                input.nextLine();
            }

            System.out.printf("%22s%6.2f\n\n", "Item total: $ ", amount * userProduct.getPrice());
            break;
        }
    }

    /**
     * Member function -
     * Starts a session in where the user must select an apporiate response.
     * In this session the user must input a payment to cover the cost of their
     * desired items.
     * 
     * @param total - <code>Item</code> that will have its quantity raised
     * @param input - The users input <code>Scanner</code>
     */
    private void promptValidTransaction(double total, Scanner input) {

        // session used to keep user in until an appropriate transaction is made
        session: while (true) {
            // format the string the user will be inputting on
            if (total > 9.99)
                System.out.printf("Tendered amount%6s%2s", "$", " ");
            else
                System.out.printf("Tendered amount%6s%3s", "$", " ");

            double change;

            // validate the user's input
            try {
                change = curSale.makePayment(input.nextDouble());

            } catch (IllegalArgumentException e) {
                // catch if the user's input is not greater than the bill
                System.out.println("!!! Not enough. Enter again.");
                continue session;

            } catch (InputMismatchException e) {
                // catch if the user's input is invalid or not a double
                System.out.println("!!! Invalid input Enter again.");
                continue session;

            } finally {
                // clear line
                input.nextLine();
            }

            System.out.printf("Change%15s%7.2f\n", "$", change);
            System.out.println("----------------------------\n");

            break session;
        }

        // record money made
        grandTotal += curSale.getTotal();
    }

    /**
     * Member function -
     * Starts a session in where the user must select an apporiate response.
     * In this session the user must select Y or N (case insenstive).
     * If the user sends in an invalid response, the user will be
     * prompted to select an apporiate response until they have selected such.
     * 
     * @param input - The users input <code>Scanner</code>
     * @return Boolean value if the user selects Y : N ? true : false
     */
    public boolean promptStartShopping(Scanner input) {

        // using an infinite loop to keep the user in the session.
        while (true) {
            System.out.print("Beginning a new sale (Y/N) "); // prompt user to select (Y/N) Prompt A

            switch (input.nextLine().toUpperCase()) {
                case "Y":
                    System.out.println("--------------------");
                    return true;
                case "N":
                    System.out.println("\n============================");
                    return false;
                default:
                    // if invalid input, prompt user to start again.
                    System.out.println("!!! Invalid input");
                    break;
            }
        }
    }
}