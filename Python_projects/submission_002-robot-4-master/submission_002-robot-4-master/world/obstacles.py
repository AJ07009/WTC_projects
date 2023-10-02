import random


class Obstacle:


    def __init__(self):
        self.size = random.randint(0, 10)
        self.list = []
        self.range_x = range(-100, 101)
        self.range_y = range(-200, 201)


    def set_world_size(self, x, y):
        self.range_x = range(-x, x+1)
        self.range_y = range(-y, y+1)
    

    def create_obstacles(self):

        i = 0
        while i < self.size:
            x = random.randint(-100, 101)
            y = random.randint(-200, 201)
            if self.is_valid_pos(x, y):
                i += 1
                self.list.append((x,y))
    

    def is_valid_pos(self, x, y):
        for l in self.list:
            if x in range(min(l[0], l[0]+5), max(l[0], l[0]+5)) and y in range(min(l[1], l[1]+5), max(l[1], l[1]+5)):
                return False
        return True


    def is_position_blocked(self, x, y):
        for obs in self.list:
            x_range = range(min(obs[0], obs[0]+5), max(obs[0], obs[0]+5))
            y_range = range(min(obs[1], obs[1]+5), max(obs[1], obs[1]+5))
            if x in x_range and y in y_range:
                return True
        return False


    def is_path_blocked(self, x1, y1, x2, y2):
        for obs in self.list:
            m, n = obs
            for i in range(5):
                if m+i in range(min(x1, x2), max(x1, x2)+1) and n+i in range(min(y1, y2), max(y1, y2)+1):
                    return True
        return False
    


    def get_obstacles(self):
        return self.list


    def print_obstacles(self):
        if self.size:
            print("There are some obstacles:")
        for o in self.list:
            print(F"- At position {o[0]},{o[1]} (to {o[0]+4},{o[1]+4})")