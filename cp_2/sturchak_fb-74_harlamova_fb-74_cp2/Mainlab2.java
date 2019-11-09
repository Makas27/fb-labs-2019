package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String path = ("C:\\Users\\kateh\\Documents\\кпи\\крипта\\hello.txt");// текст из инета
        String Internet = ReadText(path);
        String ClearInternet = ClearStr(Internet);

        String PathAlph = ("C:\\Users\\kateh\\IdeaProjects\\лаба2\\alphabet.txt");
        String alph = ReadText(PathAlph);
        String ClearAlph = ClearStr(alph);

        String key = ReadText("C:\\Users\\kateh\\IdeaProjects\\лаба2\\key.txt");
        String clearKey = ClearStr(key);

        String PathCText = ("C:\\Users\\kateh\\IdeaProjects\\лаба2\\makas.txt");
        String criptText = ReadText(PathCText);
        String ClearCText = ClearStr(criptText);

        System.out.println("Зашифрованный текст: ");
        System.out.println(criptText);
//        System.out.println(ClearCText);
//        System.out.println(alph);
//        String key1 = ReadText("C:\\Users\\kateh\\IdeaProjects\\лаба2\\key.txt");
//        System.out.println(key1);
//        System.out.println(ClearAlph);


        System.out.println("Выбирете что вы ходите сделать: ");
        System.out.println("1. Зашифровать текст: ");
        System.out.println("2. Найти индекс соответсвия: ");
        System.out.println("3. Найти длину ключа ШТ: ");
        System.out.println("4. Разбить ШТ на отрезки и найти в каждом самую популярную букву: ");
        System.out.println("5. Разшифровать ШТ с помощью найденего ключа: ");

        Scanner sc = new Scanner(System.in);

        byte a = sc.nextByte();
        float result;

        switch (a) {
            case 1:
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите ключ. Пример: библиотека");
                String key3 = scanner.nextLine();
                String clearKey3 = ClearStr(key3);
                System.out.println("Ваш ключ: ");
                System.out.println(clearKey3);
                System.out.println("Зашифрованный текст: ");
                System.out.println(encrypt(alph, ClearInternet, clearKey3));

                break;

            case 2:
                System.out.println("Индекс соответсвия - " + Index(criptText));
                break;

            case 3:
                for (int i = 1; i <= 32; i++) {
                    String str3 = EncryptText(criptText, i); // выбираем каждый второй символ из ШТ и считаем ИС
                    result = Index(str3);
                    System.out.println("Индекс ключа = " + i + " =  " + result);
                    // Длина ключа равна 16, так как ИС при ключе 16 ближе всего к ИС русского языка

                    //Index for k = 16  ---> 0.054057524
                }
                break;

            case 4:
                //   System.out.println(criptText);
                for (int i = 0; i <= 16; i++) {
                    String strET = EncryptText(criptText, i);
                    System.out.println(strET);
                    Popular(strET);
                }
                break;
            case 5:
                System.out.println(dencrypt(ClearCText, clearKey, ClearAlph));
//                String cipherTextLast = ReadText("C:\\Users\\kateh\\IdeaProjects\\лаба2\\makas.txt");
//                String cClear =ClearStr(cipherTextLast);
//                System.out.println(cipherTextLast);
//                String keyReal = ReadText("C:\\Users\\kateh\\IdeaProjects\\лаба2\\key.txt");
//                String clearKey = ClearStr(keyReal);
//                System.out.println("Your key: ");
//                System.out.println(clearKey);
//                System.out.println("Your open text: ");
//                System.out.println(decrypt(ClearAlph , cClear, clearKey));

                break;
            default:
                System.out.println("Введите привильное число");
                break;

        }
    }


    public static String ReadText(String path) {
        String text = "";
        String buf = "";
        try {
            FileInputStream File = new FileInputStream(path);
            DataInputStream dis = new DataInputStream(File);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));

            while ((buf = br.readLine()) != null) {
                text += buf;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    public static String ClearStr(String text) {
        String str = text.toLowerCase();
        String str1 = str.replaceAll("[^а-я]", "");
        return str1;
    }

    public static String encrypt(String text, String key, String alph) {

        char[] toArrayStr = text.toCharArray();
        char[] toArrayAlph = alph.toCharArray();
        char[] toArrayKey = key.toCharArray();
        String cipherText = "";

        for (int i = 0; i < text.length()- 1; i++) {
            int result = 0;
            result = ((toArrayStr[i] - toArrayKey[i % (toArrayKey.length)]) % 32);
            cipherText += toArrayAlph[result];
        }
        return cipherText;
    }

    public static String dencrypt(String text, String key, String alph) {
        char[] toArrayStr = text.toCharArray();
        char[] toArrayAlph = alph.toCharArray();
        char[] toArrayKey = key.toCharArray();
        String free = "";
        for (int i = 0; i < text.length() - 1; i++) {
            int result = 0;
            result = ((toArrayStr[i] - toArrayKey[i % (toArrayKey.length)]) % 32);
            if (result < 0) result += 32;
            free += toArrayAlph[result];
        }
        return free;
    }


    public static float Index(String Text) {
        char[] strchar = Text.toCharArray();
        float allLetters = Text.length();
        int calc = 0;
        float coefficient;
        char letter = strchar[calc];
        Map<Character, Float> Amount = new HashMap<Character, Float>();
        for (int i = 0; i < Text.length() - 1; i++) {
            letter = strchar[i];

            if (!Amount.containsKey(letter)) {
                Amount.put(letter, (float) 1);
            } else {
                Amount.put(letter, Amount.compute(letter, (k, v) -> (float) v + 1));
            }
        }
        float result = 0;
        for (Map.Entry<Character, Float> entry : Amount.entrySet()){
            result +=((entry.getValue() * (entry.getValue() - 1))*(1 / ((allLetters) * (allLetters - 1))));
        }
//        for (Character key : Amount.keySet()) {
//            //   System.out.format(key + " " +  "%.0f%n",Amount.get(key));
//        }
        //   System.out.println("Result =  " + (result);
        return result ;
    }

    public static String EncryptText(String Text, int len) {
        char[] array = Text.toCharArray();
        String str = "";
        for (int i = len; i < array.length ; i += len) {
            str += array[i];
        }
        return str;
    }


    public static void Popular(String text) {
        char[] strchar = text.toCharArray();
        int calc = 0;
        char letter = strchar[calc];
        Map<Character, Float> Amount = new HashMap<Character, Float>();
        for (int i = 0; i < text.length() - 1; i++) {
            letter = strchar[i];
            if (!Amount.containsKey(letter)) {
                Amount.put(letter, (float) 1);
            } else {
                Amount.put(letter, Amount.compute(letter, (k, v) -> (float) v + 1));
            }
        }

//        float res = 0;
//        for (Map.Entry<Character, Float> entry : Amount.entrySet()) {
//            res = entry.getValue() > res ? entry.getValue() : res;
//            System.out.println(entry.getKey() + " max = " + res);
//        }
//        for (Character key : Amount.keySet()) {
//     res = Amount.get(key) > res ? Amount.get(key) : res;
        //   System.out.println(key + "-" + Amount.get(key));
//            System.out.println(key + " max = " + res

        float maxValueInMap = (Collections.max(Amount.values()));
        for (Map.Entry<Character, Float> entry : Amount.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                System.out.println("Наиболее часто встречающаяся буква: " + entry.getKey());
                break;
            }

        }
    }
}


