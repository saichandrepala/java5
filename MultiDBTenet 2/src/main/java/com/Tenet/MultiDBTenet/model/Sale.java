package com.Tenet.MultiDBTenet.model;

import javax.persistence.*;

@Entity
@Table(name = "Sale")
public class Sale {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer sale_id;


    public Sale(){

    }

    public Sale(Integer sale_id){

        this.sale_id = sale_id;

    }

    public int getSalesId(){
        return sale_id;
    }

    public void setSalesId(int sale_id){
        this.sale_id = sale_id;
    }
    
}
