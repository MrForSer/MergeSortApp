import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class Writer {

    static void writeFile(List mergedList, String outputFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (Object o : mergedList) writer.write(o + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }
}
