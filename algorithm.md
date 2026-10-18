# アルゴリズム

コーディング問題などの**問題を解くときの手順**と、**よく使うアルゴリズム・データ構造**のまとめ。

## 問題の特徴から選ぶ

| 問題の特徴 | 解法 | Java API・構文 |
|---|---|---|
| 偶奇・余り・周期など、整数の性質で判断できる | [偶奇・剰余](#parity) | `%`、`Math` |
| 配列を一度見れば、合計・最大値などを求められる | [線形走査](#scan) | `for`、`Math.max()` |
| 候補をすべて試せる程度に入力が小さい | [全探索](#brute-force) | `for`、`if` |
| 順序を整えると比較や判定が簡単になる | [ソート](#sort) | `Arrays.sort()`、`Comparator` |
| 要素の重複・存在を高速に判定したい | [集合](#set) | `HashSet` |
| 値ごとの件数や対応関係を管理したい | [マップ](#map) | `HashMap` |
| 文字単位の判定・変換・集計をしたい | [文字列処理](#string) | `String`、`StringBuilder` |
| 両端から比較・探索して範囲を狭められる | [両端ポインタ](#two-pointers) | `while`、添字 |
| **ソート済み**の範囲から値・境界を探す | [二分探索](#binary-search) | `Arrays.binarySearch()` |
| 同じ配列の**区間合計**を何度も求める | [累積和](#prefix-sum) | `long[]` |
| **連続区間**の条件を保ちながら左右に動かせる | [尺取り法](#sliding-window) | `for`、`while` |
| 最後に入れたものから処理する | [スタック](#stack) | `ArrayDeque`、`push()`、`pop()` |
| 最初に入れたものから処理する | [キュー](#queue) | `ArrayDeque`、`offer()`、`poll()` |
| 常に最小値・最大値を優先して取り出す | [優先度付きキュー](#priority-queue) | `PriorityQueue` |
| その時点の最善の選択を繰り返せば最適になる | [貪欲法](#greedy) | `Arrays.sort()` など |

コード断片では `a` を配列、`target` や `limit` を条件値として使う。必要に応じて `import java.util.*;`。

---

<a id="parity"></a>
## 偶奇・剰余

**使いどころ**：偶奇、割り切れるか、周期性など、整数の性質で答えが決まる場合。

```java
boolean even = n % 2 == 0;
int remainder = n % k; // k != 0
int distance = Math.abs(x - y);
```

**Java**：`%`、`Math.abs()`、`Math.min()`、`Math.max()`。

<a id="scan"></a>
## 線形走査 — O(N)

**使いどころ**：各要素を一度ずつ確認して、合計・最大値・条件に合う件数を求める場合。

```java
// a は1要素以上の int[]
long sum = 0;
int max = a[0];
for (int x : a) {
    sum += x;
    max = Math.max(max, x);
}
```

**Java**：拡張`for`、`Math.max()`、`a.length`。

<a id="brute-force"></a>
## 全探索 — O(N²)（2要素の組の場合）

**使いどころ**：候補数が少なく、すべて調べても時間内に収まる場合。

```java
// 異なる2要素の組を列挙する
for (int i = 0; i < a.length; i++) {
    for (int j = i + 1; j < a.length; j++) {
        // a[i] と a[j] を判定
    }
}
```

**Java**：`for`、`if`。入力サイズから計算量を確認する。

<a id="sort"></a>
## ソート — O(N log N)

**使いどころ**：昇順・降順にすると、隣接比較や重複・大小の判定が簡単になる場合。

```java
Arrays.sort(a); // int[] を昇順

Integer[] b = {3, 1, 2};
Arrays.sort(b, Comparator.reverseOrder()); // 降順
```

**Java**：`Arrays.sort()`、`Comparator`。`int[]`に比較器は指定できない。

<a id="set"></a>
## 集合（Set） — 平均 O(N)

**使いどころ**：重複の検出や、既に登場した値の存在判定をする場合。

```java
Set<Integer> seen = new HashSet<>();
for (int x : a) {
    if (!seen.add(x)) {
        // 重複している
    }
}
```

**Java**：`HashSet`、`add()`、`contains()`。

<a id="map"></a>
## マップ（Map） — 平均 O(N)

**使いどころ**：出現回数の集計や、キーに対応する値の保持が必要な場合。

```java
Map<Integer, Integer> count = new HashMap<>();
for (int x : a) {
    count.put(x, count.getOrDefault(x, 0) + 1);
}
```

**Java**：`HashMap`、`getOrDefault()`、`put()`、`containsKey()`。

<a id="string"></a>
## 文字列処理 — O(N)

**使いどころ**：文字の比較・出現回数・部分文字列・文字列の組み立てが必要な場合。

```java
for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    // c を判定・集計
}
String reversed = new StringBuilder(s).reverse().toString();
```

**Java**：`length()`、`charAt()`、`substring()`、`equals()`、`StringBuilder`。

<a id="two-pointers"></a>
## 両端ポインタ — O(N)

**使いどころ**：左右から比較しながら探索範囲を狭められる場合。

```java
int left = 0, right = a.length - 1;
while (left < right) {
    // a[left] と a[right] を比較し、
    // 条件に応じて left++ または right--
}
```

**Java**：`while`、配列の添字。専用ライブラリなし。

<a id="binary-search"></a>
## 二分探索 — O(log N)

**使いどころ**：ソート済みデータでの値の検索や、判定が切り替わる境界の特定。

```java
// a は昇順にソート済み
int index = Arrays.binarySearch(a, target);
boolean exists = index >= 0;
```

**Java**：`Arrays.binarySearch()`。境界探索は二分探索を自分で実装する場合もある。

<a id="prefix-sum"></a>
## 累積和 — 構築 O(N)、区間合計 O(1)

**使いどころ**：同じ配列について、異なる区間の合計を繰り返し求める場合。

```java
long[] prefix = new long[a.length + 1];
for (int i = 0; i < a.length; i++) {
    prefix[i + 1] = prefix[i] + a[i];
}
long sum = prefix[r] - prefix[l]; // 区間 [l, r)
```

**Java**：`long[]`。`0 <= l <= r <= a.length`。

<a id="sliding-window"></a>
## 尺取り法 — O(N)

**使いどころ**：連続区間について、端を動かしても条件を効率よく更新できる場合。

```java
// 非負整数配列で、区間和が limit 以下になる範囲を管理
int left = 0;
long sum = 0;
for (int right = 0; right < a.length; right++) {
    sum += a[right];
    while (sum > limit) {
        sum -= a[left++];
    }
    // [left, right] が条件を満たす
}
```

**Java**：`for`、`while`。上記の形は非負整数が前提。

<a id="stack"></a>
## スタック（LIFO）

**使いどころ**：直前の状態に戻る、対応関係を閉じるなど、後入れ先出しが必要な場合。

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(value);  // 追加
int last = stack.pop(); // 最後に追加した値を取得・削除
```

**Java**：`ArrayDeque`、`push()`、`pop()`、`peek()`、`isEmpty()`。空での`pop()`は例外。

<a id="queue"></a>
## キュー（FIFO）

**使いどころ**：登録順の処理や、先に見つかった候補から順に探索する場合。

```java
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(value);   // 末尾に追加
Integer first = queue.poll(); // 先頭を取得・削除
```

**Java**：`ArrayDeque`、`offer()`、`poll()`、`peek()`。空での`poll()`は`null`。

<a id="priority-queue"></a>
## 優先度付きキュー — 追加・取り出し O(log N)

**使いどころ**：追加・削除を繰り返しながら、常に最小値・最大値を取り出す場合。

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(); // 最小値優先
pq.offer(value);
Integer min = pq.poll();
```

**Java**：`PriorityQueue`。最大値優先は`new PriorityQueue<>(Comparator.reverseOrder())`。

<a id="greedy"></a>
## 貪欲法（Greedy）

**使いどころ**：その時点の最善の選択を繰り返すことで、全体の最適解になる場合。

```java
// 選択基準に沿ってソートしてから順に採用する形が典型
Arrays.sort(a);
for (int x : a) {
    // 条件を満たすなら採用
}
```

**Java**：`Arrays.sort()`、`Comparator`など。**貪欲な選択で最適になる根拠が必要**。
