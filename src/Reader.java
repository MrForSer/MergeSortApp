import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Reader {

    static List readFiles(String[] inputFiles, boolean isInt) {
        List fileValues;
        if (isInt) {
            fileValues = new ArrayList<Integer>();
        } else {
            fileValues = new ArrayList<String>();
        }
        try {
            for (String inputFile : inputFiles) {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0) {
                        if (isInt) {
                            try {
                                fileValues.add(Integer.valueOf(line));
                            } catch (IllegalArgumentException error) {
                                System.out.println("В файле " + inputFile + " найдены значения несоответствующего типа (" + error + "), пропускаем");
                            }
                        } else {
                            fileValues.add(line);
                        }
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
        return fileValues;
    }
}
