import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String name;
    private String phone;
    public Owner(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {return name;}
    public String getPhone() {return phone;}
    public void setName(String name) {this.name = name;}
    public void setPhone(String phone) {this.phone = phone;}

    public void adoptPet(Pet pet) {
        pet.setOwner(this);
        pet.adopt();
    }


}
