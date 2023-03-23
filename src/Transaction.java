public record Transaction(String from, String to, Double amount) {
    @Override
    public String toString() {
        return "%s sent %.2f VC to %s".formatted(from, amount, to);
    }
}
