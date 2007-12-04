#!/bin/bash

cd code/uci/pacman/
grep -P '$' */*.java | grep -P '$' -c
