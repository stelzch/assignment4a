package edu.kit.usxim;

import java.util.HashMap;
import java.util.Map;

/** This class represents an address book
 */
public class AddressBook {

    Map<Integer, Contact> contacts;
    String name;

    /**
     * Construct a new address book
     * @param name the name for the addressbook, consisting of at least one lower-case character
     * @throws IllegalArgumentException if the name does not comply with the specified format
     */
    public AddressBook(String name) throws IllegalArgumentException {
        if(name.length() < 1) {
            throw new IllegalArgumentException("name of addressbook must be at least one character long");
        }
        if(!name.matches("[a-z]{1,}")) {
            throw new IllegalArgumentException("name of addressbook must only consist of lower-case letters");
        }
        contacts = new HashMap<Integer, Contact>();
    }

    /**
     * Add a contact to the address book
     * @param contact the contact that should be added
     */
    public void addContact(Contact contact) {
        contacts.put(contact.getId(), contact);
    }

    /**
     * Try to remove a contact from this addressbook
     * @param name the name of the contact to be deleted
     * @param id the id of the contact to be deleted
     * @throws IllegalArgumentException if the id and name do not match
     * @return true if the contact was removed, false if it is not in this address book
     */
    public boolean removeContact(String name, int id) throws IllegalArgumentException {
        if(!contacts.containsKey(id)) {
            return false;
        }
        Contact c = contacts.get(id);

        if(!c.getName().equals(name)) {
            throw new IllegalArgumentException("name and ID do not match any contact");
        }
        contacts.remove(c.getId());
        System.out.println("Contact was removed");
        return true;
    }

    /**
     * Get a string representation of the addressbook
     * @return a new-line seperated list of string representations of all the contacts inside this addressbook
     */
    public String toString() {
        String res = new String();
        for(Map.Entry<Integer, Contact> entry : contacts.entrySet()) {
            res += entry.getValue().toString() + "\n";
        }

        return res.strip(); // Strip it to remove that last newline that was added unneccessarily
    }
}
