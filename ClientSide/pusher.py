#THIS IS NOW AN INVALID FILE :P

import requests
import csv
import json
#Send csv data as a .tar.gz file
#change the url in the post request as necessary.
url = "http://10.42.0.88:6666" #'http://httpbin.org/post' #IP address of the server
fileobj = open('unfinished/mergedLog1.tar.gz', 'rb')
f=open("identityFile.txt", "r")
identity=f.readline()
r = requests.post(url, data={"identity":identity, "action":"pushing_logs"}, files={"archive": ("mergedLog1.tar.gz", fileobj)})
print r.text
print r.status_code