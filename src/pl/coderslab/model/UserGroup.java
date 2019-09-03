package pl.coderslab.model;

public class Group {

    private int id;
    private String name;

    //pusty konstruktor - do pobierania z bazy
    public Group() {
    }

    //konstruktor z parametrami bez id - do tworzenia nowej grupy
    public Group(String name) {
        this.name = name;
    }

    //gettery i settery dla wszystkich pól
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
