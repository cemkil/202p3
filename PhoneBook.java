package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import given.ContactInfo;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n))
 *
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 *
 * You need to use your own data structures to implement this
 *
 * Hint: You need to implement a multi-map!
 *
 */
public class PhoneBook {

  /*
   * ADD MORE FIELDS IF NEEDED
   *
   */
  ArrayList<ContactInfo> personList;
  BinarySearchTree<String, Person> nameTree;
  BinarySearchTree<String, Person> numberTree;
  ArrayList<Email> mailList;
  public PhoneBook() {
    nameTree = new BinarySearchTree<String, Person>();
    numberTree = new BinarySearchTree<String, Person>();
    mailList = new ArrayList<Email>();
    personList = new ArrayList<ContactInfo>();
    nameTree.setComparator(new Comparator<String>() {
      public int compare(String obj1, String obj2) {
        if (obj1 == obj2) {
          return 0;
        }
        if (obj1 == null) {
          return -1;
        }
        if (obj2 == null) {
          return 1;
        }
        return obj1.compareTo(obj2);
      }
    });
    numberTree.setComparator(new Comparator<String>() {
      public int compare(String obj1, String obj2) {
        if (obj1 == obj2) {
          return 0;
        }
        if (obj1 == null) {
          return -1;
        }
        if (obj2 == null) {
          return 1;
        }
        return obj1.compareTo(obj2);
      }
    });
  }

  // Returns the number of contacts in the phone book
  public int size() {
    return nameTree.size;
  }

  // Returns true if the phone book is empty, false otherwise
  public boolean isEmpty() {
    return nameTree.size==0;
  }

  //Adds a new contact or overwrites an existing contact with the given info
  // Args should be given in the order of e-mail and address which is handled for
  // you
  // Note that it can also be empty. If you do not want to update a field pass
  // null
  public void addContact(String name, String number, String... args) {
    int numArgs = args.length;
    String email = null;
    String address = null;

    /*
     * Add stuff here if needed
     */

    if (numArgs > 0)
      if (args[0] != null)
        email = args[0];
    if (numArgs > 1)
      if (args[1] != null)
        address = args[1];
    if (numArgs > 2)
      System.out.println("Ignoring extra arguments");

    /*
     * TO BE IMPLEMENTED
     *
     */

    Person addedPerson = new Person();

    Address address1 = new Address(address,addedPerson);
    addedPerson.setAddress(address1);

    Email email1 = new Email(email,addedPerson);
    addedPerson.setEmail(email1);

    Name name1 = new Name(name,addedPerson);
    addedPerson.setName(name1);

    Number number1 = new Number(number , addedPerson);
    addedPerson.setNumber(number1);

    ContactInfo addedContactInfo = new ContactInfo(name , number);
    addedContactInfo.setAddress(address);
    addedContactInfo.setEmail(email);
    addedPerson.setContactInfo(addedContactInfo);
    nameTree.put( name , addedPerson );
    numberTree.put(number , addedPerson);

    if(email!= null){
    mailList.add(email1);}
  }

  // Return the contact info with the given name
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByName(String name) {
    if(size() == 0){
      return null;
    }else{
      if(nameTree.getRoot().getKey().equals(name)){
        return nameTree.getRoot().getValue().getContactInfo();
      }
      BinaryTreeNode<String, Person> temp = nameTree.getRoot();
      while (nameTree.isInternal(temp)) {
        if (nameTree.getComparator().compare(name, temp.getKey()) > 0) {
          temp = temp.getRightChild();
        } else if (nameTree.getComparator().compare(name, temp.getKey()) < 0) {
          temp = temp.getLeftChild();
        }else{
          break;
        }
      }
      if(nameTree.isExternal(temp)){
        return null;
      }else{
        return temp.getValue().getContactInfo();
      }
    }

  }

  // Return the contact info with the given phone number
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByPhone(String phoneNumber) {
    if(size() == 0){
      return null;
    }else{
      if(numberTree.getRoot().getKey().equals(phoneNumber)){
        return numberTree.getRoot().getValue().getContactInfo();
      }
      BinaryTreeNode<String, Person> temp = numberTree.getRoot();
      while (numberTree.isInternal(temp) ) {
        if(temp.getKey().equals(phoneNumber)){
          break;
        }
        else if (numberTree.getComparator().compare(phoneNumber, temp.getKey()) > 0) {
          temp = temp.getRightChild();
        } else if (numberTree.getComparator().compare(phoneNumber, temp.getKey()) < 0) {
          temp = temp.getLeftChild();
        }
      }
      if(numberTree.isExternal(temp)){
        return null;
      }else{
        return temp.getValue().getContactInfo();
      }
    }
  }

  // Return the contact info with the given e-mail
  // Return null if it does not exists
  // Can be O(n)
  public ContactInfo searchByEmail(String email) {
    Iterator<Email> iterator = mailList.iterator();
    Email mail = null;
    while(iterator.hasNext()){
      mail=iterator.next();
      if(mail.email.equals(email)){
        return mail.person.getContactInfo();
      }
    }
    return null;

  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given name to delete, false otherwise
  public boolean removeByName(String name) {
    if (size() == 0 || name ==null) {
      return false;
    }else{
      Person removedPerson = nameTree.remove(name);
      if(removedPerson == null){
        return false;
      }else {
        if(removedPerson.getEmail().email != null){
          mailList.remove(removedPerson.email);
        }
        numberTree.remove(removedPerson.number.number);

        return true;
      }
    }
  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given number to delete, false otherwise
  public boolean removeByNumber(String phoneNumber) {
    if (size() == 0 || phoneNumber ==null) {
      return false;
    }else{
      Person removedPerson = numberTree.remove(phoneNumber);
      if(removedPerson == null){
        return false;
      }else {
        if(removedPerson.getEmail().email != null){
        mailList.remove(removedPerson.email);
        }
        nameTree.remove(removedPerson.name.name);

        return true;
      }
    }
  }

  // Returns the number associated with the name
  public String getNumber(String name) {
    if(size()==0 || name==null){
      return null;
    }else{
      ContactInfo info = searchByName(name);
      if(info == null){
        return null;
      }else{
        return info.getNumber();
      }
    }
  }

  // Returns the name associated with the number
  public String getName(String number) {
    if(size()==0 ){
      return null;
    }else{
      ContactInfo info = searchByPhone(number);
      if(info == null){
        return null;
      }else{
        return info.getName();
      }
    }
  }

  // Update the email of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateEmail(String name, String email) {
    if(size()==0 || name==null){
      return false;
    }else{
      Person person = nameTree.getValue(name);
      if(person == null){
        return false;
      }else{
        if(person.email.email != null){
        mailList.remove(person.email);}
        person.getEmail().email = email;
        person.getContactInfo().setEmail(email);
        mailList.add(person.email);

        return true;
      }
    }
  }

  // Update the address of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateAddress(String name, String address) {
    if(size()==0 || name==null){
      return false;
    }else{
      Person person = nameTree.getValue(name);
      if(person == null){
        return false;
      }else{

        person.address.Address = address;
        person.getContactInfo().setAddress(address);
        return true;
      }
    }
  }

  // Returns a list containing the contacts in sorted order by name
  public List<ContactInfo> getContacts() {
    personList.clear();
    allContacts(nameTree.getRoot());
    return personList;
  }
  public void allContacts(BinaryTreeNode< String , Person> v){
    if (nameTree.isInternal(v.getLeftChild())){
      allContacts(v.getLeftChild());
    }
    personList.add(v.getValue().contactInfo);
    if(nameTree.isInternal(v.getRightChild())){
      allContacts(v.getRightChild());
    }
  }

  // Prints the contacts in sorted order by name
  public void printContacts() {
    for(ContactInfo contact : getContacts()){
      System.out.println(contact.toString());
    }
  }
}

class Person{
  Email email;
  Name name;
  Number number;
  Address address;
  ContactInfo contactInfo;

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Number getNumber() {
    return number;
  }

  public void setNumber(Number number) {
    this.number = number;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public ContactInfo getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(ContactInfo contactInfo) {
    this.contactInfo = contactInfo;
  }
}
class Email{
  String email;
  Person person;
  public Email(String email, Person person) {
    this.email = email;
    this.person = person;
  }


}
class Name{
  String name;
  Person person;

  public Name(String name, Person person) {
    this.name = name;
    this.person = person;
  }
}
class Number{
  String number;
  Person person;

  public Number(String number, Person person) {
    this.number = number;
    this.person = person;
  }
}
class Address{
  String Address;
  Person person;

  public Address(String address, Person person) {
    Address = address;
    this.person = person;
  }
}
