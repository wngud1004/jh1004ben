import java.io.*;

public class DotComtestcode {
    public static void main(String[] args) {

        int numOfGuesses = 0;
        help helper = new help();

        SimpleDotCom theDotCom = new SimpleDotCom();
        int randomNum = (int)(Math.random()*5);

        int[] locations = {randomNum,randomNum+1,randomNum+2};
        theDotCom.setLocationCells(locations);
        boolean isAlive = true;

        while(isAlive) {

            String guess = helper.getUserInput("숫자 입력");
            String result = theDotCom.checkYourself(guess);
            numOfGuesses++;

            if(result.equals("맞춤")) {
                isAlive = false;
                System.out.println(numOfGuesses + " 횟수");
            } // if문 끝
        } // while문 끝
    } // main문 끝
} // class문 끝

class SimpleDotCom {
    int[] locationCells;
    int numOfHits = 0;

    public void setLocationCells(int[] locs) {
        locationCells = locs;
    }

    public String checkYourself(String stringGuess) {
        int guess = Integer.parseInt(stringGuess);
        String result = "없음";
        for(int i=0; i < locationCells.length; i++) {
            if(guess == locationCells[i]) {
                result = "찾음";
                numOfHits++;
                break;
            }
        }
        if(numOfHits == locationCells.length) {
            result="다 찾음";
        }
        System.out.println(result);
        return result;
    }
}

class help {
    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + " ");
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if(inputLine.length() == 0) return  null;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }
}
