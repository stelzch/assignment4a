package edu.kit.usxim;

public class Contact {
    static int nextId = 1;
    private String name;
    private String nameOfContact;
    private String email;
    private String phoneNumber;
    private int id;

    /**
     * Construct a new contact
     * @param name The name of the contact
     * @param email The E-Mail address of this contact
     * @param phoneNumber The phone-number of this contact
     * @throws IllegalArgumentException
     */
    public Contact(String name, String email, String phoneNumber) throws IllegalArgumentException {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);

        id = nextId++;
    }

    /**
     * Give the contact a new name
     * @param name the name, made up of at least one lower-case character
     * @throws IllegalArgumentException
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.length() < 1) {
            throw new IllegalArgumentException("name must be at least one character long");
        }
        if (!name.matches("[a-z]+")) {
            throw new IllegalArgumentException("name must only consist of lower-case letters");
        }

        this.name = name;
    }

    /**
     * Retrieve the contacts name
     * @return the name of the contact
     */
    public String getName() {
        return name;
    }

    /**
     * Give the contact a new e-mail address
     * @param email the email address
     * @throws IllegalArgumentException
     */
    public void setEmail(String email) throws IllegalArgumentException {
        String[] parts = email.split("@");

        if(parts.length != 2) {
            throw new IllegalArgumentException("malformed email address");
        }

        if(!parts[0].equals(name)) {
            throw new IllegalArgumentException("email username must equal contact name");
        }

        if(!parts[1].matches("[a-z]+\\.[a-z]{2,4}")) {
            throw new IllegalArgumentException("email domain part is invalid");
        }

        this.email = email;
    }

    /**
     * @return the e-mail address of the contact
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Give the contact a new phone-number
     * @param phoneNumber the new phone number
     * @throws IllegalArgumentException
     */
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if(!phoneNumber.matches("\\d{1,4}\\d{1,4}\\d{1,7}")) {
            throw new IllegalArgumentException("invalid phone number");
        }

        this.phoneNumber = phoneNumber;
    }

    /**
     * @return Get the contacts phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Get the unique identifier of this contact
     * @return the ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the string representation of this contact in the following format
     * ID,name,email,phone
     * where ID is the 4-digit identifier.
     * @return the string representation of the contact
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%04d", getId()));
        sb.append(",");
        sb.append(getName());
        sb.append(",");
        sb.append(getEmail());
        sb.append(",");
        sb.append(getPhoneNumber());

        return sb.toString();
    }

}
