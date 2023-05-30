public class Cat extends Pet {
    private boolean isVaccinated;

    public Cat(String name, int age, double adoptionFee, boolean isVaccinated,boolean isAdopted) {
        super(name, Type.CAT, age, adoptionFee,isAdopted);
        this.isVaccinated = isVaccinated;
    }
    public boolean isVaccinated() {
        return isVaccinated;
    }
    public void setVaccinated(){
        isVaccinated = true;
    }
}
