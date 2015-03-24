import requests
import csv
import json
#Send csv data as a .tar.gz file
#change the url in the post request as necessary.
#data={"Identity":"1"}
# data=json.dumps(data),
fileobj = open('ulog1.tar.gz', 'rb')
r = requests.post('http://httpbin.org/post', files={"archive": ("ulog1.tar.gz", fileobj)})
print r.text
