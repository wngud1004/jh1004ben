import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Vocaburary {
    private final HashMap<String, Word> voca; // 해쉬맵으로 어휘집 정의
    private final String filename;
    private final Scanner in = new Scanner(System.in);

    public Vocaburary(String file) {
        filename = file;
        voca = new HashMap<>();
    }

    // 입력 파일을 열고 데이터 불러오기
    public void load() {
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader buf = new BufferedReader(reader);

            String line;
            while ((line = buf.readLine()) != null) {
                String eng;
                ArrayList<String> korWords = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(line,",");
                eng = st.nextToken(); // 첫번째 토근은 영단어
                while (st.hasMoreTokens()) { // 토근이 존재는하는 동안
                    korWords.add(st.nextToken().trim()); // 공백을 제거하고 리스트에 추가
                }
                voca.put(eng, new Word(eng, korWords));
            }
            buf.close();
            reader.close();
            System.out.println(filename + "파일이 정상적으로 로드하였습니다.");
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            System.out.println(filename + "파일이 존재하지 않습니다.");
        } catch (IOException e) {
            System.out.println(filename + "파일을 읽을 수 없습니다.");
            //e.printStackTrace();
        }

    }

    // 검색할 단어 입력받고 어휘집에서 검색해 한글 출력
    public void find() {
        String eng;

        System.out.println("Eng word : ");
        eng = in.next();
        // 해쉬맵에서 영어단어를 키로 검색
        if(voca.containsKey(eng)) { // 영단어가 해쉬맵에 키로 존재하는 경우
            String output = voca.get(eng).getKorWords().toString(); // 한글 단어 리스트를 출력
            System.out.println(output);
        } else System.out.println("단어가 없습니다.");
    }

    // 해쉬맵에 새로운 어휘를 추가
    public void addWord() {
        String eng, kor;
        ArrayList<String> korWords = new ArrayList<>();

        System.out.println("English word : ");
        eng = in.next();
        if (voca.containsKey(eng)) {
            System.out.println("어휘집에 존재하는 단어입니다.");
            return;
        }
        System.out.println("korean word : ");
        while (true) {
            kor = in.next();
            if(kor.equals("#")) break;
            korWords.add(kor); // 리스트에 한글단어 추가
        }
        voca.put(eng, new Word(eng, korWords));
    }

    // 해쉬맵에 저장된 어휘집을 정렬(알파벳순)해서 출력, 해쉬맵은 정렬기능이 없음. 트리맵 이용
    public void list() {
        TreeMap<String, Word> temp = new TreeMap<>(voca); // 해쉬 -> 트리 구조로 복사, 복사생성자
        // keySet() : 키 집합 생성 point 역할
        for (String key : temp.keySet()) {
            System.out.println(key + " : " + temp.get(key).getKorWords().toString());
        }
    }

    // 삭제할 단어 입력받고 어휘집에서 단어 
    public void delete() {
        String eng;

        System.out.println("English word : ");
        eng = in.next();

        if (voca.containsKey(eng)) {
            voca.remove(eng);
        } else System.out.println("단어가 없습니다.");
    }
}
