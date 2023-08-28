package api.client;

public class ConfigApiTest {


    static final String LOCATION_URL_MOSCOW =
            "/geo/1.0/direct?q=Moscow&limit=10&appid=86d23e100cbee2cce3ab1b963d3ff7bb";

    static final String LOCATION_ANSWER_MOSCOW = """
            [
                {
                    "name": "Moscow",
                    "local_names": {
                        "tr": "Moskova",
                        "fr": "Moscou",
                        "ce": "Москох",
                        "bn": "মস্কো",
                        "sc": "Mosca",
                        "lg": "Moosko",
                        "be": "Масква",
                        "ay": "Mosku",
                        "es": "Moscú",
                        "cu": "Москъва",
                        "gd": "Moscobha",
                        "ty": "Moscou",
                        "an": "Moscú",
                        "el": "Μόσχα",
                        "az": "Moskva",
                        "lv": "Maskava",
                        "hi": "मास्को",
                        "tg": "Маскав",
                        "gv": "Moscow",
                        "hy": "Մոսկվա",
                        "ch": "Moscow",
                        "ta": "மாஸ்கோ",
                        "ko": "모스크바",
                        "sw": "Moscow",
                        "gl": "Moscova - Москва",
                        "kn": "ಮಾಸ್ಕೋ",
                        "is": "Moskva",
                        "ca": "Moscou",
                        "en": "Moscow",
                        "am": "ሞስኮ",
                        "ur": "ماسکو",
                        "iu": "ᒨᔅᑯ",
                        "ss": "Moscow",
                        "tk": "Moskwa",
                        "lt": "Maskva",
                        "sm": "Moscow",
                        "sk": "Moskva",
                        "ru": "Москва",
                        "uz": "Moskva",
                        "cv": "Мускав",
                        "nl": "Moskou",
                        "jv": "Moskwa",
                        "gn": "Mosku",
                        "ky": "Москва",
                        "pl": "Moskwa",
                        "hu": "Moszkva",
                        "cs": "Moskva",
                        "ml": "മോസ്കോ",
                        "ie": "Moskwa",
                        "mg": "Moskva",
                        "sv": "Moskva",
                        "br": "Moskov",
                        "la": "Moscua",
                        "tl": "Moscow",
                        "eu": "Mosku",
                        "ht": "Moskou",
                        "ia": "Moscova",
                        "kg": "Moskva",
                        "sr": "Москва",
                        "mi": "Mohikau",
                        "mk": "Москва",
                        "ro": "Moscova",
                        "da": "Moskva",
                        "se": "Moskva",
                        "kv": "Мӧскуа",
                        "sl": "Moskva",
                        "it": "Mosca",
                        "yi": "מאסקווע",
                        "sq": "Moska",
                        "kk": "Мәскеу",
                        "ascii": "Moscow",
                        "ak": "Moscow",
                        "kw": "Moskva",
                        "fa": "مسکو",
                        "uk": "Москва",
                        "qu": "Moskwa",
                        "bi": "Moskow",
                        "sh": "Moskva",
                        "su": "Moskwa",
                        "de": "Moskau",
                        "os": "Мæскуы",
                        "bg": "Москва",
                        "my": "မော်စကိုမြို့",
                        "cy": "Moscfa",
                        "wo": "Mosku",
                        "ps": "مسکو",
                        "pt": "Moscou",
                        "af": "Moskou",
                        "ga": "Moscó",
                        "zu": "IMoskwa",
                        "io": "Moskva",
                        "mt": "Moska",
                        "eo": "Moskvo",
                        "nn": "Moskva",
                        "co": "Moscù",
                        "bs": "Moskva",
                        "nb": "Moskva",
                        "hr": "Moskva",
                        "li": "Moskou",
                        "ar": "موسكو",
                        "ab": "Москва",
                        "fy": "Moskou",
                        "ug": "Moskwa",
                        "he": "מוסקווה",
                        "ku": "Moskow",
                        "te": "మాస్కో",
                        "no": "Moskva",
                        "vo": "Moskva",
                        "feature_name": "Moscow",
                        "st": "Moscow",
                        "th": "มอสโก",
                        "sg": "Moscow",
                        "ms": "Moscow",
                        "za": "Moscow",
                        "tt": "Мәскәү",
                        "dz": "མོསི་ཀོ",
                        "fi": "Moskova",
                        "so": "Moskow",
                        "na": "Moscow",
                        "zh": "莫斯科",
                        "wa": "Moscou",
                        "ka": "მოსკოვი",
                        "av": "Москва",
                        "ja": "モスクワ",
                        "fo": "Moskva",
                        "oc": "Moscòu",
                        "ln": "Moskú",
                        "et": "Moskva",
                        "mr": "मॉस्को",
                        "id": "Moskwa",
                        "yo": "Mọsko",
                        "mn": "Москва",
                        "kl": "Moskva",
                        "bo": "མོ་སི་ཁོ།",
                        "ba": "Мәскәү",
                        "dv": "މޮސްކޯ",
                        "vi": "Mát-xcơ-va"
                    },
                    "lat": 55.7504461,
                    "lon": 37.6174943,
                    "country": "RU",
                    "state": "Moscow"
                }
            ]""";

    static final String FORECAST_URL_MOSCOW =
            "/data/2.5/weather?lat=55.7504461&lon=37.6174943&appid=86d23e100cbee2cce3ab1b963d3ff7bb&units=metric";

    static final String FORECAST_ANSWER_MOSCOW = "{\"coord\":{\"lon\":37.6156,\"lat\":55.7522},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":20.38,\"feels_like\":19.54,\"temp_min\":18.64,\"temp_max\":21.29,\"pressure\":1011,\"humidity\":41,\"sea_level\":1011,\"grnd_level\":994},\"visibility\":10000,\"wind\":{\"speed\":2.03,\"deg\":40,\"gust\":2.41},\"clouds\":{\"all\":5},\"dt\":1693221598,\"sys\":{\"type\":2,\"id\":2000314,\"country\":\"RU\",\"sunrise\":1693189547,\"sunset\":1693240591},\"timezone\":10800,\"id\":524901,\"name\":\"Moscow\",\"cod\":200}";
}
