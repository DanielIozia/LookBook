package com.javaproject.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.javaproject.model.Capi;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvException;

public class CapiService {
    
     public List<Capi> loadCapi() {

        List<Capi> capiList = new ArrayList<>();
        try {
            // Ottieni il file CSV come risorsa
            InputStream inputStream = (InputStream) getClass().getClassLoader().getResourceAsStream("capi.csv");
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
                   
                    Capi capi = new Capi(
                            Integer.parseInt(record[0]),
                            record[1],
                            record[2],
                            record[3],
                            record[4],
                            Double.parseDouble(record[5].split(" ")[0]),
                            Boolean.parseBoolean(record[6].equals("SI")?"true":"false")
                    );  
                    capiList.add(capi);
                }
            } catch (CsvException e) {
                e.printStackTrace();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return capiList;
    }

    public boolean checkCapi(int id){
        List<Capi> capiList = loadCapi();
        for(Capi capo : capiList){
            if(capo.getId() == id){
                return true;
            }
        }
        return false;
    }

    public boolean checkAvaiability(int id){
        List<Capi> capiList = loadCapi();
        for(Capi capo : capiList){
            if(capo.getId() == id && capo.isDisponibile()){
                return true;
            }
        }
        return false;
    }

    public void updateCapi(int id){
        List<Capi> capiList = loadCapi();

        try {
                // Ottieni il file CSV come risorsa
                File file = new File(getClass().getClassLoader().getResource("capi.csv").getFile());

                if (!file.exists()) {
                    throw new IOException("File dei capi non trovato \n");
                }

                // Configura il CSVWriter per usare il punto e virgola come delimitatore
                CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file, StandardCharsets.UTF_8))
                        .withSeparator(';')
                        .build();

                // Scrivi l'intestazione
                writer.writeNext(new String[]{"ID", "Data inserimento", "Tipologia", "Marca", "Taglia", "Prezzo", "Disponibile"});

                // Scrivi i dati aggiornati
                for (Capi capo : capiList) {
                    boolean disp = capo.isDisponibile();
                    if(capo.getId() == id){
                        disp = !capo.isDisponibile();
                    }
                    String[] record = new String[]{
                        Integer.toString(capo.getId()),
                        capo.getDataInserimento(),
                        capo.getTipologia(),
                        capo.getMarca(),
                        capo.getTaglia(),
                        Double.toString(capo.getPrezzo()) + " €",
                        disp ? "SI" : "NO"
                    };
                    writer.writeNext(record);
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void exportAvailableCapi() {

        List<Capi> capiList = loadCapi();

        // Filtra solo i capi disponibili
        List<Capi> availableCapiList = capiList.stream()
                .filter(Capi::isDisponibile)
                .collect(Collectors.toList());

        // Percorso del nuovo file CSV
        String filePath = "src/main/resources/capiDisponibili.csv";
        File file = new File(filePath);

        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file, StandardCharsets.UTF_8))
                .withSeparator(';')
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build()
        ) {
            // Scrivi l'intestazione
            writer.writeNext(new String[]{"ID", "Data inserimento", "Tipologia", "Marca", "Taglia", "Prezzo", "Disponibile"});

            // Scrivi i capi disponibili
            for (Capi capo : availableCapiList) {
                String[] record = new String[]{
                    Integer.toString(capo.getId()),
                    capo.getDataInserimento(),
                    capo.getTipologia(),
                    capo.getMarca(),
                    capo.getTaglia(),
                    Double.toString(capo.getPrezzo()) + " €",
                    capo.isDisponibile() ? "SI" : "NO"
                };
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
