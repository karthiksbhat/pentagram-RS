import os 
import subprocess
import tempfile
import re
import csv
import sys

with tempfile.TemporaryFile() as tempf:
    proc = subprocess.Popen(['ps','aux'], stdout=tempf)
    proc.wait()
    tempf.seek(0)
    lines=tempf.read().split('\n')
    del lines[len(lines)-1]   #removing extraneous line
    
for number in range(len(lines)):
    lines[number]=re.sub(' +',',',lines[number])
    	

    
e=open("/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/VERSION","r+")
current_version = int(e.readline())
f=open("/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/log"+str((current_version/3)+1)+".csv","a")
if(current_version%3==0 and current_version!=0):
    os.system("sort -u "+"/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/log"+str(current_version/3)+".csv" + " > " + "/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/ulog"+str(current_version/3)+".csv")
    os.system("echo 'USER,PID,CPU,MEM,VSZ,RSS,TTY,STAT,START,TIME,PROGRAM' | cat - "+"/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/ulog"+str(current_version/3)+".csv"+" > temp && mv temp "+"/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/ulog"+str(current_version/3)+".csv")
    #after every time chunk remove redundant lines in the log files
    os.system("rm "+"/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/log"+str(current_version/3)+".csv")
writer=csv.writer(f)
    
    
with tempfile.TemporaryFile() as tempf1:
    proc = subprocess.Popen(['whoami'], stdout=tempf1)
    proc.wait()
    tempf1.seek(0)
    username=tempf1.read().strip()


for line in lines:
    if ((line.split(','))[0] in [username]):
    		array=[]
    		for word in line.split(','):
    			array.append(word)
    		writer.writerow(array)
    	
if (current_version==23):
    os.system('python /home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/cron_job.py stop')
    os.system('python /home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logmerger.py')                   
                
f.close()
current_version = current_version+1
e.seek(0)
e.write(str(current_version))
e.close()



 




    
