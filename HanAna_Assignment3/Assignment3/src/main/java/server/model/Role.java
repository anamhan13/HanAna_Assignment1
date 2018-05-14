package server.model;

public enum Role {

    WRITER {public String toString() {return "writer";}},
    READER {public String toString() {return "reader";}}
}
