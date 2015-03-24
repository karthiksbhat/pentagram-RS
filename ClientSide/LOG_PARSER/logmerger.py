#analyze log files
#merge them into single file
#add file to send_folder
#ping server--if +ve zip and send send_folder else 
#delete all ulog files
#rewrite version to 1
#restart cron_job
import csv

for i in range(1,2):
	log_file=open("/home/nitin/Desktop/MINOR_PROJECT/LOG_PARSER/logs/ulog"+str(i)+".csv","r")
	reader = csv.reader(log_file)
	for row in reader:
		program_name = ''.join(row[10:])
		print program_name.split('/')[-1]
#user,pid,cpu,mem,vsz,rss,tty,stat,start,time,program





