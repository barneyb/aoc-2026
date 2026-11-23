#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

YEAR="$1"
DAY="$2"

if [ -z "$YEAR" ] && [ -z "$DAY" ]; then
    PUZZLE=$(git status --porcelain src/main/kotlin \
        | grep -E '/y.+/d.+\.kt$' \
        | head -n 1 \
        | rev \
        | cut -d / -f 1 \
        | cut -d . -f 2 \
        | rev)
        if [ -z "$PUZZLE" ]; then
            echo "There's no dirty puzzle - specify the year and day" >&2
            exit 1
        fi
    LINE=$(./gradlew --quiet run --args=list | grep "$PUZZLE")
    YEAR=$(echo "$LINE" | cut -f 1)
    DAY=$(echo "$LINE" | cut -f 2 | sed -Ee 's/^0+//')
elif (( YEAR < DAY )); then
    # flip 'em around
    t=$YEAR
    YEAR=$DAY
    DAY=$t
fi

echo "Verifying..."
echo "  year: '$YEAR'"
echo "  day : '$DAY'"
aocd "$YEAR" "$DAY" \
    | ./gradlew --quiet run --args="solve -p $YEAR $DAY" \
    | while IFS= read -r line; do
    echo "$line"
    for PART in A B; do
        if echo "$line" | grep -qE "^PART_${PART}:\t.+$"; then
            ANSWER=$(echo "$line" | cut -f 2)
            echo -n "Verify Part $PART ... "
            python3 util/aocd_submit_wrapper.py "$YEAR" "$DAY" "$PART" "$ANSWER"
        fi
    done
done
