import requests
f=open("indentityFile.txt", "r")
identity=f.readline()
r=requests.get("http://httpbin.org/get", params={"identity":identity})
print r.url
