import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface reader {

    default String readString() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        str = bufferedReader.readLine();

        return str;
    }

}
