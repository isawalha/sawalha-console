package sawalha.console.util;

public class Sysout {

    public static void printFinish() {
        System.out.println(ConsoleColors.YELLOW + "===========================================" + ConsoleColors.RESET);
    }

    public static void printRestartServer() {
        Sysout.printRedBold("\nYou must restart the workflow server for changes take effect.");
    }

    public static void printBold(String string) {
        System.out.println(ConsoleColors.WHITE_BOLD + string + ConsoleColors.RESET);
    }

    public static void printRedBold(String string) {
        System.out.println(ConsoleColors.RED_BOLD + string + ConsoleColors.RESET);
    }

    public static void printError(String string) {
        System.out.println(ConsoleColors.RED_BOLD + string + ConsoleColors.RESET);
    }

    public static void printWarning(String string) {
        System.out.println(ConsoleColors.YELLOW_BOLD + string + ConsoleColors.RESET);
    }

}
