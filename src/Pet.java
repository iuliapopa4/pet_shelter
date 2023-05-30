import java.util.ArrayList;
import java.util.List;

public class Pet implements Adoption {
    private String name;
    private Type type;
    private int age;
    private double adoptionFee;
    private Owner owner;
    private List<Pet> pets = new ArrayList<Pet>();
    private boolean isAdopted;

    public Pet(String name, Type type, int age, double adoptionFee,boolean adopted) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.adoptionFee = adoptionFee;
        this.isAdopted=adopted;
        pets.add(this);
    }

    public String getName() {return name;}
    public Type getType() {return type;}
    public int getAge() {return age;}
    public double getAdoptionFee() {return adoptionFee;}
    public Owner getAdopter() {return owner;}

    public void setName(String name) {this.name = name;}
    public void setType(Type type) {this.type = type;}
    public void setAge(int age) {this.age = age;}
    public void setAdoptionFee(double adoptionFee) {this.adoptionFee = adoptionFee;}
    public void setOwner(Owner adopter) {this.owner = adopter;}

    @Override
    public void adopt() {
        isAdopted=true;
    }

    @Override
    public boolean isAdopted() {
        return isAdopted;
    }
}
