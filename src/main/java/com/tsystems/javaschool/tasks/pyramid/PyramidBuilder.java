package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if (isPyramid(inputNumbers.size()) == -1 || inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException("Cannot Build Pyramid");
        }
        List<Integer> sortedList;
        try {
            sortedList = inputNumbers.stream().sorted().collect(Collectors.toList());
        } catch (Exception e) {
            throw new CannotBuildPyramidException("Cannot Build Pyramid");
        }
        int row = isPyramid(inputNumbers.size());
        int column = row * 2 - 1;
        int[][] pyramid = new int[row][column];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = row - i - 1; j < row + i; j += 2) {
                pyramid[i][j] = sortedList.get(index++);
            }
        }
        return pyramid;
    }

    public static int isPyramid(int length) {
        int n = 1;
        int sum = 0;
        while (true) {
            if (length == sum) {
                return n - 1;
            }
            sum += n;
            n++;
            if (sum > length) {
                return -1;
            }
        }

    }
}
