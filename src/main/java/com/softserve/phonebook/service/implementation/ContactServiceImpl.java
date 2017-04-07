
package com.softserve.phonebook.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.phonebook.api.Contact;
import com.softserve.phonebook.dao.ContactDAO;
import com.softserve.phonebook.service.ContactService;

@Service("contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
    
    @Autowired
    private ContactDAO contactDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact getContactById(int id) {
        return contactDAO.getContactById(id);
    }

    @Override
    public void deleteContact(int id) {
        contactDAO.deleteContact(id);
    }

    @Override
    public void createContact(Contact contact) {
        contactDAO.createContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

}
