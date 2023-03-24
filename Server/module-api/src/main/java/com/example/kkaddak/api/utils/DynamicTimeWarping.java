package com.example.kkaddak.api.utils;

public class DynamicTimeWarping {

    private static final int windowSize = 50;
    public static double calculateDistance(float[][] mfcc1, float[][] mfcc2) {
        int n = mfcc1.length;
        int m = mfcc2.length;

        float[][] dtw = new float[n][m];

        // DTW 알고리즘이 대각선 방향으로 경로를 찾을 수 있도록 보장하기 위해 경계 조건 설정
        for (int i = 1; i < n; i++) {
            dtw[i][0] = Float.MAX_VALUE;
        }
        for (int i = 1; i < m; i++) {
            dtw[0][i] = Float.MAX_VALUE;
        }
        // 경계 조건 및 초기화
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                if (Math.abs(i - j) > windowSize) {
//                    dtw[i][j] = Float.MAX_VALUE;
//                }
//            }
//        }
        dtw[1][1] = 0;

//        // 동적 프로그래밍 방법으로 누적 거리 최소값을 구함
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                double cost = euclideanDistance(mfcc1[i], mfcc2[j]);
                dtw[i][j] = (float) (cost + Math.min(Math.min(dtw[i - 1][j], dtw[i][j-1]), dtw[i-1][j-1]));
            }
        }

//        for (int i = 1; i < n; i++) {
//            for (int j = Math.max(1, i - windowSize); j < Math.min(m, i + windowSize); j++) {
//                double cost = euclideanDistance(mfcc1[i], mfcc2[j]);
//                dtw[i][j] = (float) (cost + Math.min(Math.min(dtw[i - 1][j], dtw[i][j - 1]), dtw[i - 1][j - 1]));
//            }
//        }
        return dtw[n-1][m-1] / (n + m);
    }
    private static double euclideanDistance(float[] vector1, float[] vector2) {
        double sum = 0;
        for (int i = 0; i < vector1.length; i++) {
            double diff = vector1[i] - vector2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
