package locadora.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroData {

    public static String getDataAtual() {
    
        LocalDate dataAtual = LocalDate.now();

        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/DD/YYYY");


        return dataAtual.format(formato);
    }
}