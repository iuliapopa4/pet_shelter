import static org.junit.Assert.*;
import org.junit.Test;

public class PetTest {

    @Test
    public void testGetName() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        assertEquals("Fluffy", pet.getName());
    }

    @Test
    public void testGetType() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        assertEquals(Type.CAT, pet.getType());
    }

    @Test
    public void testGetAge() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        assertEquals(3, pet.getAge());
    }

    @Test
    public void testGetAdoptionFee() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        assertEquals(100, pet.getAdoptionFee(), 0);
    }

    @Test
    public void testSetName() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        pet.setName("Fido");
        assertEquals("Fido", pet.getName());
    }

    @Test
    public void testSetType() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        pet.setType(Type.DOG);
        assertEquals(Type.DOG, pet.getType());
    }

    @Test
    public void testSetAge() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        pet.setAge(5);
        assertEquals(5, pet.getAge());
    }

    @Test
    public void testSetAdoptionFee() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        pet.setAdoptionFee(200);
        assertEquals(200, pet.getAdoptionFee(), 0);
    }

    @Test
    public void testAdopt() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        pet.adopt();
        assertTrue(pet.isAdopted());
    }

    @Test
    public void testIsAdopted() {
        Pet pet = new Pet("Fluffy", Type.CAT, 3, 100, false);
        assertFalse(pet.isAdopted());
    }
}