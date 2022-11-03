import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import I18nextBrowserLanguageDetector from "i18next-browser-languagedetector";
import { TRANSLATIONS_EN } from "./translations/en";
import { TRANSLATIONS_ES } from "./translations/es";

i18n
  .use(I18nextBrowserLanguageDetector)
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    resources: {
      en: {
          translation: TRANSLATIONS_EN
      },
      es: {
          translation: TRANSLATIONS_ES
      }
  },
    fallbackLng: 'en',
    debug: true,

    interpolation: {
      escapeValue: false // react already safes from xss
    }
  });

  export default i18n;