import org.apache.commons.cli.*;
import java.io.*;
import java.util.*;

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
        String[] inputFiles = cmd.getOptionValues("in");
        List<Integer> fileValues = new ArrayList<>();
        try {
            String line;
            for(int i = 0; i < inputFiles.length; i++) {
                BufferedReader reader = new BufferedReader(new FileReader(inputFiles[i]));
                while((line = reader.readLine()) != null) {
                    Integer uuid = Integer.valueOf(line);
                    fileValues.add(uuid);
                }
                reader.close();
            }

            // todo: логика сортировки

            BufferedWriter writer = new BufferedWriter(new FileWriter(cmd.getOptionValue("out")));
            Iterator<Integer> it = fileValues.iterator(); // todo: вместо fileValues тут должен быть результирующий массив
            while(it.hasNext()) writer.write(it.next() + "\n");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}