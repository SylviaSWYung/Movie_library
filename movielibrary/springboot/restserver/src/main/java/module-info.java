module movielibrary.springboot.restserver {
  requires com.fasterxml.jackson.databind;

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;

  requires movielibrary.core;

  opens movielibrary.springboot.restserver to spring.beans, spring.context, spring.web;
}
