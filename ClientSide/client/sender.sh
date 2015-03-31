#!/bin/sh

cd unfinished
#wget -q --spider http://google.com
ping -c 1 http://google.com > /dev/null
if [$? -eq 0]; then
  #Does this work? :P
  tar -xvf mergedLog1.tar *.csv
  gzip mergedLog1.tar
  python ../pusher.py
