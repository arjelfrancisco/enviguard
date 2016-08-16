package enums;

public enum DirectObservationSpeciesEnum {

    BIRDS("Birds"),
    MAMMALS("Mammals"),
    REPTILES("Reptiles"),
    FLORA("Flora");

    DirectObservationSpeciesEnum(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }

}
