/*
 * Homework 4: Sales Register Program part 4
 * Course: CIS357
 * Due date: 8/2/2022
 * Name: Jordan Anodjo
 * GitHub: https://github.com/TheSlimyOne/cis357-hw4-Anodjo.git
 * Instructor: Il-Hyung Cho
 * ===============================================================================
 * Program description: 
 * 		In this program, a user will interact with an emulated cash register.
 * 		The user will be able to purchase items from a predetermined list.
 * 		The cash register will respond appropriately from the user. 
 * 		Always producing the correct results/response from the user.
 *      Then the user is allowed to add, delete, and modify items. 
 *      In this iteration of the program the program was refactored to follow OOP guidlines
 * ===============================================================================
 * Program features:
 * Change the item code type to String: Y | The code of an Item is a class called Code that uses a string.
 * Provide the input in CSV format. Ask the user to enter the input file name: Y | input.txt
 * 
 * Implement exception handling for:
 *      File input: Y
 *      Checking wrong input data type: Y
 *      Checking invalid data value: Y
 *      Tendered amount less than the total amount: Y
 * 
 * Use ArrayList for the items data: Y
 * Adding item data:    Y
 * Deleting item data:  Y
 * Modifying item data: Y
 * 
 * Implemented classes: 
 *      Driver class (HW4-lastName.java, or it could be Store.java) Y
 *      Register                                                    Y
 *      Sale                                                        Y
 *      SalesLineItem                                               Y
 *      ProductCatalog                                              Y
 *      ProductSpecification                                        Y
 */

import java.util.Scanner;

/**
 * Driver Class.
 * 
 * @author Jordan Anodjo
 */
public class HW4_Anodjo {
    /**
     * Start the shopping session.
     * Main dirver class.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final Store store = new Store("MartMart", "123 Street", input);
        store.createShoppingSession(input);
    }
}
