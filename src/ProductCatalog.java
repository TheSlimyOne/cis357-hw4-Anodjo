import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The <code>ProductCatalog</code> class represents a collection of <code>ProductSpecifications</code>.
 * 
 * @author Jordan Anodjo
 */
public class ProductCatalog {

    /**
     * Member variable -
     * The storage for each unique product.
     */
    private HashMap<ItemId, ProductSpecification> catalog;

    /**
     * Member variable -
     * The total amount of products in the catalog.
     */
    private int length;

    /**
     * Member variable -
     * The file the <code>ProductCatalog</code> was contructed from.
     */
    private File file;

    /**
     * Constructer -
     * Creates an empty catalog.
     */
    private ProductCatalog() {
        this.length = 0;
        this.file = null;
        catalog = new HashMap<ItemId, ProductSpecification>();
    }

    /**
     * Constructer -
     * Creates a catalog from the products in a file.
     * 
     * @param path - Location of a file that holds <code>ProductCatalog</code> data
     * @throws FileNotFoundException    if file could not be found
     * @throws IllegalArgumentException if file format is not valid
     */
    public ProductCatalog(String path) throws FileNotFoundException {
        this();

        // Open the file path
        File file = new File(path);
        Scanner input = new Scanner(file);

        // Go through each line in the file
        // Create an Item from the data
        // Then insert it into the Receptacle
        int counter = 0;
        while (input.hasNext()) {
            // Split the data up
            String[] str = input.nextLine().split(", ");

            ItemId id;
            String name;
            Float price;

            try {
                // Create the parameters for the new Item
                id = new ItemId(str[0]);
                name = str[1];
                price = Float.parseFloat(str[2]);
            } catch (RuntimeException e) {
                // Close the scanner
                input.close();
                throw new IllegalArgumentException("File format is not valid.");
            }

            // Create the product item
            ProductSpecification product = new ProductSpecification(id, name, price);

            // Add this product into the catalog
            catalog.put(id, product);

            // Increment counter
            counter++;
        }

        // Save the path
        this.file = file;

        // Update length.
        length = counter;

        // Close the scanner
        input.close();
    }

    
    /**
     * Member Function -
     * Retrieves a <code>ProductSpecification</code> if the key is within the
     * catalog.
     * 
     * @param id - <code>ItemId</code> of the product being retrieved
     * @return a <code>ProductSpecification</code>
     * @throws NullPointerException if the code is not in the catalog
     */
    public ProductSpecification get(ItemId id) {
        if (!contains(id))
            throw new NullPointerException("Code does not exist");

        return catalog.get(id);
    }

    /**
     * Member Function -
     * Retrieves a copy of <code>ProductSpecification</code> if the key is within the
     * catalog.
     * 
     * @param id - <code>ItemId</code> of the product being retrieved
     * @return a <code>ProductSpecification</code>
     * @throws NullPointerException if the code is not in the catalog
     */
    public ProductSpecification getCopy(ItemId id) {
        if (!contains(id))
            throw new NullPointerException("Code does not exist");

        return new ProductSpecification(catalog.get(id));
    }

    /**
     * Member Function -
     * Getter for the length of the <code>ProductCatalog</code>.
     * 
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Member Function -
     * Checks if <code>ItemId</code> given is within <code>ProductCatalog</code>.
     * 
     * @param id - <code>ItemId</code> being searched for
     * @return if the <code>ItemId</code> is within the <code>ProductCatalog</code>
     */
    public boolean contains(ItemId id) {
        return catalog.containsKey(id);
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints out each </code>ProductSpecification</code> in
     * <code>ProductCatalog</code>
     */
    @Override
    public String toString() {

        // Make the StringBuilder
        StringBuilder strbldr = new StringBuilder();

        // Create header
        String str = String.format("%-13s%-20s%-10s", "item code", "item name", "item price");
        strbldr.append(str);
        strbldr.append("\n");

        // record each entry in the hashmap
        for (HashMap.Entry<ItemId, ProductSpecification> entry : catalog.entrySet()) {

            ProductSpecification item = entry.getValue();

            // record the entry in a specific format
            str = String.format("%-13s%-20s%-10.2f", item.getId(), item.getName(), item.getPrice());
            strbldr.append(str);
            strbldr.append("\n");
        }

        // Remove the last new line
        strbldr.delete(strbldr.length() - 3, strbldr.length());

        return strbldr.toString();
    }

     /**
     * Member Function -
     * Adds a new <code>ProductSpecification</code> into the
     * <code>ProductCatalog</code>.
     * 
     * @param item - <code>ProductSpecification</code> being added
     */
    public void add(ProductSpecification item) {
        catalog.put(item.getId(), item);
    }

    /**
     * Member Function -
     * Removes a <code>ProductSpecification</code> from the
     * <code>ProductCatalog</code>.
     * 
     * @param id - <code>ItemId</code> of the <code>ProductSpecification</code>
     *           being removed
     */
    public void remove(ItemId id) {
        catalog.remove(id);
    }

    /**
     * Member Function -
     * This function replaces a <code>ProductSpecification</code> at a given
     * <code>ItemId</code>.
     * 
     * @param id   - <code>ItemId</code> of the
     *             <code>ProductSpecification</code> to be replaced
     * @param item - <code>ProductSpecification</code> to replace with
     */
    public void replace(ItemId id, ProductSpecification item) {
        catalog.replace(id, item);
    }

    /**
     * Member Function -
     * This function overwrites the file that was used to make the original
     * <code>ProductCatalog</code> with any new values given to it.
     */
    public void updateFile() {
        java.io.PrintWriter output;

        // Catches if the file being saved to exist.
        try {
            output = new java.io.PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new NullPointerException("Cannot update file since the source of the file is not declared");
        }

        // Writing File
        for (HashMap.Entry<ItemId, ProductSpecification> entry : catalog.entrySet()) {
            ProductSpecification item = entry.getValue();
            output.printf("%s, %s, %.2f\n", item.getId().getId(), item.getName(), item.getPrice());
        }

        // Close the File
        output.close();
    }
}
