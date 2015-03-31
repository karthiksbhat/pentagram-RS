import requests
import csv
import json
#Send csv data as a .tar.gz file
#change the url in the post request as necessary.
#data={"Identity":"1"}
# data=json.dumps(data),
url = 'http://httpbin.org/post' #IP address of the server
fileobj = open('unfinished/mergedLog1.tar.gz', 'rb')
r = requests.post(url, files={"archive": ("mergedLog1.tar.gz", fileobj)})
print r.text

#ACTION = get_reco, register, pushing_logs