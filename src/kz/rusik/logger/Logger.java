package kz.rusik.logger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


//ЭТО ЗАДАТОК НА БУДУЩИЙ ЛОГГЕР ЕСЛИ НУЖЕН БУДЕТ
//ПОКА ЧТО НЕ ТРОГАЕм
public class Logger {

    private String pathToLog;
    private BufferedWriter writer;

    public Logger(String pathToLog) {
        this.pathToLog = pathToLog;
    }

    public void setWriter() throws IOException {
        try {
            writer = new BufferedWriter(new FileWriter(pathToLog, true));

        } catch (FileNotFoundException e) {
            File file = new File(LocalDate.now() + "_log.txt");
            if (file.createNewFile()) {
                System.out.println("Test");
            }
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) throws IOException {
        writer.append( "[" + LocalDate.now() + " " +
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] -> "
                + message + "\n");
    }
    public void closeWriter() throws IOException {
        writer.close();
    }
}
