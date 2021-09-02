package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class Data {

    // Função para ler um arquivo JSON
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Função para ler um arquivo CSV
    //public Collection<String[]> lerCSV(String caminhoCsv) throws IOException {
    public List<String[]> lerCSV(String caminhoCsv) throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get(caminhoCsv)); //lê um texto
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); // abre como um csv
        List<String[]> users = csvReader.readAll(); // lê todos os dados do csv
        return users;
    }

}
