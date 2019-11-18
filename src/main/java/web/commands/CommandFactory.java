package web.commands;

import web.commands.bus.*;
import web.commands.driver.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static Map<String, Command> getCommandMap = new HashMap<>();
    private static Map<String, Command> postCommandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static{
        getCommandMap.put("/app/login", new LoginGetCommand());
        getCommandMap.put("/app/admin", new AdminCommand());

        //Drivers

        getCommandMap.put("/app/admin/drivers", new DriversCommand());
        getCommandMap.put("/app/admin/drivers/addDriver", new AddDriverGetCommand());
        getCommandMap.put("/app/admin/drivers/deleteDriver", new DeleteDriverCommand());
        getCommandMap.put("/app/admin/drivers/setReady", new SetReadyDriverCommand());
        getCommandMap.put("/app/admin/drivers/setNotReady", new SetNotReadyDriverCommand());
        getCommandMap.put("/app/admin/drivers/editDriver", new EditDriverGetCommand());

        //Buses

        getCommandMap.put("/app/admin/buses", new BusesCommand());
        getCommandMap.put("/app/admin/buses/addBus", new AddBusGetCommand());
        getCommandMap.put("/app/admin/buses/deleteBus", new DeleteBusCommand());
        getCommandMap.put("/app/admin/buses/editBus", new EditBusGetCommand());
        getCommandMap.put("/app/admin/buses/setReady", new SetReadyBusCommand());
        getCommandMap.put("/app/admin/buses/setNotReady", new SetNotReadyBusCommand());


        postCommandMap.put("/app/admin/drivers/addDriver", new AddDriverPostCommand());
        postCommandMap.put("/app/admin/drivers/editDriver", new EditDriverPostCommand());
        postCommandMap.put("/app/admin/buses/addBus", new AddBusPostCommand());
        postCommandMap.put("/app/admin/buses/editBus", new EditBusPostCommand());
    }

    private CommandFactory(){}

    public static Command getCommand(String path, String type) {
        return "GET".equals(type)
                ? getCommand(path)
                : postCommand(path);
    }

    private static Command getCommand(String path) {
        return getCommandMap.getOrDefault(path, defaultCommand);
    }

    private static Command postCommand(String path) {
        return postCommandMap.getOrDefault(path, defaultCommand);
    }
}
