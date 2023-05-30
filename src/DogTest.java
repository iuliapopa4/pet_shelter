import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {

    @Test
    public void testIsTrained() {
        Dog dog = new Dog("Buddy", 3, 100, true, true,false);
        assertTrue(dog.isTrained());
    }

    @Test
    public void testIsVaccinated() {
        Dog dog = new Dog("Buddy", 3, 100, true, true,false);
        assertTrue(dog.isVaccinated());
    }

    @Test
    public void testSetVaccinated() {
        Dog dog = new Dog("Buddy", 3, 100, true, false,false);
        dog.setVaccinated();
        assertTrue(dog.isVaccinated());
    }

    @Test
    public void testType() {
        Dog dog = new Dog("Buddy", 3, 100, true, true,false);
        assertEquals(Type.DOG, dog.getType());
    }
}
