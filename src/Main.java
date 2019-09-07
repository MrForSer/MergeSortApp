import com.sun.scenario.effect.Merge;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws Exception {

        Options options = new Options();

        Option ascended = new Option("a", "ascended", false, "порядок сортировки: по возрастанию");
        ascended.setArgs(0);
        ascended.setRequired(false);
        options.addOption(ascended);

        Option descended = new Option("d", "descended", false, "порядок сортировки: по убыванию");
        descended.setArgs(0);
        descended.setRequired(false);
        options.addOption(descended);

        Option intData = new Option("i", "integer", false, "тип данных: числа");
        intData.setArgs(0);
        intData.setRequired(false);
        options.addOption(intData);

        Option stringData = new Option("s", "string", false, "тип данных: строки");
        stringData.setArgs(0);
        stringData.setRequired(false);
        options.addOption(stringData);

        Option output = new Option("out", "output", true, "имя выходного файла");
        output.setArgs(1);
        output.setRequired(true);
        options.addOption(output);

        Option input = new Option("in", "input", true, "имена входных файлов");
        input.setArgs(Option.UNLIMITED_VALUES);
        input.setRequired(true);
        options.addOption(input);

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



        // Значения по умолчанию
        String sortOrder = "ascended";
        String dataType = "integer";

        // Обновляем значения в соответствии с данными из командной строки
        if (cmd.hasOption("d")){
            sortOrder = "descended";
        }
        if (cmd.hasOption("s")){
            dataType = "string";
        }

        /*
        String asc = cmd.getOptionValue("ascended"); -- ошибка, у этой опции нет значения
        String desc = cmd.getOptionValue("descended");
        String intd = cmd.getOptionValue("intData");
        String strd = cmd.getOptionValue("stringData");

        String inputFilePath = cmd.getOptionValue("input"); --ошибка, их может быть много
        String outputFilePath = cmd.getOptionValue("output");

        System.out.println(asc);
        System.out.println(desc);
        System.out.println(intd);
        System.out.println(strd);
        System.out.println(inputFilePath);
        System.out.println(outputFilePath);
        */

        // Для отладки

        //List test = Arrays.asList(cmd.getOptions());
        //System.out.println(test);

        List inFiles = Arrays.asList(cmd.getOptionValues("in"));
        System.out.println(inFiles);

        String outFile = cmd.getOptionValue("out");
        System.out.println(outFile);

        System.out.println(sortOrder);
        System.out.println(dataType);

        // пробуем сортировку слиянием
        //int[] myNum = {10, 20, 30, 40, 4, 8, 9, 151, 644, 46, 66, 44};

        //Работа с файлами
        try {
            File file = new File(outFile); //можно прокидывать наименование сразу сюда
            if(!file.exists())
                file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.println("Everything is working!");
            pw.println("Hello World!");
            pw.close();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}

//https://habr.com/ru/post/123360/
//https://stackoverflow.com/questions/367706/how-do-i-parse-command-line-arguments-in-java