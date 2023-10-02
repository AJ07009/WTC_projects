"""
Step 11 is where we deal with 'sprinting'. this is a recursive funtion.
"""
import re
import sys
import os


def get_command_list(ranges, reverse, prev_commands):
    #revese list if needed
    if reverse:
        prev_commands = prev_commands[::-1]
    #filter list for replayable

    #get range
    if ranges:
        ranges_list = ranges.split('-')
        begin = 0
        leng_list = len(prev_commands)

        if len(ranges_list) == 1:
            begin = len(prev_commands)-int(ranges_list[0])
            return prev_commands[begin:]
        leng_list, begin = list(map(int, ranges_list))
        return prev_commands[begin-1:leng_list-1]
    return prev_commands


def replay(robot, prev_commands):
    pass


def handle_replay(robot_name, command_list, pos, direction, prev_commands):
    #do shit
    #['replay', '4-2', 'silent']   ---> example of how command_list looks
    ignore = ['replay', 'help', 'off']
    #check for silent in list.
    #check for reverse in list.
    prev_commands = list(filter(lambda x: x[0].lower() not in ignore, prev_commands))

    #check if range in list
    replay_commands = None
    silent = ''
    reverse = ''
    ranges = None
    command_list = list(map(str.upper, command_list))

    if 'SILENT' in command_list:
        silent = ' silently'
    if 'REVERSED' in command_list:
        reverse = ' in reverse'
    # isolate digit element if it exists
    for c in command_list:
        if c[0].isdigit():
            ranges = c

    #get filtered prev_commands
    replay_commands = get_command_list(ranges, reverse, prev_commands)    
    if silent:
        old_out = sys.stdout
        sys.stdout = open(os.devnull, "w")
    for commands in replay_commands:
        running, pos, direction, prev_commands = move(robot_name, commands, pos, direction, prev_commands)
    if silent:
        sys.stdout = old_out
    print(F" > {robot_name} replayed {len(replay_commands)} commands{reverse}{silent}.")
    print(F" > {robot_name} now at position ({pos[0]},{pos[1]}).")
    return pos, direction


def sprint(robot_name, step_size, coord, direction):
    #work out total movement amount.
    s = sum(x for x in range(1, int(step_size)+1))
    change = get_move_coords(direction, s)
    #get proposed pos by ading the new changes in x and y.
    moved = tuple(sum(x) for x in zip(coord, change))
    if check_range(moved):
        #get og step tuple to pass to recursive function
        change = get_move_coords(direction, int(step_size))
        #call recursive funct.
        coord = sprint_rec(robot_name, coord, change)
    else:
        print(F"{robot_name}: Sorry, I cannot go outside my safe zone.")
    print(F" > {robot_name} now at position ({coord[0]},{coord[1]}).")
    return coord


def sprint_rec(robot_name, coord, moved):
    if moved == (0,0):
        #done all the moves
        return coord
    #delta is what we decrease or increase the tuple val by. if neg, +1 to get less.
    delta = -1
    y = list(filter(lambda x: x != 0, moved))
    y = y[0]
    if y < 0:
        delta = 1
    #update coord
    coord = tuple(sum(x) for x in zip(coord, moved))
    print(F" > {robot_name} moved forward by {abs(y)} steps.")
    #update move tuple with +/- 1
    moved = tuple(m if m == 0 else m+delta for m in moved)
    #recursive call
    coord = sprint_rec(robot_name, coord, moved)
    return coord


def check_range(coord):
    #setup the range lists. no glabals, and we dont want to call this everytime,
    #therefore we just have to pay a small performance cost and lame design of
    #runging range everytime the function is called
    range_x = range(-100, 101)
    range_y = range(-200, 201)
    #return bool for if in range
    return coord[0] in range_x and coord[1] in range_y


def right(robot_name, coord, direction):
    #right moves to the right of the list so +1 deom current index.
    direction = ((direction+1)%4)
    print(F" > {robot_name} turned right.")
    print(F" > {robot_name} now at position ({coord[0]},{coord[1]}).")
    return direction


def left(robot_name, coord, direction):
    #left moves to the left of the list so -1 from current index.
    direction = ((direction-1)%4)
    print(F" > {robot_name} turned left.")
    print(F" > {robot_name} now at position ({coord[0]},{coord[1]}).")
    return direction


def get_move_coords(direction, step_size):
    #list to contain tuples to mul step by.
    #orientation order is: UP, RIGHT, DOWN, LEFT
    dir = [(0,1), (1,0), (0,-1), (-1, 0)]
    #mul step by dir[direction] to get move tuple:
    coord = tuple([d*step_size for d in dir[direction]])
    return coord


def back(robot_name, step_size, coord, direction):
    #make coordinat tuple from step size and get_move_coords
    s = -1*int(step_size)
    change = get_move_coords(direction, s)
    #get proposed pos by ading the new changes in x and y.
    moved = tuple(sum(x) for x in zip(coord, change))
    if check_range(moved):
        #if valid, move
        coord = moved
        print(F" > {robot_name} moved back by {step_size} steps.")
    else:
        print(F"{robot_name}: Sorry, I cannot go outside my safe zone.")
    print(F" > {robot_name} now at position ({coord[0]},{coord[1]}).")
    return coord


def forward(robot_name, step_size, coord, direction):
    #make coordinat tuple from step size.
    s = int(step_size)
    change = get_move_coords(direction, s)
    #get proposed pos by ading the new changes in x and y.
    moved = tuple(sum(x) for x in zip(coord, change))
    if check_range(moved):
        #if valid, move
        coord = moved
        print(F" > {robot_name} moved forward by {step_size} steps.")
    else:
        print(F"{robot_name}: Sorry, I cannot go outside my safe zone.")
    print(F" > {robot_name} now at position ({coord[0]},{coord[1]}).")
    return coord


def handle_help():
    #method to call the help method and print the returned string
    help_string = get_help()
    print(help_string)


def get_help():
    #build up the string. when you add new functions, simply add them to this string
    help_string = "I can understand these commands:\n"
    help_string += "OFF  - Shut down robot\n"
    help_string += "HELP - provide information about commands\n"
    return help_string


def prompt(name):
    valid = False
    command = ''
    #run loop to to repeatedly ask for input until valid
    while not valid:
        command = input(F"{name}: What must I do next? ").strip()
        #validdate fuction returns true or false
        valid = validate(command)
        if not valid:
            #if the input was not valid, run in here and tell the user that
            print(F"{name}: Sorry, I did not understand '{command}'.")
    return command


def validate(command):
    #create a list of valid commands that are 1 word. We will build on this function
    #as we get to more steps
    cmds_one = ['OFF', 'HELP', 'RIGHT', 'LEFT']

    #is is because forward and back has another argument, <step> that folows it
    cmds_two = ['FORWARD', 'BACK', 'SPRINT']

    #add pattern for regex check:
    pattern = '^(replay)( \d+(-\d+)?)?(( reversed( silent)?)|( silent))?$'

    #check for empty input
    if not command:
        return False
    list_commands = command.split()
    l = len(list_commands)

    #if the command if in the list and only one word was given, return true
    if list_commands[0].upper() in cmds_one and l == 1:
        return True
    if list_commands[0].upper() in cmds_two and l == 2:
        #check if second value in list is an integer.
        if list_commands[1].isnumeric():
            return True
    if re.match(pattern, command.lower()):
        return True

    return False



def get_name():
    #get input
    robot_name = input("What do you want to name your robot? ")
    #display information as specified
    print(robot_name + ": Hello kiddo!")
    #return so it can be remembered
    return robot_name


def robot_start():
    run_game()


def move(robot_name, command_list, pos, direction, prev_commands):
    running = True
    if command_list[0].upper() == 'OFF':
        running = False
    #handle the help command
    if command_list[0].upper() == 'HELP':
        handle_help()
    if command_list[0].upper() == 'FORWARD':
        #pass position to forward, and update pos
        pos = forward(robot_name, command_list[1], pos, direction)
    if command_list[0].upper() == 'BACK':
        #pass position to back, and update pos
        pos = back(robot_name, command_list[1], pos, direction)
    if command_list[0].upper() == 'RIGHT':
        #handle right, update direction
        direction = right(robot_name, pos, direction)
    if command_list[0].upper() == 'LEFT':
        #handle left, update direction
        direction = left(robot_name, pos, direction)
    if command_list[0].upper() == 'SPRINT':
        #pass position to sprint, and update pos
        pos = sprint(robot_name, command_list[1], pos, direction)
    if command_list[0].upper() == 'REPLAY':
        pos, direction = handle_replay(robot_name, command_list, pos, direction, prev_commands)
    

    return running, pos, direction, prev_commands


def run_game():
    #get robot name
    robot_name = get_name()
    #add position tupple
    pos = (0,0)
    #add direction val. 0, default == UP
    direction = 0
    running = True
    prev_commands = []
    while running:
        command = prompt(robot_name)
        command_list = command.split()
        #add spliting of command to get function and the step value if needed.
        #update checks below
        running, pos, direction, prev_commands = move(robot_name, command_list, pos, direction, prev_commands)
        prev_commands.append(command_list)
    print(F"{robot_name}: Shutting down..")


if __name__ == "__main__":
    run_game()

