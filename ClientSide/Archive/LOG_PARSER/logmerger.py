#analyze log files
#merge them into single file
#add file to unfinished (the changed folder name)
#ping server--if +ve zip and send send_folder else
#delete all ulog files
#rewrite version to 1
#restart cron_job
import csv
import os.path
import subprocess

allowedApps = ['VLC', 'LimeChat']#For Whitelisting


path=os.path.abspath('')
versionfile=open("versionfile.txt", "r")#This file will hold the version number of logs to be pushed
version=versionfile.read()[:-1]
mergedLog=open(path+"/../client/unfinished/mergedLogs"+str(version)+".csv", "w")
print version
for i in xrange(1,3):
	readfile=open(path+"/logs/ulog"+str(i)+".csv","r")
	for line in readfile:
		mergedLog.write(line)

#subprocess.Popen(["cd", "../client/"])
subprocess.Popen(["sh", "../client/sender.sh"])
pathToCron=os.path.abspath("./cron_job.py")
print pathToCron
subprocess.Popen(["python", pathToCron, "start"])
#for i in range(1,2):
#	log_file=open("/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/ulog"+str(i)+".csv","r")
#	reader = csv.reader(log_file)
#	for row in reader:
#		program_name = ''.join(row[10:])
#		print program_name.split('/')[-1]
#user,pid,cpu,mem,vsz,rss,tty,stat,start,time,program

#Uncomment to check
#subprocess.Popen(["mv", "FileNameHere", "../../client/unfinished/FileNameHere"])