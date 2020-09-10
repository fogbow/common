package cloud.fogbow.common.util;

public class BinaryUnit {

    private static final long BINARY_BASE_UNIT = 1024;

    public static Byte bytes(double value) { return new Byte(value); }
    public static Kilobyte kilobytes(double value) { return new Kilobyte(value); }
    public static Megabyte megabytes(double value) {
        return new Megabyte(value);
    }
    public static Gigabyte gigabytes(double value) {
        return new Gigabyte(value);
    }
    public static Terabyte terabytes(double value) {
        return new Terabyte(value);
    }
    public static Petabyte petabytes(double value) {
        return new Petabyte(value);
    }

    public static class Byte {
        private double value;
        private Byte(double value) {
            this.value = value;
        }
        public double asKilobytes() { return value / Math.pow(BINARY_BASE_UNIT, 1); }
        public double asMegabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 2);
        }
        public double asGigabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 3);
        }
        public double asTerabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 4);
        }
        public double asPetabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 5);
        }
    }

    public static class Kilobyte {
        private double value;
        private Kilobyte(double value) { this.value = value; }
        public double asBytes() { return value * Math.pow(BINARY_BASE_UNIT, 1); }
        public double asMegabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 1);
        }
        public double asGigabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 2);
        }
        public double asTerabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 3);
        }
        public double asPetabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 4);
        }
    }

    public static class Megabyte {
        private double value;
        private Megabyte(double value) {
            this.value = value;
        }
        public double asBytes() { return value * Math.pow(BINARY_BASE_UNIT, 2); }
        public double asKilobytes() { return value * Math.pow(BINARY_BASE_UNIT, 1); }
        public double asGigabytes() { return value / Math.pow(BINARY_BASE_UNIT, 1); }
        public double asTerabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 2);
        }
        public double asPetabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 3);
        }
    }

    public static class Gigabyte {
        private double value;
        private Gigabyte(double value) {
            this.value = value;
        }
        public double asBytes() { return value * Math.pow(BINARY_BASE_UNIT, 3); }
        public double asKilobytes() { return value * Math.pow(BINARY_BASE_UNIT, 2); }
        public double asMegabytes() { return value * Math.pow(BINARY_BASE_UNIT, 1); }
        public double asTerabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 1);
        }
        public double asPetabytes() {
            return value / Math.pow(BINARY_BASE_UNIT, 2);
        }
    }

    public static class Terabyte {
        private double value;
        private Terabyte(double value) {
            this.value = value;
        }
        public double asBytes() { return value * Math.pow(BINARY_BASE_UNIT, 4); }
        public double asKilobytes() { return value * Math.pow(BINARY_BASE_UNIT, 3); }
        public double asMegabytes() { return value * Math.pow(BINARY_BASE_UNIT, 2); }
        public double asGigabytes() { return value * Math.pow(BINARY_BASE_UNIT, 1); }
        public double asPetabytes() { return value / Math.pow(BINARY_BASE_UNIT, 1); }
    }

    public static class Petabyte {
        private double value;
        private Petabyte(double value) {
            this.value = value;
        }
        public double asBytes() { return value * Math.pow(BINARY_BASE_UNIT, 5); }
        public double asKilobytes() { return value * Math.pow(BINARY_BASE_UNIT, 4); }
        public double asMegabytes() { return value * Math.pow(BINARY_BASE_UNIT, 3); }
        public double asGigabytes() { return value * Math.pow(BINARY_BASE_UNIT, 2); }
        public double asTerabytes() { return value * Math.pow(BINARY_BASE_UNIT, 1); }

    }
}
