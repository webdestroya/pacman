#!/bin/bash

./netTest.pl JOIN
./netTest.pl JOIN CLYDE
./netTest.pl JOIN INKY
./netTest.pl JOIN PINKY

./netTest.pl GMOVE UP BLINKY 
./netTest.pl GMOVE DOWN INKY
./netTest.pl GMOVE LEFT CLYDE
./netTest.pl GMOVE UP PINKY
./netTest.pl GMOVE UP CLYDE
./netTest.pl GMOVE UP
./netTest.pl GMOVE RIGHT CLYDE
./netTest.pl GMOVE LEFT INKY
./netTest.pl GMOVE LEFT

