from io import StringIO
import re
import sys
import os
from world.obstacles import *
if len(sys.argv) == 2 and sys.argv[1] == 'turtle':
    from world.turtle.world import *
elif len(sys.argv) == 2 and sys.argv[1] == 'text':
        from world.text.world import *
else :
    from world.text.world import *
    

class Robot:
    def __init__(self, name):
        '''
        init for robot class.
        '''
        self.turtle = None
        self.name = name.upper()
        self.x = 0
        self.y = 0
        self.d = 0
        self.orientation = 'up'
        self.range_x = range(-100, 101) #default
        self.range_y = range(-200, 201) #default
        self.directions = {
            'up': (0, 1), 'right': (1, 0),
            'down': (0, -1), 'left': (-1, 0)
            }
        self.board = None
        self.commands = []
        self.silent = False
        self.obstacles = Obstacle()
        self.obstacles.create_obstacles()
        if len(sys.argv) == 2 and sys.argv[1] == 'turtle':
            self.board = Board()
            self.turtle = self.board.get_turtle()
            self.board.draw_obstacles(self.obstacles.list)


    def get_actual_orientation(self, step):
        '''
        MOVED
        Function to get the actual move coordinates from the given
        step in the correct orientation
            Parameters: self, step (the step amount)
            Return: tuple (step-x, step-y)
        '''
        orient = self.directions[self.orientation]
        return tuple([step*x for x in orient])


    def update_pos(self, x, y):
        '''
        function to update the position of the robot.
            Parameters: self, x (new x pos), y (new y pos)
            Return: None
        '''
        self.x += x
        self.y += y


    def set_orientation(self, orientation):
        '''
        function to update the orientation of the robot.
            Parameters: self, orientation (the new orientation of robot)
            Return: None
        '''
        self.orientation = orientation


    def set_world(self, x, y):
        '''
        function to get world size from world module. Set from -x to +x etc.
            Parameters: self, x (the x range), y(the y range)
            Return: None
        '''
        self.range_x = range(-x, x+1)
        self.range_y = range(-y, y+1)


    def print_local(self):
        '''
        MOVED
        function to display the position of the robot.
            Parameters: self
            Return: None
        '''
        print(F" > {self.name} now at position ({self.x},{self.y}).")


    def get_pos(self):
        '''
        function to return the position of the robot.
            Parameters: self
            Return: tuple (x pposition, y position)
        '''
        return (self.x, self.y)


    def valid_pos(self, move):
        '''
        MOVED
        function to check whether the proposed move is valid.
            Parameters: self, move (tuple (x, y))
            Return: True (if valid), False (otherwise)
        '''
        in_range = False
        blocked = False

        move = tuple(map(sum, zip(self.get_pos(), move)))
        if move[0] in self.range_x and move[1] in self.range_y:
            #check no obstacles in the way

            in_range = True
            if self.obstacles.is_position_blocked(*move) or self.obstacles.is_path_blocked(self.x, self.y, *move):
                blocked = True
        return (in_range, blocked)


def handle_replay(robot, cmd):
    num_str = None
    match = None
    digit = "(\d+(-\d+)?)"
    rr = {
        r"^(replay)( \d+(-\d+)?)?$":replay_normal,
        r"^(replay( \d+(-\d+)?)? silent)$":replay_silent,
        r"^(replay( \d+(-\d+)?)? reversed)$":replay_reverse,
        r"^(replay( \d+(-\d+)?)? reversed silent)$":replay_reverse_silent
    }
    text = " ".join(cmd).rstrip()
    for pattern, funct in rr.items():
        if re.match(pattern, text):
            #get optional digit
            if len(cmd) > 1:
                match = re.findall(digit, cmd[1])
            if match:
                funct(robot, match[0][0])
            else:
                funct(robot, None)


def get_command_list(range_m, cmd):
    if not range_m:
        return cmd

    range_m = range_m.split("-")
    n = 0
    m = len(cmd)

    if len(range_m) == 1:
        n = len(cmd)-int(range_m[0])
        return cmd[n:]
    m, n = list(map(int,range_m))
    return cmd[n-1:m-1]


def replay(robot, command_list, text='', out=None):
    for move in command_list:
        exec(move)
    if out:
        sys.stdout = out
    print(F" > {robot.name} replayed {len(command_list)} commands{text}.")
    robot.print_local()


def replay_normal(robot, n, text='', out=None):
    command_list = get_command_list(n, robot.commands)
    replay(robot, command_list, text, out)
    return True


def replay_silent(robot, n, text=' silently'):
    old_out = sys.stdout
    sys.stdout = open(os.devnull, "w")
    replay_normal(robot, n, text, old_out)
    return True


def replay_reverse(robot, n, text=' in reverse', out=None):
    cmd = robot.commands[::-1]
    command_list = get_command_list(n, cmd)
    replay(robot, command_list, text, out)
    return True


def replay_reverse_silent(robot, n, text=' in reverse silently'):
    old_out = sys.stdout
    sys.stdout = open(os.devnull, "w")
    replay_reverse(robot, n, text, old_out)


def info(robot=None, x=None):
    '''
    Function to display help info.
        Parameters: robot (default None), x (default None)
        Returns: None
    '''
    moves = {
        "help":"OFF  - Shut down robot",
        "off":"HELP - provide information about commands",
        "forward":"FORWARD <steps>  - Moves forward by <steps> amount",
        "back":"BACK <steps>  - Moves backwards by <steps> amount",
        "right":"RIGHT  - Turn right",
        "left":"BACK  - Turn left",
        "sprint":"FORWARD <steps>  - Moves forward by [<steps>+<steps-1>+...+1] amount"
    }
    print("I can understand these commands:")
    for m, d in moves.items():
        print(d)


def move(robot, cmd):
    '''
    Function to call correct move function based on the input.
        Parameters: robot (the robot object), cmd ((list) the valid input information)
        Returns: result from chose function
    '''
    ignore = ['replay', 'help', 'off']
    moves = {
        "forward":forward, "back":back, "right":rotate, "left":rotate,
        "sprint":sprint, "help":info, "off":off, "replay":handle_replay
    }
    #append funct call to list of moves made
    if cmd[0] not in ignore:
        command_str = (cmd[0]+'(robot, '+ str(cmd) + ')')
        robot.commands.append(command_str)
    return moves[cmd[0]](robot, cmd)

"""
def sprint(robot, cmd):
    '''
    MOVED
    Handler for recursive sprint function.
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''
    y = int(cmd[1])
    lim = sum(x for x in range(1, y+1))
    actual = robot.get_actual_orientation(lim)

    if robot.valid_pos(actual):
        step = robot.get_actual_orientation(y)
        sprint_rec(robot, step)
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def sprint_rec(robot, step):
    '''
    MOVED
    Function to recursively step forward by step
        Parameters: robot (the robot object), step (the steo size)
        Returns: None
    '''
    if (step == (0, 0)):
        return
    y = [x for x in step if x != 0]
    y = y[0]
    delta = -1
    if not robot.silent:
        print(F" > {robot.name} moved forward by {abs(y)} steps.")
    robot.update_pos(*step)
    if y < 0:
        delta = 1
    step = tuple(x if x == 0 else x+delta for x in step)
    sprint_rec(robot, step)


def rotate(robot, cmd):
    '''
    MOVED
    Function to rotate the orientation in the given direction
        Parameters: robot (the robot object), cmd (the command - direction)
        Returns: True
    '''
    rot = list(robot.directions.keys())
    dist = robot.d
    if cmd[0] == 'right':
        print(F" > {robot.name} turned right.")
        dist += 1
    else:
        dist -= 1
        print(F" > {robot.name} turned left.")
    dist %= 4
    dir = rot[dist]
    robot.set_orientation(dir)
    robot.d = dist
    robot.print_local()
    return True


def forward(robot, cmd):
    '''
    MOVED
    Function to step forwards by given amount
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''
    y = int(cmd[1])
    actual =robot.get_actual_orientation(y)

    if robot.valid_pos(actual):
        robot.update_pos(*actual)
        # print update
        print(F" > {robot.name} moved forward by {y} steps.")
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def back(robot, cmd):
    '''
    MOVED
    Function to step backwards by given amount
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''

    y = -1*int(cmd[1])
    actual = robot.get_actual_orientation(y)

    if robot.valid_pos(actual):
        robot.update_pos(*actual)
        # print update
        print(F" > {robot.name} moved back by {-y} steps.")
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def off(robot=None, x=None):
    '''
    MOVED
        Function to 'exit' the game
        Parameters: robot (default None), x (default None)
        Returns: String
    '''
    return "EXIT"
"""

def prompt(msg):
    '''
    Function to get user input.
        Parameters: msg (string to prompt user with)
        returns: String
    '''
    return input(msg).strip()


def validate_command(cmd):
    '''
    Function to validate user input.
        Parameters: cmd (string - user input)
        Returns: tuple (boolean for valid or not, cmd.split(" "))
    '''
    Valid = False
    pos = ["forward", "back", "sprint"]
    not_pos = ["help", "off", "right", "left"]
    check = list(cmd.lower().split(" "))
    if check[0] == 'replay':
        return (validate_replay(cmd), *check)
    if len(check) != 2 and check[0] not in not_pos:
        return (False, "")
    if check[0] in not_pos:
        check.append("0")
        return (True, *check)
    if check[0] in pos and check[1].isdigit():
        return (True, *check)
    return (False, "")


def validate_replay(cmd):
    # ['replay', 'replay reverse', 'replay silent', 'replay reverse silent']

    pattern = "^(replay)( \d+(-\d+)?)?(( reversed( silent)?)?|( silent)?)$"
    if re.match(pattern, cmd.lower()):
        #TODO: if numeric, check that m > n
        return True
    return False


def run_game(robot):
    '''
    Function to run game loop.
        Parameters: robot (the robot object)
        Returns: None
    '''
    running = True
    valid = False
    while running:
        cmd = prompt(F"{robot.name}: What must I do next? ")
        com = validate_command(cmd)
        if com[0]:
            valid = move(robot, com[1:])
            if type(valid) == str:
                break
        else:
            print(F"{robot.name}: Sorry, I did not understand '{cmd}'.")
    print(F"{robot.name}: Shutting down..")


def robot_start():
    '''
    Entry function.
        Parameters: None
        Returns: None
    '''
    msg = "What do you want to name your robot? "
    name = prompt(msg)
    bot = Robot(name)
    print(F"{bot.name}: Hello kiddo!")
    bot.obstacles.print_obstacles()
    run_game(bot)


if __name__ == "__main__":
    robot_start()
