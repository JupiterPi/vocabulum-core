package jupiterpi.vocabulum.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface {
    protected void printLines(String... lines) {
        for (String line : lines) {
            out(line);
        }
    }

    protected void out(String message) {
        System.out.println(message);
    }

    protected void print(String message) {
        System.out.print(message);
    }

    protected String in(String message) {
        this.print(message);
        return this.in();
    }

    protected String in() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}