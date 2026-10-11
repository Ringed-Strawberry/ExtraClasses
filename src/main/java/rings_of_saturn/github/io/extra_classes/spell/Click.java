package rings_of_saturn.github.io.extra_classes.spell;

public enum Click {
    LEFT("L"),
    RIGHT("R");

    private final String symbol;

    Click(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
