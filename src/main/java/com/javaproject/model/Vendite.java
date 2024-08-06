package com.javaproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Vendite {
    

    private int id;
    private int idCapo;
    private int idUtente;
}
