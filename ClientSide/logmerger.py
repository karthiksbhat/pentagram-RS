#analyze log files
#merge them into single file
#add file to unfinished (the changed folder name)
#ping server--if +ve zip and send send_folder else
#delete all ulog files
#rewrite version to 1
#restart cron_job
import csv
import os
import subprocess

allowedApps = ['vlc','limechat','bash','chrome','sublime','bettertouchtool','eclipse','netbeans','rhythmbox','xchat','libreoffice','chromium','evince','codeblocks','geany']#For Whitelisting


path=os.path.abspath('.')
versionfile=open("versionfile.txt", "r")#This file will hold the version number of logs to be pushed
version=int(versionfile.read())
mergedLog=open(path+"/unfinished/mergedLogs"+str(version)+".csv", "w")
print "VersionFile.txt:" + str(version)
for i in xrange(1,2):
	readfile=open(path+"/logs/ulog"+str(i)+".csv","r")
	for line in readfile:
		# print line
		if "PID,CPU" in line:
			continue
		else:
			# print "here"
			values=line.split(",")
			value=[]
			# print values
			for app in allowedApps:
				tempVal=values[10:]
				# print tempVal
				for tempApp in tempVal:
					tempApp=tempApp.lower()
					if app in tempApp:
						value=values[:9]
						value.append(app)
						# print value
						break
				if len(value) > 0:
					lineNew=",".join(value)
					print lineNew
					mergedLog.write(lineNew)

versionfile.close()
versionWrite=open("versionfile.txt", "w")
version+=1
versionWrite.write(str(version))
versionWrite.close()

print "VersionFile updated"
#subprocess.Popen(["cd", "../client/"])
os.system('sh sender.sh')
# pathToCron=os.path.abspath("cron_job.py")
# print pathToCron
