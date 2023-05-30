import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            PetInventory inventory = new PetInventory();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/petshelter","root","Ggl04Pass314wrd02.");
            Statement stmt = con.createStatement();
            ResultSet rc = stmt.executeQuery("SELECT * FROM cats");
            while (rc.next()) {
                String name = rc.getString("name");
                int age = rc.getInt("age");
                int fee = rc.getInt("adoptionFee");
                boolean is_adopted = rc.getBoolean("is_adopted");
                boolean is_vaccinated = rc.getBoolean("is_vaccinated");
                Cat cat = new Cat(name,age,fee,is_vaccinated,is_adopted);
                inventory.addPet(cat);
            }
            rc.close();
            ResultSet rd = stmt.executeQuery("SELECT * FROM dogs");
            while (rd.next()) {
                String name = rd.getString("name");
                int age = rd.getInt("age");
                int fee = rd.getInt("adoptionFee");
                boolean is_adopted = rd.getBoolean("is_adopted");
                boolean is_vaccinated = rd.getBoolean("is_vaccinated");
                boolean is_trained = rd.getBoolean("is_trained");
                Dog dog = new Dog(name,age,fee,is_trained,is_vaccinated,is_adopted);
                inventory.addPet(dog);
            }
            rd.close();
            List<Staff> staffList = new ArrayList<Staff>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff");
            while (rs.next()) {
                String name = rs.getString("username");
                String password = rs.getString("password");
                Staff staff = new Staff(name,inventory, password);
                staffList.add(staff);
            }
            rc.close();

            if(args[0].equals("staff")) {
                try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter Staff name: ");
                String n = input.nextLine();
                System.out.print("Enter password: ");
                String p = input.nextLine();

                int ok;
                ok = 0;
                for (Staff s : staffList) {
                    if (s.getName().equals(n) && s.validatePassword(p)) {
                        ok = 1;
                    }
                }
                if(ok == 0) {
                    throw new StaffNotAuthorizedException("Incorrect name or password. Staff member not authorized.");
                }
                else{
                    while (true) {
                        try{
                        System.out.println();
                        System.out.println("1. Add a pet to the inventory");
                        System.out.println("2. Remove a pet from the inventory");
                        System.out.println("3. Search for a pet by name");
                        System.out.println("4. Search for pets by type");
                        System.out.println("5. Display all pets");
                        System.out.println("6. Display all owners");
                        System.out.println("7. Update pet information");
                        System.out.println("8. Display all staff members");
                        System.out.println("9. Exit");
                        System.out.print("Enter your choice: ");
                        int choice = input.nextInt();
                        input.nextLine();
                        if (choice == 1) {
                            System.out.print("Enter pet name: ");
                            String petName = input.nextLine();
                            System.out.print("Enter pet type (DOG or CAT): ");
                            String petType = input.nextLine();
                            Type type = Type.valueOf(petType.toUpperCase());
                            System.out.print("Enter pet age: ");
                            int petAge = input.nextInt();
                            input.nextLine();
                            System.out.print("Enter pet adoption fee: ");
                            double fee = input.nextDouble();
                            input.nextLine();
                            Pet newPet;
                            if (petType.equalsIgnoreCase("CAT")) {
                                System.out.print("Enter if the cat is vaccinated (true/false): ");
                                boolean isVaccinated = input.nextBoolean();
                                newPet = new Cat(petName, petAge, fee, isVaccinated, false);
                                String insertSql = "INSERT INTO cats (name, age, adoptionFee, is_vaccinated, is_adopted) VALUES (?, ?, ?, ?, ?)";
                                PreparedStatement insertStmt = con.prepareStatement(insertSql);
                                insertStmt.setString(1, petName);
                                insertStmt.setInt(2, petAge);
                                insertStmt.setDouble(3, fee);
                                insertStmt.setBoolean(4, isVaccinated);
                                insertStmt.setBoolean(5, false);
                                insertStmt.executeUpdate();
                                inventory.addPet(newPet);
                                System.out.println("Cat added successfully!");
                            } else if (petType.equalsIgnoreCase("DOG")) {
                                System.out.print("Enter if the dog is vaccinated (true/false): ");
                                boolean isVaccinated = input.nextBoolean();
                                System.out.print("Enter if the dog is trained (true/false): ");
                                boolean isTrained = input.nextBoolean();
                                newPet = new Dog(petName, petAge, fee, isTrained, isVaccinated, false);
                                String insertSql = "INSERT INTO dogs (name, age, adoptionFee, is_vaccinated, is_trained,is_adopted) VALUES (?, ?, ?, ?, ?,?)";
                                PreparedStatement insertStmt = con.prepareStatement(insertSql);
                                insertStmt.setString(1, petName);
                                insertStmt.setInt(2, petAge);
                                insertStmt.setDouble(3, fee);
                                insertStmt.setBoolean(4, isVaccinated);
                                insertStmt.setBoolean(5, isTrained);
                                insertStmt.setBoolean(6, false);
                                insertStmt.executeUpdate();
                                System.out.println("Dog added successfully!");
                                inventory.addPet(newPet);
                            } else {
                                System.out.println("Invalid pet type.");
                            }
                        } else if (choice == 2) {
                            System.out.print("Enter pet name: ");
                            String petName = input.nextLine();
                            List<Pet> pets = inventory.searchByName(petName);
                            if (pets.size() > 0) {
                                System.out.print("Enter pet type (DOG or CAT): ");
                                String petType = input.nextLine();
                                if (petType.equals("CAT")) {
                                    String sql = "DELETE FROM cats WHERE name = '" + petName + "'";
                                    stmt.executeUpdate(sql);
                                } else if (petType.equals("DOG")) {
                                    String sql = "DELETE FROM dogs WHERE name = '" + petName + "'";
                                    stmt.executeUpdate(sql);
                                }
                                for (Pet pet : pets)
                                    inventory.removePet(pet);
                                System.out.println("Pet removed successfully!");
                            } else {
                                System.out.println("Pet not found in the inventory");
                            }

                        } else if (choice == 3) {
                            System.out.print("Enter pet name: ");
                            String petName = input.nextLine();
                            List<Pet> pets = inventory.searchByName(petName);
                            if (pets.size() > 0) {
                                for (Pet pet : pets) {
                                    System.out.println(pet.getName() + " - " + pet.getType() + " - " + pet.getAge() + " years old - $" + pet.getAdoptionFee());
                                }
                            } else {
                                System.out.println("Pet not found in the inventory");
                            }
                        } else if (choice == 4) {
                            System.out.print("Enter pet type (DOG or CAT): ");
                            String petType = input.nextLine();
                            Type type = Type.valueOf(petType.toUpperCase());
                            List<Pet> pets = inventory.searchByType(type);
                            if (pets.size() > 0) {
                                for (Pet pet : pets) {
                                    System.out.println(pet.getName() + " - " + pet.getAge() + " years old - $" + pet.getAdoptionFee());
                                }
                            } else {
                                System.out.println("No pets of type " + petType + " found in the inventory");
                            }
                        } else if (choice == 5) {
                            inventory.displayAllPets();
                        } else if (choice == 6) {
                            String query = "SELECT * FROM owners";
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(query);
                            while (resultSet.next()) {
                                String name = resultSet.getString("name");
                                String phone = resultSet.getString("phone");
                                System.out.println(name + ": " + phone);
                            }
                            statement.close();
                        } else if (choice == 7) {
                            inventory.displayAllPets();
                            System.out.print("Enter the name of the pet you wish to update: ");
                            String petName = input.nextLine();
                            Pet pet = inventory.findByName(petName);
                            if (pet != null) {
                                System.out.print("Enter the new name for the pet: ");
                                String newName = input.nextLine();
                                pet.setName(newName);
                                System.out.print("Enter the new age for the pet: ");
                                int newAge = input.nextInt();
                                pet.setAge(newAge);
                                input.nextLine();
                                System.out.print("Enter the new adoption fee for the pet: ");
                                double newFee = input.nextDouble();
                                pet.setAdoptionFee(newFee);
                                input.nextLine();
                                if (pet instanceof Cat) {
                                    String updateSql = "UPDATE cats SET name = ?, age = ?, adoptionFee = ? WHERE name = ?";
                                    PreparedStatement updateStmt = con.prepareStatement(updateSql);
                                    updateStmt.setString(1, newName);
                                    updateStmt.setInt(2, newAge);
                                    updateStmt.setDouble(3, newFee);
                                    updateStmt.setString(4, petName);
                                    updateStmt.executeUpdate();
                                    updateStmt.close();
                                } else if (pet instanceof Dog) {
                                    String updateSql = "UPDATE dogs SET name = ?, age = ?, adoptionFee = ? WHERE name = ?";
                                    PreparedStatement updateStmt = con.prepareStatement(updateSql);
                                    updateStmt.setString(1, newName);
                                    updateStmt.setInt(2, newAge);
                                    updateStmt.setDouble(3, newFee);
                                    updateStmt.setString(4, petName);
                                    updateStmt.executeUpdate();
                                    updateStmt.close();
                                }
                                System.out.println("The pet's information has been updated.");
                            } else {
                                System.out.println("Pet not found.");
                            }
                            }else if(choice == 8){
                                System.out.println("Staff members:");
                                System.out.println("---------------");
                            for(Staff s : staffList){
                                    System.out.println(s.getName());
                                    System.out.println("---------------");
                                }
                            } else if (choice == 9) {
                            System.out.println("Exiting the program...");
                            break;
                            }
                        } catch (InputMismatchException e){
                            System.out.println("Invalid input. Please enter a number from 1 to 9.");
                            break;
                        }
                }}} catch (StaffNotAuthorizedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            else if(args[0].equals("person")) {
                try {
                    Scanner input = new Scanner(System.in);
                    System.out.print("Enter your name: ");
                    String name = input.nextLine();
                    System.out.print("Enter your phone number: ");
                    String phone = input.nextLine();
                    Owner adopter = new Owner(name, phone);

                    String query = "SELECT * FROM owners WHERE name = ? AND phone = ?";
                    PreparedStatement so = con.prepareStatement(query);
                    so.setString(1, adopter.getName());
                    so.setString(2, adopter.getPhone());
                    ResultSet ro = so.executeQuery();
                    if (!ro.next()) {
                        throw new OwnerNotFoundException("User not found in the system.");
                    }
                    System.out.println("Welcome back!");
                    ro.close();
                    so.close();
                    while (true) {
                        System.out.println();
                        System.out.println("1. Adopt a pet");
                        System.out.println("2. View adopted pets");
                        System.out.println("3. Search for a pet by name");
                        System.out.println("4. Search for pets by type");
                        System.out.println("5. Display all available pets");
                        System.out.println("6. Change phone number");
                        System.out.println("7. Exit");
                        System.out.print("Enter your choice: ");
                        int choice = input.nextInt();
                        input.nextLine();
                        if (choice == 1) {
                            inventory.displayNotAdoptedPets();

                            System.out.print("Enter pet type (DOG or CAT): ");
                            String petType = input.nextLine();
                            System.out.print("Enter pet name: ");
                            String petName = input.nextLine();

                            if (petType.equalsIgnoreCase("CAT")) {
                                Cat adoptedPet = (Cat) inventory.findByName(petName);
                                try {
                                    if (adoptedPet != null) {
                                        adopter.adoptPet(adoptedPet);
                                        int ownerId = 0;
                                        query = "SELECT idowners FROM owners WHERE name = ?";
                                        so = con.prepareStatement(query);
                                        so.setString(1, adoptedPet.getAdopter().getName());
                                        ro = so.executeQuery();
                                        if (ro.next()) {
                                            ownerId = ro.getInt("idowners");
                                        }
                                        ro.close();
                                        so.close();

                                        query = "UPDATE cats SET is_adopted = true, owner_id = ? WHERE name = ?";
                                        so = con.prepareStatement(query);
                                        so.setInt(1, ownerId);
                                        so.setString(2, adoptedPet.getName());
                                        so.executeUpdate();
                                        so.close();
                                        System.out.println("Congratulations! You have successfully adopted " + adoptedPet.getName());
                                    } else {
                                        throw new PetNotFoundException("Pet not found in the inventory.");
                                    }
                                } catch (PetNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }

                            } else if (petType.equalsIgnoreCase("DOG")) {
                                Dog adoptedPet = (Dog) inventory.findByName(petName);
                                try {
                                    if (adoptedPet != null) {
                                        adopter.adoptPet(adoptedPet);
                                        int ownerId = 0;
                                        query = "SELECT idowners FROM owners WHERE name = ?";
                                        so = con.prepareStatement(query);
                                        so.setString(1, adoptedPet.getAdopter().getName());
                                        ro = so.executeQuery();
                                        if (ro.next()) {
                                            ownerId = ro.getInt("idowners");
                                        }
                                        ro.close();
                                        so.close();

                                        query = "UPDATE dogs SET is_adopted = true, owner_id = ? WHERE name = ?";
                                        so = con.prepareStatement(query);
                                        so.setInt(1, ownerId);
                                        so.setString(2, adoptedPet.getName());
                                        so.executeUpdate();
                                        so.close();
                                        System.out.println("Congratulations! You have successfully adopted " + adoptedPet.getName());
                                    } else {
                                        throw new PetNotFoundException("Pet not found in the inventory.");
                                    }
                                } catch (PetNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Invalid pet type.");
                            }
                        } else if (choice == 2) {
                            int ownerId = 0;
                            query = "SELECT idowners FROM owners WHERE name = ?";
                            so = con.prepareStatement(query);
                            so.setString(1, adopter.getName());
                            ro = so.executeQuery();
                            if (ro.next()) {
                                ownerId = ro.getInt("idowners");
                            }
                            ro.close();
                            so.close();

                            List<Pet> adoptedPets = new ArrayList<>();
                            query = "SELECT * FROM cats WHERE owner_id = ?";
                            PreparedStatement statement = con.prepareStatement(query);
                            statement.setInt(1, ownerId);
                            ResultSet resultSet = statement.executeQuery();
                            while (resultSet.next()) {
                                name = resultSet.getString("name");
                                int age = resultSet.getInt("age");
                                double fee = resultSet.getDouble("adoptionFee");
                                boolean isV = resultSet.getBoolean("is_vaccinated");
                                boolean isA = resultSet.getBoolean("is_adopted");
                                Cat cat = new Cat(name, age, fee, isV, isA);
                                adoptedPets.add(cat);
                            }
                            statement.close();
                            resultSet.close();

                            query = "SELECT * FROM dogs WHERE owner_id = ?";
                            statement = con.prepareStatement(query);
                            statement.setInt(1, ownerId);
                            resultSet = statement.executeQuery();
                            while (resultSet.next()) {
                                name = resultSet.getString("name");
                                int age = resultSet.getInt("age");
                                double fee = resultSet.getDouble("adoptionFee");
                                boolean isV = resultSet.getBoolean("is_vaccinated");
                                boolean isA = resultSet.getBoolean("is_adopted");
                                boolean isT = resultSet.getBoolean("is_trained");
                                Dog dog = new Dog(name, age, fee, isV, isA, isT);
                                adoptedPets.add(dog);
                            }
                            statement.close();
                            resultSet.close();
                            System.out.println("Adopted Pets: ");
                            System.out.println("---------------");
                            for (Pet a : adoptedPets) {
                                System.out.println(a.getName());
                            }
                            System.out.println("---------------");

                        } else if (choice == 3) {
                            System.out.print("Enter pet name: ");
                            String petName = input.nextLine();
                            System.out.println();
                            Pet foundPet = inventory.findByName(petName);
                            if (foundPet != null) {
                                System.out.println("Name: " + foundPet.getName());
                                System.out.println("Type: " + foundPet.getType());
                                System.out.println("Age: " + foundPet.getAge() + " years old");
                                System.out.println("Adoption fee: $" + foundPet.getAdoptionFee());
                                if (foundPet.isAdopted()) {
                                    System.out.println("Adopted");
                                }
                            } else {
                                System.out.println("Sorry, pet not found.");
                            }
                        } else if (choice == 4) {
                            System.out.print("Enter pet type (DOG or CAT): ");
                            String petType = input.nextLine();
                            System.out.println();
                            Type type = Type.valueOf(petType.toUpperCase());
                            List<Pet> foundPets = inventory.searchByType(type);
                            if (foundPets.size() > 0) {
                                for (Pet pet : foundPets) {
                                    System.out.println("Name: " + pet.getName());
                                    System.out.println("Age: " + pet.getAge() + " years old");
                                    System.out.println("Adoption fee: $" + pet.getAdoptionFee());
                                    System.out.println("---------------");
                                }
                            } else {
                                System.out.println("Sorry, no pets of that type found.");
                            }
                        } else if (choice == 5) {
                            inventory.displayAllPets();
                        } else if (choice == 6) {
                            System.out.print("Enter your new phone number: ");
                            String newPhone = input.nextLine();
                            query = "UPDATE owners SET phone = ? WHERE name = ?";
                            PreparedStatement statement = con.prepareStatement(query);
                            statement.setString(1, newPhone);
                            statement.setString(2, adopter.getName());
                            statement.executeUpdate();
                            System.out.println("Your phone number has been updated.");
                            statement.close();
                        } else if (choice == 7) {
                            System.out.println("Thank you for visiting the pet shelter. Goodbye!");
                            break;
                        } else {
                            System.out.println("Invalid choice. Please enter a valid option.");
                        }
                    }
                } catch (OwnerNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if(args[0].equals("newPerson")){
                Runnable task = () -> {
                    Connection con1 = null;
                    try {
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/petshelter", "root", "Ggl04Pass314wrd02.");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Scanner input = new Scanner(System.in);
                    System.out.print("Enter your name: ");
                    String name = input.nextLine();
                    System.out.print("Enter your phone number: ");
                    String phone = input.nextLine();
                    // check if user already exists in the database
                    String checkQuery = "SELECT * FROM owners WHERE name = ? and phone = ?";
                    PreparedStatement checkStatement = null;
                    try {
                        checkStatement = con1.prepareStatement(checkQuery);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        checkStatement.setString(1, name);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        checkStatement.setString(2, phone);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ResultSet result = null;
                    try {
                        result = checkStatement.executeQuery();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        if (result.next()) {
                            System.out.println("User already exists in the database");
                        } else {
                            Owner newOwner = new Owner(name, phone);
                            String query = "INSERT INTO owners (name, phone) VALUES (?, ?)";
                            PreparedStatement so = con1.prepareStatement(query);
                            so.setString(1, newOwner.getName());
                            so.setString(2, newOwner.getPhone());
                            so.executeUpdate();
                            System.out.println("Welcome!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        con1.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                };
                Thread thread = new Thread(task);
                thread.start();
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: JDBC driver not found");
        }
    }
}
