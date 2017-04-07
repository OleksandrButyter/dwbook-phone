package com.softserve.phonebook.service;

import java.util.List;

import com.softserve.phonebook.api.Contact;

public interface ContactService {
    List<Contact> getAllContacts();

    Contact getContactById(int id);

    void deleteContact(int id);

    void createContact(Contact contact);

    void updateContact(Contact contact);
}
