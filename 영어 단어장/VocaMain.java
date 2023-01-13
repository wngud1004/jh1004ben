import java.util.Scanner;

public class VocaMain {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Vocaburary voca = new Vocaburary(("voca1800.txt"));
        voca.load(); //파일을 읽고 word객체를 생성해 해쉬맵에 저장

        while (true) {
            System.out.println("1.Find, 2.Add, 3.List, 4.Delete, 0.Exit >>>");
            int menu = in.nextInt();
            if (menu == 0) break;
            switch (menu) {
                case 1 -> voca.find();
                case 2 -> voca.addWord();
                case 3 -> voca.list();
                case 4 -> voca.delete();
            }
        }
    }
}
