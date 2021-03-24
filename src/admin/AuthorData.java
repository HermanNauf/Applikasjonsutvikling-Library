package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthorData {

    private final StringProperty ID;
    private final StringProperty authorName;

    public AuthorData(String ID, String authorName) {
        this.ID = new SimpleStringProperty(ID);
        this.authorName = new SimpleStringProperty(authorName);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getAuthorName() {
        return authorName.get();
    }

    public StringProperty authorNameProperty() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName.set(authorName);
    }

}
