# 파이썬 200제 활용

"""
def countBirths():
    ret = []
    for y in range(1880, 2015):
        count = 0
        filename = 'names/yob%d.txt' % y
        with open(filename, 'r') as f:
            data = f.readlines()
            for d in data:
                if d[-1] == '\n':
                    d = d[:-1]

                birth = d.split(',')[2]
                count += int(birth)
        ret.append((y, count))
    return ret

result = countBirths()
with open('birth_by_year.csv', 'w') as f:
    for year, birth in result:
        data = '%s,%s\n' % (year, birth)
        print(data)
        f.write(data)
print()
"""
# -------------------------------------------
"""
def countBirthsBySex():
    ret = []
    for y in range(1880, 2015):
        count_f = 0  # 여자아기 출생아수
        count_m = 0  # 남자아기 출생아수
        filename = 'names/yob%d.txt' % y
        with open(filename, 'r') as f:
            data = f.readlines()
            for d in data:
                if d[-1] == '\n':
                    d = d[:-1]

                tmp = d.split(',')
                sex = tmp[1]
                birth = tmp[2]

                if sex == 'F':
                    count_f += int(birth)
                else:
                    count_m += int(birth)
        ret.append((y, count_f, count_m))
    return ret


result = countBirthsBySex()
with open('birth_by_sex.csv', 'w') as f:
    for y, bf, bm in result:
        data = '%s,%s,%s\n' % (y, bf, bm)
        print(data)
        f.write(data)
print()
"""
# -------------------------------------------

"""
from os.path import exists


def getTop10BabyName(year):
    nameF = {}
    nameM = {}

    filename = 'names/yob%s.txt' % year
    if not exists(filename):
        print('[%s] 파일이 존재하지 않습니다.' % filename)
        return None

    with open(filename, 'r') as f:
        data = f.readlines()
        for d in data:
            if d[-1] == '\n':
                d = d[:-1]

            tmp = d.split(',')
            name = tmp[0]
            sex = tmp[1]
            birth = tmp[2]

            if sex == 'F':
                ret = nameF
            else:
                ret = nameM

            if name in ret:
                ret[name] += int(birth)
            else:
                ret[name] = int(birth)

    retF = sorted(nameF.items(), key=lambda x: x[1], reverse=True)
    retM = sorted(nameM.items(), key=lambda x: x[1], reverse=True)

    for i, name in enumerate(retF):
        if i > 9:
            break
        print('TOP_%d 여자아기이름: %s' % (i + 1, name))

    for i, name in enumerate(retM):
        if i > 9:
            break
        print('TOP_%d 남자아기이름: %s' % (i + 1, name))


y = input('인기순 상위10개 이름을 알고 싶은 출생년도를 입력하세요(예:2001): ')
getTop10BabyName(y)
print()
"""

print()

import pygame
import random
from time import sleep

# 게임에 사용되는 전역 변수 정의
BLACK = (0, 0, 0)
RED = (255, 0, 0)
WHITE = (255, 255, 255)
pad_width = 480
pad_height = 640
fight_width = 36
fight_height = 38
enemy_width = 26
enemy_height = 20


def gameover():
    global gamepad
    dispMessage('Game Over')


# 적을 맞춘 개수 계산
def drawScore(count):
    global gamepad

    font = pygame.font.SysFont(None, 20)
    text = font.render('Enemy Kills: ' + str(count), True, WHITE)
    gamepad.blit(text, (0, 0))


def drawPassed(count):
    global gamepad

    font = pygame.font.SysFont(None, 20)
    text = font.render('Enemy Passed: ' + str(count), True, RED)
    gamepad.blit(text, (360, 0))


# 화면에 글씨 보이게 하기
def dispMessage(text):
    global gamepad

    textfont = pygame.font.Font('freesansbold.ttf', 80)
    text = textfont.render(text, True, RED)
    textpos = text.get_rect()
    textpos.center = (pad_width / 2, pad_height / 2)
    gamepad.blit(text, textpos)
    pygame.display.update()
    sleep(2)
    runGame()


def crash():
    global gamepad
    dispMessage('Crashed!')


# 게임에 등장하는 객체를 그려줌
def drawObject(obj, x, y):
    global gamepad
    gamepad.blit(obj, (x, y))


# 게임 실행 메인 함수
def runGame():
    global gamepad, fighter, clock
    global bullet, enemy

    isShot = False
    shotcount = 0
    enemypassed = 0

    x = pad_width * 0.45
    y = pad_height * 0.9
    x_change = 0

    bullet_xy = []
    enemy_x = random.randrange(0, pad_width - enemy_width)
    enemy_y = 0
    enemy_speed = 3

    ongame = False
    while not ongame:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                ongame = True

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    x_change -= 5

                elif event.key == pygame.K_RIGHT:
                    x_change += 5

                elif event.key == pygame.K_LCTRL:
                    if len(bullet_xy) < 2:
                        bullet_x = x + fight_width / 2
                        bullet_y = y - fight_height
                        bullet_xy.append([bullet_x, bullet_y])

            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT:
                    x_change = 0

        gamepad.fill(BLACK)

        x += x_change
        if x < 0:
            x = 0
        elif x > pad_width - fight_width:
            x = pad_width - fight_width

        # 게이머 전투기가 적과 충돌했는지 체크
        if y < enemy_y + enemy_height:
            if (enemy_x > x and enemy_x < x + fight_width) or \
                    (enemy_x + enemy_width > x and enemy_x + enemy_width < x + fight_width):
                crash()

        drawObject(fighter, x, y)

        # 전투기 무기 발사 구현
        if len(bullet_xy) != 0:
            for i, bxy in enumerate(bullet_xy):
                bxy[1] -= 10
                bullet_xy[i][1] = bxy[1]

                if bxy[1] < enemy_y:
                    if bxy[0] > enemy_x and bxy[0] < enemy_x + enemy_width:
                        bullet_xy.remove(bxy)
                        isShot = True
                        shotcount += 1

                if bxy[1] <= 0:
                    try:
                        bullet_xy.remove(bxy)
                    except:
                        pass

        if len(bullet_xy) != 0:
            for bx, by in bullet_xy:
                drawObject(bullet, bx, by)

        drawScore(shotcount)

        # 적 구현
        enemy_y += enemy_speed

        if enemy_y > pad_height:
            enemy_x = random.randrange(0, pad_width - enemy_width)
            enemy_y = 0
            enemypassed += 1

        if enemypassed == 3:
            gameover()

        drawPassed(enemypassed)

        if isShot:
            enemy_speed += 1
            if enemy_speed >= 10:
                enemy_speed = 10

            enemy_x = random.randrange(0, pad_width - enemy_width)
            enemy_y = 0
            isShot = False

        drawObject(enemy, enemy_x, enemy_y)

        pygame.display.update()
        clock.tick(60)

    pygame.quit()


def initGame():
    global gamepad, fighter, clock
    global bullet, enemy

    pygame.init()
    gamepad = pygame.display.set_mode((pad_width, pad_height))
    pygame.display.set_caption('MyGalaga')
    fighter = pygame.image.load('fighter.png')
    enemy = pygame.image.load('enemy.png')
    bullet = pygame.image.load('bullet.png')

    clock = pygame.time.Clock()


initGame()
runGame()

# -------------------------------------------
# 파이썬 200제 활용