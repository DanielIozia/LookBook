package com.javaproject.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import com.javaproject.model.Vendite;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvException;

public class VenditeService {
    
    public List<Vendite> loadVendite() {

        List<Vendite> venditeList = new ArrayList<>();
        try {
            // Ottieni il file CSV come risorsa
            InputStream inputStream = (InputStream) getClass().getClassLoader().getResourceAsStream("vendite.csv");
            if (inputStream == null) {
                throw new IOException("File non trovato");
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

                // Mappa i dati nella classe vendite
                for (String[] record : records) {
                   
                    Vendite vendite = new Vendite(
                    Integer.parseInt(record[0]),
                    Integer.parseInt(record[1]),
                    Integer.parseInt(record[2])
                    );  
                    venditeList.add(vendite);
                }
            } catch (CsvException e) {
                e.printStackTrace();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return venditeList;
    }

     // Inserisce una nuova vendita nel file CSV
    public void insertVendite(int idCapo, int idUtente) {
        List<Vendite> venditeList = loadVendite();

        int newId = venditeList.stream()
                .mapToInt(Vendite::getId)
                .max()
                .orElse(0) + 1;

        Vendite newVendita = new Vendite(newId, idCapo, idUtente);
        venditeList.add(newVendita);

        File file = new File(getClass().getClassLoader().getResource("vendite.csv").getFile());

        try (
                CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file, StandardCharsets.UTF_8, true))
                        .withSeparator(';')
                        .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                        .build()
        ) {
            
            writer.writeNext(new String[]{String.valueOf(newVendita.getId()), String.valueOf(newVendita.getIdCapo()), String.valueOf(newVendita.getIdUtente())});
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkVendita(int id){
        List<Vendite> venditeList = loadVendite();
        for(Vendite vendita : venditeList){
            if(vendita.getId() == id){
                return true;
            }
        }
        return false;
    }

    public void deleteVendita(int idVendita){
        int idCapo = -1;
        List<Vendite> venditeList = loadVendite();
        for(Vendite vendita : venditeList){
            if(idVendita == vendita.getId()){
                idCapo = vendita.getIdCapo();
                venditeList.remove(venditeList.indexOf(vendita));
                break;
            }
        }

        //aggiorno il capo
        CapiService capiService = new CapiService();
        capiService.updateCapi(idCapo);
       
        File file = new File(getClass().getClassLoader().getResource("vendite.csv").getFile());
        try (
                CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file, StandardCharsets.UTF_8))
                        .withSeparator(';')
                        .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                        .build()
        ) {
            writer.writeNext(new String[]{"ID", "ID Capo", "ID Utente"});
            for(Vendite vendita : venditeList){
                writer.writeNext(new String[]{String.valueOf(vendita.getId()), String.valueOf(vendita.getIdCapo()), String.valueOf(vendita.getIdUtente())});
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
