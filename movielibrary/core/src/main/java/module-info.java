module movielibrary.core {

  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.annotation;

  exports movielibrary.core;
  exports movielibrary.json.internal;

  opens movielibrary.core to com.fasterxml.jackson.databind;
}
