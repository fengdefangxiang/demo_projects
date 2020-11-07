import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MinPathSum {

    public static void main(String[] args) {
        int[][] m = new int[4][4];
        m[0] = new int[]{1, 3, 5, 9};
        m[1] = new int[]{8, 1, 3, 4};
        m[2] = new int[]{5, 0, 6, 1};
        m[3] = new int[]{8, 8, 4, 0};
        System.out.println(getMinPath(m));
    }

    public static int getMinPathSum(int[][] m) {
        // 特殊情况校验
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        int[][] dp = new int[m.length][m[0].length];
        dp[0][0] = m[0][0]; // 填充起始位置的值
        for (int i = 1; i < dp.length; i++) { // 填充最左侧一列的值
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < dp[0].length; j++) { // 填充最上面一行的值
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }

        // 填充剩余位置的值
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }

        // 右下角位置的值就是从左上角到右下角的最下路径和了
        return dp[m.length - 1][m[0].length - 1];
    }

    public static List<Integer> getMinPath(int[][] m) {
        // 特殊情况校验
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return new ArrayList<>();
        }

        int[][] dp = new int[m.length][m[0].length];
        dp[0][0] = m[0][0]; // 填充起始位置的值
        for (int i = 1; i < dp.length; i++) { // 填充最左侧一列的值
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < dp[0].length; j++) { // 填充最上面一行的值
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }

        // 填充剩余位置的值
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }

        // 从右下角开始往左上角走
        int i = m.length - 1;
        int j = m[0].length - 1;
        List<Integer> path = new ArrayList<>();
        path.add(m[i][j]);
        while (i > 0 && j > 0) {
            if (dp[i][j] - m[i][j] == dp[i - 1][j]) {
                path.add(m[i - 1][j]);
                i--;
            } else {
                path.add(m[i][j - 1]);
                j--;
            }
        }

        // 到达最左侧或最上侧后直接把剩余一行或一列的值加上
        if (i > 0) {
            i--;
            for (; i >= 0; i--) {
                path.add(m[i][0]);
            }
        } else {
            j--;
            for (; j >= 0; j--) {
                path.add(m[0][j]);
            }
        }

        // 翻转返回
        Collections.reverse(path);
        return path;
    }

    public static int getMinPathSum2(int[][] m) {
        // 特殊情况校验
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        // 第一行数据
        int[] dpRow = new int[m[0].length];
        dpRow[0] = m[0][0];
        for (int j = 1; j < m[0].length; j++) {
            dpRow[j] = dpRow[j - 1] + m[0][j];
        }

        // 循环计算后续每一行数据
        for (int i = 1; i < m.length; i++) {
            dpRow[0] = dpRow[0] + m[i][0];
            // 滚动更新数组每一项
            for (int j = 1; j < m[0].length; j++) {
                dpRow[j] = Math.min(dpRow[j - 1], dpRow[j]) + m[i][j];
            }
        }

        return dpRow[m[0].length - 1];
    }

}
