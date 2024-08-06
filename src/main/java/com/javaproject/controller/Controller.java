package com.javaproject.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.javaproject.model.Utenti;
import com.javaproject.service.CSVService;

public class Controller {

    public Controller(){}
    private CSVService csvService = new CSVService();

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        do {
            displayMenu();
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    List<Utenti> utenti = csvService.loadUtenti();

                    for (Utenti utente : utenti) {
                        System.out.println(utente.getId());
                        System.out.println(utente.getNome());
                        System.out.println(utente.getCognome());
                        System.out.println(utente.getDataDiNascita());
                        System.out.println(utente.getIndirizzo());
                        System.out.println(utente.getDocumentoId());
                    }

                    break;
                /*case 2:
                    buyCapo(reader);
                    break;
                case 3:
                    returnCapo(reader);
                    break;
                case 4:
                    addUtente(reader);
                    break;
                case 5:
                    exportAvailableCapi();
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    break;*/
                default:
                    System.out.println("Comando non valido.");
            }
        } while (choice != 0);
    }

    private void displayMenu() {
        System.out.println("1 -> Visualizzare tutti i capi second hand dell'interno del sistema");
        System.out.println("2 -> Comprare un capo esistente");
        System.out.println("3 -> Restituire un capo");
        System.out.println("4 -> Aggiungere un nuovo utente");
        System.out.println("5 -> Esportare un file con i capi disponibili");
        System.out.println("0 -> Uscire dal programma");
        System.out.print("Scegli un'opzione: ");
    }

    


       
        


   


}