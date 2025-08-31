# DataBase-Engine

A simple database engine implemented in Java with paged storage and Octree-based indexing across three columns. It supports creating tables, inserting rows, creating indexes, and selecting via basic predicates.


## Features
- Table creation with metadata persisted to CSV.
- Paged storage with configurable max rows per page.
- Octree index on three columns for efficient multi-attribute lookup.
- Insert and select with basic operators.
- Demo CSV datasets: [students_table.csv](students_table.csv), [courses_table.csv](courses_table.csv), [transcripts_table.csv](transcripts_table.csv), [pcs_table.csv](pcs_table.csv).

## Project structure
- Core classes:
  - [src/DBApp.java](src/DBApp.java) — entry point, API, serialization, config.
  - [src/SQLTerm.java](src/SQLTerm.java) — selection predicates
- Runtime artifacts:
  - table metadata: metadata.csv, table names: tableNames.bin
  - tables: {Table}.bin, pages: {Table}Page{ID}.bin
  - octrees: Octrees.bin

## Requirements
- Java 8+ (JDK)

## Build and run
Use the integrated terminal in VS Code (or any shell):

```sh
# Compile
javac -d bin src/*.java

# Run (main method in DBApp)
java -cp bin DBApp
```

The first run initializes config and metadata, creates demo tables, builds an index, and inserts sample records.

## Configuration
The engine reads/writes its config at startup:
- Path: src/resources/DBApp.config
- Keys:
  - MaximumRowsCountinTablePage — page row capacity
  - MaximumEntriesinOctreeNode — Octree node fanout
You can edit these values before running to tune page and index sizes.

## Usage (API)
Minimal example using the public API in [src/DBApp.java](src/DBApp.java):

```java
// Create and init
DBApp db = new DBApp();
db.init();

// Create a table (see createTable usage in DBApp.main)
Hashtable<String, String> types = new Hashtable<>();
types.put("id", "java.lang.String");
types.put("gpa", "java.lang.Double");
types.put("first_name", "java.lang.String");
types.put("last_name", "java.lang.String");
types.put("dob", "java.util.Date");

Hashtable<String, String> min = new Hashtable<>();
min.put("id", "43-0000"); min.put("gpa", "0.7");
min.put("first_name", "AAAAAA"); min.put("last_name", "AAAAAA");
min.put("dob", "1990-01-01");

Hashtable<String, String> max = new Hashtable<>();
max.put("id", "99-9999"); max.put("gpa", "5.0");
max.put("first_name", "zzzzzz"); max.put("last_name", "zzzzzz");
max.put("dob", "2000-12-31");

db.createTable("students", "id", types, min, max);

// Insert a row
Hashtable<String, Object> row = new Hashtable<>();
row.put("id", "88-1096");
row.put("gpa", 4.32);
row.put("first_name", "Alice");
row.put("last_name", "Smith");
row.put("dob", new java.util.Date(1998 - 1900, 4 - 1, 29));
db.insertIntoTable("students", row);

// Create an Octree index on three columns
String[] idxCols = { "id", "gpa", "first_name" };
db.createIndex("students", idxCols);

// Select with predicates
SQLTerm s1 = new SQLTerm("students", "id", "=", "88-1096");
SQLTerm s2 = new SQLTerm("students", "gpa", ">=", 4.0);
SQLTerm[] terms = { s1, s2 };
String[] ops = { "AND" };
Iterator it = db.selectFromTable(terms, ops);
while (it.hasNext()) System.out.println(it.next());
```

Reference implementations and examples:
- Entry points and demos: [src/DBApp.java](src/DBApp.java)
- Selection predicate class: [src/SQLTerm.java](src/SQLTerm.java)

## Notes
- Demo helpers in [src/DBApp.java](src/DBApp.java) can load rows from the CSVs to populate tables.
- Runtime files (.bin, metadata.csv) are created in the project root.
- Adjust config before first run to avoid re-initialization overwriting values.