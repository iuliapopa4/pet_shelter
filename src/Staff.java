public class Staff {
    private String name;
    private PetInventory inventory;
    private String password;
    public Staff(String name, PetInventory inventory, String password) {
        this.name = name;
        this.inventory = inventory;
        this.password = password;
    }
    public boolean validatePassword(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    public String getName() {
        return name;
    }

}