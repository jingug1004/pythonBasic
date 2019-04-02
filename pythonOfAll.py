import pygame
import sys
from time import sleep

import turtle as t

angle = 80
t.bgcolor("black")
t.color("yellow")
t.speed(0)
for x in range(200):
    t.forward(x)
    t.left(angle)

t.done()