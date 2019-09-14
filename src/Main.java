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

        // todo: сделать нормальный хэллпер
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        // ============================= Работа с файлами ======================================
        String[] inputFiles = cmd.getOptionValues("in");
        // создаем массив и заполняем содержимым файлов, затем сортируем его и записываем в файл
        // тут логика для массива типа int
        List fileValues;

        if (cmd.hasOption("i")) fileValues = new ArrayList<Integer>();
        else fileValues = new ArrayList<String>();
            try {
                String line;
                for (String inputFile : inputFiles) {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    while ((line = reader.readLine()) != null)
                        if (cmd.hasOption("i")) fileValues.add(Integer.valueOf(line));
                        else fileValues.add(line);
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                // сортировка
                System.out.println(fileValues);

                List mergedList;
                if (cmd.hasOption("d")) mergedList = Sorter.mergeSortDesc(fileValues);
                else mergedList = Sorter.mergeSortAsc(fileValues);

                System.out.println(mergedList);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(cmd.getOptionValue("out")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object o : mergedList) {
            try {
                writer.write(o + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}