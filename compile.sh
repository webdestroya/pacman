#!/bin/bash

echo -n > files.txt
find ucigame/ -type f -iname '*.java' >> files.txt

find code/uci/pacman/ai/ -type f -iname '*.java' >> files.txt
find code/uci/pacman/controllers/ -type f -iname '*.java' >> files.txt
find code/uci/pacman/game/ -type f -iname '*.java' >> files.txt
find code/uci/pacman/gui/ -type f -iname '*.java' >> files.txt
find code/uci/pacman/objects/ -type f -iname '*.java' >> files.txt
find code/uci/pacman/multiplayer/ -type f -iname '*.java' >> files.txt

javac -Xlint:unchecked -d bin -deprecation @files.txt
rm files.txt
