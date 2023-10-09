import requests

url = 'http://localhost:8080/laborator1-1.0-SNAPSHOT/adjacency-matrix'
data = {'numVertices': '7'}

response = requests.post(url, data=data)

if response.status_code == 200:
    matrix_text = response.text
    matrix = [list(map(int, filter(bool, row.split(',')))) for row in matrix_text.splitlines() if row] #filter(bool, row.split(',')) removes empty strings from the split 

    for row in matrix:
        print(row)
else:
    print('Error:', response.status_code, response.text)