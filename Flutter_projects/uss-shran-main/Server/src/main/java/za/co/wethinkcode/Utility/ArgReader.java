package za.co.wethinkcode.Utility;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

public class ArgReader {
    private Options options = new Options();
    private String[] args;
    private int port;
    private int worldSize;
    private List<int[]> obstacles;
    
    public ArgReader(String[] args) {
        this.args = args;
        this.port = 5000;
        this.worldSize = 1;
        this.obstacles = new ArrayList<int[]>();
        addOptions();
    }

    private void addOptions() {
        Option portNum = new Option("p","port",true,"Port number");
        portNum.setRequired(false);
        options.addOption(portNum);

        Option size = new Option("s","size",true,"Size of world");
        size.setRequired(false);
        options.addOption(size);

        Option obs = new Option("o","obstacles",true,"Add in obstacles");
        size.setRequired(false);
        options.addOption(obs);
    }

    public int getPort() {
        return port;
    }

    public int getWorldSize() {
        return worldSize;
    }

    public List<int[]> getObstacles() {
        return obstacles;
    }

	public void setConfigs() {
        CommandLine cmd;
        CommandLineParser parser = new BasicParser();

        try{
            cmd = parser.parse(options, args);

            if (cmd.hasOption("p")) {
                port = Integer.parseInt(cmd.getOptionValue("port"));
            }

            if (cmd.hasOption("s")) {
                worldSize = Integer.parseInt(cmd.getOptionValue("size"));
            }

            if (cmd.hasOption("o")) {
                String[] strObs = cmd.getOptionValue("obstacles").split(",");
                for (int i = 0; i < strObs.length; i = i + 2) {
                    int[] currentObs = {Integer.parseInt(strObs[i]),Integer.parseInt(strObs[i + 1])};
                    obstacles.add(currentObs);
                }
            }
        }catch (Exception e) {

        }
	}
    
}
