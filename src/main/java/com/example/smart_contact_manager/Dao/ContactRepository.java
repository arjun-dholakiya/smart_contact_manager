package com.example.smart_contact_manager.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smart_contact_manager.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    //Pagination...



    @Query("from Contact as c where c.user.id =:userId")
    public Page<Contact> findContactsByUser(@Param("userId")int userId,Pageable pageable);
}
