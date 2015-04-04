import os.path
import requests
import random
#For new user only.
#SHOULD BE "if not os.path..."
if os.path.isfile("identityFile.txt"):
	opF = open("identityFile.txt", "w")
	identity=raw_input("Enter your unique username: ")
	name=raw_input("Enter your name: ")
	age=int(raw_input("Enter your age: "))
	emailID=raw_input("Enter your emailID: ")
	opF.write(identity)
	opF.close()
	parameters={"action":"register","identity":identity,"name":name, "age":age, "email":emailID}
	url="http://10.42.0.88:6666"#"http://httpbin.org/get"#IP Address of server
	r=requests.get(url, params={"action":"register","identity":identity,"name":name, "age":age, "email":emailID})
	print r.url
	print r.text
	print r.status_code
	#WORKING, no change required.