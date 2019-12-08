package com.company;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String string = arr().toString().replaceAll("[^0-1]", "");
        fillMap(string);
        corelation(arr());

    }

    public static String smaller(int i) {
        StringBuilder s = new StringBuilder();
        s.append("(?<=\\G");
        for (int j = 0; j < i; j++)
            s.append(".");
        s.append(")");

        return s.toString();
    }

    static void fillMap(String string) throws IOException {
        String[] nGramms;
        HashMap<String, Float> map = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            nGramms = string.split(smaller(i));
            for (String symb : nGramms) {
                if (!symb.isEmpty())
                    if (map.containsKey(symb)) {
                        map.put(symb, map.get(symb) + 1);
                    } else {
                        map.put(symb, (float) 1);
                    }
            }

            String mString = map.toString();
            FileOutputStream fos = new FileOutputStream("file" + i + ".txt");
            System.out.println("Размер " + i + " грамм = " + map.size());
            fos.write(mString.getBytes());
            fos.flush();
            fos.close();
            map.clear();
        }
        //System.out.println(map);
    }


    public static ArrayList arr() {
       // int[] arr1 = {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0};
         int[] arr1 = {1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0};
        int[] arr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        int[] a2 = Arrays.copyOf(arr2, arr2.length);
        int period = 0;
        ArrayList<Integer> arrResult = new ArrayList<>();
        int sum;
        do {
            period++;
            sum = sumOfMas(arr1, arr2);
            arrResult.add(arr2[0]);
            moveLeftTheArray(arr2, sum);
            //System.out.println(Arrays.toString(arr2));

        } while (!ifArr1EqArr2(a2, arr2));
        System.out.println("Период = " + period);

        return arrResult;
    }
    public static boolean ifArr1EqArr2(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    public static int[] moveLeftTheArray(int arr[], int lastNumber) {
        int size = arr.length;
        for (int j = 0; j < size - 1; j++) {
            arr[j] = arr[j + 1];
        }
        arr[size - 1] = lastNumber;

        return arr;
    }

    public static int sumOfMas(int arr1[], int arr2[]) {
        int sum = 0;
        int multiply;
        for (int i = 0; i < arr1.length; i++) {
            multiply = arr1[i] * arr2[i];
            sum += multiply;
        }

        return sum % 2;
    }




    public static void corelation(ArrayList arrayList) {
        int mainSum = 0;
        int result;
        List<Integer> newArrList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                result = (arrayList.get(j % arrayList.size()) == arrayList.get((j + i) % arrayList.size())) ? 0 : 1;
                newArrList.add(result);
                //j++;
            }

            for (int j = 0; j < newArrList.size(); j++) {
                mainSum += newArrList.get(j);
            }
            System.out.println("Сумма  " + i + " = " + mainSum);
            newArrList.clear();
            mainSum = 0;

        }
    }

}



