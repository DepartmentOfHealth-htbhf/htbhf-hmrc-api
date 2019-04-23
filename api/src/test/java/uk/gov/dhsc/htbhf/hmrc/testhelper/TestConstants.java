package uk.gov.dhsc.htbhf.hmrc.testhelper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestConstants {

    public static final String HOUSEHOLD_INDENTIFIER = "household1";
    public static final String SIMPSONS_SURNAME = "Simpson";
    public static final String SIMPSONS_ADDRESS_LINE_1 = "742 Evergreen Terrace";
    public static final String SIMPSONS_ADDRESS_LINE_2 = "Mystery Spot";
    public static final String SIMPSONS_TOWN_OR_CITY = "Springfield";
    public static final String SIMPSONS_POSTCODE = "AA11AA";

    public static final LocalDate LISA_DATE_OF_BIRTH = LocalDate.now().minusMonths(24);
    public static final String LISA_FORENAME = "Lisa";

    public static final String HOMER_NINO = "EB654321B";
    public static final String HOMER_FORENAME = "Homer";
    public static final LocalDate HOMER_DATE_OF_BIRTH = LocalDate.parse("1985-12-31");

    public static final String MARGE_NINO = "EB876543A";
    public static final String MARGE_FORENAME = "Marge";

    public static final String MAGGIE_FORENAME = "Maggie";
    public static final LocalDate MAGGIE_DATE_OF_BIRTH = LocalDate.now().minusMonths(6);

    public static final String BART_FORENAME = "BART";
    public static final LocalDate BART_DATE_OF_BIRTH = LocalDate.now().minusMonths(48);

    public static final BigDecimal CTC_ANNUAL_INCOME_THRESHOLD = new BigDecimal(408);
    public static final LocalDate ELIGIBLE_START_DATE = LocalDate.parse("2019-01-01");
    public static final LocalDate ELIGIBLE_END_DATE = LocalDate.parse("2019-02-01");

}
