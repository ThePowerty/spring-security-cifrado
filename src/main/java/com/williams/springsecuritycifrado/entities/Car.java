package com.williams.springsecuritycifrado.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fabricante")
    private String manufacturer;
    private String model; // CONTAINS
    private Double cc;
    private Integer doors;

    private Integer year; // IN
    private LocalDate releaseDate; // BETWEEN
    private Boolean available; // True or False

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Double getCc() {
        return cc;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCc(Double cc) {
        this.cc = cc;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getYear() {
        return year;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Car [id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + ", cc=" + cc + ", doors="
                + doors + ", year=" + year + ", releaseDate=" + releaseDate + ", available=" + available + "]";
    }

}