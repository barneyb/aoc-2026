#!/usr/bin/env python3
import datetime
import re
import subprocess
import sys

from aocd.models import Puzzle
from pathlib import Path
from zoneinfo import ZoneInfo


def nope(msg):
    print(msg)
    exit(1)


def exec(*args):
    subprocess.run([*args]).check_returncode()


def git(*args):
    exec("git", *args)


if len(sys.argv) == 1:
    nope(f"Usage: {sys.argv[0]} day [year]")

AOC_NOW = datetime.datetime.now(tz=ZoneInfo("America/New_York"))
LATEST_YEAR = AOC_NOW.year - int(AOC_NOW.month != 12)

year = int(sys.argv[1])
day = int(sys.argv[2]) if len(sys.argv) > 2 and sys.argv[2] != "" else AOC_NOW.year
if year < day:
    year, day = day, year
if year < 2015:
    nope(f"No Advent of Code in {year}")
if year > LATEST_YEAR:
    nope(f"Advent of Code {year} hasn't started")
if day > 25 or (year >= 2025 and day > 12):
    nope(f"No day {day} for {year}")
day_str = f"{day:02d}"

try:
    git("diff", "--staged", "--quiet")
    git("diff", "--quiet")
except subprocess.CalledProcessError:
    git("commit", "-am", f"WIP: auto-commit before starting {year}/{day_str}")
git("checkout", "master")
git("pull")
exec("./gradlew", "test")

name = Puzzle(year=year, day=day).title
name = f"{name}"
name = re.sub(r'[^a-zA-Z0-9]+', '', name)

root = "src/main/kotlin/com/barneyb/aoc2026"
if year == 2026:
    dir = f"day{day_str}"
    pkg = f"day{day_str}"
else:
    dir = f"y{year}/d{day_str}"
    pkg = f"y{year}.d{day_str}"

git("checkout", "--no-track", "-b", f"{dir}/{name}", "master")


def add_file(path, text):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(text)
    git("add", str(path))
    exec("idea", str(path))


add_file(Path(root, dir, f"{name}.kt"), f"""\
package com.barneyb.aoc2026.{pkg}

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class {name} : Solver<Any, Any>() {{

    override fun solveInternal(ctx: Context) = ctx
//        .parse {{ it.readLine() }}
        .solveA(::solveA)

    fun solveA(input: String) = input.length

}}

fun main() {{
    {name}().solveAocd()
}}
""")

add_file(Path(re.sub(r'/main/', "/test/", root), dir, f"{name}Test.kt"), f"""\
package com.barneyb.aoc2026.{pkg}

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class {name}Test {{

    @Test
    fun solveA() {{
        assertEquals(6,
                     {name}().solveA("oh hai"))
    }}

}}
""")
