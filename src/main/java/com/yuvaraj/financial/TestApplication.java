package com.yuvaraj.financial;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.FrequencyHelper;

/**
 * @author Yuvaraj
 */
public class TestApplication {

    public static void main(String[] args) throws InvalidArgumentException {
        FrequencyHelper.DateRange dateRange = FrequencyHelper.Frequency.WEEKLY.getDateRange();
        System.out.println(dateRange.getStartDate().toString());
        System.out.println(dateRange.getEndDate().toString());
        dateRange = FrequencyHelper.Frequency.MONTHLY.getDateRange();
        System.out.println(dateRange.getStartDate().toString());
        System.out.println(dateRange.getEndDate().toString());
        dateRange = FrequencyHelper.Frequency.YEARLY.getDateRange();
        System.out.println(dateRange.getStartDate().toString());
        System.out.println(dateRange.getEndDate().toString());
    }
}
