#!/bin/sh
#echo "Done"
ID=`cat identityFile.txt`
cd unfinished
#echo "done cd"
#wget -q --spider http://google.com
ping -c 1 10.42.0.88 > /dev/null
#echo "done ping"
if [ $? -eq 0 ] 
then
  tar -cvf mergedLog1.tar *.csv
  #echo "done tar"
  gzip mergedLog1.tar
  #echo "done gzip"
  #cd ..
  #python pusher.py
  curl --form "fileupload=@mergedLog1.tar.gz" --form identity=$ID --form action=pushing_logs 10.42.0.88:6666
  cd unfinished
  rm *
  cd ..
fi