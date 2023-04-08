package com.yuvaraj.financial.helpers;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * @author Yuvaraj
 */
public class FrequencyHelper {

    public static void validateFrequencyForCustom(Frequency frequency, Date startDate, Date endDate) throws InvalidArgumentException {
        if (Objects.equals(frequency.getPeriod(), FrequencyHelper.Frequency.CUSTOM.getPeriod())) {
            if (null == startDate) {
                throw new InvalidArgumentException("startDate must not be null", ErrorCode.INVALID_ARGUMENT);
            }
            if (null == endDate) {
                endDate = startDate;
            }
            if (endDate.before(startDate)) {
                throw new InvalidArgumentException("endDate cannot be before start date", ErrorCode.INVALID_ARGUMENT);
            }

            frequency.setCustomDateRange(startDate, endDate);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Frequency {
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY"),
        YEARLY("YEARLY"),
        CUSTOM("CUSTOM");

        final String period;
        DateRange dateRange;

        Frequency(String period) {
            this.period = period;
        }

        public DateRange getDateRange() throws InvalidArgumentException {
            if (null != this.dateRange) {
                return this.dateRange;
            }

            DateRange dateRange = null;
            switch (this.period) {
                case "MONTHLY":
                    Date startDateOfTheMonth = DateHelpers.getStartDateOfTheMonth();
                    dateRange = new DateRange(startDateOfTheMonth, DateHelpers.getEndDateOfTheMonth(startDateOfTheMonth));
                    break;
                case "YEARLY":
                    Date startDateOfTheYear = DateHelpers.getStartDateOfTheYear();
                    dateRange = new DateRange(startDateOfTheYear, DateHelpers.getEndDateOfTheyYear(startDateOfTheYear));
                    break;
                case "WEEKLY":
                default:
                    Date startDateOfTheWeek = DateHelpers.getStartDateOfTheWeek();
                    dateRange = new DateRange(startDateOfTheWeek, DateHelpers.getEndDateOfTheWeek(startDateOfTheWeek));
                    break;
            }

            return dateRange;
        }

        public void setCustomDateRange(Date startDate, Date endDate) {
            this.dateRange = new DateRange(DateHelpers.resetToStartOfTheDay(startDate), DateHelpers.resetToEndOfTheDay(endDate));
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DateRange {

        private Date startDate;

        private Date endDate;

    }
}
