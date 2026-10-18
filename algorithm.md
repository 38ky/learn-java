# 競技プログラミングの代表的なアルゴリズム

Javaでのコーディングテスト・競技プログラミング対策用チートシートです。まずは **計算量・全探索・ソート・二分探索・累積和・貪欲法・BFS/DFS・動的計画法** を優先して学びましょう。

## 1. 計算量の基礎

| 計算量 | 典型例 | 目安 |
|---|---|---|
| O(1) | 配列アクセス、偶奇判定 | 入力サイズに依存しない |
| O(log N) | 二分探索 | 毎回探索範囲を半分にする |
| O(N) | 配列の走査、累積和の構築 | 要素数に比例 |
| O(N log N) | 一般的な効率のよいソート | Nが大きくても比較的実用的 |
| O(N²) | 二重ループ、全ペア比較 | Nが大きいと厳しい |
| O(2^N) | 部分集合の全列挙 | 小さいN向け |

実行時間の許容範囲は言語・環境・定数倍で変わるため、上記は絶対的な基準ではありません。

## 2. 数学・整数の性質

### 偶奇・剰余（パリティ）

- `n % 2 == 0` なら偶数、`n % 2 != 0` なら奇数。
- 偶数の和は偶数。奇数個の奇数の和は奇数。
- 周期性・交互操作・グループ分けで役立つ。

```java
int odd = n % 2;
```

### 最大公約数・最小公倍数

- ユークリッドの互除法：`gcd(a, b) = gcd(b, a % b)`
- 最小公倍数：`lcm(a, b) = a / gcd(a, b) * b`（オーバーフロー注意）

```java
static long gcd(long a, long b) {
    while (b != 0) {
        long t = a % b;
        a = b;
        b = t;
    }
    return Math.abs(a);
}
```

### 素数判定・エラトステネスの篩

- 単一整数の素数判定：2から平方根まで試し割り、O(√N)。
- ある上限までの素数を一括列挙：エラトステネスの篩、O(N log log N)。

### 組み合わせ・場合の数

- 順列：順序を区別する。組み合わせ：順序を区別しない。
- `nCr = n! / (r! (n-r)!)`。大きな値ではオーバーフローや剰余計算に注意。

## 3. 全探索（Brute Force）

可能性をすべて試す基本手法。まず全探索で解けるか考え、計算量を確認します。

| 手法 | 用途 | 典型計算量 |
|---|---|---|
| 一重ループ | 全要素を調べる | O(N) |
| 二重ループ | すべてのペアを調べる | O(N²) |
| ビット全探索 | 部分集合をすべて試す | O(N 2^N)（各集合を走査する場合） |
| バックトラッキング | 順列・組み合わせ・制約付き探索 | 問題依存、指数的な場合が多い |

```java
// 全ペアを列挙
for (int i = 0; i < a.length; i++) {
    for (int j = i + 1; j < a.length; j++) {
        // a[i] と a[j] を調べる
    }
}
```

## 4. ソート（Sorting）

並べ替えることで、隣接要素の比較・二分探索・貪欲法が使いやすくなります。

```java
import java.util.Arrays;

int[] a = {5, 1, 4};
Arrays.sort(a); // [1, 4, 5]
```

- `Arrays.sort(int[])`：整数配列を昇順にソート。
- `Arrays.sort(Integer[], Comparator.reverseOrder())`：オブジェクト配列の降順ソート。
- `list.sort(Comparator.naturalOrder())`：リストの昇順ソート。
- Javaのプリミティブ配列とオブジェクト配列ではソート実装・性質が異なります。

## 5. 二分探索（Binary Search）

**ソート済み配列**や、条件が途中で一度だけ切り替わる単調な範囲に使います。計算量は O(log N)。

```java
// 昇順配列から target 以上の最初の位置を探す（lower_bound）
static int lowerBound(int[] a, int target) {
    int left = 0, right = a.length;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (a[mid] < target) left = mid + 1;
        else right = mid;
    }
    return left;
}
```

**典型問題**：値の検索、境界位置の特定、答えを二分探索する最小値・最大値問題。

## 6. 累積和（Prefix Sum）

配列の区間合計を何度も求めるときに有効。前処理 O(N)、各区間クエリ O(1)。

```java
int[] a = {2, 4, 1, 3};
long[] sum = new long[a.length + 1];
for (int i = 0; i < a.length; i++) {
    sum[i + 1] = sum[i] + a[i];
}
// 半開区間 [l, r) の合計
long rangeSum = sum[r] - sum[l];
```

**発展**：二次元累積和、差分配列（区間更新）。

## 7. 尺取り法・スライディングウィンドウ

連続区間を左右のポインタで管理します。条件に応じて区間を広げたり縮めたりすることで、二重ループを O(N) に改善できる場合があります。

```java
// 非負整数配列で「合計 <= limit」の最長連続区間
int left = 0, best = 0;
long sum = 0;
for (int right = 0; right < a.length; right++) {
    sum += a[right];
    while (sum > limit) sum -= a[left++];
    best = Math.max(best, right - left + 1);
}
```

**注意**：この例のような単調な区間条件は非負整数という前提に依存します。

## 8. いもす法・差分配列

大量の区間加算を効率化します。各更新 O(1)、最後の復元 O(N)。

```java
long[] diff = new long[n + 1];
// 半開区間 [l, r) に x を加える
diff[l] += x;
diff[r] -= x;
// 元の配列を復元
for (int i = 1; i < n; i++) diff[i] += diff[i - 1];
```

## 9. 貪欲法（Greedy）

その時点で最善に見える選択を繰り返します。**局所的な選択が全体の最適解になる理由の証明が重要**です。

- 典型例：終了時刻の早い区間から採用する区間スケジューリング。
- 典型例：ソート後に条件を満たす要素を順に選ぶ。
- 注意：直感的に最大・最小を選ぶだけでは誤答になる問題もあります。

## 10. ハッシュ（HashMap / HashSet）

出現回数の集計・重複判定・既出要素の検索でよく使います。平均 O(1) の挿入・検索が期待できます。

```java
import java.util.*;
Map<Integer, Integer> count = new HashMap<>();
for (int x : a) count.merge(x, 1, Integer::sum);

Set<Integer> seen = new HashSet<>();
for (int x : a) {
    if (!seen.add(x)) {
        // x は重複している
    }
}
```

## 11. スタック・キュー・Deque

| 構造 | 取り出し順 | 典型用途 |
|---|---|---|
| スタック | LIFO（後入れ先出し） | 括弧判定、DFS、単調スタック |
| キュー | FIFO（先入れ先出し） | BFS、順番待ち |
| Deque | 両端から追加・削除 | スライディングウィンドウ最大値など |

```java
import java.util.*;
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
stack.pop();

Queue<Integer> queue = new ArrayDeque<>();
queue.offer(10);
queue.poll();
```

## 12. グラフ探索：BFS / DFS

### BFS（幅優先探索）

- キューを使い、近い頂点から探索。
- **重みなしグラフの最短辺数**を求められる。
- 計算量 O(V + E)（隣接リスト表現）。

```java
Queue<Integer> q = new ArrayDeque<>();
boolean[] visited = new boolean[n];
visited[start] = true;
q.offer(start);
while (!q.isEmpty()) {
    int v = q.poll();
    for (int next : graph.get(v)) {
        if (!visited[next]) {
            visited[next] = true;
            q.offer(next);
        }
    }
}
```

### DFS（深さ優先探索）

- 再帰またはスタックで奥まで探索。
- 連結成分、到達可能性、木の探索、バックトラッキングに使う。
- 計算量 O(V + E)（隣接リスト表現）。
- Javaでは深い再帰による `StackOverflowError` に注意。

## 13. 動的計画法（DP）

同じ部分問題を繰り返し解かないよう、計算結果を保存します。

**考える順番**：①状態 `dp[i]` の意味 → ②遷移式 → ③初期値 → ④答え。

```java
// 階段を1段または2段ずつ上がる方法の数
long[] dp = new long[n + 2];
dp[0] = 1;
for (int i = 1; i <= n; i++) {
    dp[i] += dp[i - 1];
    if (i >= 2) dp[i] += dp[i - 2];
}
System.out.println(dp[n]);
```

代表例：フィボナッチ数列、ナップサック問題、最長増加部分列（LIS）、最長共通部分列（LCS）、区間DP、ビットDP。

## 14. 最短経路・グラフの発展

| アルゴリズム | 目的 | 目安計算量・条件 |
|---|---|---|
| ダイクストラ法 | 非負辺重みの最短経路 | 優先度付きキュー利用で O((V+E) log V) |
| ベルマン–フォード法 | 負辺を含む最短経路 | O(VE)、負閉路の検出も可能 |
| ワーシャル–フロイド法 | 全頂点対の最短経路 | O(V³) |
| トポロジカルソート | DAGの依存順序 | O(V+E) |
| Union-Find | 連結成分の統合・判定 | ほぼ O(1) 償却 |
| クラスカル法 | 最小全域木 | O(E log E) |

## 15. 文字列アルゴリズム

| 手法 | 目的 |
|---|---|
| 文字頻度カウント | アナグラム判定など |
| Two Pointers | 回文判定、両端からの比較 |
| KMP法 | 文字列パターン検索 |
| Z-algorithm | 接頭辞との一致長をまとめて計算 |
| Rolling Hash | 部分文字列の高速比較（衝突に注意） |
| Trie | 接頭辞検索・辞書管理 |

## 16. 区間クエリのデータ構造

| データ構造 | 得意な処理 |
|---|---|
| Fenwick Tree（BIT） | 一点加算・区間和 |
| Segment Tree | 区間和・最小値・最大値など |
| Lazy Segment Tree | 区間更新・区間取得 |
| Sparse Table | 静的配列の区間最小値など |

## 17. ビット演算

```java
int mask = 0;
mask |= (1 << i);           // i番目を立てる
boolean on = (mask & (1 << i)) != 0;
mask &= ~(1 << i);          // i番目を消す
```

部分集合列挙、集合の状態管理、ビットDPなどで使います。Javaの `int` は32ビット、`long` は64ビットです。シフト幅・符号に注意してください。

## 18. Java標準ライブラリ早見表

| 用途 | Java API |
|---|---|
| 最大・最小・絶対値 | `Math.max`, `Math.min`, `Math.abs` |
| ソート | `Arrays.sort`, `List.sort` |
| 二分探索 | `Arrays.binarySearch`（挿入位置の戻り値に注意） |
| 可変長配列 | `ArrayList` |
| 連想配列 | `HashMap` |
| 集合 | `HashSet` |
| キュー・スタック | `ArrayDeque` |
| 優先度付きキュー | `PriorityQueue` |
| 文字列連結 | `StringBuilder` |
| 数値範囲 | `Integer.MAX_VALUE`, `Long.MAX_VALUE` |

## 19. 問題文から解法を推測するヒント

| 問題の特徴 | 最初に疑う手法 |
|---|---|
| 偶数・奇数、周期 | 偶奇性・剰余 |
| 全組み合わせを試す | 全探索・ビット全探索 |
| 区間の合計を何度も聞かれる | 累積和 |
| 連続部分列の最大・最小 | 尺取り法・スライディングウィンドウ |
| 条件を満たす最小値を探す | 二分探索（単調性の確認が必要） |
| 最適な選び方・並べ方 | ソート・貪欲法・DP |
| 最短手数（各操作のコストが同じ） | BFS |
| 最短経路（非負の重み） | ダイクストラ法 |
| 重複や出現回数 | HashSet・HashMap |
| 選択肢を再利用して最適化 | DP |

## 20. おすすめの学習順序

### 初級：コーディングテストで優先
1. 計算量・偶奇・剰余
2. 配列・文字列・HashMap・HashSet
3. 全探索・ソート
4. 累積和・二分探索
5. Two Pointers・尺取り法
6. スタック・キュー

### 中級：典型問題の対応範囲を広げる
7. 貪欲法
8. BFS・DFS
9. DP
10. Union-Find・優先度付きキュー
11. ダイクストラ法

### 発展：競技プログラミング向け
12. Segment Tree・BIT
13. 高度な文字列アルゴリズム
14. 最小全域木・トポロジカルソート
15. 高度なDP・数論・組合せ

---

**学習のコツ**：各手法について「使う条件」「計算量」「Javaの典型実装」「典型問題1〜3問」をセットで覚えると、単なる暗記になりません。
