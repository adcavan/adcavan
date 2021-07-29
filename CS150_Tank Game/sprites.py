import pygame
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

SCREEN_WIDTH = 800
SCREEN_HEIGHT = 600
BLOCK_SIZE = 20
INIT_XPOS_P1 = 250
INIT_XPOS_P2 = 510
INIT_YPOS_P1 = 290
INIT_YPOS_P2 = 290
WHITE = (255,255,255)
class Player(pygame.sprite.Sprite):
	def __init__(self, health, tank_type, player_number):
		super(Player, self).__init__()
		global INIT_XPOS_P1, INIT_XPOS_P2, INIT_YPOS_P1, INIT_YPOS_P2 
		self.health = health
		self.player_number = player_number
		self.tank = Tank(health, tank_type, player_number)
		
		self.surf = self.tank.set_tank_image()
		self.tank_size = self.tank.size
		self.surf.set_colorkey((255, 255, 255), RLEACCEL)
		surfsize = self.surf.get_size()
		self._surf = self.surf
		if player_number == 1:
			if tank_type == 'A':
				INIT_XPOS_P1 = INIT_XPOS_P1 + 5
				INIT_YPOS_P1 = INIT_YPOS_P1 + 5
			self.rect = self.surf.get_rect(center=(INIT_XPOS_P1, INIT_YPOS_P1))
			self.facing = 0
		elif player_number == 2:
			if tank_type == 'A':
				INIT_XPOS_P2 = INIT_XPOS_P2 + 5
				INIT_YPOS_P2 = INIT_YPOS_P2 + 5
			self.rect = self.surf.get_rect(center=(INIT_XPOS_P2, INIT_YPOS_P2))
			self.rotate(180)
			self.facing = 180	
		self.create_sensors(self.player_number)

		self.has_north_collision = False
		self.has_south_collision = False
		self.has_east_collision = False
		self.has_west_collision = False
		self.elapsed = 500
	
	def rotate(self, angle):
		center = self.rect.center
		self.surf = pygame.transform.rotate(self._surf, angle)
		self.rect = self.surf.get_rect(center = center)

	def update(self, pressed_keys):
		
		if self.player_number == 1:
			if pressed_keys[K_w]:
				if not (self.has_north_collision):	
					self.move_sensors(K_w)
					self.rect.move_ip(0, -self.tank_size)
				self.rotate(90)
				self.facing = 90
			elif pressed_keys[K_s]:
				if not self.has_south_collision:
					self.move_sensors(K_s)
					self.rect.move_ip(0, self.tank_size)
				self.rotate(270)
				self.facing = 270
			elif pressed_keys[K_a]:
				if not self.has_west_collision:
					self.move_sensors(K_a)
					self.rect.move_ip(-self.tank_size, 0)
				self.rotate(180)
				self.facing = 180
			elif pressed_keys[K_d]:
				if not self.has_east_collision:
					self.move_sensors(K_d)
					self.rect.move_ip(self.tank_size, 0)
				self.rotate(0)
				self.facing = 0
		
		elif self.player_number == 2:
			if pressed_keys[K_i]:
				if not (self.has_north_collision):		
					self.move_sensors(K_i)
					self.rect.move_ip(0, -self.tank_size)
				self.rotate(90)
				self.facing = 90
			elif pressed_keys[K_k]:
				if not (self.has_south_collision):		
					self.move_sensors(K_k)
					self.rect.move_ip(0, self.tank_size)
				self.rotate(270)
				self.facing = 270
			elif pressed_keys[K_j]:
				if not (self.has_west_collision):	
					self.move_sensors(K_j)
					self.rect.move_ip(-self.tank_size, 0)
				self.rotate(180)
				self.facing = 180
			elif pressed_keys[K_l]:
				if not (self.has_east_collision):	
					self.move_sensors(K_l)
					self.rect.move_ip(self.tank_size, 0)
				self.rotate(0)
				self.facing = 0
		
		# Keep player on the screen
		if self.rect.left < 0:
		    self.rect.left = 0
		if self.rect.right >= SCREEN_WIDTH:
		    self.rect.right = SCREEN_WIDTH
		if self.rect.top < 0:
		    self.rect.top = 0
		if self.rect.bottom >= SCREEN_HEIGHT:
		    self.rect.bottom = SCREEN_HEIGHT

	def create_sensors(self, player_number):
		self.north_sensor = pygame.Surface((self.tank_size, self.tank_size))
		self.south_sensor = pygame.Surface((self.tank_size, self.tank_size))
		self.east_sensor = pygame.Surface((self.tank_size, self.tank_size))
		self.west_sensor = pygame.Surface((self.tank_size, self.tank_size))
		self.north_sensor.fill(WHITE)
		self.south_sensor.fill(WHITE)
		self.east_sensor.fill(WHITE)
		self.west_sensor.fill(WHITE)

		self.north_sensor.set_colorkey((255, 255, 255), RLEACCEL)
		self.south_sensor.set_colorkey((255, 255, 255), RLEACCEL)
		self.east_sensor.set_colorkey((255, 255, 255), RLEACCEL)
		self.west_sensor.set_colorkey((255, 255, 255), RLEACCEL)
		
		if player_number == 1:
			self.north_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P1, INIT_YPOS_P1 - self.tank_size))
			self.south_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P1, INIT_YPOS_P1 + self.tank_size))
			self.east_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P1 + self.tank_size, INIT_YPOS_P1 ))
			self.west_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P1 - self.tank_size, INIT_YPOS_P1))
		elif player_number == 2:
			self.north_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P2, INIT_YPOS_P2 - self.tank_size))
			self.south_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P2, INIT_YPOS_P2 +self.tank_size))
			self.east_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P2 + self.tank_size, INIT_YPOS_P2 ))
			self.west_sensor_rect = self.surf.get_rect(center = (INIT_XPOS_P2 - self.tank_size, INIT_YPOS_P2))
		self.sensors_list = [	self.north_sensor_rect, 
								self.south_sensor_rect,
								self.east_sensor_rect,
								self.west_sensor_rect]

	def move_sensors(self, key):
		if self.player_number == 1:
			if key == K_w:
				if self.rect.top > 0:
					for sensor in self.sensors_list:
						sensor.move_ip(0, -self.tank_size)
			elif key == K_s:
				if self.rect.bottom < SCREEN_HEIGHT:	
					for sensor in self.sensors_list:
						sensor.move_ip(0, self.tank_size)
			elif key == K_d:
				if self.rect.right < SCREEN_WIDTH:
					for sensor in self.sensors_list:
						sensor.move_ip(self.tank_size, 0)
			elif key == K_a:
				if self.rect.left > 0:	
					for sensor in self.sensors_list:
						sensor.move_ip(-self.tank_size, 0)
		elif self.player_number == 2:
			if key == K_i:
				if self.rect.top > 0:
					for sensor in self.sensors_list:
						sensor.move_ip(0, -self.tank_size)
			elif key == K_k:
				if self.rect.bottom < SCREEN_HEIGHT:
					for sensor in self.sensors_list:
						sensor.move_ip(0, self.tank_size)
			elif key == K_j:
				if self.rect.left > 0:
					for sensor in self.sensors_list:
						sensor.move_ip(-self.tank_size, 0)
			elif key == K_l:
				if self.rect.right < SCREEN_WIDTH:
					for sensor in self.sensors_list:
						sensor.move_ip(self.tank_size, 0)


	def set_collision(self, boolean, direction):
		if direction == 'n':
			self.has_north_collision = boolean
		elif direction == 's':
			self.has_south_collision = boolean
		elif direction == 'e':
			self.has_east_collision = boolean
		elif direction == 'w':
			self.has_west_collision = boolean

	def return_health(self):
		return int(self.tank.health)


class Tank:
	def __init__(self, health, tank_type, player_number):
		self.health = health
		self.tank_type = tank_type
		self.player_number = player_number
		self.size = BLOCK_SIZE

	def set_tank_image(self):
		if self.player_number == 1:
			if self.tank_type == "A":
				surface = pygame.image.load("smalltank.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE/2), int(BLOCK_SIZE/2)))
				self.size = int(BLOCK_SIZE/2)
			elif self.tank_type == "B":
				surface = pygame.image.load("quicktank.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
			elif self.tank_type == "C":
				surface = pygame.image.load("fasttank.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
			elif self.tank_type == "D":
				surface = pygame.image.load("largetank.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
		elif self.player_number == 2:
			if self.tank_type == "A":
				surface = pygame.image.load("smalltank2.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE/2), int(BLOCK_SIZE/2)))
				self.size = int(BLOCK_SIZE/2)
			elif self.tank_type == "B":
				surface = pygame.image.load("quicktank2.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
			elif self.tank_type == "C":
				surface = pygame.image.load("fasttank2.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
			elif self.tank_type == "D":
				surface = pygame.image.load("largetank2.png").convert()
				surface = pygame.transform.scale(surface, (int(BLOCK_SIZE), int(BLOCK_SIZE)))
		return surface

	def got_hit(self, bullet_damage):
		self.health = int(int(self.health) - int(bullet_damage))

	def bullet_type(self):
		if self.tank_type == 'C':
			return "fast"
		elif self.tank_type == 'D':
			return "big"
		else:
			return "regular"
	# def updatePowerups(powerup):

BULLET_SPEED = 10
class Bullet(pygame.sprite.Sprite):
	def __init__(self, tank, direction, center):
		super(Bullet, self).__init__()
		# self.surf.set_colorkey((0, 0, 0), RLEACCEL)
		self.tank = tank
		self.direction = direction
		self.bullet_speed = 0
		self.set_bullet_type()
		self.surf.set_colorkey((0,0,0), RLEACCEL)
		surfsize = self.surf.get_size()
		self._surf = self.surf
		self.rect = self.surf.get_rect(center=center)
		

	def rotate(self, angle):
		center = self.rect.center
		self.surf = pygame.transform.rotate(self._surf, angle)
		self.rect = self.surf.get_rect(center = center)

	def update(self):
		self.rotate(self.direction)
		if self.direction == 90:
			self.rect.move_ip(0, -self.bullet_speed)
		elif self.direction == 270:
			self.rect.move_ip(0, self.bullet_speed)
		elif self.direction == 180:
			self.rect.move_ip(-self.bullet_speed, 0)
		elif self.direction == 0:
			self.rect.move_ip(self.bullet_speed, 0)

		if self.rect.left < 0:
		    self.kill()
		if self.rect.right > SCREEN_WIDTH:
		    self.kill()
		if self.rect.top <= 0:
		    self.kill()
		if self.rect.bottom >= SCREEN_HEIGHT:
		    self.kill()

	def set_bullet_type(self):
		bullet_type = self.tank.bullet_type()
		if bullet_type == "fast":
			self.surf = pygame.image.load("fastbullet2.png").convert()
			self.surf = pygame.transform.scale(self.surf, (int(BLOCK_SIZE/2), int(BLOCK_SIZE/2)))
			self.bullet_speed = BULLET_SPEED * 2
		elif bullet_type == "big":
			self.surf = pygame.image.load("bullet2.png").convert()
			self.surf = pygame.transform.scale(self.surf, (int(BLOCK_SIZE*1.5), int(BLOCK_SIZE*1.5)))
			self.bullet_speed = BULLET_SPEED
		else:
			self.surf = pygame.image.load("bullet2.png").convert()
			self.surf = pygame.transform.scale(self.surf, (int(BLOCK_SIZE/2), int(BLOCK_SIZE/2)))
			self.bullet_speed = BULLET_SPEED

HEALTHBAR_POS_1 = (200, 50)
HEALTHBAR_POS_2 = (600, 50)
HEALTHBAR_SIZE = (200,20)
HEALTHBAR_COLOR_1 = (0,0,255)
HEALTHBAR_COLOR_2 = (255,0,0)

class HealthBar(pygame.sprite.Sprite):
	def __init__(self, health, player_number, screen):
		super(HealthBar, self).__init__()
		self.health = int(health)
		self.player_number = player_number
		self.surf = pygame.Surface(HEALTHBAR_SIZE)
		font = pygame.font.Font('freesansbold.ttf', 20)
		if player_number == 1:
			self.surf.fill(HEALTHBAR_COLOR_1)
			self.rect = self.surf.get_rect(center = HEALTHBAR_POS_1)
			self.title = font.render('Player 1', True, HEALTHBAR_COLOR_1)
			self.textrect = self.title.get_rect(center = (150, 20))
		else:
			self.surf.fill(HEALTHBAR_COLOR_2)
			self.rect = self.surf.get_rect(center = HEALTHBAR_POS_2)
			self.title = font.render('Player 2', True, HEALTHBAR_COLOR_2)
			self.textrect = self.title.get_rect(center = (650, 20))

	def update(self, health):
		self._surf = self.surf
		self.surf = pygame.transform.scale(self.surf, (int(health), 20))

		surfsize = self.surf.get_size()
		_surfsize = self._surf.get_size()
		if self.player_number == 2:
			self.rect.centerx += _surfsize[0] - surfsize[0]

class Tree(pygame.sprite.Sprite):
	def __init__(self, coordinates):
		super(Tree, self).__init__()
		self.coordindates = coordinates
		self.surf = pygame.image.load("tree.png").convert()
		self.surf = pygame.transform.scale(self.surf, (BLOCK_SIZE, BLOCK_SIZE))
		self.rect = self.surf.get_rect(center=self.coordindates)

class Brick(pygame.sprite.Sprite):
	def __init__(self, coordinates, health):
		super(Brick, self).__init__()
		self.coordindates = coordinates
		self.health = int(health)
		self._health = int(health)
		self.surf = pygame.image.load("brick_100.png").convert()
		self.surf = pygame.transform.scale(self.surf, (BLOCK_SIZE, BLOCK_SIZE))
		self.rect = self.surf.get_rect(center=self.coordindates)
		self.is_dead = False

	def update(self, bullet_damage):
		self.bullet_damage = int(bullet_damage)
		self.health = int(self.health - self.bullet_damage)
		self.percentage = int((self.health/self._health)*100)
		# print(self.percentage)
		if self.percentage < 100 and self.percentage >= 75:
			self.surf = pygame.image.load("brick_75.png").convert()
		elif self.percentage < 75 and self.percentage >= 50:
			self.surf = pygame.image.load("brick_75.png").convert()
		elif self.percentage < 50 and self.percentage >= 25:
			self.surf = pygame.image.load("brick_50.png").convert()
		elif self.percentage > 0 and self.percentage < 25:
			self.surf = pygame.image.load("brick_25.png").convert()
		elif self.percentage <= 0:
			self.is_dead = True
		
		self.surf = pygame.transform.scale(self.surf, (BLOCK_SIZE, BLOCK_SIZE))

	def return_health(self):
		return self.health
