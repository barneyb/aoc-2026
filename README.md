# aoc-2026

Advent of Code 2026 solvers, with some solvers from earlier years as warmup. If you don't know
what [Advent of Code](https://adventofcode.com/) is, you should go see! It's both lovely, and will help make sense of
this repo. :)

https://github.com/barneyb/aoc2017 has an index of all years' repositories.

## Prerequisites

Solvers are written in Kotlin, built with Gradle, and require Java 21 or newer. Input is acquired
via [advent-of-code-data](https://github.com/wimglenn/advent-of-code-data), which requires Python and your token
properly configured. Alternatively, you can pipe input in; see the `--pipe` option on `solve`.

Verify everything is set up (your output will differ):

```
% ./gradlew classes
BUILD SUCCESSFUL in 295ms

% aocd 2015 1
()(()((()((((()(((((((() # and ~7,000 more...
```

## Running for Dev

Once set up, you can solve [Not Quite Lisp](https://adventofcode.com/2015/day/1) (again, your output will differ):

```
% ./gradlew run --args="solve 2015 1"
Part A:	321
Part B:	1234
Nanos : 99578000
Time  : 99.578ms
```

There's still an `aocd` call behind the curtain, reusing how the solver was selected. The explicit version seems faster,
but it's only because of how `aocd` gets invoked:

```
% aocd 2015 1 | ./gradlew run --args="solve --pipe 2015 1"
Part A: 321
Part B: 1234
Nanos : 2679208
Time  : 2.679208ms
```

There are other things you can do too:

```
% ./gradlew run --args="-h"
Usage: main [<options>] <command> [<args>]...

Options:
  -h, --help  Show this message and exit

Commands:
  list   List available solvers, one per line.
  solve  Run a specific solver, then print its answers and timing info.
```

## Building for Distribution

If you aren't interested in touching the sources, a binary can be built with a rather more graceful CLI than Gradle's.
The *nix entry is shown below, but there's a `.bat` file in the same directory.

```
% ./gradlew installDist
% cd build/install/aoc-2026/bin
% ./aoc-2026 solve 2015 1
Part A:	321
Part B:	1234
Nanos :	100507208
Time  :	100.507208ms
```

Pre-built binaries are not published, so you must create your own.
