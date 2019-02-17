package com.cmbetting.generator;

import java.util.*;
import java.util.stream.Collectors;

public class NumbersGenerator {

    public static int TOT_RAND_NUMS_TO_CHOOSE_FROM = 1000;

    public static final int LOTTO_MAX = 52;
    public static final int POWERBALL_OTHERS_MAX = 50;
    public static final int POWERBALL_MAX = 20;
    public static final int PICK3_MAX = 9;

    public static final int LOTTO_NUM_BALLS = 6;
    public static final int POWERBALL_NUM_BALLS = 5;
    public static final int PICK3_NUM_BALLS = 3;

    private static Random random;

    static {
        random = new Random();
    }

    public static void main(String[] args) {
//        playLotto();

        playPowerball();

//        playPick3();
    }

    private static void playLotto() {
        List<Integer> toPlayNumbers = getNumbersUsingComplexWay(LOTTO_NUM_BALLS, LOTTO_MAX, false);
//        List<Integer> toPlayNumbers = getNumbersUsingSimpleWay(LOTTO_NUM_BALLS, LOTTO_MAX, false);
        print("lotto", toPlayNumbers);
    }

    private static void playPowerball() {
        List<Integer> toPlayNumbers = getNumbersUsingComplexWay(POWERBALL_NUM_BALLS, POWERBALL_OTHERS_MAX, false);
//        List<Integer> toPlayNumbers = getNumbersUsingSimpleWay(POWERBALL_NUM_BALLS, POWERBALL_OTHERS_MAX, false);
        print("powerball", toPlayNumbers);

        List<Integer> theRealPB = getNumbersUsingComplexWay(1, POWERBALL_MAX, false);
//        List<Integer> theRealPB = getNumbersUsingSimpleWay(1, POWERBALL_MAX, false);
        print("The real powerball", theRealPB);

    }

    private static void playPick3() {
        List<Integer> toPlayNumbers = getNumbersUsingComplexWay(PICK3_NUM_BALLS, PICK3_MAX, true);
//        List<Integer> toPlayNumbers = getNumbersUsingSimpleWay(PICK3_NUM_BALLS, PICK3_MAX, true);
        print("pick3", toPlayNumbers);
    }

    private static List<Integer> getNumbersUsingComplexWay(int numberOfBalls, int maxBall, boolean allowDuplicates) {
        List<List<Integer>> listOfList = getListOfListNumbers(numberOfBalls, maxBall, allowDuplicates);
        while (listOfList.size() > 1) {
            Collections.shuffle(listOfList);
            listOfList = eliminateFirstForListOfList(listOfList);
        }
        return listOfList.get(0);
    }

    private static List<List<Integer>> getListOfListNumbers(int numberOfBalls, int maxBall, boolean allowDuplicates) {
        List<List<Integer>> toPlayListOfList = new ArrayList<>();

        for (int i = 0; i < TOT_RAND_NUMS_TO_CHOOSE_FROM; i++) {
            List<Integer> possibleNums = getNumbersUsingSimpleWay(numberOfBalls, maxBall, allowDuplicates);
            toPlayListOfList.add(possibleNums);
        }
        return toPlayListOfList;
    }

    private static List<Integer> getNumbersUsingSimpleWay(int numberOfBalls, int maxBall, boolean allowDuplicates) {
        List<Integer> toPlayNumbers = new ArrayList<>();

        for (int i = 0; i < numberOfBalls; i++) {
            Integer luckBall;
            if (allowDuplicates) {
                luckBall = getRandomNum(maxBall);
            } else {
                do {
                    luckBall = getRandomNum(maxBall);
                } while (toPlayNumbers.contains(luckBall));
            }
            toPlayNumbers.add(luckBall);
        }
        return toPlayNumbers;
    }

    private static Integer getRandomNum(int maxBall) {
        Integer luckBall;
        List<Integer> numbers = random.ints(1, maxBall + 1).limit(TOT_RAND_NUMS_TO_CHOOSE_FROM).boxed().collect(Collectors.toList());
        while (numbers.size() > 1) {
            Collections.shuffle(numbers);
            numbers = eliminateFirst(numbers);
        }
        luckBall = numbers.get(0);
        return luckBall;
    }

    private static List<Integer> eliminateFirst(List<Integer> numbers) {
        return numbers.stream().skip(1).collect(Collectors.toList());
    }

    private static List<List<Integer>> eliminateFirstForListOfList(List<List<Integer>> numbers) {
        return numbers.stream().skip(1).collect(Collectors.toList());
    }

    private static <T> void print(String label, Collection<T> numbers) {
        String toPrint = numbers.stream().map(item -> item + " ").collect(Collectors.joining(","));
        System.out.println(String.format("%s: (%s)", label, toPrint));
    }

}
