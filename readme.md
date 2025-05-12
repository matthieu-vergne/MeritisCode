# Steps

## Unit testability

### Level 1

#### Start

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.a_unit_testability.level1.A_*" -DtargetTests="fr.vergne.meritis.code.a_unit_testability.level1.A_*"
```
TODO

### Level 2

#### Start

Functional code which depends on DB.

```bash
mvn clean test -Dtest="fr.vergne.meritis.code.a_unit_testability.level2.A1_*"
```

Tests with DB: ~0.250s
(local SQLite DB, in practice usually add network too)

#### Sanity check: No DB

Tests remove DB init.

```bash
mvn clean test -Dtest="fr.vergne.meritis.code.a_unit_testability.level2.A2_*"
```

Failed tests

#### Improve

Refactor code to abstract DB dependency completely (custom interface).

```bash
mvn clean test -Dtest="fr.vergne.meritis.code.a_unit_testability.level2.B_*"
```

Tests with mocks: ~0.050s

#### Go Further

Refactor code to abstract DB dependency partially (mock `java.sql` classes).

```bash
mvn clean test -Dtest="fr.vergne.meritis.code.a_unit_testability.level2.C_*"
```

Tests with mocks: ~1.050s
Complexity is also way higher.

Warning: SQL language and dependencies are part of DB adapter, not functional code, they should be abstracted with the DB.

## Test Coverage

### Level 1

#### Start

Trivial test on trivial code.

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.b_test_coverage.level1.A_*" -DtargetTests="fr.vergne.meritis.code.b_test_coverage.level1.A_*"
```

Jacoco
* Instructions: 97%
* Branches: 66%

PIT
* Lines: 100%
* Mutations: 57%

#### Improve

One more test.

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.b_test_coverage.level1.B_*" -DtargetTests="fr.vergne.meritis.code.b_test_coverage.level1.B_*"
```

Jacoco
* Instructions: 100%
* Branches: 83%

PIT
* Lines: 100%
* Mutations: 86%

#### Go further

One more test.

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.b_test_coverage.level1.C_*" -DtargetTests="fr.vergne.meritis.code.b_test_coverage.level1.C_*"
```

Jacoco
* Instructions: 100%
* Branches: 100%

PIT
* Lines: 100%
* Mutations: 100%

### Level 2

#### Start

Single test on trivial but heavy code (1 path, many things at once).

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.b_test_coverage.level2.A_*" -DtargetTests="fr.vergne.meritis.code.b_test_coverage.level2.A_*"
```

Jacoco
* Instructions: 97% (just missing exception case)
* Branches: 100%

PIT
* Lines: 95% (just missing exception case)
* Mutations: 20%

1 path, 1 test is enough to execute everything.
But executing does not mean testing: we lack assertions.

#### Improve

Add missing assertions.

```bash
mvn clean compile test-compile test jacoco:report org.pitest:pitest-maven:mutationCoverage -Dtest="fr.vergne.meritis.code.b_test_coverage.level2.B_*" -DtargetTests="fr.vergne.meritis.code.b_test_coverage.level2.B_*"
```

Jacoco
* Instructions: 97% (just missing exception case)
* Branches: 100%

PIT
* Lines: 95% (just missing exception case)
* Mutations: 100%

#### Go further

Let's dig deeper with more mutations.

```bash
<mutators>ALL</mutators>
```

Coverage: 100% of 10 mutation -> 82% of 146 mutations.
Would need additional assertions or tests.
