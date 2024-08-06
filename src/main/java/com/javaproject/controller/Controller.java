package com.javaproject.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.javaproject.model.Capi;
import com.javaproject.service.CapiService;
import com.javaproject.service.UserService;
import com.javaproject.service.VenditeService;

public class Controller {

    public Controller(){}
    private CapiService capiService = new CapiService();
    private UserService userService = new UserService();
    private VenditeService venditeService = new VenditeService();

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        do {
            displayMenu();
            
            try{
                choice = Integer.parseInt(reader.readLine());
            }
            catch(NumberFormatException e){
                System.out.println("Opzione non valida");
                choice = -1;
            }
            switch (choice) {
                case 1:
                    List<Capi> capi = capiService.loadCapi();

                    for (Capi capo : capi) {
                        System.out.println(capo.getId());
                        System.out.println(capo.getDataInserimento());
                        System.out.println(capo.getTipologia());
                        System.out.println(capo.getMarca());
                        System.out.println(capo.getTaglia());
                        System.out.println(capo.getPrezzo());
                        System.out.println(capo.isDisponibile() ? "SI" : "NO");
                    }

                    break;
                case 2:
                    
                    //prende l'input dell'utente e verifica che sia numerico
                    System.out.print("Inserisci l'id del capo da comprare: ");
                    int idCapo = -1;
                    try{
                        idCapo = Integer.parseInt(reader.readLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("L'id deve essere numerico");
                    }

                    //uguale per id utente

                    System.out.print("Inserisci l'id dell'utente: ");
                    int idUtente = -1;
                    try{
                        idUtente = Integer.parseInt(reader.readLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("L'id deve essere numerico");
                        break;
                    }

                    if(!capiService.checkCapi(idCapo)){
                        System.out.println("Il capo non esiste");
                        break;
                    }

                    if(!capiService.checkAvaiability(idCapo)){
                        System.out.println("Il capo non e' disponibile");
                        break;
                    }

                    if(!userService.checkUser(idUtente)){
                        System.out.println("L'utente non esiste");
                        break;
                    }

                    try{
                        venditeService.insertVendite(idCapo, idUtente);
                        capiService.updateCapi(idCapo);
                    }
                    catch(Exception e){
                        System.out.println("Errore durante l'inserimento della vendita");
                        break;
                    }

                    break;
                
                    case 3:
                    //check vendita inserendo l'id
                    System.out.print("Inserisci l'id della vendita da restituire: ");
                    int idVendita = -1;
                    try{
                        idVendita = Integer.parseInt(reader.readLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("L'id deve essere numerico");
                        break;
                    }
                    if(!venditeService.checkVendita(idVendita)){
                        System.out.println("La vendita non esiste");
                        break;
                    }
                    //se la vendita esiste mi elimini la vendita
                    venditeService.deleteVendita(idVendita);
                    break;
                /*case 4:
                    addUtente(reader);
                    break;
                case 5:
                    exportAvailableCapi();
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    break;*/
                default:
                    
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