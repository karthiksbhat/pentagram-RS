import requests
import csv

#Send csv data as a .tar.gz file
#change the url in the post request as necessary.
fileobj = open('ulog1.tar.gz', 'rb')
r = requests.post('http://httpbin.org/post', files={"archive": ("ulog1.tar.gz", fileobj)})
print r.text

#Send csv data as a JSON
# sendCSV={}
# c=0
# with open('ulog1.csv', 'r') as f:
# 	readFile=csv.reader(f)
# 	for row in readFile:
# 		sendCSV['line'+str(c)]=row
# 		c+=1

# r = requests.post("http://httpbin.org/post", data=sendCSV)
# print r.text