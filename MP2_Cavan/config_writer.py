import configparser

write_config = configparser.ConfigParser()

write_config.add_section("Default")
write_config.set("Default", "POWERUP_GENERATION_PERIOD", "10")
write_config.set("Default", "POWERUP_MAP_DURATION", "10")
write_config.set("Default", "CAMOFLAGE_DURATION", "10")
write_config.set("Default", "STOPWATCH_DURATION", "10")
write_config.set("Default", "DOUBLE_DAMAGE_DURATION", "10")
write_config.set("Default", "PLAYER_HEALTH", "10")
write_config.set("Default", "BRICK_HEALTH", "10")
write_config.set("Default", "BULLET_DAMAGE", "10")

cfgfile = open("setup.ini",'w')
write_config.write(cfgfile)
cfgfile.close()

# Reading
# read_config = configparser.ConfigParser()
# read_config.read("sample.ini")
# 
# name = read_config.get("Section1", "name")
# print(name)