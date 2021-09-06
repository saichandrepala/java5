package com.Tenet.MultiDBTenet.controller;

import java.util.List;

import javax.persistence.EntityManager;

import com.Tenet.MultiDBTenet.DAO.SaleDAO;
import com.Tenet.MultiDBTenet.config.DBConfig;
import com.Tenet.MultiDBTenet.model.Sale;

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
public class SaleController {

    DBConfig dbConfig = new DBConfig();

    @GetMapping("/sale")
    public ResponseEntity<List<Sale>> getSale(@RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        SaleDAO saleDAO = new SaleDAO(em);
        return new ResponseEntity<List<Sale>>(saleDAO.getAllSale(tenant_id), HttpStatus.OK);
    }

    @RequestMapping(value = "/sale", method = RequestMethod.POST)
    public Sale addSale(@RequestHeader (value = "tenant") String tenant_id, @RequestBody Sale theSale){
        EntityManager em = dbConfig.getEntity(tenant_id);

        SaleDAO saleDAO = new SaleDAO(em);
        return(saleDAO.saveSale(theSale, tenant_id));
    }

    @RequestMapping(value = "/sale", method = RequestMethod.PUT)
    public Sale updatSale(@RequestBody Sale theSale, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        SaleDAO saleDAO = new SaleDAO(em);
        Sale sale = saleDAO.findSalesById(theSale.getSalesId(), tenant_id);

        if(sale == null) {
            throw new RuntimeException("Sale to update doesn't exist");
        }

        return (saleDAO.saveSale(theSale, tenant_id));
    }

    @RequestMapping(value = "/sales/{salesId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable int salesId, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        SaleDAO saleDAO = new SaleDAO(em);

        Sale tempSales = saleDAO.findSalesById(salesId, tenant_id);

        if(tempSales == null){
            throw new RuntimeException("Sales Id not found");
        }
        saleDAO.deleteSalesById(salesId, tenant_id);

        return "Deleted sales id " + salesId;
    }
    
}
