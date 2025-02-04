import java.util.ArrayList;

public class MobilePhone {
    private String myNumber;
    private ArrayList<Contact> myContacts;

    public MobilePhone(String myNumber){
        this.myNumber = myNumber;
        this.myContacts = new ArrayList<Contact>();
    }

    public boolean addNewContact(Contact contact){
        if(this.myContacts.contains(contact)){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateContact(Contact newContact, Contact oldContact){
        if(newContact.equals(oldContact)){
            return true;
        }else{
            return false;
        }
    }

    public boolean removeContact(Contact contact){
        if(!this.myContacts.contains(contact)){
            return true;
        }else{
            return false;
        }
    }

    public void printContacts(){
        System.out.println("Contact List:");
        for (int i = 0; i < this.myContacts.size(); i++) {
            System.out.println((i + 1) + ". " +
                    this.myContacts.get(i).getName() + " -> " +
                    this.myContacts.get(i).getPhoneNumber());
        }
    }


    private int findContact(Contact contact){
        return this.myContacts.indexOf(contact);
    };

    private int findContact(Contact contact, String param){
        return this.myContacts.indexOf(contact);
    };

    public Contact queryContact(String name){
        for (Contact contact: this.myContacts){
            if (contact.getName().equals(name)){
                return contact;
            }
        }
        return null;
    }
}
