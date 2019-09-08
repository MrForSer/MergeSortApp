import org.apache.commons.cli.*;
import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        // ============================= Опции командной строки ================================
        Options options = new Options();
        OptionGroup sortOrder = new OptionGroup();
        sortOrder.setRequired(true);
        OptionGroup dataType = new OptionGroup();
        dataType.setRequired(true);

        Option ascended = new Option("a", "ascended", false, "порядок сортировки: по возрастанию");
        ascended.setArgs(0);
        Option descended = new Option("d", "descended", false, "порядок сортировки: по убыванию");
        descended.setArgs(0);
        Option intData = new Option("i", "integer", false, "тип данных: числа");
        intData.setArgs(0);
        Option stringData = new Option("s", "string", false, "тип данных: строки");
        stringData.setArgs(0);
        Option outputOption = new Option("out", "output", true, "имя выходного файла");
        outputOption.setArgs(1);
        outputOption.setRequired(true);
        Option inputOption = new Option("in", "input", true, "имена входных файлов");
        inputOption.setArgs(Option.UNLIMITED_VALUES);
        inputOption.setRequired(true);

        sortOrder.addOption(ascended);
        sortOrder.addOption(descended);
        dataType.addOption(intData);
        dataType.addOption(stringData);

        options.addOption(ascended);
        options.addOption(descended);
        options.addOption(intData);
        options.addOption(stringData);
        options.addOption(outputOption);
        options.addOption(inputOption);

        // ============================= Парсер командной строки ================================

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        // Сохраняем тип данных в переменную
        String dt = "string";
        if (cmd.hasOption("i")){
            dt = "integer";
        }

        // ============================= Работа с файлами ======================================
        String[] inputFiles = cmd.getOptionValues("in");
        // создаем массив и заполняем содержимым файлов, затем сортируем его и записываем в файл
        // тут логика для массива типа int
        if (dt.equals("integer")) {
            List<Integer> fileValues = new ArrayList<>();
            try {
                String line;
                for (int i = 0; i < inputFiles.length; i++) {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFiles[i]));
                    while ((line = reader.readLine()) != null) {
                        Integer intValue = Integer.valueOf(line);
                        fileValues.add(intValue);
                    }
                    reader.close();
                }
                // сортировка
                System.out.println(fileValues);
                List<Integer> mergedList =  Sorter.mergeSort(fileValues);
                System.out.println(mergedList);

                BufferedWriter writer = new BufferedWriter(new FileWriter(cmd.getOptionValue("out")));
                Iterator<Integer> it = mergedList.iterator();
                while (it.hasNext()) writer.write(it.next() + "\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // тут логика для массива типа String
            List<String> fileValues = new ArrayList<>();
            try {
                String line;
                for (int i = 0; i < inputFiles.length; i++) {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFiles[i]));
                    while ((line = reader.readLine()) != null) {
                        String value = (line);
                        fileValues.add(value);
                        //if (cmd.hasOption("i")) {
                        //    Integer.parseInt(line);
                        //} else {
                        //    fileValues.add(line);
                        //}
                    }
                    reader.close();
                }
                // сортировка
                System.out.println(fileValues);
                List<String> mergedList =  Sorter.mergeSort(fileValues);
                System.out.println(mergedList);

                BufferedWriter writer = new BufferedWriter(new FileWriter(cmd.getOptionValue("out")));
                Iterator<String> it = mergedList.iterator();
                while (it.hasNext()) writer.write(it.next() + "\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}