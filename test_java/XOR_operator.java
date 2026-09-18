import java.util.Random;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class XOR_operator{
    public static void main(String[] args){

        List<Integer> list = List.of(88, 36, 44, 90, 72, 81, 16, 33, 73, 38, 98, 22, 49, 46, 3, 18, 6, 13, 78, 28, 65, 53, 89, 99, 52, 10, 68, 83, 61, 75, 8, 56, 87, 0, 64, 79, 4, 86, 84, 24, 34, 39, 5, 92, 15, 20, 58, 40, 7, 31, 2, 77, 66, 50, 41, 74, 14, 85, 93, 70, 59, 94, 27, 11, 37, 47, 95, 9, 17, 1, 80, 63, 21, 97, 26, 30, 69, 82, 42, 48, 57, 62, 43, 55, 96, 19, 14, 100, 45, 60, 67, 25, 29, 32, 54, 51, 91, 12, 35, 71, 76, 23);
        
        // x ^ y ^ y = x
        // 0 ^ x = x

        int x = 0;
        for(int i = 0; i <= 100; i++){
            x^=i;
        }

        int n = list.size();
        
        for(int j = 0; j < n; j++){
            x^=list.get(j);
        }
        System.out.println(x);
    }
}