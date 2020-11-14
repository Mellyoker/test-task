import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FileParser extends SimpleFileVisitor<Path> {

    private final List<String> filePath = new LinkedList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (file.toString().endsWith(".txt")) {
            filePath.add(file.toString());
        }

        return FileVisitResult.CONTINUE;
    }

    private void sort(){
        Collections.sort(filePath, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String [] tmpO1 = o1.split("\\\\");
                String [] tmpO2 = o2.split("\\\\");

                return (tmpO1[tmpO1.length - 1].compareToIgnoreCase(tmpO2[tmpO2.length - 1]));
            }
        });
    }

    private void RR() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))){

            for (String s : filePath){
                try(BufferedReader reader = new BufferedReader(new FileReader(s))){
                    String tmp;

                    while ((tmp = reader.readLine()) != null){

                        writer.write(tmp);

                    }
                    writer.write('\n');
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void validator (String path) throws FileNotFoundException {

        if ( !new File(path).exists()){
            throw new FileNotFoundException();
        }
    }

    public void start (String path, FileParser parser){

        try {

            validator(path);

            Files.walkFileTree(Paths.get(path), parser);

        } catch (FileNotFoundException e) {
            System.out.println("Указан не путь к несуществующей папке");
        } catch (IOException e) {
            e.printStackTrace();
        }

        sort();
        RR();
    }

}
