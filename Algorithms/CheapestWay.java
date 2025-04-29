//Ivan Vasilev 
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CheapestWay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        sc.nextLine();
        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int[][] dp = new int[row][col];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < row; i++) {

            dp[i][0] = dp[i - 1][0] + matrix[i][0];

        }
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        System.out.println(dp[row - 1][col - 1]);
        List<Integer> path = new ArrayList<>();
        int i = row - 1, j = col - 1;
        while (i > 0 || j > 0) {
            path.add(matrix[i][j]);
            if (i == 0) j--;
            else if (j == 0) i--;
            else {
                if (dp[i - 1][j] < dp[i][j - 1]) i--;
                else  j--;
            }
        }
        path.add(matrix[0][0]);
        for(int k = path.size() - 1; k >= 0; k --){
            System.out.print(path.get(k) + " ");
        }


    }
}
