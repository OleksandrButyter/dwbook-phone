
package com.softserve.phonebook.dao;

import java.util.List;

import com.softserve.phonebook.api.Contact;

public interface ContactDAO {

    List<Contact> getAllContacts();

    Contact getContactById(int id);

    void deleteContact(int id);

    void createContact(Contact contact);

    void updateContact(Contact contact);
}
