import os.path
import requests
import random
if os.path.isfile("identityFile.txt"):
	opF = open("identityFile.txt", "w")
	#identity = random.randint(1000,1000000) #NEEDS TO BE CHANGED AFTER DB CONNECTIVITY
	#opF.write(identity)
	#opF.close()
	identity=raw_input("Enter your unique username: ")
	name=raw_input("Enter your name: ")
	age=int(raw_input("Enter your age: "))
	emailID=raw_input("Enter your emailID: ")
	opF.write(identity)
	opF.close()
	parameters={"action":"register","identity":identity,"name":name, "age":age, "email":emailID}
	url="http://httpbin.org/get"#IP Address of Nitin
	r=requests.get(url, params={"action":"register","identity":identity,"name":name, "age":age, "email":emailID})
	print r.url