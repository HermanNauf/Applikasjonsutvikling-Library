package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookData {

    private final StringProperty bookID;
    private final StringProperty bookName;
    private final StringProperty publisherName;
    private final StringProperty publishedDate;

    public String getBookID() {
        return bookID.get();
    }

    public StringProperty bookIDProperty() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public String getBookName() {
        return bookName.get();
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getPublisherName() {
        return publisherName.get();
    }

    public StringProperty publisherNameProperty() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName.set(publisherName);
    }

    public String getPublishedDate() {
        return publishedDate.get();
    }

    public StringProperty publishedDateProperty() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate.set(publishedDate);
    }

    public BookData(String bookID, String bookName, String publisherName, String publishedDate) {
        this.bookID = new SimpleStringProperty(bookID);
        this.bookName = new SimpleStringProperty(bookName);
        this.publisherName = new SimpleStringProperty(publisherName);
        this.publishedDate = new SimpleStringProperty(publishedDate);
    }
}
