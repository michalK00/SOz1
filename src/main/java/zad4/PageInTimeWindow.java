package zad4;

public class PageInTimeWindow {

    private int timestamp;
    private int pageNumber;

    public PageInTimeWindow(int timestamp, int pageNumber) {
        this.timestamp = timestamp;
        this.pageNumber = pageNumber;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
