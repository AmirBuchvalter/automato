package helpers;

public class OperatingSystemHelper {

    public String getCurrentOperatingSystem()
    {
        return System.getProperty("os.name").toLowerCase();
    }
}
