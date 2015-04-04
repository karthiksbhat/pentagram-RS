import requests
f=open("identityFile.txt", "r")
identity=f.readline()
url="http://httpbin.org/get"#"http://10.42.0.88:6666"#"http://httpbin.org/get"
r=requests.get(url, params={"identity":identity, "action":"get_reco"})
print r.text
print r.status_code
#WORKING, no change required.