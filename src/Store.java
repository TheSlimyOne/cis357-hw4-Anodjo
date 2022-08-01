import java.util.InputMismatchException;
import java.util.Scanner;

public class Store {

	/**
	 * Member variable -
	 * The <code>ProductCatalog</code> of the whole store
	 */
	private ProductCatalog catalog;

	/**
	 * Constructor -
	 * Creates a <code>Store</code> with a name and an address.
	 * 
	 * @param name    - the name of the <code>store</code>
	 * @param address - the address of the <code>Store</code>
	 * @param input   - The users input <code>Scanner</code>
	 */
	public Store(String name, String address, Scanner input) {
		// Fill up the stock of the store with a file
		promptSelectFile(input);
	}

	/**
	 * Member Function -
	 * This function starts the shopping session allowing users to exprience the
	 * continous shopping process.
	 * 
	 * @param input - The users input <code>Scanner</code>
	 */
	public void createShoppingSession(Scanner input) {
		// Greet the user.
		System.out.println("Welcome to Anodjo POST system!\n");

		// Create a register
		final Register register = new Register(catalog);

		// While the user chooses to remain in shopping session
		while (register.promptStartShopping(input)) {
			register.startSale();
			register.selectProduct(input);
			register.makePayment(input);
			register.endSale(); // Can return the sale if needed for later
		}
		System.out.printf("The total sale for the day is%3s%7.2f\n", "$", register.getGrandTotal());
		promptUpdateStock(input);
		input.close();
	}

	/**
	 * Member function -
	 * Starts a session in where the user must select an apporiate response.
	 * In this session the user must select prompted input to populate the
	 * <code>ProductCatalog</code>.
	 * 
	 * @param input - The users input <code>Scanner</code>
	 */
	private void promptSelectFile(Scanner input) {

		// Try to open user file.
		while (catalog == null) {
			System.out.print("Input File: ");

			// Store user input
			String userInput = input.nextLine();

			try {
				// Ask the user for a valid file.
				catalog = new ProductCatalog("../input/" + userInput);
			} catch (java.io.FileNotFoundException e) {
				// Catches if the user submits a file that does not exist.
				System.out.printf("!!! File %s is not found\n", userInput);
			} catch (IllegalArgumentException e) {
				// Catches if the user submits a file that is not formatted correctly.
				System.out.printf("!!! File %s is not valid\n", userInput);
			}

		}
	}

	/**
	 * Member function -
	 * Starts a session in where the user must select an apporiate response.
	 * In this session the user must fill out the forms correctly to
	 * create a new <code>ProductSpecification</code> in the
	 * <code>ProductCatalog</code>.
	 * 
	 * @param input - The users input <code>Scanner</code>
	 */
	private void promptAddItem(Scanner input) {
		ItemId id;
		String name;
		float price;

		System.out.print("item code: ");
		try {
			id = new ItemId(input.nextLine());
			if (catalog.contains(id)) {
				System.out.println("!!! Code is not unique");
				return;
			}

		} catch (IllegalArgumentException e) {
			// Catches if the user inputs a invalid field.
			System.out.println("!!! Invalid code format");
			return;
		}

		// Name can be anything.
		System.out.print("item name: ");
		name = input.nextLine().toLowerCase();

		System.out.print("item price: ");

		try {
			price = input.nextFloat();
			if (price <= 0) {
				System.out.println("!!! Invalid price");
				return;
			}

		} catch (InputMismatchException e) {
			// Catches if the user inputs a invalid price.
			System.out.println("!!! Invalid price");
			return;

		} finally {
			// Clear line.
			input.nextLine();
		}

		catalog.add(new ProductSpecification(id, name, price));
		System.out.println("Item add successful!");
	}

	/**
	 * Member function -
	 * Starts a session in where the user must select an apporiate response.
	 * In this session the user must submit a valid <code>ItemId</code> to delete
	 * its associated <code>ProductSpecification</code> in the
	 * <code>ProductCatalog</code>.
	 * 
	 * @param input - The users input <code>Scanner</code>
	 */
	private void promptDeleteItem(Scanner input) {
		ItemId id;

		System.out.print("item code: ");

		try {
			id = new ItemId(input.nextLine());
			catalog.get(id);
			catalog.remove(id);

		} catch (IllegalArgumentException e) {
			// Catches if the user inputs a invalid field.
			System.out.println("!!! Invalid code format");
			return;

		} catch (NullPointerException e) {
			// Catches if the item exists or not.
			System.out.println("!!! Item at code does not exist");
			return;
		}

		
		System.out.println("Item delete successful!");

	}

	/**
	 * Member function -
	 * Starts a session in where the user must select an apporiate response.
	 * In this session the user must submit a valid <code>ItemId</code> to modify
	 * its associated <code>ProductSpecification</code> in the
	 * <code>ProductCatalog</code>.
	 * 
	 * @param input - The users input <code>Scanner</code>.
	 */
	private void promptModifyItem(Scanner input) {
		ItemId id;
		String name;
		float price;

		// Catches if the user inputs a invalid code.
		System.out.print("item code: ");
		try {
			id = new ItemId(input.nextLine());
		} catch (IllegalArgumentException e) {
			System.out.println("!!! Invalid code format");
			return;
		}

		// Catches if the item exists.
		try {
			catalog.get(id);
		} catch (NullPointerException e) {
			System.out.println("!!! Item at code does not exist");
			return;
		}

		// Name can be anything.
		System.out.print("item name: ");
		name = input.nextLine().toLowerCase();

		// Catches if the user inputs a invalid price.

		System.out.print("item price: ");
		try {
			price = input.nextFloat();
			if (price <= 0) {
				System.out.println("!!! Invalid price");
				return;
			}
		} catch (InputMismatchException e) {
			System.out.println("!!! Invalid price");
			return;
		} finally {
			// Clear line.
			input.nextLine();
		}

		catalog.replace(id, new ProductSpecification(id, name, price));
		System.out.println("Item modify successful!");
	}

	/**
	 * Member function -
	 * Starts a session in where the user must select an apporiate response.
	 * In this session the user must select prompted input to update the
	 * <code>ProductCatalog</code>.
	 * 
	 * @param input - The users input <code>Scanner</code>
	 */
	private void promptUpdateStock(Scanner input) {
		session: while (true) {
			// Prompt the user.
			System.out.print("\nDo you want to update the items data? (A/D/M/Q): ");

			switch (input.nextLine().toUpperCase()) {
				case "A":
					promptAddItem(input);
					break;

				case "D":
					promptDeleteItem(input);
					break;

				case "M":
					promptModifyItem(input);
					break;

				case "Q":
					System.out.println("\n" + catalog);
					catalog.updateFile();
					System.out.println("\nThanks for using POST system. Goodbye.");
					break session;

				default:
					System.out.println("!!! Invalid input");
					break;
			}
		}
	}
}
