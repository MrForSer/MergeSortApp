import org.apache.commons.cli.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // todo: подумать над тем, чтобы убрать опции
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

        List fileValues = Reader.readFiles(cmd.getOptionValues("in"), cmd.hasOption("i"));

        List mergedList;
        if (cmd.hasOption("d")) {
            mergedList = Sorter.mergeSortDesc(fileValues);
        } else {
            mergedList = Sorter.mergeSortAsc(fileValues);
        }

        Writer.writeFile(mergedList, cmd.getOptionValue("out"));
    }
}