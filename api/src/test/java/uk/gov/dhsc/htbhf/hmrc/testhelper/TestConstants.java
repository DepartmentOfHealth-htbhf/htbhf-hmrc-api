package uk.gov.dhsc.htbhf.hmrc.testhelper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestConstants {

    public static final LocalDate LISA_DOB = LocalDate.parse("1985-12-31");
    public static final String LISA_NINO = "EB123456C";
    public static final String LISA_FORENAME = "Lisa";
    public static final String LISA_SURNAME = "Simpson";
    public static final BigDecimal CTC_ANNUAL_INCOME_THRESHOLD = new BigDecimal(408);
    public static final LocalDate ELIGIBLE_START_DATE = LocalDate.parse("2019-01-01");
    public static final LocalDate ELIGIBLE_END_DATE = LocalDate.parse("2019-02-01");
}
