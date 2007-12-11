#!/bin/bash

cd bin/

echo "Building JAR File"

echo -n > files.txt
find code/uci/pacman/ai/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/controllers/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/game/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/gui/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/objects/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/multiplayer/ -type f -iname '*.class' >> files.txt

#find javazoom/ -type f -iname '*.class' >> files.txt
#find org/ -type f -iname '*.class' >> files.txt
#find ucigame/ -type f -iname '*.class' >> files.txt

jar cmf0 Manifest.txt pacman.jar @files.txt images sounds levels

rm files.txt

