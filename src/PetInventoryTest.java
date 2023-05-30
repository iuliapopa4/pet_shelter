import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetInventoryTest {
    PetInventory inventory = new PetInventory();

    @Test
    void addPet() {
        PetInventory inventory = new PetInventory();
        Pet pet = new Pet("Fluffy", Type.CAT, 2, 50,false);

        inventory.addPet(pet);
        assertEquals(1, inventory.getAllPets().size());
        assertEquals(pet, inventory.getAllPets().get(0));
    }

    @Test
    void removePet() {
        PetInventory inventory = new PetInventory();
        Pet cat1 = new Cat("Whiskers", 2, 50, true,false);
        Pet cat2 = new Cat("Luna", 3, 75, true,false);
        Pet dog1 = new Dog("Buddy", 5, 100, true, true,false);
        inventory.addPet(cat1);
        inventory.addPet(cat2);
        inventory.addPet(dog1);

        inventory.removePet(cat1);

        List<Pet> remainingPets = inventory.getAllPets();
        assertEquals(2, remainingPets.size());
        assertFalse(remainingPets.contains(cat1));
        assertTrue(remainingPets.contains(cat2));
        assertTrue(remainingPets.contains(dog1));
    }

    @Test
    void testGetAllPets() {
        PetInventory inventory = new PetInventory();
        Pet pet1 = new Pet("Fluffy", Type.CAT, 2, 50.0, false);
        Pet pet2 = new Pet("Buddy", Type.DOG, 4, 100.0, false);
        inventory.addPet(pet1);
        inventory.addPet(pet2);
        List<Pet> expectedPets = new ArrayList<>();
        expectedPets.add(pet1);
        expectedPets.add(pet2);

        List<Pet> actualPets = inventory.getAllPets();

        assertEquals(expectedPets, actualPets);
    }

    @Test
    public void testDisplayAllPets() {
        PetInventory inventory = new PetInventory();
        Pet cat1 = new Cat("Whiskers", 2, 50.0, true, false);
        Pet cat2 = new Cat("Fluffy", 3, 75.0, true, true);
        Pet dog1 = new Dog("Fido", 5, 100.0, true, true, false);
        Pet dog2 = new Dog("Max", 1, 125.0, true, true, true);

        inventory.addPet(cat1);
        inventory.addPet(cat2);
        inventory.addPet(dog1);
        inventory.addPet(dog2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        inventory.displayAllPets();

        String expectedOutput = "Name: Whiskers\nType: CAT\nAge: 2 years old\nAdoption fee: $50.0\nNot adopted\n---------------\n" +
                "Name: Fluffy\nType: CAT\nAge: 3 years old\nAdoption fee: $75.0\nAdopted\n---------------\n" +
                "Name: Fido\nType: DOG\nAge: 5 years old\nAdoption fee: $100.0\nNot adopted\n---------------\n" +
                "Name: Max\nType: DOG\nAge: 1 years old\nAdoption fee: $125.0\nAdopted\n---------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void searchByNameTest() {
        Pet pet1 = new Dog("Fido", 3, 200, true, true, false);
        Pet pet2 = new Cat("Whiskers", 2, 100, true, true);
        Pet pet3 = new Dog("Buddy", 5, 250, false, true, false);

        inventory.addPet(pet1);
        inventory.addPet(pet2);
        inventory.addPet(pet3);

        List<Pet> foundPets = inventory.searchByName("fido");

        assertEquals(1, foundPets.size());
        assertTrue(foundPets.get(0).getName().equalsIgnoreCase("Fido"));
    }

    @Test
    void testFindByName() {
        PetInventory inventory = new PetInventory();

        Pet pet1 = new Pet("Fluffy", Type.CAT, 3, 50.0, false);
        Pet pet2 = new Pet("Buddy", Type.DOG, 5, 75.0, true);
        Pet pet3 = new Pet("Whiskers", Type.CAT, 1, 25.0, false);

        inventory.addPet(pet1);
        inventory.addPet(pet2);
        inventory.addPet(pet3);

        String name = "Fluffy";
        Pet foundPet = inventory.findByName(name);
        assertNotNull(foundPet);
        assertEquals(name, foundPet.getName());

        name = "Fido";
        foundPet = inventory.findByName(name);
        assertNull(foundPet);
    }


    @Test
    void searchByType() {
        Pet pet1 = new Pet("Fluffy", Type.CAT, 2, 100,false);
        Pet pet2 = new Pet("Buddy", Type.DOG, 4, 150,false);
        Pet pet3 = new Pet("Whiskers", Type.CAT, 3, 75,false);
        Pet pet4 = new Pet("Fido", Type.DOG, 1, 200,false);

        inventory.addPet(pet1);
        inventory.addPet(pet2);
        inventory.addPet(pet3);
        inventory.addPet(pet4);

        List<Pet> foundCats = inventory.searchByType(Type.CAT);
        assertEquals(2, foundCats.size());
        assertTrue(foundCats.contains(pet1));
        assertTrue(foundCats.contains(pet3));
        List<Pet> foundDogs = inventory.searchByType(Type.DOG);
        assertEquals(2, foundDogs.size());
        assertTrue(foundDogs.contains(pet2));
        assertTrue(foundDogs.contains(pet4));
    }

    @Test
    void searchByAge() {
        Pet pet1 = new Pet("Fido", Type.DOG, 3, 100,false);
        Pet pet2 = new Pet("Whiskers", Type.CAT, 2, 50,false);
        Pet pet3 = new Pet("Buddy", Type.DOG, 5, 150,false);
        inventory.addPet(pet1);
        inventory.addPet(pet2);
        inventory.addPet(pet3);

        List<Pet> foundPets = inventory.searchByAge(3);
        assertEquals(1, foundPets.size());
        assertEquals("Fido", foundPets.get(0).getName());

        foundPets = inventory.searchByAge(5);
        assertEquals(1, foundPets.size());
        assertEquals("Buddy", foundPets.get(0).getName());

        foundPets = inventory.searchByAge(6);
        assertEquals(0, foundPets.size());
    }

    @Test
    public void testDisplayNotAdoptedPets() {
        PetInventory inventory = new PetInventory();
        Pet pet1 = new Pet("Fluffy", Type.CAT, 3, 150,false);
        Pet pet2 = new Pet("Buddy", Type.DOG, 5, 200,true);
        Pet pet3 = new Pet("Max", Type.DOG, 2, 100,false);
        inventory.addPet(pet1);
        inventory.addPet(pet2);
        inventory.addPet(pet3);
        pet2.adopt();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        inventory.displayNotAdoptedPets();
        assertEquals("Name: Fluffy\nType: CAT\nAge: 3 years old\nAdoption fee: $150.0\n---------------\nName: Max\nType: DOG\nAge: 2 years old\nAdoption fee: $100.0\n---------------\n", outContent.toString());
    }

}