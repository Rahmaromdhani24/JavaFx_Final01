package Front_java.Configuration;

public class ToleranceParser {

    public static class Tolerance {
        public double upper;
        public double lower;

        public Tolerance(double upper, double lower) {
            this.upper = upper;
            this.lower = lower;
        }

        @Override
        public String toString() {
            return "Tolérance [+ " + upper + " / - " + lower + "]";
        }
    }

    public static Tolerance parseTolerance(String raw) {
        raw = raw.trim();

        if (raw.startsWith("±")) {
            // Cas symétrique : ±0.005
            double value = Double.parseDouble(raw.replace("±", "").trim());
            return new Tolerance(value, value);
        } else if (raw.contains("/") && (raw.contains("+") || raw.contains("-"))) {
            // Cas asymétrique : +0.27/-0.19 ou +0.15/-0
            String[] parts = raw.split("/");
            double upper = Double.parseDouble(parts[0].replace("+", "").trim());
            double lower = Math.abs(Double.parseDouble(parts[1].replace("-", "").trim()));
            return new Tolerance(upper, lower);
        } else {
            throw new IllegalArgumentException("Format de tolérance non reconnu : " + raw);
        }
    }
}