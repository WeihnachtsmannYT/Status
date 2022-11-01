package de.weihnachtsmannyt.status.Language;

public class LanguageManager {

    private static Boolean languageApiIsEnabled = false;

    public static void setLanguageApiIsEnabled(Boolean IsEnabled) {
        languageApiIsEnabled = IsEnabled;
    }

    public static Boolean getLanguageApiIsEnabled() {
        return languageApiIsEnabled;
    }
}
