package enums;


public enum HomeMenuEnum {

    START_CYBERTRACKER("Start Cyber Tracker"),
    EXIT("Exit");

    HomeMenuEnum(String label) {
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
