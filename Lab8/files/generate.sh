#!/bin/bash

# Налаштування
NUM_DIRS=10          # кількість головних директорій
NUM_FILES_PER_DIR=5  # кількість файлів у директорії
WORDS_PER_FILE=20    # кількість слів у файлі
SUBDIR_CHANCE=30     # шанс (у %) створити підпапку замість файлу

# Масив слів для генерації випадкового тексту
WORDS=("apple" "banana" "car" "dog" "elephant" "fish" "grape" "house" "ice" "juice" "kite" "lion" "moon" "night" "orange")

generate_dir() {
    local DIR_PATH=$1
    local DEPTH=$2  # глибина рекурсії, щоб не зациклитися

    mkdir -p "$DIR_PATH"

    for ((i=1; i<=NUM_FILES_PER_DIR; i++))
    do
        RAND=$((RANDOM % 100))
        if [[ $RAND -lt $SUBDIR_CHANCE && $DEPTH -lt 3 ]]; then
            # Створюємо піддиректорію
            SUBDIR_NAME="$DIR_PATH/subdir_$i"
            generate_dir "$SUBDIR_NAME" $((DEPTH+1))
        else
            # Створюємо файл
            FILE_NAME="$DIR_PATH/file_$i.txt"
            > "$FILE_NAME"
            for ((k=1; k<=WORDS_PER_FILE; k++)); do
                RANDOM_WORD=${WORDS[$RANDOM % ${#WORDS[@]}]}
                echo -n "$RANDOM_WORD " >> "$FILE_NAME"
            done
            echo "" >> "$FILE_NAME"
        fi
    done
}

# Генеруємо головні директорії
for ((i=1; i<=NUM_DIRS; i++))
do
    generate_dir "dir_$i" 0
done

echo "Генерація завершена!"
