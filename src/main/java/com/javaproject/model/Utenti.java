package com.javaproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Utenti {

    //ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID
    
    private int id;
    private String nome;
    private String cognome;
    private String dataDiNascita;
    private String indirizzo;
    private String documentoId;

}
