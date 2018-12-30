package edu.kit.usxim;

import edu.kit.informatik.Terminal;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


/**
 * The Main Class that parses user input and correctly stores data into the designated classes
 */
public class Main {
    private Map<String, AddressBook> addressBooks;

    /**
     * Construct the MainApplication
     */
    public Main() {
        addressBooks = new HashMap<String, AddressBook>();
    }

    /**
     * Add an addressbook
     * @param name the name of the newly added addressbook consisting of at least one lower-case character
     */
    public void addAddressBook(String name) {
        if(addressBooks.containsKey(name)) {
            Terminal.printError("an address book with that name already exists");
            return;
        }

        try {
            AddressBook a = new AddressBook(name);
            addressBooks.put(name, a);
            Terminal.printLine("OK");
        } catch (IllegalArgumentException e) {
            Terminal.printError("" + e.getMessage());
        }
    }

    /**
     * Remove an addressbook
     * @param name the name of the addressbook that should be deleted.
     */
    public void removeAddressBook(String name) {
        if(!addressBooks.containsKey(name)) {
            Terminal.printError("this address book does not exist");
            return;
        }

        addressBooks.remove(name);
        Terminal.printLine("OK");
    }

    /**
     * Add a contact to a addressbook
     * @param args the arguments in the following format: <nameOfAddressBook>,<nameOfContact>,<email>,<phone>
     */
    public void addContact(String args) {
        String[] parts = args.split(",");
        if(parts.length != 4) {
            /* This command needs exactly four parameters to work:
             * - the name of the addressbook the contact should be added to
             * - the name of the contact itself
             * - the email address
             * - a phone number
             * If any of these is missing, information will be incomplete and the program
             * will run into errors. Thus, we inform the user he should respecify his
             * request without us taking any further actions.
             */
            Terminal.printError("invalid number of arguments");
            return;
        }

        try {
            /* Parse the arguments into named variables for better readability */
            String nameOfAddressBook = parts[0];
            String nameOfContact = parts[1];
            String email = parts[2];
            String phoneNumber = parts[3];

            if (!addressBooks.containsKey(nameOfAddressBook)) {
                /* The user wants to add a contact to an address book that does not (yet) exist. */
                Terminal.printError("the address book '" + nameOfAddressBook + "' does not exist.");
                return;
            }
            Contact c = new Contact(nameOfContact, email, phoneNumber);

            addressBooks.get(nameOfAddressBook).addContact(c);
            Terminal.printLine("OK");
        } catch(IllegalArgumentException e) {
            /* If anything goes wrong, print the error message to the user */
            Terminal.printError("" + e.getMessage());
        }
    }

    /**
     * Remove a contact from the address books
     * @param args the arguments specified in the following format: <nameOfContact>,<id>
     */
    public void removeContact(String args) {
        String[] parts = args.split(",");
        if(parts.length != 2) {
            /* This command needs exactly two parameters to work:
             * - the name of the contact
             * - the id of the contact
             * If any of these are missing, this method cannot fulfill its purpose
             */
            Terminal.printError("invalid number of arguments");
            return;
        }

        String name = parts[0];
        int id = 0;
        try {
            id = Integer.parseInt(parts[1]);
        } catch(Exception e) {
            Terminal.printError("malformed contact ID");
            return;
        }

        for(Map.Entry<String, AddressBook> addressBook : addressBooks.entrySet()) {
            try {
                boolean removed = addressBook.getValue().removeContact(name, id);
                if(removed) {
                    Terminal.printLine("OK");
                    return;
                }
            } catch(IllegalArgumentException e) {
                Terminal.printError(e.getMessage());
                return;
            }
        }

        Terminal.printError("contact does not exist");
    }

    /**
     * Print a text representation of a specific address book
     * @param name the name of the address book to be printed
     */
    public void printAddressbook(String name) {
        if(!addressBooks.containsKey(name)) {
            Terminal.printError("this address book does not exist");
            return;
        }
        Terminal.printLine(addressBooks.get(name));
    }

    /**
     * Parse user-input and execute valid commands
     * @param input the user input string consisting of a lower-case command and a comma-seperated list of arguments that is distinguished from the command name by a single space.
     */
    public boolean parseInput(String input) {
        if(input == "quit")
            return false;

        String[] parts = input.split(" ");
        if(parts.length < 2) {
            Terminal.printError("invalid command format, try <command> <arg1>,<arg2>,...");
            return true;
        }
        String command = parts[0];
        String args = "";

        args = parts[1];

        switch(command) {
            case "add-addressbook":
                addAddressBook(args);
                break;
            case "remove-addressbook":
                removeAddressBook(args);
                break;
            case "add-contact":
                addContact(args);
                break;
            case "remove-contact":
                removeContact(args);
                break;
            case "print-addressbook":
                printAddressbook(args);
                break;
            default:
                Terminal.printError("unknown command: '" + command + "'");
        }

        return true;
    }

    /**
     * Main method
     * @param parts CLI arguments
     */
    public static void main(String[] parts) {
        Main m = new Main();

        while(m.parseInput(Terminal.readLine())) {}
    }
}
