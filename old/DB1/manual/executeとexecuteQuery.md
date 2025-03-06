# `execute` の比較

| 特徴                      | `execute`                                    | `executeQuery`                            | `executeUpdate`                         | `executeBatch`                             |
|---------------------------|----------------------------------------------|-------------------------------------------|-----------------------------------------|-------------------------------------------|
| **戻り値の型**             | `boolean`                                    | `ResultSet`                               | `int`                                   | `int[]`                                   |
| **主な用途**               | 結果セットを返すかもしれないSQLを実行       | `SELECT`文や結果セットを返すSQLを実行    | 行を変更するSQL（例: `INSERT`, `UPDATE`, `DELETE`）を実行 | バッチ処理（複数のSQLを一度に実行）     |
| **SQLのタイプ**            | `SELECT`以外にも、`INSERT`、`UPDATE`、`DELETE`なども実行可能 | 主に`SELECT`文                             | `INSERT`、`UPDATE`、`DELETE`文          | 複数の`INSERT`、`UPDATE`、`DELETE`文を一度に実行 |
| **実行結果**               | 結果セットがある場合は`true`、なければ`false` | `ResultSet`（結果セット）を返す           | 影響を受けた行数を返す                 | 各SQLステートメントの影響を受けた行数の配列 |
| **例**                     | `INSERT INTO users (name) VALUES ('John')`   | `SELECT * FROM users`                    | `UPDATE users SET name = 'John' WHERE id = 1` | `batch.add("INSERT INTO users (name) VALUES ('John')");` |
| **主に使われる場面**       | 複雑なSQLを実行、結果セットがあるか不明な場合 | `SELECT`文やデータ取得                   | データを挿入、更新、削除する場合       | 複数のSQL文をまとめて実行したい場合（executeUpdateの複数版）|


※複数のSQLを実行するexecuteBatch()もある

# CASCADE

アーティストが削除されているのに、そのアーティストの音楽が残っていたらおかしい
そのため、アーティストを削除したら、そのアーティストに関連する音楽も全て自動的に削除されるようにする
※親テーブル: アーティストの一覧
　子テーブル: 音楽の一覧

# RESTRICT

子テーブルに関連するデータが存在する限り、親テーブルのレコードが削除できないようにする場合

# AUTO_INCREMENT

idなど自動生成されるカラム値
INSERTする際に指定しなくて良い値
以下のように第二引数でオプション指定することでgetGeneratedKeys()で自動生成されたidを取得可能
statement.execute(query, Statement.RETURN_GENERATED_KEYS); 
statement.getGeneratedKeys(); // ResultSet に格納される

# TRANSACTION (COMMIT/ROLLBACK)

JDBCではデフォルトでauto-commitモードが有効化されている
auto-commitモードを有効化すれば、自動的にCommitされ、InsertやDeleteが自動的に反映される
複数のSQLを実行してからCommitやRollbackをしたい場合は、手動でCommitするようにする必要がある
Commit/Rollbackまでの一貫性のある処理をTransactionという

conn.setAutoCommit(false); // でオフ
conn.commit(); // コミット try:
conn.rollback(); // ロールバック catch:

※DDL(テーブルの作成や削除)はロールバックできないため注意

# throws SQLException

関数で明示的にthrowsしないとエラーになる
private static boolean checkSchema(Connection conn) throws SQLException{}

# BATCH

// 複数のSQLを実行するTransactionの場合は、Batchにして実行すると1度の通信で実行されるため、パフォーマンスが良い
statement.addBatch(deleteSongs);
statement.addBatch(deleteAlbums);
statement.addBatch(deleteArtist);
int[] results = statement.executeBatch(); // 上記3つのsqlをaddされた順番に実行

# SQL State

StackTraceのエラーメッセージからSQLのステータスコードやエラーコードが分かり、それらの情報からエラーの原因を調べることが可能
42000は、操作対象のDatabaseが存在しないことを示す
        }catch(SQLException e){
            e.printStackTrace();
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());

# CONSTRAINT/REFERENCES (Foreign Key)

Constraintにより、他のテーブルにも存在するidを自身のテーブルのid出も利用する場合
他のテーブルに存在するidの値でなければ挿入できないような制約を設定可能

Referencesも同様


# SCHEMA

データベースの中で設定できるグループ名
RDBにおける名前空間(グループ名)であり、データベースの中で同じ名前のテーブルは異なるスキーマであれば作成可能

# メモ

%sなどで代入する際は、文字列は必ずシングルクォートで囲むことを忘れずに