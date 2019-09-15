import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Reader {

    static List readFiles(String[] inputFiles, boolean isInt) {



        List fileValues;
        if (isInt) {
            fileValues = new ArrayList<Integer>();
        } else {
            fileValues = new ArrayList<String>();
        }
        try {
            for (String inputFile : inputFiles) {

                // получаем путь к папке с jar'ом (чтобы не прописывать полный путь для каждого файла)
                URL url = Reader.class.getProtectionDomain().getCodeSource().getLocation();
                File myfile = null;
                try {
                    myfile = new File(url.toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                File dir = myfile.getParentFile();
                // считываем сведения из файла в соответствии с указанным типом
                BufferedReader reader = new BufferedReader(new FileReader(dir + "\\" + inputFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0) {
                        if (isInt) {
                            try {
                                fileValues.add(Integer.valueOf(line));
                            } catch (IllegalArgumentException error) {
                                System.out.println("File " + inputFile + " has incorrect data (" + error + "), skip");
                            }
                        } else {
                            fileValues.add(line);
                        }
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return fileValues;
    }
}