#!/usr/bin/env bash

function exit_error() {
    echo "$1"
    exit 1
}

# Exit with instructions on how to use if a valid file isn't supplied as input
[[ ! -f "$1" ]] && echo 'Run with patch from non-GDX version as input' && exit

# Create a backup of the patch before converting
cp "$1" "$1".bak && echo "A backup has been created of your patch @ ${1}.bak" || exit_error "A backup couldn't be created @ ${1}.bak"

# Convert /src/com/* to /core/src/com/*
sed -i -re 's|([ab])(/src/com/)|\1/core\2|g' "$1"

# Convert /assets/* to /android/assets/*
sed -i -re 's|([ab])(/assets/)|\1/android\2|g' "$1"

# Convert /res/* to /android/res/*
sed -i -re 's|([ab])(/res/)|\1/android\2|g' "$1"

[[ $(diff "$1" "$1".bak) ]] && echo "Your patch has been successfully updated" || exit_error "No changes were made to your patch"
