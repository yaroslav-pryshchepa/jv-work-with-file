package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int AMOUNT_INDEX = 1;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void getStatistic(String fromFileName, String toFileName) {
        String[] infoFromFile = readFromFile(fromFileName);
        writeToFile(toFileName, createReport(infoFromFile));
    }

    private String[] readFromFile(String fromFileName) {
        File file = new File(fromFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(LINE_SEPARATOR);
                value = reader.readLine();
            }
            return builder.toString().split(LINE_SEPARATOR);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from " + fromFileName, e);
        }
    }

    private String createReport(String[] infoFromFile) {
        int supply = 0;
        int buy = 0;
        for (String infoFromLine : infoFromFile) {
            String[] information = infoFromLine.split(",");
            int amount = Integer.parseInt(information[AMOUNT_INDEX]);
            if (information[OPERATION_TYPE_INDEX].equals("supply")) {
                supply += amount;
            } else {
                buy += amount;
            }
        }
        return new StringBuilder()
                .append("supply,").append(supply).append(LINE_SEPARATOR)
                .append("buy,").append(buy).append(LINE_SEPARATOR)
                .append("result,").append(supply - buy).toString();
    }

    private void writeToFile(String toFileName, String report) {
        File file = new File(toFileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to " + toFileName, e);
        }
    }
}
