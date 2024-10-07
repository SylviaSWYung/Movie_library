module movielibrary.ui {
    
  requires movielibrary.core;
  requires javafx.controls;
  requires javafx.fxml;

  opens movielibrary.ui to javafx.graphics, javafx.fxml;
}
