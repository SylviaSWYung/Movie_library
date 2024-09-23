module movielibrary {
    requires javafx.controls;
    requires javafx.fxml;

    opens movielibrary to javafx.graphics, javafx.fxml;
}
