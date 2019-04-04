# 파이썬 200제 활용

"""
f = open('stockcode.txt', 'r', encoding='UTF8')
data = f.read()
print(data)
f.close()
print()
"""
# -------------------------------------------

"""
f = open('stockcode.txt', 'r', encoding='UTF8')
line_num = 1
line = f.readline()
while line:
    print('%d %s' %(line_num, line), end='')
    line = f.readline()
    line_num += 1
f.close()
print()
"""
# -------------------------------------------

"""
f = open('stockcode.txt', 'r', encoding='UTF8')
lines = f.readlines()
for line_num, line in enumerate(lines):
    print('%d %s' %(line_num + 1, line), end='')
f.close()
print()
"""
# -------------------------------------------

"""
text = input('파일에 저장할 내용을 입력하세요 : ')
f = open('mydata.txt', 'w')
f.write(text)
f.close()
print()
"""
# -------------------------------------------

"""
count = 1
data = []
print('파일에 내용을 저장하려면 내용을 입력하지말고 [Enter]를 누르세요.')
while True:
    text = input('[%d] 파일에 저장할 내용을 입력하세요 : ' %count)
    if text == '':
        break
    data.append(text + '\n')
    count += 1

f = open('mydata.txt', 'w')
f.writelines(data)
f.close()
print()
"""
# -------------------------------------------

"""
f = open('stockcode.txt', 'r', encoding='UTF8')
h = open('stockcode_copy.txt', 'w', encoding='UTF8')

data = f.read()
h.write(data)

f.close()
h.close()
print()
"""
# -------------------------------------------

bufsize = 1024
f = open('lena.png', 'rb')
h = open('lena_copy.png', 'wb')

data = f.read(bufsize)
while data:
    h.write(data)
    data = f.read(bufsize)

f.close()
h.close()
print()
# -------------------------------------------

# with open('stockcode.txt', 'r', encoding='UTF8') as f:
#     for line_num, line in enumerate(f.readlines()):
#         print('%d %s' %(line_num + 1, line), end='')
# print()
# -------------------------------------------

spos = 105
size = 500

f = open('stockcode.txt', 'r', encoding='UTF8')
h = open('stockcode_part.txt', 'w', encoding='UTF8')

f.seek(spos)
data = f.read(size)
h.write(data)

h.close()
f.close()
print()
# -------------------------------------------

from os.path import getsize

file1 = 'stockcode.txt'
file2 = 'lena.png'

file_size1 = getsize(file1)
file_size2 = getsize(file2)

print('File Name : %s \t File Size : %d' %(file1, file_size1))
print('File Name : %s \t File Size : %d' %(file2, file_size2))
print()
# -------------------------------------------

print()
# -------------------------------------------




# 파이썬 200제 활용