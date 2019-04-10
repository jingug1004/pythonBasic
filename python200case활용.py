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
    data.append(text + '/n')
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

print('File Name : %s /t File Size : %d' %(file1, file_size1))
print('File Name : %s /t File Size : %d' %(file2, file_size2))
print()
# -------------------------------------------

"""
from os import remove

target_file = 'stockcode_copy.txt'
k = input('[%s] 파일을 삭제하겠습니까? (y/n)' %target_file)
if k == 'y':
    remove(target_file)
    print('[%s]를 삭제했습니다.' %target_file)
print()
"""
# -------------------------------------------

from os import rename

"""
target_file = 'stockcode_copy.txt'
newname = input('[%s]에 대한 새로운 파일이름을 입력하세요: ' %target_file)
rename(target_file, newname)
print('[%s] -> [%s]로 파일이름이 변경되었습니다. ' %(target_file, newname))
print()
"""
# -------------------------------------------

"""
target_file = 'stockcode_copy.txt'
newpath = input('[%s]를 이동할 디렉터리의 절대경로를 입력하세요: ' %target_file)

if newpath[-1] == '/':
    newname = newpath + target_file
else:
    newname = newpath + '/' + target_file

try:
    rename(target_file, newname)
    print('[%s] -> [%s]로 이동되었습니다.' %(target_file, newname))
except FileNotFoundError as e:
    print(e)
print()
"""
# -------------------------------------------

import os, glob

folder = 'c:/Temp'
file_list = os.listdir(folder)
print(file_list)

files = '*.txt'
file_list = glob.glob(files)
print(file_list)
print()
# -------------------------------------------

pdir = os.getcwd(); print(pdir)
os.chdir('..'); print(os.getcwd())
os.chdir(pdir); print(os.getcwd())
print()
# -------------------------------------------
"""
newfolder = input('새로 생성할 디렉터리 이름을 입력하세요: ')
try:
    os.mkdir(newfolder)
    print('[%s] 디렉터리를 새로 생성했습니다.' %newfolder)
except Exception as e:
    print(e)
print()
# -------------------------------------------

target_folder = 'a1'
k = input('[%s] 디렉터리를 삭제하시겠습니까? (y/n) ' %target_folder)
if k == 'y':
    try:
        os.rmdir(target_folder)
        print('[%s] 디렉터리를 삭제했습니다.' %target_folder)
    except Exception as e:
        print(e)
print()
"""
# -------------------------------------------

import shutil
import os

"""
target_folder = 'C:/Users/A/PycharmProjects/untitled/sdfg'
print('[%s] 하위 모든 디렉터리 및 파일들을 삭제합니다.' %target_folder)
for file in os.listdir(target_folder):
    print(file)
k = input('[%s] 디렉터리를 삭제하시겠습니까? (y/n) ' %target_folder)
if k == 'y':
    try:
        shutil.rmtree(target_folder)
        print('[%s] 디렉터리를 삭제했습니다.' %target_folder)
    except Exception as e:
        print(e)
print()
"""
# -------------------------------------------

"""
from os.path import exists
dir_name = input('새로 생성할 디렉터리 이름을 입력하세요: ')
if not exists(dir_name):
    os.mkdir(dir_name)
    print('[%s] 디렉터리를 생성했습니다.' %dir_name)
else:
    print('[%s]은 이미 존재합니다. ' %dir_name)
print()
"""
# -------------------------------------------

import os
from os.path import exists, isdir, isfile

files = os.listdir()
for file in files:
    if isdir(file):
        print('DIR: %s' %file)

for file in files:
    if isfile(file):
        print('FILE: %s' %file)
print()
# -------------------------------------------

from time import localtime, strftime

logfile = 'test.log'

def writelog(logfile, log):
    time_stamp = strftime('%Y-%m-%d %X\t', localtime())
    log = time_stamp + log + '\n'

    with open(logfile, 'a', encoding='UTF8') as f:
        f.writelines(log)

writelog(logfile, '2번째 로깅 문장입니다.')
print()
# -------------------------------------------

from time import localtime

t = localtime()
start_day = '%d-01-01' % t.tm_year
elapsed_day = t.tm_yday

print('오늘은 [%s]이후 [%d]일째 되는 날입니다.' % (start_day, elapsed_day))
print()
# -------------------------------------------

from time import localtime

weekdays = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일']

t = localtime()
today = '%d-%d-%d' % (t.tm_year, t.tm_mon, t.tm_mday)
week = weekdays[t.tm_wday]

print('[%s] 오늘은 [%s]입니다.' % (today, week))
print()
# -------------------------------------------

from datetime import datetime

start = datetime.now()
print('1에서 백만까지 더합니다.')
ret = 0
for i in range(10):
    ret += i
print('1에서 백만까지 더한 결과: %d' % ret)
end = datetime.now()
elapsed = end - start
print('총 계산 시간: ', end='');
print(elapsed)
elapsed_ms = int(elapsed.total_seconds() * 1000)
print('총 계산 시간: %dms' % elapsed_ms)
print()
# -------------------------------------------

"""
num = input('아무 숫자를 입력하세요: ')

if num.isdigit():
    num = num[::-1]
    ret = ''
    for i, c in enumerate(num):
        i += 1
        if i != len(num) and i % 3 == 0:
            ret += (c + ',')
        else:
            ret += c
    ret = ret[::-1]
    print(ret)
else:
    print('입력한 내용 [%s]: 숫자가 아닙니다.' % num)
print()
"""
# -------------------------------------------

"""
text = input('문장을 입력하세요: ')

ret = ''
for i in range(len(text)):
    if i != len(text) - 1:
        ret += text[i + 1]
    else:
        ret += text[0]
print(ret)
print()
"""
# -------------------------------------------

url = 'http://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=105&oid=028&aid=0002334601'

tmp = url.split('/')
domain = tmp[2]
print(domain)
print()
# -------------------------------------------

url = 'http://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=105&oid=028&aid=0002334601'

tmp = url.split('?')
queries = tmp[1].split('&')
for query in queries:
    print(query)
print()
# -------------------------------------------

mystack = []


def putdata(data):
    global mystack
    mystack.append(data)


def popdata():
    global mystack
    if len(mystack) == 0:
        return None
    return mystack.pop()


putdata('데이터1')
putdata([3, 4, 5, 6])
putdata(12345)

print('<스택상태>: ', end='');
print(mystack)

ret = popdata()
while ret != None:
    print('스택에서 데이터 추출:', end='');
    print(ret)
    print('<스택상태>: ', end='');
    print(mystack)
    ret = popdata()
print()
# -------------------------------------------

def getTextFreq(filename):
    with open(filename, 'r') as f:
        text = f.read()
        fa = {}
        for c in text:
            if c in fa:
                fa[c] += 1
            else:
                fa[c] = 1
    return fa

ret = getTextFreq('mydata.txt')
ret = sorted(ret.items(), key=lambda x: x[1], reverse=True)
for c, freq in ret:
    if c == '\n':
        continue
    print('[%c] -> [%d]회 나타남' % (c, freq))
print()
# -------------------------------------------

with open('mydata.txt', 'r') as f:
    data = f.read()
    tmp = data.split()
    print('단어수: [%d]' % len(tmp))
print()
# -------------------------------------------

def countWord(filename, word):
    with open(filename, 'r') as f:
        text = f.read()
        text = text.lower()
        pos = text.find(word)
        count = 0
        while pos != -1:
            count += 1
            pos = text.find(word, pos + 1)
    return count

word = input('mydata.txt에서 개수를 구할 단어를 입력하세요: ')
word = word.lower()
ret = countWord('mydata.txt', word)
print('[%s]의 개수: %d' % (word, ret))
print()
# -------------------------------------------



print()
# -------------------------------------------




print()
# -------------------------------------------
# 파이썬 200제 활용