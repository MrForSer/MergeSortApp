import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

class Writer {

    static void writeFile(List mergedList, String outputFile) {

        try {
            // получаем путь к папке с JAR-файлом
            URL url = Reader.class.getProtectionDomain().getCodeSource().getLocation();
            File myfile = null;
            try {
                myfile = new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File dir = myfile.getParentFile();
            // записываем данные в файл
            BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "\\" + outputFile));
            for (Object o : mergedList) writer.write(o + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
