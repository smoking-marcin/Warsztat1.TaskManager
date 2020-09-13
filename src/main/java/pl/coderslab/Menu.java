package pl.coderslab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu{
    List<menuItem> items =  new ArrayList<menuItem>();

    public Menu(String[] str) {
        for (int i = 0;i<str.length;i++) {
            items.add(new menuItem(i,str[i]));
        }
    }

    public List<menuItem> getItems() {
        return items;
    }

    public menuItem getItem(int index) {
        return items.get(index);
    }

    public int printMenu() {
        if (this.size() > 0) {
            System.out.println(ConsoleColors.BLUE + this.getItem(0).getOption());
            for (int i = 1; i < this.size(); i++) {
                System.out.print(ConsoleColors.RED + this.getItem(i).getIndex());
                System.out.println(ConsoleColors.WHITE + ") "+this.getItem(i).getOption());
            }
            return 1;
        }
        return 0;
    }

    public int size() {
        return this.items.size();
    }

}
