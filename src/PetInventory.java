import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PetInventory implements Searchable {
    private List<Pet> pets;

    public PetInventory() {
        pets = new ArrayList<Pet>();
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
    }
    public List<Pet> getAllPets() {
        return pets;
    }


    public void displayAllPets() {
        List<Pet> pets = getAllPets();
        if (pets.size() > 0) {
            for (Pet pet : pets) {
                System.out.println("Name: " + pet.getName());
                System.out.println("Type: " + pet.getType());
                System.out.println("Age: " + pet.getAge() + " years old");
                System.out.println("Adoption fee: $" + pet.getAdoptionFee());
                if (pet.isAdopted()) {
                    System.out.println("Adopted");
                } else {
                    System.out.println("Not adopted");
                }
                System.out.println("---------------");
            }
        } else {
            System.out.println("No pets found in the inventory.");
        }
    }


    @Override
    public List<Pet> searchByName(String name) {
        List<Pet> foundPets = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                foundPets.add(pet);
            }
        }
        return foundPets;
    }
    public Pet findByName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return pet;
            }
        }
        return null;
    }

    @Override
    public List<Pet> searchByType(Type type) {
        List<Pet> foundPets = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getType() == type) {
                foundPets.add(pet);
            }
        }
        return foundPets;
    }

    @Override
    public List<Pet> searchByAge(int age) {
        List<Pet> foundPets = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getAge() == age) {
                foundPets.add(pet);
            }
        }
        return foundPets;
    }

    public void displayNotAdoptedPets() {
        pets.stream()
                .filter(p -> !p.isAdopted())
                .forEach(p -> {
                    System.out.println("Name: " + p.getName());
                    System.out.println("Type: " + p.getType());
                    System.out.println("Age: " + p.getAge() + " years old");
                    System.out.println("Adoption fee: $" + p.getAdoptionFee());
                    System.out.println("---------------");
                });
    }


}
