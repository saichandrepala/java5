package com.Tenet.MultiDBTenet.controller;

import java.util.List;

import javax.persistence.EntityManager;

import com.Tenet.MultiDBTenet.DAO.BookDAO;
import com.Tenet.MultiDBTenet.config.DBConfig;
import com.Tenet.MultiDBTenet.model.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    DBConfig dbConfig = new DBConfig();

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getBook(@RequestHeader (value = "tenant") String tenant_id){
        
        EntityManager em = dbConfig.getEntity(tenant_id);

        BookDAO bookDAO = new BookDAO(em);
        
        return new ResponseEntity<List<Book>>(bookDAO.getAllBooks(tenant_id), HttpStatus.OK);
        
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book addBook(@RequestBody Book theBook ,@RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);

        BookDAO bookDAO = new BookDAO(em);
        return(bookDAO.saveBook(theBook, tenant_id));
    }

    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public Book updateBook(@RequestBody Book theBook, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        BookDAO bookDAO = new BookDAO(em);

        Book book = bookDAO.findBookById(theBook.getBook_id(), tenant_id);

        if(book == null) {
            throw new RuntimeException("Book to update doesn't exist");
        }

        return (bookDAO.saveBook(theBook, tenant_id));
    }

    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.DELETE)
    public String deleteBook(@PathVariable int bookId, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        BookDAO bookDAO = new BookDAO(em);

        Book tempBook = bookDAO.findBookById(bookId, tenant_id);

        if(tempBook == null){
            throw new RuntimeException("Book Id not found");
        }
        bookDAO.deleteBookById(bookId, tenant_id);

        return "Deleted book id " + bookId;
    }

}
