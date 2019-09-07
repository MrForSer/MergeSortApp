import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {


    public static <T> void main(String[] args) throws IOException {

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
        Option output = new Option("out", "output", true, "имя выходного файла");
        output.setArgs(1);
        output.setRequired(true);
        Option input = new Option("in", "input", true, "имена входных файлов");
        input.setArgs(Option.UNLIMITED_VALUES);
        input.setRequired(true);

        sortOrder.addOption(ascended);
        sortOrder.addOption(descended);
        dataType.addOption(intData);
        dataType.addOption(stringData);

        options.addOption(ascended);
        options.addOption(descended);
        options.addOption(intData);
        options.addOption(stringData);
        options.addOption(output);
        options.addOption(input);

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

        // Создаем переменные для хранения опций и присваиваем им значения по умолчанию
        //String sortOrder = "ascended";
        //String dataType = "integer";
        //List inFiles = Arrays.asList(cmd.getOptionValues("in"));
        //String[] inFiles = cmd.getOptionValue("in");
        String outFile = cmd.getOptionValue("out");

        // Обновляем значения в соответствии с данными из командной строки
        /*
        if (cmd.hasOption("d")){
            // todo: дописать
            //sortOrder = "descended";
        }
        if (cmd.hasOption("s")){
            //dataType = "string";
        }
        */
        // ============================= Вывод опций (убрать) =================================

        //System.out.println(inFiles);
        System.out.println(outFile);
        System.out.println(sortOrder);
        System.out.println(dataType);

        // ============================= Работа с файлами ======================================
        try {
            String[] inputFiles = cmd.getOptionValues("in");
            for (String str : inputFiles) {
                //System.out.println(str);

                // todo: читаем каждый файл и сравниваем полученные строки

                BufferedReader reader = new BufferedReader(new FileReader(cmd.getOptionValue("in")));

                // todo: тут должна быть логика для сортировки полученных массивов


                // todo: записываем полученный результат в итоговый файл
                String line;
                File result = new File(cmd.getOptionValue("out", "out.txt")); //можно прокидывать наименование сразу сюда
                if (!result.exists())
                    result.createNewFile();
                PrintWriter writer = new PrintWriter(result);
                while ((line = reader.readLine()) != null) {
                    // тут осуществляется запись в файл
                    writer.println(line);
                }
                writer.close();
                reader.close();
            }
            // todo: разобраться с try / catch / finally и расставить их корректно
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}