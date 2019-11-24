package sawalha.console.util;

import java.io.Console;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ConsoleUtil {
    private static Console console = System.console();
    private static Scanner scanner = new Scanner(System.in);
    static {
        if (scanner == null) {
            // System.err.println("ERROR: Couldn't get CMD java.io.Console instance.");
            Sysout.printError("ERROR: Couldn't get CMD java.io.Console instance.");
            // System.err.println("ERROR: Couldn't get CMD java.io.Console instance.");
            System.exit(1);
        }
        if (console == null) {
            // System.err.println("ERROR: Couldn't get CMD java.util.Scanner instance.");
            Sysout.printError("ERROR: Couldn't get CMD java.util.Scanner instance.");
            // System.exit(1);
        }
    }

    public static String readPasswordOrNull(String prompt) {
        // System.out.print("> " + prompt);
        char[] outarr = console.readPassword("> " + prompt + ": ");
        String out = new String(outarr);

        while (StringUtils.contains(out, " ")) {
            System.out.println("ERROR: Password should not contains any spaces.");
            out = readPassword(prompt);
        }
        if (StringUtils.isBlank(out)) {
            System.out.println();
            return null;
        }

        if (StringUtils.equals(out, "q:") || StringUtils.equals(out, "+")) {
            System.err.println("\n\n\n*** Application Terminated by user. ***");
            System.exit(0);
        }
        return out;
    }

    public static String readPassword(String prompt) {
        System.out.print("> " + prompt + ": ");
        char[] outarr = console.readPassword();
        String out = new String(outarr);

        while (StringUtils.isBlank(out) || StringUtils.contains(out, " ")) {
            System.out.println("ERROR: Password should not be NULL and it should not contains any spaces.");
            out = readPassword(prompt);
        }
        if (StringUtils.equals(out, "q:") || StringUtils.equals(out, "+")) {
            System.err.println("\n\n\n*** Application Terminated by user. ***");
            System.exit(0);
        }
        return out;
    }

    public static String readLine(String _prompt) {
        String prompt = _prompt.trim();
        if (!StringUtils.endsWith(prompt, ":")) {
            prompt += ":";
        }
        System.out.print("> " + prompt + " ");
        String out = scanner.nextLine();
        if (StringUtils.equals(out, "q:") || StringUtils.equals(out, "+")) {
            System.err.println("\n\n\n<<< Application is terminated by the user >>>");
            System.exit(0);
        }
        return out;
    }

    public static String readLineNoNull(String prompt) {
        String out = readLine(prompt);
        while (StringUtils.isBlank(out)) {
            out = readLine(prompt);
        }
        return out;
    }

    public static String readLineOrDefault(String prompt, String defaultValue) {
        String out = readLine(prompt);
        if (StringUtils.isBlank(out)) {
            System.out.println("< Using default value: " + defaultValue);
            return defaultValue;
        } else {
            return out;
        }
    }

    public static String readLineNumberOrDefault(String prompt, String defaultValue) {
        String out = readLine(prompt);
        while (!StringUtils.isBlank(out) && !StringUtils.isNumeric(out)) {
            out = readLine(prompt);
        }
        if (StringUtils.isBlank(out)) {
            System.out.println("< Using default value: " + defaultValue);
            return defaultValue;
        }
        return out;
    }

    public static String readLineContainsAny(String prompt, String... any) {
        String out = readLine(prompt);
        while (!onlyContainsAny(out, any)) {
            out = readLine(prompt);
        }
        return out;
    }

    public static String readLineIPAddress(String prompt) {
        String out = readLineNoNull(prompt);
        while (!InputValidator.validateIP(out)) {
            out = readLineNoNull(prompt);
        }
        return out;
    }

    public static String readLineEmailAddress(String prompt) {
        String out = readLineNoNull(prompt);
        while (!InputValidator.isEmailValid(out)) {
            out = readLineNoNull(prompt);
        }
        return out;
    }

    public static String readLineNumber(String prompt) {
        String out = readLineNoNull(prompt);
        while (!StringUtils.isNumeric(out)) {
            out = readLineNoNull(prompt);
        }
        return out;
    }

    public static int readNumberBetween(String prompt, int start, int end) {
        String out = readLineNoNull(prompt);

        while (!StringUtils.isNumeric(out)) {
            out = readLineNoNull(prompt);
        }

        int outInt = -1;
        try {
            outInt = Integer.parseInt(out);
            while (outInt < start || outInt > end) {
                outInt = readNumberBetween(prompt, start, end);
            }
        } catch (NumberFormatException e) {
            outInt = readNumberBetween(prompt, start, end);
        }

        return outInt;
    }

    public static String readLineFile(String prompt) {
        String out = readLineNoNull(prompt);
        while (!FileUtils.getFile(out).exists()) {
            System.out.println("File does not exist.");
            out = readLineNoNull(prompt);
        }
        return out;
    }

    public static boolean readYesNo(String prompt) {
        String out = readLineContainsAny(prompt, "y", "n", "yes", "no", "YES", "NO", "Y", "N");
        if (out.toLowerCase().startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean readConfirmation() {
        return readYesNo("WARNING!!! Are you sure you want to continue? (yes/no): ");
    }

    public static int readMenuOption(String prompt, String... option) {
        Sysout.printBold(prompt);
        for (int i = 0; i < option.length; i++) {
            String string = option[i];
            Sysout.printBold("  " + (i + 1) + "- " + string);
        }
        int out = readNumberBetween("Enter your option", 1, option.length);
        return out;
    }

    ////
    public static boolean onlyContainsAny(CharSequence cs, CharSequence... searchCharSequences) {
        if (StringUtils.isEmpty(cs) || ArrayUtils.isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence searchCharSequence : searchCharSequences) {
            if (StringUtils.equalsIgnoreCase(cs, searchCharSequence)) {
                return true;
            }
        }
        return false;
    }
}
