import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StaffTest {

    @Test
    public void testValidatePassword() {
        Staff staff = new Staff("Mihai", new PetInventory(), "password123");
        assertTrue(staff.validatePassword("password123"));
        assertFalse(staff.validatePassword("wrongpassword"));
    }

    @Test
    public void testGetName() {
        Staff staff = new Staff("Mihai", new PetInventory(), "password123");
        assertEquals("Mihai", staff.getName());
    }
}
