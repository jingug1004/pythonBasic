# k = input('<값>을 입력하세요. ')
# print('당신이 입력한 값은 <' + k + '> 입니다. ')
print('------------------------------------------------------------')
# ------------------------------------------------------------

numdata = 57
strdata = '파이썬'
listdata = [1, 2, 3]
dictdata = {'a':1, 'b':2}

def func():
    print('안녕하세요.01')

print(type(numdata))
print(type(strdata))
print(type(listdata))
print(type(dictdata))
print(type(func))
print('------------------------------------------------------------')
# ------------------------------------------------------------

a = 11113
b = 23
ret = a % b
print('<%d>를 <%d>로 나누면 <%d>가 나머지로 남습니다.' %(a, b, ret))
print('------------------------------------------------------------')
# ------------------------------------------------------------

a = 11113
b = 23
ret1, ret2 = divmod(a, b)
print('<%d / %d>로 나누면 몫이 <%d>, 나머지가 <%d> 입니다.' %(a, b, ret1, ret2))
print('------------------------------------------------------------')
# ------------------------------------------------------------

h1 = hex(97)
h2 = hex(98)
ret1 = h1 + h2
print(ret1)
a = int(h1, 16)
b = int(h2, 16)
ret2 = a + b
print(ret2)
print(hex(ret2))
print('------------------------------------------------------------')
# ------------------------------------------------------------

b1 = bin(97)
b2 = bin(98)
ret1 = b1 + b2
print(ret1)
a = int(b1, 2)
b = int(b2, 2)
ret2 = a + b
print(ret2)
print(bin(ret2))
print('------------------------------------------------------------')
# ------------------------------------------------------------

bnum = 0b11110000; bstr = '0b11110000'
onum = 0o360; ostr = '0o360'
hnum = 0xf0; hstr = '0xf0'
b1 = int(bnum); b2 = int(bstr, 2)
o1 = int(onum); o2 = int(ostr, 8)
h1 = int(hnum); h2 = int(hstr, 16)
print(b1); print(b2)
print(o1); print(o2)
print(h1); print(h2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

abs1 = abs(-3)
abs2 = abs(-5.72)
abs3 = abs(3+4j)
print(abs1)
print(abs2)
print(abs3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

ret1 = round(1118)
ret2 = round(16.554)
ret3 = round(1118, -1)
ret4 = round(16.554, 2)
print(ret1)
print(ret2)
print(ret3)
print(ret4)
print('------------------------------------------------------------')
# ------------------------------------------------------------

idata1 = int(-5.4)
idata2 = int(1.78e1)
idata3 = int(171.56)
print(idata1)
print(idata2)
print(idata3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

fdata = float(10)
print(fdata)
print('------------------------------------------------------------')
# ------------------------------------------------------------

def getPrime(x):
    for i in range(2, x - 1):
        if x % i == 0:
            break

    else:
        return x


listdata = [117, 119, 1113, 11113, 11119]
ret = filter(getPrime, listdata)
print(list(ret))
print('------------------------------------------------------------')
# ------------------------------------------------------------

def getPrimeUnder(n):
    ret = [2, 3]
    if n < 3:
        return ret

    for i in range(4, n + 1):
        for k in range(2, i - 1):
            a = i % k
            if a == 0:
                isPrime = False
                break

    else:
        ret.append(i)

    return ret

print(getPrimeUnder(11))
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [9.96, 1.27, 5.07, 6.45, 8.38, 9.29, 4.93, 7.73, 3.71, 0.93]
maxval = max(listdata)
minval = min(listdata)
print(maxval)
print(minval)

txt = 'Alotofthingsoccureachday'
maxval = max(txt)
minval = min(txt)
print(maxval)
print(minval)

maxval = max(2+3, 2*3, 2**3, 3**2)
minval = min('abz', 'a12')
print(maxval)
print(minval)
print('------------------------------------------------------------')
# ------------------------------------------------------------

a = 107
b = a & 0x0f
print(b)
print('------------------------------------------------------------')
# ------------------------------------------------------------

a = 107
b = (a>>4) & 0x0f
print(b)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt1 = 'A tale that was not right'
txt2 = '이 또한 지나가리라.'
print(txt1[5])
print(txt2[-2])
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt1 = 'A tale that was not right'
txt2 = '이 또한 지나가리라.'
print(txt1[3:7])
print(txt1[:6])
print(txt2[-4:])
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'python'
for i in range(len(txt)):
    print(txt[:i + 1])
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'aAbBcCdDeE'
ret = txt[::2]
ret1 = txt[1::2]
ret2 = txt[::3]
print(ret)
print(ret1)
print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'aAbBcCdDeE'
ret = txt[::-1]
ret1 = txt[::-2]
ret11 = txt[-1::-2]
ret2 = txt[-2::-2]
print(ret)
print(ret1)
print(ret11)
print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

"""
filename = input('저장할 파일이름을 입력하세요: ')
filename = filename + '.jpg'
display_msg = '당신이 저장한 파일은 <' + filename + '> 입니다.'
print(display_msg)
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

msg1 = '여러분'
msg2 = '파이팅'
display_msg = msg1 + ', ' + msg2 * 3 + '~!'
print(display_msg)
print('------------------------------------------------------------')
# ------------------------------------------------------------

"""
msg = input('임의의 문장을 입력하세요 : ')
if 'a' in msg:
    print('당신이 입력한 문장에 a가 있습니다.')
else:
    print('당신이 입력한 문장에 a가 없습니다.')
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

"""
msg = input('임의의 문장을 입력하세요 : ')
if 'is' in msg:
    print('당신이 입력한 문장에 is가 있습니다.')
else:
    print('당신이 입력한 문장에 is가 없습니다.')
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

"""
msg = input('임의의 문장을 입력하세요.')
msglen = len(msg)
print('당신이 입력한 문장의 길이는 <%d> 입니다.' %msglen)
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

"""
msg = input('임의의 문장을 입력하세요.')
# msglen = len(msg)
msglen = len(msg.encode())
print('당신이 입력한 문장의 길이는 <%d> 입니다.' %msglen)
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

txt1 = 'A'
txt2 = '안녕'
txt3 = 'Warcraft Three'
txt4 = '3PO'
ret1 = txt1.isalpha()
ret2 = txt2.isalpha()
ret3 = txt3.isalpha()
ret4 = txt4.isalpha()
print(ret1)
print(ret2)
print(ret3)
print(ret4)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt1 = '010-1234-5678'
txt2 = 'R2D2'
txt3 = '1234'
ret1 = txt1.isdigit()
ret2 = txt2.isdigit()
ret3 = txt3.isdigit()
print(ret1)
print(ret2)
print(ret3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt1 = '안녕하세요?'
txt2 = '1. Title-제목을 넣으세요'
txt3 = '3피오R2D2'
ret1 = txt1.isalnum()
ret2 = txt2.isalnum()
ret3 = txt3.isalnum()
print(ret1)
print(ret2)
print(ret3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'A lot of Things occur each day.'
ret1 = txt.upper()
ret2 = txt.lower()
print(ret1)
print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = '  양쪽에 공백이 있는 문자열입니다.  '
ret1 = txt.lstrip()
ret2 = txt.rstrip()
ret3 = txt.strip()
print('<' + txt + '>')
print('<' + ret1 + '>')
print('<' + ret2 + '>')
print('<' + ret3 + '>')
print('------------------------------------------------------------')
# ------------------------------------------------------------

"""
numstr = input('숫자를 입력하세요: ')
try:
    num = int(numstr)
    print('당신이 입력한 숫자는 정수 <%d> 입니다.' %num)
except:
    try:
        num = float(numstr)
        print('당신이 입력한 숫자는 실수<%f>입니다.' %num)
    except:
        print('+++ 숫자를 입력하세요~ +++')
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

num1 = 1234
num2 = 3.14

numstr1 = str(num1)
numstr2 = str(num2)
print('num1을 문자열로 변환한 값은 "%s"입니다.' %numstr1)
print('num2을 문자열로 변환한 값은 "%s"입니다.' %numstr2)

print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'A lot of things occur each day. every day.'
word_count1 = txt.count('o')
word_count2 = txt.count('day')
word_count3 = txt.count(' ')
word_len = len(txt)
print(word_count1)
print(word_count2)
print(word_count3)
print(word_len)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'A lot of things occur each day. every day.'
offset1 = txt.find('e')
offset2 = txt.find('day')
offset3 = txt.find('day', 30)
print(offset1)
print(offset2)
print(offset3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

url = 'http://www.naver.com/news/today=20160831'
log = 'name:홍길동 age:17 sex:남자 nation:조선'

ret1 = url.split('/')
print(ret1)

ret2 = log.split()
print(ret2)
for data in ret2:
    d1, d2 = data.split(':')
    print('%s -> %s' %(d1, d2))
print('------------------------------------------------------------')
# ------------------------------------------------------------

loglist = ['2016/08/26 10:12:11', '200', 'OK', '이 또한 지나가리라']
bond = ';'
log = bond.join(loglist)
print(log)
print('------------------------------------------------------------')
# ------------------------------------------------------------

txt = 'My password is 1234'
ret1 = txt.replace('1', '0')
ret2 = txt.replace('1', 'python')
print(ret1)
print(ret2)

txt = '매일 많은 일들이 일어납니다.'
ret3 = txt.replace('매일', '항상')
ret4 = txt.replace('일', '사건')
print(ret3)
print(ret4)
print('------------------------------------------------------------')
# ------------------------------------------------------------

u_txt = 'I love python'
b_txt = u_txt.encode()
print(u_txt)
print(b_txt)

ret1 = 'I' == u_txt[0]
ret2 = 'I' == b_txt[0]
print(ret1)
print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

b_txt = b'A lot of things occur each day. every day.'
u_txt = b_txt.decode()
print(u_txt)
print('------------------------------------------------------------')
# ------------------------------------------------------------

"""
strdata = input('정렬할 문자열을 입력하세요: ')
ret1 = sorted(strdata)
ret2 = sorted(strdata, reverse=True)
print(ret1)
print(ret2)

ret1 = ''.join(ret1)
ret2 = ''.join(ret2)
print('오름차순으로 정렬된 문자열은 <' + ret1 + '> 입니다.')
print('내림차순으로 정렬된 문자열은 <' + ret2 + '> 입니다.')
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

range1 = range(10)
range2 = range(10, 20)
print(list(range1))
print(list(range2))
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [1, 2, 'a', 'b', 'c', [4, 5, 6]]
val1 = listdata[1]
val2 = listdata[3]
val3 = listdata[5][1]
print(val1)
print(val2)
print(val3)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성', '지구']
planet = '지구'
pos = solarsys.index(planet)
print('%s는 태양계에서 %d번쩨에 위치하고 있습니다.' %(planet, pos))
pos = solarsys.index(planet, 5)
print('%s는 태양계에서 %d번째에 위치하고 있습니다.' %(planet, pos))
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
planet = '화성'
pos = solarsys.index(planet)
solarsys[pos] = 'Mars'
print(solarsys)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
rock_planets = solarsys[1:4]
gas_planets = solarsys[4:]
print('태양계의 암석형 행성 : ', end=''); print(rock_planets)
print('태양계의 가스형 행성 : ', end=''); print(gas_planets)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = list(range(1, 21))
evenlist = listdata[1::2]
oddlist = listdata[::2]
print(evenlist)
print(oddlist)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = list(range(5))
listdata.reverse()
print(listdata)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = list(range(5))
ret1 = reversed(listdata)
print('원본 리스트', end=''); print(listdata)
print('역순 리스트', end=''); print(list(ret1))
print('역순 리스트', end=''); print(ret1)

ret2 = listdata[::-1]
print('슬라이싱 이용', end=''); print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata1 = ['a', 'b', 'c', 'd', 'e']
listdata2 = ['f', 'g', 'h', 'i', 'j']
listdata3 = listdata1 + listdata2
listdata4 = listdata2 + listdata1
print(listdata3)
print(listdata4)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = list(range(3))
ret = listdata * 3
print(ret)
print('------------------------------------------------------------')
# ------------------------------------------------------------

"""
listdata = []
for i in range(3):
    txt = input('리스트에 추가할 값을 입력하세요[%d/3]: ' %(i + 1))
    listdata.append(txt)
    print(listdata)
print('------------------------------------------------------------')
"""
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
pos = solarsys.index('목성')
solarsys.insert(pos, '소행성')
print(solarsys)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
del solarsys[0]
print(solarsys)
del solarsys[-2]
print(solarsys)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
solarsys.remove('태양')
print(solarsys)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
del solarsys[1:3]
print(solarsys)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [2, 2, 1, 3, 8, 5, 7, 6, 3, 6, 2, 3, 9, 4, 4]
listsize = len(listdata)
print(listsize)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [2, 2, 1, 3, 8, 5, 7, 6, 3, 6, 2, 3, 9, 4, 4]
c1 = listdata.count(2)
c2 = listdata.count(10)
print(c1)
print(c2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [2, 2, 1, 3, 8, 5, 7, 6, 3, 6, 2, 3, 9, 4, 4]
del listdata
# print(listdata)
print('------------------------------------------------------------')
# ------------------------------------------------------------

namelist = ['Mary', 'Sams', 'Aimy', 'Tom', 'Michale', 'Bob', 'Kelly']
namelist.sort()
print(namelist)
namelist.sort(reverse=True)
print(namelist)
print('------------------------------------------------------------')
# ------------------------------------------------------------

namelist = ['Mary', 'Sams', 'Aimy', 'Tom', 'Michale', 'Bob', 'Kelly']
ret1 = sorted(namelist)
ret2 = sorted(namelist, reverse=True)
print(namelist)
print(ret1)
print(ret2)
print('------------------------------------------------------------')
# ------------------------------------------------------------

from random import shuffle

listdata = list(range(1, 11))
for i in range(3):
    shuffle(listdata)
    print(listdata)
print('------------------------------------------------------------')
# ------------------------------------------------------------

solarsys = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
ret = list(enumerate(solarsys))
print(ret)

for i, body1 in enumerate(solarsys):
    print('태양계의 %d번째 천체: %s ' %(i + 1, body1))
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata = [2, 2, 1, 3, 8, 5, 7, 6, 3, 6, 2, 3, 9, 4, 4]
ret = sum(listdata)
print(ret)
print('------------------------------------------------------------')
# ------------------------------------------------------------

listdata1 = [0, 1, 2, 3, 4]
listdata2 = [True, True, True]
listdata3 = ['', [], (), {}, None, False]
print(all(listdata1))
print(any(listdata1))
print(all(listdata2))
print(any(listdata2))
print(all(listdata3))
print(any(listdata3))
print('------------------------------------------------------------')
# ------------------------------------------------------------

solar1 = ['태양', '수성', '금성', '지구', '화성', '목성', '토성', '천왕성', '해왕성']
solar2 = ['Sun', 'Mercury', 'Venus', 'Earth', 'Mars', 'Jupiter', 'Saturn', 'Uranus', 'Neptune']
solardict = {}
for i, k in enumerate(solar1):
    val = solar2[i]
    solardict[k] = val

print(solardict)
print('------------------------------------------------------------')
# ------------------------------------------------------------

names = {'Mary': 10999, 'Sams': 2111, 'Aimy': 9778, 'Tom': 20245, 'Michale': 27115, 'Bob': 5887, 'Kelly': 7855}
names['Aimy'] = 10000
print(names)
print('------------------------------------------------------------')
# ------------------------------------------------------------

names = {'Mary': 10999, 'Sams': 2111, 'Aimy': 9778, 'Tom': 20245, 'Michale': 27115, 'Bob': 5887, 'Kelly': 7855}
del names['Sams']
print(names)
print('------------------------------------------------------------')
# ------------------------------------------------------------

names = {'Mary': 10999, 'Sams': 2111, 'Aimy': 9778, 'Tom': 20245, 'Michale': 27115, 'Bob': 5887, 'Kelly': 7855}
names.clear()
print(names)
print('------------------------------------------------------------')
# ------------------------------------------------------------

names = {'Mary': 10999, 'Sams': 2111, 'Aimy': 9778, 'Tom': 20245, 'Michale': 27115, 'Bob': 5887, 'Kelly': 7855}
ks = names.keys()
print(ks)

for k in ks:
    print('Key: %s \tValue: %d' %(k, names[k]))
print('------------------------------------------------------------')
# ------------------------------------------------------------

names = {'Mary': 10999, 'Sams': 2111, 'Aimy': 9778, 'Tom': 20245, 'Michale': 27115, 'Bob': 5887, 'Kelly': 7855}
vals = names.values()
print(vals)

vals_list = list(vals)
print(vals_list)
ret = sum(vals_list)
print('출생아 수 총계 : %d' %ret)
print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------




print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------




print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------




print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------




print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------




print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



print('------------------------------------------------------------')
# ------------------------------------------------------------



















