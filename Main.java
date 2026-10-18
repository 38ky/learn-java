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
        
        // read from command line arguments
        // args[0]

        // 競技プログラミングでは、標準入力からの読み取りが一般的です
        // Javaでは、Scannerクラスを使用して標準入力を読み取ることができます
        // 以下は、標準入力から1行を読み取り、その内容を出力する例です
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        // String first = sc.next();
        System.out.println(line);
        sc.close();
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