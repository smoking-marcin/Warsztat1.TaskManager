package pl.coderslab;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Date;

public class TaskManager {

    public static List<String> readFile(Path path) {
        List<String> tasks;
        try {
            tasks = Files.readAllLines(path);
            return tasks;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int writeFile(List<String> tasks,Path path) {
        try {
            Files.write(path,tasks, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int readInput(Scanner scan,Menu menu) {
        String input = scan.nextLine();
        if (input.length() == 1 && Integer.parseInt(input) < menu.size()) {
            return Integer.parseInt(input);
        } else {
            for (int i = 1; i< menu.size();i++) {
                if (menu.getItem(i).getOption().equals(input)) {
                    return i;
                };
            }
        }
        return -1;
    }

    public static int listTasks(List<String> tasks) {
        if (tasks.size() > 0) {
            for (int i = 0; i<tasks.size();i++) {
                System.out.println(i+") "+tasks.get(i));
            }
            return 1;
        } else {
            return -1;
        }
    }

    private static int getInt(Scanner scan, String prompt) {
        System.out.println(prompt);
        int input = 0;
        boolean isInt = false;
        while (!isInt) {
            try {
                input = scan.nextInt();
                isInt = true;
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Not an index!");
            }
        }
        return input;
    }

    public static int removeTasks(List<String> tasks,Scanner scan) {
        if (listTasks(tasks) != 1) {
            System.out.println("No tasks available. Feel free to add some.");
        } else {
            try {
                int index = getInt(scan, "Choose which task to remove:");
                tasks.remove(index);
                return 1;
            } catch (IndexOutOfBoundsException e) {
                e.getStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public static String getString(Scanner scan, String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();
        return input;
    }

    public static Date getDate(Scanner scan, String prompt,SimpleDateFormat format) {
        String str = getString(scan, prompt);
        Date date;
        if (str != null) {
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Check data format: yyyy-MM-dd");
                date = getDate(scan,prompt,format);
            }
        } else {
            date = getDate(scan,prompt,format);
        }
        if (!format.format(date).equals(str)) {
            System.out.println("No such day in the calendar.");
            date = getDate(scan,prompt,format);
        }
        return date;
    }

    public static boolean getImportance(Scanner scan, String prompt) {
        String str = getString(scan, prompt);
        boolean importance;
        if (str.equals("true") || str.equals("false")) {
            try {
                importance = Boolean.parseBoolean(str);
                return importance;
            } catch (NullPointerException e) {
                e.getStackTrace();
                importance = getImportance(scan, prompt);
            }
        } else {
            System.out.println("Wrong input.");
            importance = getImportance(scan, prompt);
        }
        return importance;
    }

    public static void addTasks(List<String> tasks,Scanner scan,SimpleDateFormat format) {
        String description = getString(scan, "Provide new taks description:");
        Date date = getDate(scan, "Provide date for the task: (yyyy-MM-dd)",format);
        String strDate = format.format(date);
        boolean importance = getImportance(scan, "Is the task important?: (true/false)");
        tasks.add(description+", "+strDate+", "+importance);
    }

    public static void main(String[] args) {
        String fileName = "tasks.csv";
        Path path = Paths.get(fileName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int decision;
        Scanner scan = new Scanner(System.in);
        List<String> tasks = readFile(path);
        String[] menuStrings = {"Please select an option", "add", "remove", "list", "quit"};
        Menu menu = new Menu(menuStrings);
        while (true) {
            menu.printMenu();
            decision = readInput(scan, menu);
            if (decision == 4) {
                writeFile(tasks,path);
                break;
            } else if (decision == 3) {
                if (listTasks(tasks) != 1) {
                    System.out.println("No tasks available. Feel free to add some.");
                }
            } else if (decision == 2) {
                if (removeTasks(tasks,scan) != 1) {
                    System.out.println("No tasks removed.");
                }
            } else if (decision == 1) {
                addTasks(tasks,scan,format);
            }
        }
        scan.close();
    }
}