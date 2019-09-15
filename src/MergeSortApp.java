import org.apache.commons.cli.*;
import java.util.List;

public class MergeSortApp {

    public static void main(String[] args) {

        // опции командной строки
        Options options = new Options();
        OptionGroup sortOrder = new OptionGroup();
        sortOrder.setRequired(true);
        OptionGroup dataType = new OptionGroup();
        dataType.setRequired(true);

        Option ascended = new Option("a", "ascended", false, "sort order: ascended");
        ascended.setArgs(0);
        Option descended = new Option("d", "descended", false, "sort order: descended");
        descended.setArgs(0);
        Option intData = new Option("i", "integer", false, "data type: integer");
        intData.setArgs(0);
        Option stringData = new Option("s", "string", false, "data type: strings");
        stringData.setArgs(0);
        Option outputOption = new Option("out", "outputFile", true, "output file name");
        outputOption.setArgs(1);
        outputOption.setRequired(true);
        Option inputOption = new Option("in", "inputFiles", true, "input files names");
        inputOption.setArgs(Option.UNLIMITED_VALUES);
        inputOption.setRequired(true);
        Option printHelp = new Option("h", "help", true, "help");
        printHelp.setArgs(0);

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
        options.addOption(printHelp);

        // парсер командной строки
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("app_name [-a/-d] [-i/-s] [-out <path>] [-in <path> <path> ...]", options);
            System.exit(1);
        }

        // чтение файлов
        List fileValues = Reader.readFiles(cmd.getOptionValues("inputFiles"), cmd.hasOption("integer"));

        // сортировка
        List mergedList = Sorter.mergeSort(fileValues, cmd.hasOption("descended"));

        // запись результата сортировки в файл
        Writer.writeFile(mergedList, cmd.getOptionValue("outputFile", "out.txt"));
    }
}