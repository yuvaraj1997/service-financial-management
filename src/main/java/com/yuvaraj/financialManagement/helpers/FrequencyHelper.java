package com.yuvaraj.financialManagement.helpers;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Yuvaraj
 */
public class FrequencyHelper {

    @Getter
    @AllArgsConstructor
    public enum Frequency {
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY"),
        YEARLY("YEARLY");

        final String period;

        public DateRange getDateRange() throws InvalidArgumentException {
            DateRange dateRange = null;
            switch (this.period){
                case "WEEKLY":
                    Date startDateOfTheWeek = DateHelpers.getStartDateOfTheWeek();
                    dateRange = new DateRange(startDateOfTheWeek, DateHelpers.getEndDateOfTheWeek(startDateOfTheWeek));
                    break;
                case "MONTHLY":
                    Date startDateOfTheMonth = DateHelpers.getStartDateOfTheMonth();
                    dateRange = new DateRange(startDateOfTheMonth, DateHelpers.getEndDateOfTheMonth(startDateOfTheMonth));
                    break;
                case "YEARLY":
                    Date startDateOfTheYear = DateHelpers.getStartDateOfTheYear();
                    dateRange = new DateRange(startDateOfTheYear, DateHelpers.getEndDateOfTheyYear(startDateOfTheYear));
                    break;
                default:
                    throw new InvalidArgumentException("Invalid frequency range" , ErrorCode.INVALID_ARGUMENT);
            }

            return dateRange;
        }
    }


    @AllArgsConstructor
    @Getter
    public static class DateRange {

        private Date startDate;

        private Date endDate;

    }
}
