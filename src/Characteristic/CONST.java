package Characteristic;

/**
 *
 * @author Admin
 */
public class CONST {
    private static final double BYTES_TO_GIGABYTES = 1073741824; //1024^3
    private static final double BYTES_TO_MEGABYTES = 1048576; //1024^2
    
    public static double BytesToGigabytes(long input)
    {
        return Round(input / BYTES_TO_GIGABYTES);
    }
    
    public static double BytesToMegabytes(long input)
    {
        return Round(input / BYTES_TO_MEGABYTES);
    }
    
    public static double Round(double number)
    {
        return Math.round(number * 10.0) / 10.0;
    }
}
