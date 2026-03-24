package ru.itmo.browser;

import java.nio.file.Files;
import java.nio.file.Path;

public final class BrowserSupport {
    private static final Path CHROME_BINARY =
            Path.of("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
    private static final Path FIREFOX_BINARY =
            Path.of("/Applications/Firefox.app/Contents/MacOS/firefox");

    private BrowserSupport() {
    }

    public static boolean isInstalled(BrowserType browserType) {
        return Files.exists(getBinary(browserType));
    }

    public static String getBinaryPath(BrowserType browserType) {
        return getBinary(browserType).toString();
    }

    private static Path getBinary(BrowserType browserType) {
        return switch (browserType) {
            case CHROME -> CHROME_BINARY;
            case FIREFOX -> FIREFOX_BINARY;
        };
    }
}
