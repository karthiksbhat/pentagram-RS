from Tkinter import *
import requests
import os.path
import sys
import subprocess

def register():
	uname=username.get()
	Name=name.get()
	Age=age.get()
	Email=email.get()
	
	ipF = open("identityFile.txt", "w")
	ipF.write(uname)
	ipF.close()

	parameters={"action":"register","identity":uname,"name":Name, "age":Age, "email":Email}
	url="http://httpbin.org/get"#"http://10.42.0.88:6666"#IP Address of server
	r=requests.get(url, params={"action":"register","identity":uname,"name":Name, "age":Age, "email":Email})
	if r.status_code == 200:
		print r.text
		print r.url
		master.quit()
		generalCall()
 
def start():
	print "In start"
	pathToCron=os.path.abspath("../LOG_PARSER/cron_job.py")
	#print pathToCron
	#type(pathToCron)
	subprocess.Popen(['python', pathToCron, "start"])
	#subprocess.call(["cd", str(current)+"/../LOG_PASRER/"])
	#print current
	#subprocess.call(["python", "tester.py", "start"])

def stop():
	print "In stop"
	pathToCron=os.path.abspath("../LOG_PARSER/cron_job.py")
	subprocess.Popen(['python', pathToCron, "stop"])
	#subprocess.call(["python", "../LOG_PASRER/cron_job.py", "stop"])

def getReco():
	print "get reco"
	#subprocess.call(["python", "getReco.py"])

def generalCall():
	master = Tk()
	Label(master, text="Pentagram-RS").grid(row=0, column=1, columnspan=2)
	Label(master, text="Hi. The most suitable recommendation for you will appear in the textbox below, when you ask for recommendations.").grid(row=2, column=0, columnspan=4)
	Button(master, text='Start RS', command=start).grid(row=0, column=0, sticky=E, pady=4)
	Button(master, text='Stop RS', command=stop).grid(row=0, column=3, sticky=W, pady=4)
	Button(master, text='Quit', command=master.quit).grid(row=0, column=4, sticky=E, pady=4)
	recoArea = Entry(master)
	recoArea.grid(row=3, column=0, columnspan=4, sticky=EW)
	Button(master, text='Get Recommendation', command=getReco).grid(row=4, column=2, sticky=W, pady=4)
	mainloop()

if not os.path.isfile("identityFile.txt"):
	master = Tk()
	Label(master, text="Pentagram-RS").grid(row=0, column=1)
	Label(master, text="username").grid(row=1)
	Label(master, text="Name").grid(row=2)
	Label(master, text="Age").grid(row=3)
	Label(master, text="Email").grid(row=4)

	username = Entry(master)
	name = Entry(master)
	age = Entry(master)
	email = Entry(master)

	username.grid(row=1, column=1)
	name.grid(row=2, column=1)
	age.grid(row=3, column=1)
	email.grid(row=4, column=1)

	Button(master, text='Quit', command=master.quit).grid(row=5, column=1, sticky=E, pady=4)
	Button(master, text='Register', command=register).grid(row=5, column=1, sticky=W, pady=4)
	mainloop()

else:
	generalCall()


















