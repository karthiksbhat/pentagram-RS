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
version=int(versionfile.read()[:-1])
mergedLog=open(path+"/unfinished/mergedLogs"+str(version)+".csv", "w")
print version
for i in xrange(1,3):
	readfile=open(path+"/logs/ulog"+str(i)+".csv","r")
	for line in readfile:
		mergedLog.write(line)

versionfile.close()
versionWrite=open("versionfile.txt", "w")
version+=1
versionWrite.write(version)

#subprocess.Popen(["cd", "../client/"])
subprocess.Popen(["sh", "sender.sh"])
pathToCron=os.path.abspath("./cron_job.py")
print pathToCron
subprocess.Popen(["python", pathToCron, "start"])