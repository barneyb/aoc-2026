import os
import subprocess
import sys
import tempfile


def java_dir():
    return os.path.join(os.path.dirname(__file__), "..")


def _gradle(args, input=None):
    return subprocess.run(
        ["./gradlew", "--quiet", "run", f"--args={args}"],
        cwd=java_dir(),
        input=input,
        text=True,
        capture_output=True,
    )


def support():
    proc = _gradle("list")
    return [(int(y), int(d))
            for (y, d) in [line.split('\t')[:2]
                           for line in proc.stdout.strip().splitlines()]]


def solve(year, day, data):
    proc = _gradle(f"solve -p {year} {day}", data)

    part_a = None
    part_b = None
    nanos = None
    for line in proc.stdout.strip().splitlines():
        (label, value) = line.split("\t")[:2]
        if label == "PART_A:":
            part_a = value
        elif label == "PART_B:":
            part_b = value
        elif label == "NANOS :":
            nanos = int(value)
    return part_a, part_b, nanos


def main() -> int:
    cmd = sys.argv[1]
    if cmd == "support":
        print(support())
    elif cmd == "solve":
        print(solve(int(sys.argv[2]),
                    int(sys.argv[3]),
                    sys.argv[4]))
    else:
        return f"Unknown '{cmd}' command"


if __name__ == "__main__":
    sys.exit(main())
