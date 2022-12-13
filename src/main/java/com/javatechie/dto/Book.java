package com.javatechie.dto;

import lombok.Data;

public record Book(int id,String name,String author,double price) {

    public int getId(){
        return this.id;
    }
}
