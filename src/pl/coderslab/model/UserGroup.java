package pl.coderslab.model;

public class UserGroup {

    private int id;
    private String name;

    //pusty konstruktor - do pobierania z bazy
    public UserGroup() {
    }

    //konstruktor z parametrami bez id - do tworzenia nowej grupy
    public UserGroup(String name) {
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
