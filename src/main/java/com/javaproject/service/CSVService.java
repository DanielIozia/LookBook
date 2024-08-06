package com.javaproject.service;
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
import com.opencsv.exceptions.CsvException;


public class CSVService {

    public List<Utenti> loadUtenti() {

        List<Utenti> utentiList = new ArrayList<>();
        try {
            // Ottieni il file CSV come risorsa
            InputStream inputStream = (InputStream) getClass().getClassLoader().getResourceAsStream("Utenti.csv");
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

                // Mappa i dati nella classe Utenti
                for (String[] record : records) {
                    Utenti utente = new Utenti(
                            Integer.parseInt(record[0]),
                            record[1],
                            record[2],
                            record[3],
                            record[4],
                            record[5]
                    );
                    utentiList.add(utente);
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

}




