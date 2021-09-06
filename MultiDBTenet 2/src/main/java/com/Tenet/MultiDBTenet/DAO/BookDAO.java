package com.Tenet.MultiDBTenet.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.Tenet.MultiDBTenet.model.Book;

public class BookDAO {
    
    private EntityManager em;

    public BookDAO(EntityManager em) {
        this.em = em;
    }

    public List<Book> getAllBooks(String tenant_id) {
        Query theQuery = (Query) em.createQuery("from Book");
        List<Book> books = theQuery.getResultList();

        return books;
    }

    public Book saveBook(Book theBook, String tenant_id) {
        Book dbBook = em.merge(theBook);
        theBook.setBook_id(dbBook.getBook_id());
        em.getTransaction().commit();
        return theBook;
    }

    public Book findBookById(int theId, String tenant_id) {
        Book theBook = em.find(Book.class, theId);
        return theBook;
    }

    public void deleteBookById(int theId, String tenant_id) {
        Query theQuery = (Query) em.createQuery("delete from Book where id=:bookId");
        theQuery.setParameter("bookId", theId);
        theQuery.executeUpdate();
        em.getTransaction().commit();
        
    }

}
