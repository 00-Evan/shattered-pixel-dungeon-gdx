#!/usr/bin/env bash

function echo_exit() {
    echo -e "$1\n"
    exit $2
}

# Exit with instructions on how to use if a valid file isn't supplied as input
[[ ! -f "$1" ]] && echo_exit 'Run with patch from non-GDX version as input' 0

# Set the backup name and if it already exists, increment the backup number and try again
COUNT=1
BACKUP="${1}.${COUNT}.bak"
while [[ -f "$BACKUP" ]]; do
    COUNT=$(expr $COUNT + 1)
    BACKUP="${1}.${COUNT}.bak"
done

# Create a backup of the patch before converting
echo
cp "$1" "$BACKUP" \
    && echo "Created backup @ $BACKUP" \
    || echo_exit "Error: Couldn't create backup @ $BACKUP" 1

# Convert /src/com/* to /core/src/com/*
sed -i -re 's|([ab])(/src/com/)|\1/core\2|g' "$1"

# Convert /assets/* to /android/assets/*
sed -i -re 's|([ab])(/assets/)|\1/android\2|g' "$1"

# Convert /res/* to /android/res/*
sed -i -re 's|([ab])(/res/)|\1/android\2|g' "$1"

# Convert pixeldungeon locations to shatteredpixeldungeon ones
sed -i 's|com/watabou/pixeldungeon|com/shatteredpixel/shatteredpixeldungeon|g' "$1"

# Exit and report whether updates have been made, deleting the backup if they haven't
[[ ! $(diff "$1" "$BACKUP") ]] \
    && rm "$BACKUP" \
    && echo_exit "Deleting backup as no changes were made when converting" 0 \
    || echo "Your patch has been successfully updated"
