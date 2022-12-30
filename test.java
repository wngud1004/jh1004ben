import java.util.Arrays;
import java.util.Scanner;

public class test {
    public static void CountNum(int[] dice, int m) { // 각 숫자 세는 함수
        int sum = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0;
        int o = 0, f = 0, k = 0, n = 0, d = 0, u = 0;
        int[] diceNum = {1, 2, 3, 4, 5, 6};
        int[] sangdan = new int[6];

        for (int i = 0; i < m; i++) {
            sum += dice[i];

            if(dice[i] == 1) { // 1
                one += dice[i];
                sangdan[0] = one;
                o++;
            }
            if(dice[i] == 2) { // 2
                two += dice[i];
                sangdan[1] = two;
                f++;
            }
            if(dice[i] == 3) { // 3
                three += dice[i];
                sangdan[2] = three;
                k++;}
            if(dice[i] == 4) { // 4
                four += dice[i];
                sangdan[3] = four;
                n++;
            }
            if(dice[i] == 5) { // 5
                five += dice[i];
                sangdan[4] = five;
                d++;
            }
            if(dice[i] == 6) { // 6
                six += dice[i];
                sangdan[5] = six;
                u++;
            }
        }

        //출력
        System.out.println("모든 눈의 합 :" + sum);
        for (int j = 0; j < 6; j++) {
            if(sangdan[j] != 0) System.out.println(diceNum[j] + " 의 합 :" + sangdan[j]);
        }

        if(o == 3 || f == 3|| k == 3 || n == 3 || d == 3 || u == 3 ) {
            if(o == 2 || f == 2|| k == 2 || n == 2 || d == 2 || u == 2 ) {
                System.out.println("full house!!");
            }
        }

        if(o == 4 || f == 4|| k == 4 || n == 4 || d == 4 || u == 4 ) System.out.println("4 of Kind!");
        if(o == 5 || f == 5 || k == 5 || n == 5 || d == 5 || u == 5 ) System.out.println("!!!Yacht!!!");
        if(o == 1 && f == 1 && k == 1 && n == 1 || f == 1 && k == 1 && n == 1 && d == 1 || k == 1 && n == 1 && d == 1 && u == 1) System.out.println("Small_Straight!");
        if(o == 1 && f == 1 && k == 1 && n == 1 && d == 1 || f == 1 && k == 1 && n == 1 && d == 1 && u == 1) System.out.println("Large_Straight!!");

    }
    public static void main(String[] args) {
        while (true) {
            // 변수 선언
            Scanner sc = new Scanner(System.in);
            int sum = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0;
            int[] dice = new int[5];
            int[] diceNum = {1, 2, 3, 4, 5, 6};

            System.out.println("돌릴 주사위 개수 :");
            int n = sc.nextInt();
            if(n == 0) break; // 0 넣으면 좋료

            //랜덤 숫자 뽑아 배열에 넣기
            for (int i = 0; i < n; i++) {
                int num = (int) (Math.random() * 6) + 1;
                dice[i] = num;
            }

            //숫자 들어간 배열 정렬
            Arrays.sort(dice);

            //배열 출력
            System.out.print("[");
            for (int i = 0; i < n; i++) {
                System.out.print(dice[i]);
                sum += dice[i];
            }
            System.out.print("] \n");

            CountNum(dice, n);

            // 주사위 수정
            int flag = 1;
            while (flag < 3) { // 최대 다시 뽑을 수 있는 주사위 수
                // 바꿀 주사위 개수와 번호 받기
                System.out.println("바꿀 주사위 개수 :");
                int s = sc.nextInt();
                if (s == 0) break; // 0넣으면 확정


                for (int i = 0; i < s; i++) {
                    System.out.println("바꿀 주사위 번수 :");
                    int m = sc.nextInt();


                    // 개수만큼 다시 랜덤으로 뽑아 배열에 저장
                    int num1 = (int) (Math.random() * 6) + 1;
                    dice[m - 1] = num1;
                }

                // 다시 뽑은 배열 정렬
                Arrays.sort(dice);

                // 배열 출력
                System.out.print("[");
                for (int i = 0; i < n; i++) {
                    System.out.print(dice[i]);
                }
                System.out.print("] \n");

                CountNum(dice, n);
                flag++;
            }

        }
    }
}