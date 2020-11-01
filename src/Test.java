import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("Do you wanna write in database Y/N");
        String answer = bf.readLine();

        if (answer.equals("Y") || answer.equals("y")) {
            DataBase.test();
        }

        Result.showCount();
    }
}