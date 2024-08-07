package com.javaproject.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.javaproject.model.Utenti;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvException;

public class UserService {
    
    //loadUser

    public List<Utenti> loadutenti() {

        List<Utenti> utentiList = new ArrayList<>();
        try {
            // Ottieni il file CSV come risorsa
            InputStream inputStream = (InputStream) getClass().getClassLoader().getResourceAsStream("utenti.csv");
            if (inputStream == null) {
                throw new IOException("File degli utenti non trovato \n");
            }

            // Configura il CSVParser per usare il punto e virgola come delimitatore
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            // Configura il CSVReader per usare il CSVParser
            try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .withSkipLines(1) // Salta l'intestazione
                    .withCSVParser(parser) // Imposta il CSVParser
                    .build()) {

                List<String[]> records = csvReader.readAll();

                // Mappa i dati nella classe Utenti
                for (String[] record : records) {
                   
                    Utenti utenti = new Utenti(
                            Integer.parseInt(record[0]),
                            record[1],
                            record[2],
                            record[3],
                            record[4],
                            record[5]
                    );  
                    utentiList.add(utenti);
                }
            } catch (CsvException e) {
                e.printStackTrace();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return utentiList;
    }

    public boolean checkUser(int id){
        List<Utenti> utentiList = loadutenti();
        for(Utenti utente : utentiList){
            if(utente.getId() == id){
                return true;
            }
        }
        return false;
    }


    public void insertUtente(Utenti utente) {

        List<Utenti> utentiList = loadutenti();
        utentiList.add(utente);

        File file = new File(getClass().getClassLoader().getResource("utenti.csv").getFile());

        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file, StandardCharsets.UTF_8))
                .withSeparator(';')
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build()
        ) {
            
            
            
                String[] header = {"ID", "Nome", "Cognome", "Data di nascita", "Indirizzo", "Documento ID"};
                writer.writeNext(header);
            

            // Scrivi tutti gli utenti nel file CSV
            for (Utenti u : utentiList) {
                String[] record = {
                        String.valueOf(u.getId()),
                        u.getNome(),
                        u.getCognome(),
                        u.getDataDiNascita(),
                        u.getIndirizzo(),
                        u.getDocumentoId()
                };
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            


    }

    public boolean checkDocumentoId(String documentoId){
        List<Utenti> utentiList = loadutenti();
        for(Utenti utente : utentiList){
            if(utente.getDocumentoId().equals(documentoId)){
                return true;
            }
        }
        return false;
    }
}
