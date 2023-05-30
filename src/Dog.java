public class Dog extends Pet {
    private boolean isTrained;
    private boolean isVaccinated;

    public Dog(String name, int age, double adoptionFee, boolean isTrained, boolean isVaccinated, boolean adopted) {
        super(name, Type.DOG, age, adoptionFee,adopted);
        this.isTrained = isTrained;
        this.isVaccinated = isVaccinated;
    }
    public boolean isTrained() {
        return isTrained;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }
    public void setTrained(){
        isTrained = true;
    }
    public void setVaccinated(){
        isVaccinated = true;
    }

}
