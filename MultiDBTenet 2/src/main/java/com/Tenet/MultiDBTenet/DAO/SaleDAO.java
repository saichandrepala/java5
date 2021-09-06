package com.Tenet.MultiDBTenet.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.Tenet.MultiDBTenet.model.Sale;

public class SaleDAO {

    private EntityManager em;

    public SaleDAO(EntityManager em) {
        this.em = em;
    }

    public List<Sale> getAllSale(String tenant_id) {
        Query theQuery = (Query) em.createQuery("from Sale");
        List<Sale> sales = theQuery.getResultList();

        return sales;
    }

    public Sale saveSale(Sale theSale, String tenant_id) {
        Sale dbSale = em.merge(theSale);
        theSale.setSalesId(dbSale.getSalesId());
        em.getTransaction().commit();
        return theSale;
    }

    public Sale findSalesById(int theId, String tenant_id) {
        Sale theSale = em.find(Sale.class, theId);
        return theSale;
    }

    public void deleteSalesById(int theId, String tenant_id) {
        Query theQuery = (Query)em.createQuery("delete from Sale where id=:salesId");
        theQuery.setParameter("salesId", theId);
        theQuery.executeUpdate();
        em.getTransaction().commit();
        
    }
    
}
