package com.javaproject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javaproject.model.Capi;
import com.javaproject.model.Utenti;
import com.javaproject.service.CapiService;
import com.javaproject.service.UserService;
import com.javaproject.service.VenditeService;

public class Controller {

    private CapiService capiService = new CapiService();
    private UserService userService = new UserService();
    private VenditeService venditeService = new VenditeService();

    public Controller() {}

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        do {
            displayMenu();
            choice = getChoice(reader);

            switch (choice) {
                case 1:
                    displayCapi();
                    break;
                case 2:
                    processPurchase(reader);
                    break;
                case 3:
                    processReturn(reader);
                    break;
                case 4:
                    addUser(reader);
                    break;
                case 5:
                    capiService.exportAvailableCapi();
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    if((choice > 5 || choice < 0) && choice != -1){
                        System.out.println("Inserisci un numero compreso tra 0 e 5! \n");
                    }
                    break;
            
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

    private int getChoice(BufferedReader reader) {
        int choice = -1;
        try {
            try {
                choice = Integer.parseInt(reader.readLine());
                System.out.println();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        } 
        catch (NumberFormatException e) {
            System.out.println("Devi selezionare un numero! \n");
        }
        return choice;
    }

    private void displayCapi() {
        List<Capi> capi = capiService.loadCapi();
        for (Capi capo : capi) {
            System.out.println("ID: " + capo.getId());
            System.out.println("Data Inserimento: " + capo.getDataInserimento());
            System.out.println("Tipologia: " + capo.getTipologia());
            System.out.println("Marca: " + capo.getMarca());
            System.out.println("Taglia: " + capo.getTaglia());
            System.out.println("Prezzo: " + capo.getPrezzo());
            System.out.println("Disponibile: " + (capo.isDisponibile() ? "SI" : "NO"));
            System.out.println();
        }
    }

    private void processPurchase(BufferedReader reader) {
        int idCapo = getIdFromUser(reader, "Inserisci l'id del capo da comprare: ");

        //CONTROLLO DEL CAPO
        if (idCapo < 0) return;

        if (!capiService.checkCapi(idCapo)) {
            System.out.println("Il capo non esiste \n");
            return;
        }

        if (!capiService.checkAvaiability(idCapo)) {
            System.out.println("Il capo non è disponibile \n");
            return;
        }
        
        System.out.println();
        int idUtente = getIdFromUser(reader, "Inserisci l'id dell'utente: ");
        System.out.println();

        //CONTROLLO DELL'UTENTE
        if(idUtente < 0) return;    

        if (!userService.checkUser(idUtente)) {
            System.out.println("L'utente non esiste \n");
            return;
        }

        try {
            venditeService.insertVendite(idCapo, idUtente);
            capiService.updateCapi(idCapo);
            System.out.println("Acquisto effettuato con successo.\n");
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento della vendita \n");
        }
    }

    private void processReturn(BufferedReader reader) {
        int idVendita = getIdFromUser(reader, "Inserisci l'id della vendita da restituire: ");
        if (idVendita < 0) return;

        if (!venditeService.checkVendita(idVendita)) {
            System.out.println("La vendita non esiste \n");
            return;
        }

        venditeService.deleteVendita(idVendita);
        System.out.println("Vendita eliminata con successo. \n");
    }

    private void addUser(BufferedReader reader) {
        int id = getIdFromUser(reader, "Inserisci l'id: ");
        if (id < 0){
            System.out.println("L'id deve essere positivo \n");
            return;
        }

        if (userService.checkUser(id)) {
            System.out.println("Esiste già un utente con questo ID \n");
            return;
        }

        try {
            String name = "";
            do{
                System.out.print("Inserisci il nome: ");
                name = reader.readLine();
                if(name.length()==0){
                    System.out.println("Il nome non puè essere vuoto \n");
                }
            }while(name.length()==0);

            String surname = "";
            do{
                System.out.print("Inserisci il cognome: ");
                surname = reader.readLine();
                if(surname.length()==0){
                    System.out.println("Il cognome non puè essere vuoto \n");
                }
            }while(surname.length()==0);

            String date = "";
            Boolean flag = true;
            do{
                System.out.print("Inserisci la data di nascita (gg/mm/aaaa): ");
                date = reader.readLine();
                if(date.length()==0){
                    System.out.println("La data di nascita non puè essere vuota \n");
                }

                //verifica che sia nel formato dd/mm/yyyy con LocalDate
                try{
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    flag = false;
                }
                catch(Exception e){
                    System.out.println("La data di nascita non è nel formato dd/mm/yyyy \n");
                }

            }while(date.length()==0||flag);

            String address = "";
            do{
                System.out.print("Inserisci l'indirizzo: ");
                address = reader.readLine();
                if(address.length()==0){
                    System.out.println("L'indirizzo non puè essere vuoto \n");
                }
            }while(address.length()==0);

            String docId = "";
            String regex = "^[A-Z]{2} \\d{6} \\d$";
            Pattern pattern = Pattern.compile(regex);

            do{
                flag = false;
                System.out.print("Inserisci il documento ID: ");
                docId = reader.readLine();

                //docId deve rispettare la regex che deve avere 
                Matcher matcher = pattern.matcher(docId);

                if(userService.checkDocumentoId(docId)){
                    System.out.println("Esiste più un utente con questo documento ID \n");
                    flag = true;
                }

                else if (!matcher.matches()) {
                    System.out.println("Il formato deve essere XX 000000 0\n");
                    flag=true;
                } 
            } while(docId.length() == 0||flag);
            
            Utenti newUtente = new Utenti(id, name, surname, date, address, docId);
            userService.insertUtente(newUtente);
            System.out.println("Utente aggiunto con successo. \n");
        } catch (IOException e) {
            System.out.println("Errore durante l'aggiunta dell'utente. \n");
        }
    }

    private int getIdFromUser(BufferedReader reader, String prompt) {
        int id = -1;
        System.out.print(prompt);
        try {
            id = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println("L'id deve essere numerico \n");
        }
        return id;
    }
}
