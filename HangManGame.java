package MatchWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class HangManGame {
    private static final Scanner sc = new Scanner(System.in);
    private static final Vector<String> wordVector = new Vector<String>();

    private static void readFile() { // words.txt 파일 읽기
        String filename = "words.txt";
        try {
            Scanner fScanner = new Scanner(new FileInputStream(filename));
            while(fScanner.hasNext())
                wordVector.add(fScanner.nextLine()); // 한 라인에 하나의 단어
            fScanner.close();
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            System.out.println(filename + "파일이 존재하지 않습니다.");
        }
        System.out.println("프로젝트 폴더 밑의 words.txt 파일을 읽었습니다...");
    }
    private static String[] RandomWord(){
        int num = (int) (Math.random()* wordVector.size());
        String Word = wordVector.get(num); // 파일에서 단어 하나 뽑기
        String[] word = new String[Word.length()];
        for (int j=0; j<word.length; j++) {
            word[j] = String.valueOf(Word.charAt(j));
        }
        return word;
    }

    public static void main(String[] args) {
        readFile();
        System.out.println("\n지금부터 행맨 게임을 시작합니다.");
        while (true) {
            String[] word = RandomWord();
            String[] Word = word.clone();
            int hideWord = 2; // 가릴 단어 개수 조정
            int[] hideNum = new int[hideWord];
            String[] hide = new String[hideWord]; // 가려진 단어들

            for (int i = 0; i < hideWord; i++) {
                hideNum[i] = (int) (Math.random() * word.length);
                for (int j = 0; j < i; j++) {
                    if (hideNum[i] == hideNum[j]) { // 중복제거
                        i--;
                    }
                }
            }

            for (int i = 0; i < hideWord; i++) {
                int num2 = hideNum[i];
                hide[i] = word[num2];
                word[num2] = "-";
            }

            int count = 0; // 맞추는 횟수

            for(String len : word) System.out.print(len);
            while (true) {
                System.out.println("\n>> ");
                String char2 = sc.nextLine();
                for (int num2 : hideNum) {
                    if (Word[num2].equals(char2)) {
                        word[num2] = char2;
                    }
                }
                count++;
                if (count == 5) break; //횟수 제한
                if ((Arrays.equals(Word, word)))break;
                for (String len : word) System.out.print(len);
            }
            System.out.println("\nNext(y/n)? ");
            String char3 = sc.nextLine();
            if(char3.equals("n")) break;
        }
    }
}
