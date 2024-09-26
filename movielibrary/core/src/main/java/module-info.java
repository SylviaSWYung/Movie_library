module movielibrary.core {

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports movielibrary.core;
    exports movielibrary.json.internal;

    opens movielibrary.core to com.fasterxml.jackson.databind;
}
