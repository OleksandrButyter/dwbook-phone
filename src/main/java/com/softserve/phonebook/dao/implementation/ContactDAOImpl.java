
package com.softserve.phonebook.dao.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softserve.phonebook.api.Contact;
import com.softserve.phonebook.dao.ContactDAO;
import com.softserve.phonebook.dao.mappers.ContactMapper;

@Repository
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Contact> getAllContacts() {
        return jdbc.query("SELECT * FROM contact", new ContactMapper());
    } 
    
    public Contact getContactById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject("SELECT * FROM contact WHERE id = :id", params, new ContactMapper());
    }
    
    public void deleteContact(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbc.update("DELETE FROM contact WHERE id = :id", params);
    }

    public void createContact(Contact contact) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(contact);
        jdbc.update("INSERT INTO contact (firstname, lastname, phone) VALUES (:firstName, :lastName, :phone)", params);
    }

    public void updateContact(Contact contact) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(contact);
        jdbc.update("UPDATE contact SET firstname = :firstName, lastname = :lastName, phone = :phone WHERE id = :id",
                params);
    }

}
