import pygame
import configparser
import random
from sprites import (Player, Bullet, HealthBar, Tree, Brick)
# Import pygame.locals for easier access to key coordinates
from pygame.locals import (
    RLEACCEL,
    K_i,
    K_k,
    K_l,
    K_j,
    K_ESCAPE,
    KEYDOWN,
    QUIT,
    K_w,
    K_a,
    K_s,
    K_d,
    K_SPACE,
    K_KP_ENTER
)

#Config reader
read_config = configparser.ConfigParser()
read_config.read("setup.ini")
pygame.display.set_caption("CS150 MP2 Antonio Cavan")
#Constants
SCREEN_WIDTH = 800
SCREEN_HEIGHT = 600
POWERUP_GENERATION_PERIOD = 	read_config.get("Default", "POWERUP_GENERATION_PERIOD")
POWERUP_MAP_DURATION = 			read_config.get("Default", "POWERUP_MAP_DURATION")
CAMOFLAGE_DURATION = 			read_config.get("Default", "CAMOFLAGE_DURATION")
STOPWATCH_DURATION = 			read_config.get("Default", "STOPWATCH_DURATION")
DOUBLE_DAMAGE_DURATION = 		read_config.get("Default", "DOUBLE_DAMAGE_DURATION")
PLAYER_HEALTH = 				read_config.get("Default", "PLAYER_HEALTH")
BRICK_HEALTH = 					read_config.get("Default", "BRICK_HEALTH")
BULLET_DAMAGE = 				read_config.get("Default", "BULLET_DAMAGE")
INIT_XPOS_P1 = 250
INIT_XPOS_P2 = 510
INIT_YPOS_P1 = 290
INIT_YPOS_P2 = 290
NO_OF_BRICKS_TREES = 100
#List of occupied cells, ensures no overlap of sprites
COORDINATE_TRACKER = [(INIT_XPOS_P1, INIT_YPOS_P1), (INIT_XPOS_P2, INIT_YPOS_P2)]
#Acceptable Values: A, B, C, D
P1_TANKTYPE = 'B'
P2_TANKTYPE = 'B'

def main():
	#Initialize pygame
	pygame.init()
	 
	#Clock
	clock = pygame.time.Clock()
	delta_time = 0
	p1_timer = 0.1
	p2_timer = 0.1

	#Set Window size
	SCREEN = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
	
	ADDBULLET = pygame.USEREVENT + 1

	#Initalize Sprites
	#	players, player healthbars, player bullets, bricks and trees.
	#	No. of bricks = No. of trees (only one loop)
	player1 = Player(PLAYER_HEALTH, P1_TANKTYPE, 1)
	player2 = Player(PLAYER_HEALTH, P2_TANKTYPE, 2)
	player1_healthbar = HealthBar(player1.return_health(), 1, SCREEN)
	player2_healthbar = HealthBar(player2.return_health(), 2, SCREEN)
	player1_bullets = pygame.sprite.Group()
	player2_bullets = pygame.sprite.Group()
	bricks = pygame.sprite.Group()
	trees = pygame.sprite.Group()
	brick_rects = []
	all_sprites = pygame.sprite.Group()
	all_sprites.add(player1)
	all_sprites.add(player2)
	all_sprites.add(player1_healthbar)
	all_sprites.add(player2_healthbar)
	for i in range(0,NO_OF_BRICKS_TREES):
		tree_coord = randomizer()
		if tree_coord not in COORDINATE_TRACKER:
			COORDINATE_TRACKER.append(tree_coord)
			new_tree = Tree(tree_coord)
			trees.add(new_tree)
			all_sprites.add(new_tree)
		brick_coord = randomizer()
		if brick_coord not in COORDINATE_TRACKER:
			COORDINATE_TRACKER.append(brick_coord)
			new_brick = Brick(brick_coord, BRICK_HEALTH)
			bricks.add(new_brick)
			brick_rects.append(new_brick.rect)
			all_sprites.add(new_brick)
	#LOG: Number of generated bricks and trees
	print("Bricks: ", len(bricks), " Trees: ", len(trees))
	running = True
	while running:
		# Victory checker
		running = victory_checker(player1, player2)
		# Event checker
		for event in pygame.event.get():
			if event.type == KEYDOWN:
				if event.key == K_ESCAPE:
					running = False
				if event.key == K_SPACE: 
					if len(player1_bullets.sprites()) < 3:
						new_bullet_1 = Bullet(player1.tank, player1.facing, player1.rect.center)
						player1_bullets.add(new_bullet_1)
						all_sprites.add(player1_bullets)
				if event.key == K_KP_ENTER:
					if len(player2_bullets.sprites()) < 3:
						new_bullet_2 = Bullet(player2.tank, player2.facing, player2.rect.center)
						player2_bullets.add(new_bullet_2)
						all_sprites.add(player2_bullets)

			elif event.type == QUIT:
				running = False
		 

		pressed_keys = pygame.key.get_pressed()
		# Sprite pressed_key response time (in sec)
		#	change p1 or p2 timers for faster or slower response to keys
		p1_timer -= delta_time
		if p1_timer <= 0:
			player1.update(pressed_keys)
			if P1_TANKTYPE == 'B' or P1_TANKTYPE == 'A':
				p1_timer = 0.05
			else:
				p1_timer = 0.1
		p2_timer -= delta_time
		if p2_timer <= 0:
			player2.update(pressed_keys)
			if P2_TANKTYPE == 'B' or P2_TANKTYPE == 'A':
				p2_timer = 0.05
			else:
				p2_timer = 0.1
		
		# Update sprites
		player1_bullets.update()
		player2_bullets.update()
		player1_healthbar.update(player1.return_health())
		player2_healthbar.update(player2.return_health())

		SCREEN.fill((0,0,0))
		delta_time = clock.tick(60) / 1000

		#Block Transfer all necessary surfaces and rectangles
		for entity in all_sprites:
			if entity == player1_healthbar or entity == player2_healthbar:
				SCREEN.blit(entity.surf, entity.rect)
				SCREEN.blit(entity.title, entity.textrect)
			elif entity == player1 or entity == player2:
				SCREEN.blit(entity.surf, entity.rect)
				SCREEN.blit(entity.north_sensor, entity.north_sensor_rect)
				SCREEN.blit(entity.south_sensor, entity.south_sensor_rect)
				SCREEN.blit(entity.east_sensor, entity.east_sensor_rect)
				SCREEN.blit(entity.west_sensor, entity.west_sensor_rect)
			else:
				SCREEN.blit(entity.surf, entity.rect)
		
		
		# Collision Detection
		#	of p1 and p2 bullets and p2 and p1 bullets
		#	of brick rectangles and sensors of player 1 and 2 (for brick detection)
		brick_rects = update_brick_rects(bricks)
		collision_detection(player1, player2, player1_bullets, player2_bullets, bricks)
		brick_sensor_p1(player1, brick_rects)
		brick_sensor_p2(player2, brick_rects)
		# draw_grid(SCREEN)
		pygame.display.flip()


	pygame.quit()
# draw_grid method shows a grid in the screen
# uncomment on game loop to show
def draw_grid(SCREEN):
    blockSize = 20 #Set the size of the grid block
    for x in range(0, SCREEN_WIDTH, blockSize):
        for y in range(0, SCREEN_HEIGHT, blockSize):
            rect = pygame.Rect(x, y, blockSize, blockSize)
            pygame.draw.rect(SCREEN, (255, 255, 255), rect, 1)

# collision_detection method detects any collision between
#	-p1 and p2 bullets
# 	-p2 and p1 bullets
#	-p1 bullets and bricks
#	-p2 bullets and bricks
#	after detection, update sprite health and/or existence
def collision_detection(player1, player2, player1_bullets, player2_bullets, bricks):
	if pygame.sprite.spritecollideany(player1, player2_bullets):
		bullet_hit_list = pygame.sprite.spritecollide(player1, player2_bullets, True)
		for bullet in bullet_hit_list:
			bullet.kill()
			player1.tank.got_hit(BULLET_DAMAGE)
			

	if pygame.sprite.spritecollideany(player2, player1_bullets):
		bullet_hit_list = pygame.sprite.spritecollide(player2, player1_bullets, True)
		for bullet in bullet_hit_list:
			bullet.kill()
			player2.tank.got_hit(BULLET_DAMAGE)

	for bullet in player1_bullets:
		if pygame.sprite.spritecollideany(bullet, bricks):
			brick_hit_list = pygame.sprite.spritecollide(bullet, bricks, False)
			bullet.kill()
			for brick in brick_hit_list:
				brick.update(BULLET_DAMAGE)
				if brick.is_dead == True:
					brick.kill()

	for bullet in player2_bullets:
		if pygame.sprite.spritecollideany(bullet, bricks):
			brick_hit_list = pygame.sprite.spritecollide(bullet, bricks, False)
			bullet.kill()
			for brick in brick_hit_list:
				brick.update(BULLET_DAMAGE)
				if brick.is_dead == True:
					brick.kill()

# brick_sensor_p1 method detects any collision between
#	-p1 north, south, east, west sensors and bricks
# 	This method prevents player 1 to pass through bricks
def brick_sensor_p1(player1, bricks):
	# for brick in bricks:
	if player1.north_sensor_rect.collidelist(bricks) > 0:
		player1.set_collision(True, 'n')
	else: player1.set_collision(False, 'n')

	if player1.south_sensor_rect.collidelist(bricks) > 0:
		player1.set_collision(True, 's')
	else: player1.set_collision(False, 's')

	if player1.east_sensor_rect.collidelist(bricks) > 0:
		player1.set_collision(True, 'e')
	else: player1.set_collision(False, 'e')

	if player1.west_sensor_rect.collidelist(bricks) > 0:
		player1.set_collision(True, 'w')
	else: player1.set_collision(False, 'w')

# brick_sensor_p2 method detects any collision between
#	-p2 north, south, east, west sensors and bricks
# 	This method prevents the player 2 to pass through bricks
def brick_sensor_p2(player2, bricks):
	# for brick in bricks:
	if player2.north_sensor_rect.collidelist(bricks) > 0:
		player2.set_collision(True, 'n')
	else: player2.set_collision(False, 'n')

	if player2.south_sensor_rect.collidelist(bricks) > 0:
		player2.set_collision(True, 's')
	else: player2.set_collision(False, 's')

	if player2.east_sensor_rect.collidelist(bricks) > 0:
		player2.set_collision(True, 'e')
	else: player2.set_collision(False, 'e')

	if player2.west_sensor_rect.collidelist(bricks) > 0:
		player2.set_collision(True, 'w')
	else: player2.set_collision(False, 'w')

# victory_checker method checks if the game must end. 
# The game will end if:
#	- player1 and player 2 collides
#	- player1 or player 2 health falls below 0
def victory_checker(player1, player2):
	if pygame.sprite.collide_rect(player1, player2):
		if player1.return_health() < player2.return_health():
			player1.kill()
			print("Game Over! Player 2 wins")
			return False
		elif player2.return_health() < player1.return_health():
			player2.kill()
			print("Game Over! Player 1 Wins")
			return False
		else:
			print("Game Over! It's a draw")
			return False
	if int(player1.return_health()) <= 0 and int(player2.return_health()) <= 0:
		print("Game Over! It's a draw.")
		return False
	elif int(player1.return_health()) <= 0:
		print("Game Over! Player 2 Wins.")
		return False
	elif int(player2.return_health()) <= 0:
		print("Game Over! Player 1 Wins.")
		return False
	else:
		return True

# randomizer method generates random coordinates withing screen size
# This method is used when generating trees and bricks
def randomizer():
	random_x = random.randint(0, 40)*20 - 10
	random_y = random.randint(5, 30)*20 - 10
	return (random_x, random_y)

# update_brick_rects method updates the list of brick rectangles alive in the screen
# This method is used to make sure that the sensors don't detect phantom bricks 
# This is called in the game loop, before collision detection
def update_brick_rects(bricks):
	brick_rects = []
	for brick in bricks:
		brick_rects.append(brick.rect)
	return brick_rects

#Get Tank Type player 1
#Get Tank Type player 2
#Run Game
main()