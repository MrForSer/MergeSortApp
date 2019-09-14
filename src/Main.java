import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // опции командной строки
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

        // парсер командной строки
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        // todo: сделать нормальный хэллпер
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        // сохраняем полученные имена входящих файлов в массив;
        String[] inputFiles = cmd.getOptionValues("in");
        // в соответствии с типом файла создаем массив для хранения его содержимого
        List fileValues;
        if (cmd.hasOption("i")) {
            fileValues = new ArrayList<Integer>();
        } else {
            fileValues = new ArrayList<String>();
        }

        // сохраняем данные в массив
        try {
            for (String inputFile : inputFiles) {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0) {
                        if (cmd.hasOption("i")) {
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


        // дебаг
        System.out.println(fileValues);

        // сортируем в соответствии с указанным порядком сортировки
        List mergedList;
        if (cmd.hasOption("d")) mergedList = Sorter.mergeSortDesc(fileValues);
        else mergedList = Sorter.mergeSortAsc(fileValues);

        // дебаг
        System.out.println(mergedList);

        // записываем отсортированный массив в файл
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(cmd.getOptionValue("out")));
            for (Object o : mergedList) writer.write(o + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }
}