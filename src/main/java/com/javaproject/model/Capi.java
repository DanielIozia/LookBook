package com.javaproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Capi {

    //ID;Data inserimento;Tipologia;Marca;Taglia;Prezzo;Disponibile
    private int id;
    private String dataInserimento;
    private String tipologia;
    private String marca;
    private String taglia;
    private double prezzo;
    private boolean disponibile;

    
}
