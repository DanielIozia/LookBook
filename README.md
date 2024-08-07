# LookBook - Sistema di Gestione di Capi di Abbigliamento

## Descrizione

Questo progetto Java consente di gestire un sistema di capi di abbigliamento usati, permettendo le seguenti operazioni:

1. Visualizzare tutti i capi di abbigliamento.
2. Comprare capi di abbigliamento inserendo l'ID del capo e l'ID dell'utente.
3. Restituire una vendita inserendo l'ID della vendita.
4. Aggiungere un nuovo utente inserendo ID, Nome, Cognome, Data di nascita, Indirizzo e ID Documento.
5. Creare un nuovo file CSV con i capi di abbigliamento disponibili.

## Struttura del Progetto

Il progetto è strutturato nei seguenti file principali:

- `Controller.java`: Gestisce le interazioni dell'utente e le operazioni principali.
- `UserService.java`: Gestisce le operazioni relative agli utenti.
- `VenditeService.java`: Gestisce le operazioni relative alle vendite.
- `CapiService.java`: Gestisce le operazioni relative ai capi di abbigliamento.

## Requisiti
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)


## Verifica le versioni:

- Per verificare la giusta versione di java eseguire il seguente comando:
`java -version`;
- Per verificare la giusta installazione e versione di Maven eseguire il seguente comando:
`mvn -v`

## Inizializzazione

### Clonare il Repository

Puoi clonare il repository eseguendo il seguente comando: `git clone https://github.com/DanielIozia/Java.git`

## Eseguire l'applicazione

1. Una volta clonato il progetto eseguire il seguente comando:
   `mvn clean package`
   Eseguendo questo comando verranno compilati tutti i file nella cartella "target" e verrà creato il file .jar
2. Lanciare l'applicazione
   Per lanciare l'applicazione attraverso il file .jar appena generato all'interno della cartella "target" eseguire il seguente comando:
   `java -jar target/LookBook.jar`

## Eseguire l'applicazione solo con il file `.JAR`

Per lanciare l'applicazione utilizzando solo il file .jar eseguire il comando `java -jar LookBook.jar`