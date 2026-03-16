import java.time.LocalDate;

public class DataRecord {
    private LocalDate date;
    private double sales;

    public DataRecord(LocalDate date, double sales) {
        this.date = date;
        this.sales = sales;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getSales() {
        return sales;
    }
}