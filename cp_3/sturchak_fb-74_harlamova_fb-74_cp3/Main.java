package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileReader;

public class Main {


    public static void main(String[] args) throws IOException {
        String text = readText("C:\\Users\\kateh\\Documents\\кпи\\крипта\\19.txt");
        String cText = text.replaceAll("[^а-я]", "");
        result(topOpen(), topClose(),cText);
//        solveEquation(509, 100, 961);
    }

    static String readText(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        String res = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str + " ");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        res = sb.toString();
        return res;

    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        //  System.out.println(gcd(b, a % b));
        return gcd(b, a % b);
    }

    public static void bigramm(String str) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String[] words = str.split("(?!^)");
        for (int i = 0; i < str.length() - 1; i++) {
            String s = words[i];
            String s1 = words[i + 1];
            String s2 = s + s1;
            if (!s2.isEmpty())
                if (map.containsKey(s2)) {
                    map.put(s2, (map.get(s2) + 1));
                } else {
                    map.put(s2, 1);
                }
        }

        Map<String, Integer> top;
        top = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(top);
    }

    public static int inverse(int a, int m) {
        int t = 0;
        int T = 1;
        int r = m;
        int R = a;
        if (gcd(a, m) != 1) return 0;
        else {
            while (R != 0) {
                int q = r / R;

                int newt = t - q * T;
                t = T;
                T = newt;

                int newr = r - q * R;
                r = R;
                R = newr;
            }
        }
        if (t < 0) t += m;
        return t;
    }

    public static int index(char c) {
        String string = "абвгдежзийклмнопрстуфхцчшщьыэюя";
        char[] ch = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (ch[i] == c) {
//                System.out.println(i);
                return i;
            }

        }
        return -1;
    }

    public static char indexNew(int number) {
        String string = "абвгдежзийклмнопрстуфхцчшщьыэюя";

        char[] ch = string.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (i == number) {
//                System.out.println(ch[i]);
                return ch[i];
            }
        }
        return 0;
    }

    public static ArrayList<Integer> topClose() {

        ArrayList<String> bigrams = new ArrayList<>(5);
        bigrams.add("уф");
        bigrams.add("иж");
        bigrams.add("ьи");
        bigrams.add("кщ");
        bigrams.add("хф");

        ArrayList<Integer> numbers = getNumberFromBigram(bigrams);
//        numbers.forEach(System.out::println);
        return numbers;
    }

    public static ArrayList<Integer> topOpen() {

        ArrayList<String> bigrams = new ArrayList<>(5);
        bigrams.add("ст");
        bigrams.add("но");
        bigrams.add("то");
        bigrams.add("на");
        bigrams.add("ен");

        ArrayList<Integer> numbers = getNumberFromBigram(bigrams);
//        numbers.forEach(System.out::println);
        return numbers;
    }

    public static void result(ArrayList<Integer> topOpen, ArrayList<Integer> topClose, String criptedText) {
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
        int[] closeBigramm = new int[5];
        for (int i = 0; i < 5; i++) {
            closeBigramm[i] = topClose.get(i);
        }
        int[] openBigramm = new int[5];
        for (int i = 0; i < 5; i++) {
            openBigramm[i] = topOpen.get(i);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {

                        y = inverse((closeBigramm[i] - closeBigramm[j]), 961);
                        x = openBigramm[k] - openBigramm[l];

                        if (y < 0) y += 961;
                        if (x < 0) x += 961;

                        a = (x * y) % 961;

                        if (a < 0) a += 961;
                        if (a > 1) {
                            b = (closeBigramm[i] - (inverse(a, 961) * openBigramm[k])) % 961;
                        }
                        if (b < 0) b += 961;
                        if (solveEquation(a, b, 961) < 0) {
                            System.out.println();
                        }
                        System.out.println("a = " + a + " " + "b = " + b);
                        String o = String.valueOf(indexNew(a / 31));
                        String o1 = String.valueOf(indexNew(a % 31));
                        String o2 = String.valueOf(indexNew(b / 31));
                        String o3 = String.valueOf(indexNew(b % 31));
                        System.out.println(("(" + o + o1 + " , " + o2 + o3 + ")").toString());
                        prov(decrypt(criptedText, a, b));
                        System.out.println("-------------");
                    }
                }
            }
        }
    }

    public static ArrayList getNumberFromBigram(ArrayList<String> bigramms) {
        ArrayList<Integer> numbers = new ArrayList<>(5);
        int number;

        for (int i = 0; i < bigramms.size(); i++) {
            ArrayList<Character> chars = getFromBigramToChars(bigramms.get(i));
            number = index(chars.get(0)) * 31 + index(chars.get(1));
            numbers.add(number);
        }
        return numbers;
    }

    public static ArrayList getFromBigramToChars(String bigram) {
        char[] twoChars = bigram.toCharArray();
        ArrayList<Character> chars = new ArrayList<>();
        chars.add(twoChars[0]);
        chars.add(twoChars[1]);

        return chars;
    }


    public static int solveEquation(int a, int b, int n) {
        int x = 0;
        if (gcd(a, n) == 1) {
            x = (inverse(a, n) * b) % n;
            System.out.println(" x = " + x);
        }
        if (gcd(a, n) > 1) {
            if ((b / gcd(a, n) != 0)) {
                System.out.println("Error");
                return -1;
            } else if ((b / gcd(a, n)) == 0) {
                int temp = b / gcd(a, n);
                for (int i = 0; i < temp; i++) {
                    x += temp * i + inverse(a, n) % n;
                    System.out.println("x " + i +" = "+x);
                }
            }
        }
        return x;
    }


    static public int funcY(String ab) {
        char[] newAB = ab.toCharArray();
        int a = index(newAB[0]);
        int b = index(newAB[1]);
        int y = a * 31 + b;
//        System.out.println(y);
        return y;
    }

    static public String decrypt(String str, int a, int b) {
        String[] words = str.split("(?<=\\G.{2})");
        String Text = "";
        for (String symb : words) {
            int open = a * (funcY(symb) - b) % 961;
            if (open < 0) open += 961;
            int el1 = open / 31;
            int el2 = open % 31;
            String s = String.valueOf(indexNew(el1));
            String s1 = String.valueOf(indexNew(el2));
            Text += s + s1;
        }
//        System.out.println(Text);
        return Text;
    }


    public static void prov(String str) {

        boolean isContain = str.contains("кщ");
        if (isContain == true) {
            System.out.println("Невозможное сочитание : кщ");
        }

        boolean isContain1 = str.contains("шя");
        if (isContain1 == true) {
            System.out.println("Невозможное сочитание : шя");
        }

        boolean isContain2 = str.contains("чц");
        if (isContain2 == true) {
            System.out.println("Невозможное сочитание : чц");
        }

        boolean isContain3 = str.contains("вй");
        if (isContain3 == true) {
            System.out.println("Невозможное сочитание : вй");
        }

        boolean isContain4 = str.contains("гю");
        if (isContain4 == true) {
            System.out.println("Невозможное сочитание : гю");
        }

        boolean isContain5 = str.contains("жш");
        if (isContain5 == true) {
            System.out.println("Невозможное сочитание : жш");
        }
        boolean isContain6 = str.contains("ааааа");
        if (isContain6 == true) {
            System.out.println("Частое повторение : а");
        } else if (isContain == false && isContain1 == false && isContain2 == false && isContain3 == false && isContain4 == false && isContain5 == false && isContain6 == false)
            System.out.println(str);
    }

}





