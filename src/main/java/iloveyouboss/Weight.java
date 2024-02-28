package iloveyouboss;

public enum Weight {
    REQUIRED(Integer.MAX_VALUE),
    VERY_IMPORTANT(5000),
    IMPORTANT(1000),
    NICE_TO_HAVE(100),
    IRRELEVANT(0);

    private final int value;

    Weight(int value) { this.value = value; }
    public int value() { return value; }
}
