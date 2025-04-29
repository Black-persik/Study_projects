//Ivan Vasilev B24-CSE-01
import java.util.Scanner;

public class Schedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfIntervals = sc.nextInt();
        int[][] data = new int[numberOfIntervals][3];
        for (int i = 0; i < numberOfIntervals; i++) {
            data[i][0] = sc.nextInt();
        }
        for (int i = 0; i < numberOfIntervals; i++) {
            data[i][1] = sc.nextInt();
        }
        for (int i = 0; i < numberOfIntervals; i++) {
            data[i][2] = sc.nextInt();
        }
        System.out.println(findTheMaximumProfit(data, numberOfIntervals));
    }

    public static int[][] bubbleSort(int[][] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j][1] > data[j + 1][1]) {
                    int[] temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
        return data;
    }

    public static int findTheMaximumProfit(int[][] data, int numberOfIntervals) {
        data = bubbleSort(data);
        int[] maxProfit = new int[numberOfIntervals];
        maxProfit[0] = data[0][2];

        for (int i = 1; i < numberOfIntervals; i++) {
            int currentProfit = data[i][2];
            int l = -1; 
            for (int j = i - 1; j >= 0; j--) {
                if (data[j][1] <= data[i][0]) {
                    l = j;
                    break;
                }
            }
            if (l != -1) {
                currentProfit += maxProfit[l];
            }
            maxProfit[i] = Math.max(currentProfit, maxProfit[i - 1]);
        }
        return maxProfit[numberOfIntervals - 1];
    }
}
