import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String plannedDate = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return plannedDate;
    }
}
