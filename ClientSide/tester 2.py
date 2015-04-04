import requests
f=open("identityFile.txt", "r")
identity=f.readline()
url="http://10.42.0.88:6666"#"http://httpbin.org/get"
r=requests.get(url, params={"identity":identity})
print r.text
print r.status_code