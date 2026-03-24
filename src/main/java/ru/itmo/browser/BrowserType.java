package ru.itmo.browser;

public enum BrowserType {
    CHROME,
    FIREFOX;

    public static BrowserType fromSystemProperty() {
        String value = System.getProperty("browser", "chrome").trim().toLowerCase();
        return switch (value) {
            case "chrome" -> CHROME;
            case "firefox" -> FIREFOX;
            default -> throw new IllegalArgumentException("Unsupported browser: " + value);
        };
    }
}
