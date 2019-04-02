import pygame
import sys
from time import sleep

import turtle as t

# BLACK = (0, 0, 0)
padWidth = 480
padHeight = 640

def drawObject(obj, x, y):
    global gamePad
    gamePad.blit(obj, (x, y))

def initGame():
    global gamePad, clock, background
    pygame.init()
    gamePad = pygame.display.set_mode((padWidth, padHeight))
    pygame.display.set_caption('PyShooting')
    background = pygame.image.load('background.png')
    clock = pygame.time.Clock()

def runGame():
    global gamePad, clock, background

    onGame = False
    while not onGame:
        for event in pygame.event.get():
            if event.type in [pygame.QUIT]:
                pygame.quit()
                sys.exit()

        # gamePad.fill(BLACK)
        drawObject(background, 0, 0)

        pygame.display.update()

        clock.tick(60)

    pygame.quit()

initGame()

runGame()

t.done()