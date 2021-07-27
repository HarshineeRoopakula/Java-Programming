import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class LinearRegression {
    private static final String FILE_LOCATION = "src/main/java/input.txt";
    static final List<Float> columnXValues = new ArrayList<>();
    static final List<Float> columnYValues = new ArrayList<>();

    private static void readFile() {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(LinearRegression.FILE_LOCATION));
            String line = bReader.readLine();
            float x, y;
            System.out.println("X    Y");
            while (line != null) {
                String[] fields = line.split("\\s+");
                // Get the 1st column
                x = Float.parseFloat(fields[0]);
                // Adding the 1st column values to a list
                columnXValues.add(x);
                // Get the second column
                y = Float.parseFloat(fields[1]);
                // Adding the 2nd column values to a list
                columnYValues.add(y);
                System.out.println(x + " " + y);
                System.out.println();
                // Read next line
                line = bReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading the file, message: ");
            e.printStackTrace();
        }
    }

    private static void calculateLinearRegression() {
        System.out.println("The values of X(First Score) are:" + LinearRegression.columnXValues);
        System.out.println("The values of Y(Second Score) are:" + LinearRegression.columnYValues);

        float sumX = Util.sum(LinearRegression.columnXValues);
        System.out.println();
        System.out.println("Sum of first scores(ΣX):" + sumX);

        float sumY = Util.sum(LinearRegression.columnYValues);
        System.out.println("Sum of second scores(ΣY):" + sumY);

        List<Float> multiplyXY = Util.mul(LinearRegression.columnXValues, LinearRegression.columnYValues);
        System.out.println();
        System.out.println("Product of first and Second Scores (XY): " + multiplyXY);

        float sumXY = Util.sum(multiplyXY);
        System.out.println("Sum of the product of first and Second Scores (ΣXY): " + sumXY);
        System.out.println();

        List<Float> multiplyXX = Util.mul(LinearRegression.columnXValues, LinearRegression.columnXValues);
        System.out.println("Square of First Scores(X*X): " + multiplyXX);

        float sumXX = Util.sum(multiplyXX);
        System.out.println("Sum of squares of First Scores(ΣX*X): " + sumXX);
        System.out.println();

        double b = Util.getB(sumX, sumY, sumXY, sumXX);
        System.out.println("Value of Slope(b) is: " + b);

        double a = Util.getA(sumX, sumY, b);
        System.out.println("Value of Intercept(a) is: " + a);
        System.out.println();
        System.out.println("Regression Equation(y) = a + bx ");
        System.out.println("y=" + a + "+" + b + "x");
        System.out.println("Suppose if we want to know the approximate y value for the variable x = 64. " +
                "Then we can substitute the value in the above equation.");

        double ypred = Util.getYpred(b, a);
        System.out.println("y predicted for value x=64 is " + ypred);
    }

    public static void main(String[] args) {
        readFile();
        calculateLinearRegression();
    }

}

final class Util {
    private Util() {
    }

    static float sum(List<Float> list) {
        float sum = 0;
        for (float i : list) {
            sum += i;
        }
        return sum;
    }

    static List<Float> mul(List<Float> list, List<Float> list1) {
        float[] result = new float[list.size()];
        List<Float> mulResult = new ArrayList<>();

        IntStream.range(0, list.size()).forEach(i -> {
            result[i] = list.get(i) * list1.get(i);
            mulResult.add(result[i]);
        });

        return mulResult;
    }

    static double getA(float sumX, float sumY, double b) {
        return Math.round((sumY - b * sumX) / LinearRegression.columnXValues.size() * 1000.00) / 1000.00;
    }

    static double getB(float sumX, float sumY, float sumXY, float sumXX) {
        return Math.round((LinearRegression.columnXValues.size() * sumXY - sumX * sumY) /
                (LinearRegression.columnXValues.size() * sumXX - Math.pow(sumX, 2.0)) * 100.00) / 100.00;
    }



    static double getYpred(double b, double a) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the X value for which you want to predict Y");
        float x=sc.nextFloat();
        return Math.round((a + b * x) * 100.00) / 100.00;
    }
}
    