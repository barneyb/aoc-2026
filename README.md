# aoc-2026

Advent of Code 2026 solvers, with some solvers from earlier years as warmup. If you don't know
what [Advent of Code](https://adventofcode.com/) is, you should go see! It's both lovely, and will help make sense of
this repo. :)

https://github.com/barneyb/aoc2017 has an index of all years' repositories.

## Running for Dev

Solvers are written in Kotlin, built with Gradle, and require Java 21 or newer. Input is acquired
via [advent-of-code-data](https://github.com/wimglenn/advent-of-code-data), which requires Python and your token
properly configured. Alternatively, you can pipe input in; see the `--pipe` option on `solve`.

To verify (your output will differ):

```
% ./gradlew classes
BUILD SUCCESSFUL in 295ms

% aocd 2015 1
()(()((()((((()(((((((() # and ~7,000 more...
```

With that squared away, solve [Not Quite Lisp](https://adventofcode.com/2015/day/1) (again, your output will differ):

```
% ./gradlew run --quiet --args="solve 2015 1"
Part A:	321
Part B:	1234
Nanos :	174518625
Time  :	174.518625ms
```

There are other things you can do too:

```
% ./gradlew run --quiet --args="-h"
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
Nanos :	174518625
Time  :	174.518625ms
```

Pre-built binaries are not published, so you must create your own.
