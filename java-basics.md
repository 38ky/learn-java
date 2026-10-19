# Java 基本仕様チートシート

Javaの型・初期値・配列の基本を確認するためのメモ。

## インデックス

- [各型のデフォルト値](#default-values)
- [初期値が付く場所・付かない場所](#initialization)
- [配列・多次元配列](#arrays)
- [未設定の判定](#unset-check)

<a id="default-values"></a>
## 各型のデフォルト値

**配列の要素**と**クラスのフィールド**は、値を指定しなければ次の値で初期化される。

| 型 | デフォルト値 | 備考 |
|---|---|---|
| `byte` | `0` | 整数 |
| `short` | `0` | 整数 |
| `int` | `0` | 整数 |
| `long` | `0L` | 整数 |
| `float` | `0.0f` | 小数 |
| `double` | `0.0d` | 小数 |
| `char` | `'\u0000'` | ヌル文字。Javaコードでは `'\0'` とも書ける |
| `boolean` | `false` | 真偽値 |
| `String` | `null` | 参照型 |
| `Integer` / `Character` など | `null` | ラッパー型 |
| その他の参照型 | `null` | オブジェクトや配列など |

※ `char` の初期値は **`'0'`（数字のゼロ）ではない**。

<a id="initialization"></a>
## 初期値が付く場所・付かない場所

```java
int[] numbers = new int[3];       // {0, 0, 0}
char[] chars = new char[3];       // {'\0', '\0', '\0'}
String[] words = new String[3];   // {null, null, null}
boolean[] flags = new boolean[3]; // {false, false, false}
```

メソッド内の**ローカル変数**は自動初期化されない。値を代入してから使う。

```java
int x;
// System.out.println(x); // コンパイルエラー：初期化前
x = 10;
System.out.println(x); // 10
```

<a id="arrays"></a>
## 配列・多次元配列

```java
// 【2次元配列】行数3、列数4。各要素はnull
String[][] board = new String[3][4];

// 【個別代入】0始まり。2行目・3列目にX
board[1][2] = "X";

// 【宣言と同時に値を指定】
String[][] grid = {
    {".", "."},
    {".", "X"}
};

// 【全要素を"."にする】行ごとにfillする
for (int i = 0; i < board.length; i++) {
    java.util.Arrays.fill(board[i], ".");
}
```

`char[][]` も同じ考え方で使える。

```java
char[][] board = new char[3][4]; // 初期値はすべて'\0'
board[1][2] = 'X';
```

<a id="unset-check"></a>
## 未設定の判定

```java
char[] chars = new char[2];
System.out.println(chars[0] == '\0'); // true：初期値のまま

String[] words = new String[2];
System.out.println(words[0] == null); // true
System.out.println("X".equals(words[0])); // false：nullでも安全
// words[0].equals("X"); // NullPointerException
```

**注意：** `0`・`false`・`'\0'` は「未設定専用の印」ではなく通常の値としても代入できる。初期値と有効な値を区別したいときは、別のフラグや適切な番兵値を使う。
