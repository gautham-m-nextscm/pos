package com.shubham.increff.pojo;

import javax.persistence.*;

@Entity
public class ProductPojo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "brand_category")
    private int brand_category;
    @Column(name = "name")
    private String name;
    @Column(name = "mrp")
    private double mrp;


//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "brand_category")
//    private BrandCategoryPojo brandCategoryPojo;

    public int getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(int brand_category) {
        this.brand_category = brand_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }



}
