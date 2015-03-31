import requests
f=open("identityFile.txt", "r")
identity=f.readline()[:-1]
url="http://httpbin.org/get"
r=requests.get(url, params={"identity":identity})
print r.url

#WORKING, no change required.