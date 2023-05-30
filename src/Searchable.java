import java.util.List;

public interface Searchable {
    public List<Pet> searchByName(String name);
    public List<Pet> searchByType(Type type);
    public List<Pet> searchByAge(int age);
}
