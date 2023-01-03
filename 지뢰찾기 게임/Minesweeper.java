import java.util.ArrayList;
import java.io.*;
import java.util.Objects;


public class Minesweeper {
    private final helper helper = new helper(); //헬퍼 인스턴스 생성
    private final ArrayList<Minesweep> MinesList = new ArrayList<>(); //Minesweep 객체로 이루어진 arraylist를 만든다
    private int numOfGuesses = 0; //몇 번 시도했는지 저장

    private void setUpGame() { //객체 몇 개를 만ㄷ르고 위치를 지정
        Minesweep one = new Minesweep(); // 객체 3개를 만들어 각각 이름을 부여
        one.setName("첫번째 지뢰");
        Minesweep two = new Minesweep();
        two.setName("두번째 지뢰");
        Minesweep three = new Minesweep();
        three.setName("세번째 지뢰");

        MinesList.add(one); // arraylist에 저장
        MinesList.add(two);
        MinesList.add(three);

        System.out.println("당신의 목표는 3개의 지뢰를 찾아내는 것입니다!"); // 사용자에게 간단한 게임 방법을 설명
        System.out.println("지뢰엔 첫번째 지뢰, 두번째 지뢰, 세번쨰 지뢰가 있습니다.");
        System.out.println("가장 적은 수의 시도로 모든 지뢰를 찾으십시오.");

        for (Minesweep MineToSet: MinesList) { //목록에 각 Minesweep에 대해 반복
            ArrayList<String> newLocation = helper.PlaceMine(3); // Minesweep의 위치를 지정하기 위한 메소드를 호출
            MineToSet.setLocationCells(newLocation); // 이 Minesweep객체의 세터 매소드를 호출하여 방금 보조 메소드에서 받아온 위치를 지정
        } //for 순환문 끝
    } // setUpGame 메소드 끝

    private void startPlaying() {
        while (!MinesList.isEmpty()) { // ( MinesList.isEmpty() == false)
            String userGuess = helper.getUserInput("구역을 입력하세요 : "); // 입력 받기
            checkUserGuess(userGuess); // checkUserGuess 메소드를 호출
        } //while 문 끝
        finishGame(); // finishGame 메소드를 호출
    }

    private void checkUserGuess(String userGuess) { //userGuess를 테스트
        numOfGuesses++; // 사용자가 추측한 횟수를 증가시킵니다.
        String result = "이 구역엔 아무것도 없습니다."; // 따로 바꾸지 않으면 구역에 없다고 가정

        for (Minesweep mineToTest : MinesList) { // 목록에 들어있는 모든 Minesweep 객체에 대해 반복
            result = mineToTest.checkYourself(userGuess); //checkYourself 메소드를 통해 나오는 결과값이 hit이면 멈추고 다음 guess 확인, kill이면 그 닷컴을 지움
            if (result.equals("지뢰를 찾았습니다.")) {
                break;
            }

            if (result.equals("지뢰 하나를 없앴습니다!") ) {
                MinesList.remove(mineToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("게임이 끝났습니다.");
        if (numOfGuesses <= 18) {
            System.out.println("당신에게 필요한건 오직" + numOfGuesses + "번의 시도 뿐이였군요..!");
        } else {
            System.out.print("당신은 모든 지뢰를 찾아내기까지" + numOfGuesses + "번의 시도를 했습니다.");
        }
    }

    public static void main (String[] args) {
        Minesweeper game = new Minesweeper();
        game.setUpGame();
        game.startPlaying();
    }
}

class Minesweep {
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList loc) {
        locationCells = loc;
    }

    public void setName(String n) {
        name = n;
    }

    public String checkYourself(String userInput) {
        String status = "이 구역엔 아무것도 없습니다";
        int index = locationCells.indexOf(userInput);
        if (index >= 0) {
            locationCells.remove(index);
            if (locationCells.isEmpty()) {
                status = "지뢰 하나를 없앴습니다";
                System.out.println("오! 당신은 " + name + "를 찾았습니다!");
            } else {
                status = "지뢰를 찾았습니다";
            }
        }
        return status;
    }
}

class helper {
    private static final String alphabet = "abcdefg";
    private final int gridSize = 49;
    private final int [] grid = new int[gridSize];
    private int comCount = 0;

    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + " ");;
        try {
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0) return null;
        } catch (IOException e) {
            System.out.println("OException: " + e);
        }
        return Objects.requireNonNull(inputLine).toLowerCase();
    }

    public ArrayList<String> PlaceMine(int comSize) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        String temp = null;
        int [] coords = new int[comSize];
        int attempts = 0;
        boolean success = false;
        int location = 0;
        comCount++;
        int incr = 1;

        int gridLength = 7;
        if ((comCount % 2) ==1) {
            incr = gridLength;
        }

        while(!success&attempts++ < 200) {
            location = (int) (Math.random() * gridSize);
            //System.out.print(" Try " + location);
            int x = 0;
            success = true;

            while (success && x < comSize) {
                if (grid[location] == 0) {
                    coords[x++] = location;
                    location += incr;

                    if (location >= gridSize) {
                        success = false;
                    }
                    if (location % gridLength == 0) {
                        success = false;
                    }
                } else {
                    //System.out.print(" used " + location);
                    success = false;
                }
            }
        }

        int x = 0;
        int row = 0;
        int column = 0;
        //System.out.println("\n");

        while (x < comSize) {
            grid[coords[x]] = 1;
            row = (int) (coords[x] / gridLength);
            column = coords[x] % gridLength;
            temp = String.valueOf(alphabet.charAt(column));
            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;
            //System.out.print(" coord " + x + " = " + alphaCells.get(x-1));
        }
        //System.out.println("\n?");
        return alphaCells;
    }
}
