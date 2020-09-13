package pl.coderslab;

public class menuItem {
    int index;
    String option;

    public menuItem(int index,String option) {
        this.index = index;
        this.option = option;
    }

    public int getIndex() {
        return index;
    }

    public String getOption() {
        return option;
    }
}
