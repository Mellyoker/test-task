import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***********************************************************
 * Эта программа требует некоторых уточнений, я ее делал с расчетом что файлы имеют минимальное
 * форматирование(присутствует разделение по строкам, а не весь файл записан в строку),
 * при этом размер самих файлов не известен
 */
public class main {
    public static void main(String[] args) {
        FileParser parser = new FileParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Укажите путь к папке(не забудьте использовать экранирование для управляющих символов)");


        try {
            parser.start(reader.readLine(), parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
