#!/bin/bash

cd bin/

echo -n > files.txt
find code/uci/pacman/ai/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/controllers/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/game/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/gui/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/objects/ -type f -iname '*.class' >> files.txt
find code/uci/pacman/multiplayer/ -type f -iname '*.class' >> files.txt

find javazoom/ -type f -iname '*.class' >> files.txt
find org/ -type f -iname '*.class' >> files.txt
find ucigame/ -type f -iname '*.class' >> files.txt

find images/ -type f -iname '*.png' >> files.txt
find sounds/ -type f -iname '*.mp3' >> files.txt
find levels/ -type f -iname '*.txt' >> files.txt


jar cmf Manifest.txt pacman.jar @files.txt

#mv pacman.jar /home/inf122/public_html/pacman.jar

#javac -Xlint:unchecked -d bin -deprecation @files.txt
rm files.txt

