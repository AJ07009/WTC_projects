package za.co.wethinkcode.toyrobot;



public class ReplayCommand extends Command {
    public ReplayCommand(String argument) {
        super("replay", argument);
    }

    public ReplayCommand() {
        super("replay");
    }

    @Override
    public boolean execute(Robot target) {
        //break up argument
        //case 1: single number
        //case 2: range
        //case 3: reversed
        //case 4: nothing

        boolean reverse = false;
        boolean ranged = false;
        boolean single = false;
        int start = 0, end = -1;

        String argument = this.getArgument();

        if (argument.contains("reversed")) {
            //case 3
            //set reverse flag
            reverse = true;
            argument = argument.replace("reversed", "");
        }
        if (!argument.equals("") && Character.isDigit(argument.charAt(0))) {
            String[] range = argument.split("-");
            if (range.length == 2) {
                //case 2
//                reverse = true;
                ranged = true;
                start = Integer.parseInt(range[0]);
                end = Integer.parseInt(range[1]);
            } else if (range.length == 1) {
                //case 1
                start = Integer.parseInt(range[0]);
                single = true;
            }
            //error
        }

        target.setStatus(target.handleReplayCommand(reverse, ranged, single, start, end));

        return true;
    }
}