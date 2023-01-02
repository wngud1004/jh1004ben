import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class NumberBallGame extends JPanel implements ActionListener, ChangeListener{
    JTabbedPane pane; // JTabbedPane 초기화
    Button button1 = new Button("입력");
    TextField keyb = new TextField(10);
    TextArea txtStatus = new TextArea(15,55); // 게임 진행 및 결과를 표시할 TextArea 생성
    int[] CN = new int[4];
    int[] PN = new int[4];
    int num = 35, count = 0, mynum = 0, gamecount = 1; // 입력받을 숫자 및 인덱스 변수 생성
    String PNum;
    boolean flag = false;

    public NumberBallGame(){
        // NumberBallGame 생성자
        JPanel panel;
        pane = new JTabbedPane(); // 탭 생성
        setLayout(new BorderLayout());
        String helpMessage ="\n\t랜덤으로 0~9까지의 숫자 중 중복되지 않는 4자리 숫자를 정하고\n\t그 숫자가 무엇인지 찾는 게임이다.\n\n\t플레이어가 임의의 4자리 숫자를 선택하면 랜덤숫자와 비교하여\n\t숫자와 자리가 같다면 strike 숫자면 같다면 ball \n\t둘다 같지 않다면 out이 나온다. ";
        computer(); // 게임 시작시 임의의 수 생성

        //게임 플레이 공간
        panel = new JPanel();
        panel.add(new Label("숫자 입력 :"));
        panel.add(keyb);
        keyb.addActionListener(this);
        panel.add(button1);
        button1.addActionListener(this); // 버튼을 눌렀을 때 실행할 ActionListner 연결
        panel.add(txtStatus);
        pane.addTab("게임",panel);

        //도움말 공간
        panel = new JPanel();
        TextArea help= new TextArea(helpMessage,15,55); // 도움말 TextArea 출력
        panel.add(new Label("도움말:"));
        panel.add(help);
        pane.addTab("도움말",panel);
        pane.setSelectedIndex(0);

        pane.addChangeListener(this);
        add(pane,"Center");
    }

    static boolean isNumber(String str) {
        boolean result = true;
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            // 숫자가 아니라면
            if (c < 48 || c > 57) {
                result = false;
                break;
            }
        }
        return result;
    }

    // 임의의 수 생성
    public void computer(){
        Random r = new Random();
        do {
            for (int i = 0; i < 4; i++) {
                CN[i] = r.nextInt(10);
                for (int j = 0; j < i; j++) {
                    if (CN[i] == CN[j]) {
                        i--;
                    }
                }
            }
        }while (CN[0] == 0);

        num = 1000 * CN[0] + 100 * CN[1] + 10 * CN[2] + CN[3];
        System.out.println("생성된 임의의 수:"+num);
        gamecount = 1; // 게임 카운터 초기화
        txtStatus.setText("임의의 4자리 숫자가 생성되었습니다.\n중복되지 않는 4자리 숫자를 입력해주세요.\n");
    }

    // 숫자 입력받기
    public void player() {
        PNum = keyb.getText();
        if(PNum == null || PNum.length() == 0) {
            flag = true;
        } else {
            if (!isNumber(PNum)) {
                txtStatus.setText(txtStatus.getText() + "<입력하신 " + PNum + "은(는) 옳바른 입력 값이 아닙니다.>\n");
                flag = true;
            } else {
                if (PNum.length() == 4) {
                    mynum = Integer.parseInt(PNum);
                    flag = false;
                } else {
                    txtStatus.setText(txtStatus.getText() + "<입력하신 " + PNum + "은(는) 옳바른 입력 값이 아닙니다.>\n");
                    flag = true;
                }
            }
            keyb.setText(""); // 숫자 입력 후 입력 필드를 초기화 합니다.
        }
    }

    //게임 시작
    public void compare(){
        PN[0] = mynum / 1000;
        PN[1] = (mynum % 1000) / 100;
        PN[2] = (mynum % 1000 % 100) / 10;
        PN[3] = mynum % 1000 % 100 % 10;

        int[] c = new int[2];
        int correctN = 0;
        int result = 0;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (CN[i] == PN[j]) {
                    correctN++;
                }
            }
        }
        System.out.println("맞는 숫자 개수" + correctN);
        c[0] = correctN;
        for (int i = 0; i <= 3; i++) {
            if (CN[i] == PN[i]) {
                result++;
            }
        }
        System.out.println("스트라이크 개수" + result);
        c[1] = result;

        int strike = c[1];
        int ball = c[0] - c[1];
        int out = 4 - ball - strike;
        int sum = strike + ball + out;

        System.out.println("strike " + strike + ", ball " + ball + ", out" + out);
        if(sum == 4) {
            if (strike == 4) {
                txtStatus.setText(txtStatus.getText() + "\n★★★★★정답★★★★★\n");
                txtStatus.setText(txtStatus.getText() + "게임이 끝났습니다\n"+ "[ " + gamecount + " 번] 만에 정답을 맟추셨습니다!\n");
                txtStatus.setText(txtStatus.getText() + "정답은 " + num + " 입니다.<정답을 다시 입력하면 게임을 다시 시작합니다>\n");
                count++;
            } else {
                txtStatus.setText(txtStatus.getText() + "\n입력하신 " + mynum + " 은\n");
                txtStatus.setText(txtStatus.getText() + "Strike " + strike + "개 " + "Ball " + ball + "개 " + "Out " + out + "개 \n");
                gamecount++;
            }
        }
        if(count == 2){
            computer();//다시 새로운 숫자 생성
            count = 0;
        }
    }

    // 윈도우 사이즈
    public Dimension getPreferredSize(){
        return new Dimension(500, 350);
    }
    // 탭이 전환될 때 효과
    public void stateChanged(ChangeEvent e){
        int curSelIndex = pane.getSelectedIndex();
        String curPaneTitle = pane.getTitleAt(curSelIndex);
        System.out.println("["+curPaneTitle + "]탭이 선택되었습니다");
    }
    //액션 추가
    public void actionPerformed(ActionEvent e) {
        player();
        if(!flag) compare();
    }

    // 메인 함수
    public static void main(String s[]){
        JFrame frame = new JFrame("숫자 야구 게임");
        NumberBallGame panel = new NumberBallGame();

        frame.setForeground(Color.black);
        frame.setBackground(Color.lightGray);
        frame.getContentPane().add(panel,"Center");

        frame.setSize(panel.getPreferredSize());
        extracted(frame);
        //원래 위의 문장은 frame.show(); 였음.

        frame.setDefaultCloseOperation(
                WindowConstants.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    private static void extracted(JFrame frame) {
        frame.show();
        //frame.show();
    }
}
