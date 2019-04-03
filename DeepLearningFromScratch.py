# 1장 - 헬로 파이썬

import numpy as np

x = np.array([1.0, 2.0, 3.0])
print(x)
print(type(x))
print()
# -------------------------------------------

x = np.array([1.0, 2.0, 3.0])
y = np.array([2.0, 4.0, 6.0])
print(x + y)
print(x - y)
print(x * y)
print(x / y)
print()
# -------------------------------------------

x = np.array([1.0, 2.0, 3.0])
print(x / 2.0)
print()
# -------------------------------------------

A = np.array([[1, 2], [3, 4]])
print(A)
print(A.shape)
print(A.dtype)
print()
# -------------------------------------------

B = np.array([[3, 0], [0, 6]])
print(A + B)
print(A * B)
print()
# -------------------------------------------

print(A)
print(A * 10)
print()
# -------------------------------------------

A = np.array([[1, 2], [3, 4]])
B = np.array([[10, 20]])
print(A * B)
print()
# -------------------------------------------

x = np.array([[51, 55], [14, 19], [0, 4]])
print(x)
print(x[0])
print(x[0][1])
print()
# -------------------------------------------

for row in x:
    print(row)
print()
# -------------------------------------------

x = x.flatten()
print(x)
print(x[np.array([0, 2, 4])])
print()
# -------------------------------------------

print(x > 15)
print(x[x > 15])
print()
# -------------------------------------------

import matplotlib.pyplot as plt

x = np.arange(0, 6, 0.1)
y = np.sin(x)

plt.plot(x, y)
plt.show()
print()
# -------------------------------------------

x = np.arange(0, 6, 0.1)
y1 = np.sin(x)
y2 = np.cos(x)

plt.plot(x, y1, label="sin")
plt.plot(x, y2, linestyle="--", label="cos")
plt.xlabel("x")
plt.ylabel("y")
plt.title('sin & cos')
plt.legend()
plt.show()
print()
# -------------------------------------------

from matplotlib.image import imread

img = imread('lena.png')

plt.imshow(img)
plt.show()
print()
# -------------------------------------------

print()
# -------------------------------------------

# 1장 - 헬로 파이썬