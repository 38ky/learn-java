# コーディングテストで使う基本アルゴリズムとJava標準ライブラリ

問題文から **「どう解くか」→「Javaで何を使うか」** をすぐ判断するための早見表です。初歩的な配列・文字列・数値問題を中心にまとめています。

> **ポイント**：アルゴリズム（解き方）とライブラリ（Javaで使う道具）は別です。たとえば「出現回数を数える」が解き方で、`HashMap` はそのための道具です。問題によってはライブラリを使わず、`for` と `if` だけで解けます。

## まずここを見る：問題の特徴 → 解法 → Javaで使うもの

| こんな問題文・条件 | 考える解法 | Javaで使うもの | 具体例・注意 |
|---|---|---|---|
| 偶数・奇数、余り、交互、周期 | **偶奇・剰余** | `%`、`Math` | `n % 2` で偶奇を判断 |
| Nが小さい／全パターンを試せる | **全探索** | `for`、`if` | すべてのペアを調べる |
| 最大値・最小値・合計を求める | **1回走査** | `for`、`Math.max/min` | 配列を順番に調べる |
| 小さい順・順位・隣同士の差 | **ソート** | `Arrays.sort`、`List.sort` | 並べ替えて比較する |
| 指定値が存在するか／何回出たか | **集合・頻度集計** | `HashSet`、`HashMap` | 重複や出現回数 |
| 文字ごとの出現回数を数える | **文字の集計** | `charAt`、`int[]`、`HashMap` | 英小文字のみなら `int[26]` |
| 文字列を反転・組み立てる | **文字列操作** | `StringBuilder` | `reverse()`、`append()` |
| 回文や両端からの比較 | **両端ポインタ** | `charAt`、配列アクセス | 左右を内側に進める |
| 2つの数の和が目標になるペア | **HashSet またはソート＋両端ポインタ** | `HashSet`、`Arrays.sort` | 元の並び順が必要か注意 |
| ソート済み配列から値を探す | **二分探索** | `Arrays.binarySearch` | 戻り値が負の場合あり |
| ソート済み配列で「初めて条件を満たす位置」 | **境界の二分探索** | `while`（自作） | `binarySearch`だけでは不十分な場合あり |
| 同じ配列の連続区間の合計を何度も聞かれる | **累積和** | `long[]`、`for` | `prefix[r] - prefix[l]` |
| 連続した区間の長さ・合計を調べる | **スライディングウィンドウ／尺取り法** | `while`、`for` | 条件が単調に変化するか確認 |
| 括弧の対応・直近の操作を取り消す | **スタック** | `ArrayDeque` の `push/pop/peek` | 後入れ先出し |
| 順番に処理・先に入れたものから取り出す | **キュー** | `ArrayDeque` の `offer/poll/peek` | 先入れ先出し |
| 何回も最小値・最大値を取り出す | **優先度付きキュー** | `PriorityQueue` | 通常は最小値から取り出す |
| 選び方を工夫して最大・最小にしたい | **貪欲法／場合分け** | `Arrays.sort`、`Math`、`for` | その選択が常に最適か確認 |
| 迷路で最少移動回数を求める | **幅優先探索（BFS）** | `ArrayDeque`、`boolean[]` | 1移動のコストが同じ場合 |
| 前の計算結果を再利用して場合の数や最小コストを求める | **動的計画法（DP）** | `int[]`、`long[]` | `dp[i]` の意味を決める |

※ この表は**最初に疑う候補**です。同じ問題を複数の方法で解けることもあります。

---

## 1. 偶奇・剰余・数値の性質

**こんなとき**：奇数・偶数、人数の分割、操作を繰り返した後の位置、周期的な並び。

**解き方**：シミュレーションせずに `%` や数値の性質で答えが決まらないか考えます。

```java
boolean even = n % 2 == 0; // 偶数か
boolean odd = n % 2 != 0;  // 奇数か
int remainder = n % k;     // kで割った余り

int difference = Math.abs(a - b);
int larger = Math.max(a, b);
int smaller = Math.min(a, b);
```

**例**：最大チーム人数 `K >= 2` で、奇数人数チームを最小にしたいなら、全人数 `N` が偶数なら0、奇数なら1。`K == 1` のときは全員が1人チームなので `N`。これはチーム分けを列挙する必要がない例です。

**注意**：`Math.pow()`、`Math.sqrt()` は `double` を返します。整数の厳密な計算にそのまま使うときは注意。

## 2. 配列を1回調べる（線形探索・集計）

**こんなとき**：最大・最小・合計・条件に一致する要素数・最初に見つかる位置。

**解き方**：先頭から順番に確認します。計算量は **O(N)**。

```java
int[] nums = {4, 2, 9, 3};
int max = nums[0];
long sum = 0;
int countEven = 0;

for (int num : nums) {
    max = Math.max(max, num);
    sum += num;
    if (num % 2 == 0) countEven++;
}
// max=9, sum=18, countEven=2
```

**Javaで使うもの**：`for`、拡張for、`Math.max/min`、配列 `.length`。

## 3. 全探索（すべての候補を試す）

**こんなとき**：配列から2つ選ぶ、条件に合う組を探す、入力が小さい。

**解き方**：まず全部試せるかを考えます。2つの組なら二重ループで **O(N²)**。

```java
int[] nums = {2, 4, 7, 9};
int target = 11;
boolean found = false;

for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) found = true;
    }
}
```

**Javaで使うもの**：主に `for`、`if`。特別なライブラリは不要です。

**注意**：`N = 100,000` で二重ループを回すと約100億回規模の比較になるため、別の方法を考えます。

## 4. ソート（並べ替え）

**こんなとき**：大小順、順位、隣接する値の差、重複が隣接するようにしたい。

**解き方**：先に並べ替えると比較が簡単になることがあります。一般に **O(N log N)**。

```java
import java.util.*;

int[] nums = {5, 1, 4};
Arrays.sort(nums); // [1, 4, 5]

List<Integer> list = new ArrayList<>(List.of(5, 1, 4));
list.sort(Comparator.reverseOrder()); // [5, 4, 1]
```

**Javaで使うもの**：`Arrays.sort()`、`List.sort()`、`Comparator.reverseOrder()`。

**注意**：`int[]` には `Comparator.reverseOrder()` を直接渡せません。降順なら `Integer[]` や `List<Integer>` を使います。ソートすると元の順番は変わります。

## 5. 重複チェック・出現回数（HashSet / HashMap）

**こんなとき**：「同じ値があるか」「値ごとの個数」「以前に登場したか」。

**解き方**：存在確認は `HashSet`、個数や値の対応付けは `HashMap`。検索・追加は平均 **O(1)**。

```java
import java.util.*;

int[] nums = {3, 5, 3, 8};

Set<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (!seen.add(num)) {
        System.out.println("重複: " + num); // 3
    }
}

Map<Integer, Integer> freq = new HashMap<>();
for (int num : nums) {
    freq.put(num, freq.getOrDefault(num, 0) + 1);
}
// {3=2, 5=1, 8=1}（表示順は保証されない）
```

**覚えるAPI**：`set.add()`、`set.contains()`、`map.put()`、`map.get()`、`map.getOrDefault()`、`map.containsKey()`。

**注意**：`HashMap` / `HashSet` は並び順を保証しません。

## 6. 文字列操作・文字のカウント

**こんなとき**：文字列を逆にする、各文字の個数を数える、回文を判定する、文字を連結する。

```java
String s = "level";
char first = s.charAt(0);        // 'l'
int len = s.length();           // 5
String part = s.substring(1, 4); // "eve"
boolean same = s.equals("level"); // true

String reversed = new StringBuilder(s).reverse().toString();

StringBuilder sb = new StringBuilder();
sb.append("ab");
sb.append("cd");
String joined = sb.toString();  // "abcd"
```

**英小文字 a〜z の出現回数を数える場合**：

```java
int[] freq = new int[26];
for (int i = 0; i < s.length(); i++) {
    freq[s.charAt(i) - 'a']++;
}
```

**Javaで使うもの**：`String.charAt()`、`length()`、`substring()`、`equals()`、`StringBuilder`。文字種が限定されない場合は `HashMap<Character, Integer>` も候補です。

**注意**：文字列の内容比較は基本的に `==` ではなく `.equals()` を使います。`charAt()` はUTF-16のコード単位を返すため、絵文字などを1文字として扱う問題は別途考慮が必要です。

## 7. 2つのポインタ（Two Pointers）

**こんなとき**：回文の判定、ソート済み配列から条件を満たすペアを探す。

**解き方**：左右の位置を表す変数を動かし、同じ要素を何度も調べるのを避けます。多くは **O(N)**（ソートを除く）。

```java
// 文字列が回文か判定する
String s = "level";
int left = 0, right = s.length() - 1;
boolean palindrome = true;

while (left < right) {
    if (s.charAt(left) != s.charAt(right)) {
        palindrome = false;
        break;
    }
    left++;
    right--;
}
```

**Javaで使うもの**：`while`、`charAt()`、配列インデックス。専用ライブラリはありません。

## 8. 二分探索（Binary Search）

**こんなとき**：ソート済み配列から値を高速に探したい。

**解き方**：真ん中を見て、探索範囲を半分ずつ狭めます。**O(log N)**。

```java
import java.util.Arrays;

int[] nums = {1, 3, 5, 7, 9};
int index = Arrays.binarySearch(nums, 5); // 2
boolean exists = index >= 0;
```

**Javaで使うもの**：`Arrays.binarySearch()`。

**注意**：ソートが前提です。見つからなければ負数が返ります。同じ値が複数あるとき、必ず最初の位置が返るわけではありません。**「初めて条件を満たす位置」** の問題は、境界を探す二分探索を `while` で自作することがあります。

## 9. 累積和（Prefix Sum）

**こんなとき**：「配列の3番目から8番目までの合計」のような質問が何回も来る。

**解き方**：先頭からの合計を先に作り、引き算で区間和を求めます。前処理 **O(N)**、1回の区間取得 **O(1)**。

```java
int[] nums = {2, 4, 1, 3};
long[] prefix = new long[nums.length + 1];

for (int i = 0; i < nums.length; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}

// [l, r) = l番目からr-1番目の合計
int l = 1, r = 3;
long sum = prefix[r] - prefix[l]; // 4 + 1 = 5
```

**Javaで使うもの**：`long[]`、`for`。特別なライブラリは不要です。

**注意**：区間の指定が **`[l, r)`（右端を含まない）** なのか確認します。

## 10. スライディングウィンドウ／尺取り法

**こんなとき**：連続した要素の合計が条件以下となる、最長の区間を探す。

**解き方**：区間の右端を広げ、条件を破ったら左端を縮めます。条件が適切なら **O(N)**。

```java
// numsは非負整数のみ。合計がlimit以下の最長連続区間
int[] nums = {2, 1, 3, 2, 1};
int limit = 5;
int left = 0, best = 0;
long sum = 0;

for (int right = 0; right < nums.length; right++) {
    sum += nums[right];
    while (sum > limit) {
        sum -= nums[left++];
    }
    best = Math.max(best, right - left + 1);
}
```

**Javaで使うもの**：`for`、`while`、`Math.max()`。

**注意**：この実装は**要素が非負**であることが重要です。負数が混じる問題ではそのまま使えません。

## 11. スタック・キュー（ArrayDeque）

**こんなとき**：括弧の対応や直近の操作ならスタック、順番待ちや1手ずつの探索ならキュー。

| 使い方 | 追加 | 取り出して削除 | 先頭を見るだけ | 順序 |
|---|---|---|---|---|
| スタック | `push(x)` | `pop()` | `peek()` | 後入れ先出し（LIFO） |
| キュー | `offer(x)` | `poll()` | `peek()` | 先入れ先出し（FIFO） |

```java
import java.util.*;

Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
stack.push(20);
System.out.println(stack.pop()); // 20

Queue<Integer> queue = new ArrayDeque<>();
queue.offer(10);
queue.offer(20);
System.out.println(queue.poll()); // 10
```

**Javaで使うもの**：`ArrayDeque`、`Deque`、`Queue`。

**注意**：空のキューで `poll()` は `null` を返し、空のスタックで `pop()` は例外が発生します。`ArrayDeque` に `null` は追加できません。

## 12. 優先度付きキュー（PriorityQueue）

**こんなとき**：値を追加しながら、常に最小値（または最大値）を取り出したい。

```java
import java.util.*;

PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(5);
pq.offer(2);
pq.offer(8);
System.out.println(pq.poll()); // 2

PriorityQueue<Integer> maxQ =
        new PriorityQueue<>(Comparator.reverseOrder());
maxQ.offer(5);
maxQ.offer(8);
System.out.println(maxQ.poll()); // 8
```

**Javaで使うもの**：`PriorityQueue`、`Comparator.reverseOrder()`。

**注意**：`poll()` は最優先の要素を取り出しますが、`PriorityQueue` の全要素がソート済みで保持・反復されるわけではありません。追加・取り出しは通常 **O(log N)**。

## 13. 貪欲法（Greedy）

**こんなとき**：大きいものから選ぶ、小さいものから使う、早く終わる予定から選ぶなどで最適化できそう。

**解き方**：その場の選択を繰り返します。ただし、**その選び方が全体でも最適になる理由**が必要です。

**例**：各仕事の開始時刻・終了時刻が与えられ、重ならずにできる仕事の最大件数を求める → 終了時刻が早い順に選ぶ方法を検討する。

**Javaで使うもの**：`Arrays.sort()`、`Comparator`、`for`。

**注意**：単に「最大を選べばよさそう」という直感だけでは貪欲法は成立しません。

## 14. 幅優先探索（BFS）と動的計画法（DP）の入口

初歩的な問題でも登場するため、**何の問題で使うか**だけ押さえておきます。

| 手法 | 典型問題 | Javaで使うもの | 考え方 |
|---|---|---|---|
| **BFS** | 迷路の最短手数（上下左右へ1手ずつ） | `Queue`、`ArrayDeque`、`boolean[][]` | 近い場所から順に調べる |
| **DP** | 階段を1段か2段で上がる方法の数 | `int[]`、`long[]`、`for` | 小さな答えを記録し再利用する |

```java
// DPの例：n段の階段を1段か2段ずつ上る方法の数
int n = 5;
long[] dp = new long[n + 2];
dp[0] = 1;
for (int i = 1; i <= n; i++) {
    dp[i] = dp[i - 1];
    if (i >= 2) dp[i] += dp[i - 2];
}
System.out.println(dp[n]); // 8
```

BFSにもDPにも、Javaの専用アルゴリズム関数があるわけではありません。**標準の配列やキューを組み合わせて実装**します。

---

## Java標準ライブラリの逆引き表

| やりたいこと | クラス／メソッド | 覚えておくポイント |
|---|---|---|
| 最大・最小 | `Math.max(a, b)` / `Math.min(a, b)` | 比較するのは2値 |
| 絶対値 | `Math.abs(x)` | 負の差を正にする |
| 配列長 | `array.length` | メソッドではなくフィールド |
| 文字列長 | `string.length()` | `()` が必要 |
| 文字の取得 | `string.charAt(i)` | `char` を返す |
| 文字列の内容比較 | `a.equals(b)` | `==` は原則使わない |
| 部分文字列 | `substring(start, end)` | `end` は含まない |
| 可変長の配列 | `ArrayList<E>` | `add/get/size` |
| 配列ソート | `Arrays.sort(array)` | 基本は昇順 |
| リストソート | `list.sort(comparator)` | 降順なら `Comparator.reverseOrder()` |
| ソート済み配列を検索 | `Arrays.binarySearch(array, x)` | 見つからないと負数 |
| 重複・存在確認 | `HashSet<E>` | `add/contains` |
| 値ごとの集計 | `HashMap<K,V>` | `put/getOrDefault` |
| スタック | `ArrayDeque<E>` | `push/pop/peek` |
| キュー | `ArrayDeque<E>` | `offer/poll/peek` |
| 最小値を優先して取り出す | `PriorityQueue<E>` | `offer/poll` |
| 文字列の連結・反転 | `StringBuilder` | `append/reverse/toString` |
| 大きい整数 | `long` | 合計のオーバーフローを避ける |
| 最大の `int` 値 | `Integer.MAX_VALUE` | 最小値探しの初期値など |

多くのコレクションは冒頭に `import java.util.*;` と書けば使えます（`Math`、`String`、`StringBuilder` は不要）。

## 迷ったら最初に確認すること

1. **何を求める問題か**：存在、個数、最大・最小、区間、最短手数など。
2. **入力サイズはいくつか**：二重ループ `O(N²)` で間に合うか、`O(N)` / `O(N log N)` が必要か。
3. **データの特徴は何か**：ソート済み、重複あり、非負数のみ、文字種が限定、など。
4. **標準ライブラリで簡単に書けるか**：`Arrays.sort`、`HashMap`、`HashSet`、`ArrayDeque` など。

> **覚え方**：「問題のキーワードを見てライブラリを選ぶ」のではなく、**問題の特徴から解法を決め、その実装に合ったJavaの道具を選ぶ**。
