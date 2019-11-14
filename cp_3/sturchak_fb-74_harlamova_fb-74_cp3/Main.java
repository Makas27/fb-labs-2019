package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileReader;

public class Main {
    static String pathToFile =
            "C:\\Users\\kateh\\Documents\\кпи\\крипта\\17.txt";

    public static void main(String[] args) throws IOException {
        String Text = returnText("C:\\Users\\kateh\\Documents\\кпи\\крипта\\17.txt");
     //   System.out.println(Text);
      //  Bigramm(Text);
    //   System.out.println(Inverse(10, 7));


    }

    public static String returnText(String path) throws IOException {
        String everything;
        String line;
        BufferedReader br = new BufferedReader(new
                FileReader(pathToFile));
        try {
            StringBuilder sb = new StringBuilder();
            line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        return everything;
    }

    public static int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }

    public static void Bigramm(String str) {
        char[] strtochar = str.toCharArray();
        Character ch1, ch2;
        Map<String, Integer> BiMap = new HashMap<String, Integer>();
        for (int i = 0; i < str.length() - 1; i++) {
            ch1 = strtochar[i];
            ch2 = strtochar[i + 1];
            String s1 = ch1.toString();
            String s2 = ch2.toString();
            String s3 = s1 + s2;
            if (!BiMap.containsKey(s3)) {
                BiMap.put(s3, 1);
            } else {
                BiMap.put(s3, BiMap.compute(s3, (k, v) -> v + 1));
            }
        }
        Map<String, Integer> topTen;
        topTen = BiMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(topTen);
    }

    public static int Inverse(int a, int m) {
        int t = 0;
        int T = 1;
        int r = m;
        int R = a;
        if (GCD(a, m) != 1) return 0;
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
}