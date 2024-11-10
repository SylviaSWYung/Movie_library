module movielibrary.ui {
    
  requires movielibrary.core;
  requires javafx.controls;
  requires javafx.fxml;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;

  opens movielibrary.ui to javafx.graphics, javafx.fxml;
}
