package course.examples.Services.KeyCommon;

interface KeyGenerator {

    String getMonthlyCash(int year);
    String getDailyCash(int month, int day, int year, int days);
    String getYearlyAverage(int year);
    String status(String x);

}