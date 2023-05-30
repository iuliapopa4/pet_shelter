import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OwnerTest {

    @Test
    public void testOwner() {
        Owner owner = new Owner("John Smith", "555-555-5555");
        assertEquals("John Smith", owner.getName());
        assertEquals("555-555-5555", owner.getPhone());

        owner.setName("Jane Smith");
        owner.setPhone("555-555-5556");
        assertEquals("Jane Smith", owner.getName());
        assertEquals("555-555-5556", owner.getPhone());
    }

    @Test
    public void testAdoptPet() {
        Owner owner = new Owner("John Smith", "555-555-5555");
        Pet pet = new Pet("Fido", Type.DOG, 3, 100.0,false);
        owner.adoptPet(pet);
        assertEquals(owner, pet.getAdopter());
        assertEquals(true, pet.isAdopted());
    }
}
