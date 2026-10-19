import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 基本データ型	int, long, boolean, char
        System.out.println("基本データ型");
        int a = 1; long b = 1; boolean c = false; char d = 'A';
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        // 参照型
        
        // String
        String e = "abc";
        System.out.println(e);
        System.out.println(e.charAt(0));
        
        // 配列	int[], length
        int[] g = {1, 2, 3};
        System.out.println(g[0]);
        System.out.println(g.length);
        
        // リスト	ArrayList, get, add, size
        ArrayList<Integer> h = new ArrayList<>();
        h.add(1);
        h.add(2);
        System.out.println(h.get(0));
        System.out.println(h.size());
        System.out.println(h.contains(1));

        // 文字列の長さが最小・最大の文字列を求める
        List<String> l = Arrays.asList("ab gbc dddd f".split(" "));
        String min = Collections.min(l, Comparator.comparingInt(String::length));
        String max = Collections.max(l, Comparator.comparingInt(String::length));
        System.out.println(min + max); // fdddd
        
        // マップ	HashMap, getOrDefault
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        System.out.println(map.getOrDefault("one", 0));
        System.out.println(map.getOrDefault("three", 0));
        
        // 集合	HashSet, contains
        HashSet<String> hash = new HashSet<>();
        hash.add("one");
        hash.add("two");
        hash.add("one");
        HashSet<String> set = new HashSet<>();
        set.add("one");
        System.out.println(hash.contains("one"));
        System.out.println(hash.size());
        System.out.println(hash.containsAll(set));
        System.out.println(set.containsAll(hash));
        
        // ソート	Arrays.sort, Collections.sort
        System.out.println("ソート");
        Integer[] nums = {3, 1, 2};
        System.out.println(Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        Arrays.sort(nums, Comparator.reverseOrder());
        System.out.println(Arrays.toString(nums));

        List<Integer> numCollections = new ArrayList<>(
            Arrays.asList(3, 1, 2)
        );
        Collections.sort(numCollections);
        System.out.println(numCollections);
        Collections.sort(numCollections, Collections.reverseOrder());
        System.out.println(numCollections);

        // パース
        System.out.println("パース");
        System.out.println(Integer.parseInt("125"));
        System.out.println(Double.parseDouble("3.14"));
        System.out.println(Boolean.parseBoolean("true"));

        // 条件分岐	if, else
        System.out.println("条件分岐");
        System.out.println(e == "abc");
        System.out.println(e == "ab" + "c");
        System.out.println(e == new String("abc"));
        System.out.println(e.equals("ab" + "c"));
        String en = null;
        System.out.println(new Test().equalsAbc(e));
        System.out.println(new Test().equalsAbc(en));

        // 例外処理
        System.out.println("例外処理");
        try {
            System.out.println(en.equals("abc"));
        } catch (NullPointerException ex) {
            System.out.println("NullPointerExceptionが発生しました");
        }
        try {
            System.out.println(en != null && en.equals("abc"));
            throw new RuntimeException("RuntimeExceptionが発生しました");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // ループ	for, while, 拡張for
        String[] arr = {"a", "b", "c"};
        System.out.println("ループ");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        int count = 0;
        while (count < arr.length) {
            System.out.println("whileループ" + count);
            count++;
        }
        for (int num: nums) {
            System.out.println("拡張forループ" + num);
        }
        
        // 数学関数	Math.max, Math.min, Math.abs
        System.out.println("数学関数");
        System.out.println(Math.max(1, 2));
        System.out.println(Math.min(1, 2)); 
        System.out.println(Math.abs(-5));
        
        // 文字列連結	StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        System.out.println(sb.toString());
        
        // キュー	ArrayDeque
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        queue.add(2);
        queue.push(3);
        System.out.println(queue);
        System.out.println(queue.pop());
        System.out.println(queue);
        System.out.println(queue.poll());
        System.out.println(queue);
        System.out.println(queue.poll());   
        System.out.println(queue);
        

        // ===== チートシート：文字列操作 =====
        String sampleText = "abc def";
        System.out.println(sampleText.length());                 // 7：文字数
        System.out.println(sampleText.charAt(0));                // a：0番目の文字
        // 【String → char[]】文字列を1文字ずつ扱うときに使う
        char[] sampleChars = sampleText.toCharArray();            // ['a','b','c',' ','d','e','f']
        System.out.println(sampleChars[0]);                      // a：0番目の文字
        System.out.println(new String(sampleChars));             // abc def：char[] → String
        System.out.println(sampleText.substring(0, 3));         // abc：終了位置3は含まない
        System.out.println(sampleText.replace("abc", "XYZ"));    // XYZ def：文字列置換
        String[] sampleParts = sampleText.split(" ");            // 空白で分割
        System.out.println(Arrays.toString(sampleParts));       // [abc, def]
        System.out.println(sampleText.equals("abc def"));       // true：文字列の値の比較はequals
        System.out.println(sampleText.indexOf('b'));            // 1：見つからない場合は-1
        System.out.println(new StringBuilder("abc").reverse()); // cba：文字列反転

        // ===== チートシート：文字・数値の変換 =====
        int sampleInt = Integer.parseInt("123");                 // 文字列 → 整数
        String sampleNumber = String.valueOf(123);              // 整数 → 文字列
        int sampleDigit = '7' - '0';                             // 数字の文字 → 整数（0～9）
        char sampleChar = (char) ('0' + 7);                      // 整数 → 数字の文字（0～9）
        double sampleRatio = (double) 10 / 3;                    // 小数で割り算する
        System.out.println(sampleInt + " " + sampleNumber + " " + sampleDigit + " " + sampleChar + " " + sampleRatio);

        // ===== チートシート：配列・List =====
        int[] sampleValues = {3, 1, 2};
        int[] sampleCopy = Arrays.copyOf(sampleValues, sampleValues.length); // 配列をコピー
        Arrays.sort(sampleCopy);                               // コピー側を昇順ソート
        System.out.println(Arrays.toString(sampleCopy));       // [1, 2, 3]
        List<String> sampleWords = new ArrayList<>(Arrays.asList("A", "B"));
        sampleWords.add("C");                                   // 末尾に追加
        System.out.println(sampleWords.get(0));                 // A：0番目
        System.out.println(sampleWords.get(sampleWords.size() - 1)); // C：最後の要素
        System.out.println(sampleWords.contains("B"));          // true：存在判定
        sampleWords.remove("A");                                // 値を指定して削除

        // ===== チートシート：HashMap・HashSet =====
        HashMap<String, Integer> sampleMap = new HashMap<>();
        sampleMap.put("A", 10);                                 // キーと値を登録
        System.out.println(sampleMap.getOrDefault("B", 0));      // 0：未登録なら既定値
        System.out.println(sampleMap.containsKey("A"));         // true：キーが存在するか
        HashSet<String> sampleSet = new HashSet<>();
        System.out.println(sampleSet.add("A"));                  // true：初回追加
        System.out.println(sampleSet.add("A"));                  // false：重複追加
        System.out.println(sampleSet.contains("A"));            // true：存在判定
        sampleSet.remove("A");                                  // 削除

        // ===== チートシート：標準入力（必要なときだけコメント解除） =====
        // Scanner input = new Scanner(System.in);
        // int n = input.nextInt();                             // 整数を1つ読む
        // int[] numbers = new int[n];
        // for (int i = 0; i < n; i++) numbers[i] = input.nextInt(); // 整数をN個読む
        // String word = input.next();                          // 空白・改行までの1語
        // String line = input.nextLine();                      // 改行までの1行
        // ※ nextInt()の直後にnextLine()を使う場合は、残りの改行に注意
        // input.close();

        // read from command line arguments
        // args[0]

        // 競技プログラミングでは、標準入力からの読み取りが一般的です
        // Javaでは、Scannerクラスを使用して標準入力を読み取ることができます
        // 以下は、標準入力から1行を読み取り、その内容を出力する例です
        // Scanner sc = new Scanner(System.in);
        // String line = sc.nextLine();
        // String[] nums = sc.nextLine().trim().split("\\s+");
        // String first = sc.next();
        // int first = sc.nextInt();
        // System.out.println(line);
        // sc.close();
    }
}

class Test {
    public String equalsAbc(String en) {
        if (en != null && en.equals("abc")) {
            return "eはabcです";
        } else {
            return "eはabcではありません";
        }
    }
}