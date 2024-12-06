package Day5;

import java.util.ArrayList;

/*
    Helper class designed to simplify the organization of invalid page sequences, but it might have just
    made things worse. Not sure.
 */
public class PageConditions {
    private final int pageNumber;
    private final ArrayList<Integer> mustComeBefore;

    public PageConditions(int pageNumber) {
        this.pageNumber = pageNumber;
        mustComeBefore = new ArrayList<>();
    }

    public ArrayList<Integer> mustComeBefore() {
        return mustComeBefore;
    }

    public void addNewCondition(int page) {
        mustComeBefore.add(page);
    }

    @Override
    public String toString() {
        String pagesAfterStr = "";
        for (int page : mustComeBefore) {
            pagesAfterStr += page + " ";
        }
        return "Page Number: " + pageNumber + "\nRequired Before: " + pagesAfterStr;
    }
}
