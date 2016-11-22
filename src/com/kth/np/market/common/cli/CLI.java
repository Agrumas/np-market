package com.kth.np.market.common.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class CLI {
    HashMap<String, Class<? extends Command>> commands;
    Class<? extends Command> defaultCommand;
    String prompt = "Guest";

    public CLI() {
        this.commands = new HashMap<>();
        this.defaultCommand = UsageCommand.class;
        register(this.defaultCommand);
    }

    public void register(Class<? extends Command> cmd) {
        String name = cmd.getSimpleName().replaceAll("Command$", "");
        name = name.replaceAll("([a-z])([A-Z])", "$1-$2").replaceAll("_", "-").toLowerCase();
        register(name, cmd);
    }

    public void register(String name, Class<? extends Command> cmd) {
        commands.put(name, cmd);
    }

    public void handle() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(prompt + ">");
            try {
                String userInput = consoleIn.readLine();
                String input[] = userInput.split(" ");
                String cmdName = extractCommandName(input);
                Class<? extends Command> cmdClass = commands.get(cmdName);
                if (cmdClass == null) {
                    cmdClass = this.defaultCommand;
                }
                Command cmd = getCommand(cmdClass, cmdName, extractArguments(input));
                execute(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void execute(Command cmd){
        cmd.execute();
    }


    protected Command getCommand(Class<? extends Command> cmdClass, String cmdName, String[] args) {
        Command instance = null;
        try {
            Constructor<? extends Command> constr = cmdClass.getConstructor();
            instance = constr.newInstance();
            instance.init(this, cmdName, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Set<String> getAvailableCommands() {
        return commands.keySet();
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    private static String extractCommandName(String[] args) {
        return (args.length == 0) ? null : args[0];
    }

    private static String[] extractArguments(String[] args) {
        return (args.length <= 1) ? new String[]{} : Arrays.copyOfRange(args, 1, args.length);
    }
}
