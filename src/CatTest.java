import static org.junit.Assert.*;
import org.junit.Test;

public class CatTest {

    @Test
    public void testIsVaccinated() {
        Cat cat = new Cat("Whiskers", 3, 100, true,false);
        assertTrue(cat.isVaccinated());
    }

    @Test
    public void testSetVaccinated() {
        Cat cat = new Cat("Whiskers", 3, 100, false,false);
        cat.setVaccinated();
        assertTrue(cat.isVaccinated());
    }

    @Test
    public void testType() {
        Cat cat = new Cat("Whiskers", 3, 100, true,false);
        assertEquals(Type.CAT, cat.getType());
    }
}
