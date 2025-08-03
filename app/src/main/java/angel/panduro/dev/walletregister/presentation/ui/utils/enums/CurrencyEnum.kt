package angel.panduro.dev.walletregister.presentation.ui.utils.enums

enum class CurrencyEnum(val code: String, val symbol: String) {
    NONE("", ""),
    DOLAR_ESTADOUNIDENSE("USD", "$"),
    EURO("EUR", "€"),
    LIBRA_ESTERLINA("GBP", "£"),
    YEN_JAPONES("JPY", "¥"),
    YUAN_CHINO("CNY", "¥"),
    RUPIA_INDIA("INR", "₹"),
    DOLAR_AUSTRALIANO("AUD", "A$"),
    DOLAR_CANADIENSE("CAD", "C$"),
    FRANCO_SUIZO("CHF", "CHF"),
    SOL_PERUANO("PEN", "S/"),
    PESO_MEXICANO("MXN", "$"),
    REAL_BRASILENO("BRL", "R$"),
    PESO_ARGENTINO("ARS", "$"),
    PESO_CHILENO("CLP", "$"),
    PESO_COLOMBIANO("COP", "$"),
    PESO_URUGUAYO("UYU", "$${"U"}"),
    BOLIVIANO("BOB", "Bs."),
    BOLIVAR_VENEZOLANO("VEF", "Bs.F"),
    WON_SURCOREANO("KRW", "₩"),
    RAND_SUDAFRICANO("ZAR", "R"),
    RUBLO_RUSO("RUB", "₽"),
    LIRA_TURCA("TRY", "₺"),
    CORONA_SUECA("SEK", "kr"),
    CORONA_NORUEGA("NOK", "kr"),
    CORONA_DANESA("DKK", "kr"),
    DOLAR_NEOZELANDES("NZD", "NZ$"),
    DOLAR_SINGAPURENSE("SGD", "S$"),
    DOLAR_HONKONES("HKD", "HK$"),
    ZLOTY_POLACO("PLN", "zł"),
    CORONA_CHECA("CZK", "Kč"),
    FORINTO_HUNGARO("HUF", "Ft"),
    BAHT_TAILANDES("THB", "฿"),
    RINGGIT_MALASIO("MYR", "RM"),
    RUPIA_INDONESIA("IDR", "Rp"),
    PESO_FILIPINO("PHP", "₱"),
    LIBRA_EGIPCIA("EGP", "E£"),
    NAIRA_NIGERIANA("NGN", "₦"),
    DIRHAM_EMIRATI("AED", "د.إ"),
    RIYAL_SAUDI("SAR", "﷼"),
    SHEKEL_ISRAELI("ILS", "₪"),
    DOLAR_TAIWANESES("TWD", "NT$"),
    DONG_VIETNAMITA("VND", "₫");

    companion object {
        val typesMap: Map<String, CurrencyEnum> = entries.associateBy { it.code }

        fun getSymbol(code: String): String = typesMap[code]?.symbol ?: NONE.symbol
    }
}
