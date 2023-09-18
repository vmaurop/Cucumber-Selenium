package com.vmavropo.utils.common;


import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class IsoUtil {

    private static final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());


    private IsoUtil() {
    }

    public static boolean isValidISOLanguage(String s) {
        return ISO_LANGUAGES.contains(s);
    }

    public static boolean isValidISOCountry(String s) {
        return ISO_COUNTRIES.contains(s);
    }

    public static boolean isValidISOCurrency(String s) {
        return getAllCurrencyCodes().contains(s);
    }

    public static boolean isValidISOCustomOffice(String s) {
        return s.length() == 8 && s.equals(s.toUpperCase()) && !s.contains(" ") && isNumeric(s.substring(6, 8));
    }

    private static List<String> getAllCurrencyCodes() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        return currencies.stream().map(Currency::getCurrencyCode).collect(Collectors.toList());
    }
}
