package web.commands;

import web.commands.assignment.ApproveAssignmentCommand;
import web.commands.assignment.AssignmentsCommand;
import web.commands.bus.*;
import web.commands.driver.*;
import web.commands.login.LoginGetCommand;
import web.commands.login.LoginPostCommand;
import web.commands.login.LogoutCommand;
import web.commands.route.*;
import web.commands.user.UserCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static Map<String, Command> getCommandMap = new HashMap<>();
    private static Map<String, Command> postCommandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static{
        getCommandMap.put("/app/login", new LoginGetCommand());
        getCommandMap.put("/app/admin", new AdminCommand());
        getCommandMap.put("/app/user", new UserCommand());
        getCommandMap.put("/app/logout", new LogoutCommand());
        getCommandMap.put("/app/user/approve", new ApproveAssignmentCommand());

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


        getCommandMap.put("/app/admin/routes", new RoutesCommand());
        getCommandMap.put("/app/admin/routes/addRoute", new AddRouteGetCommand());
        getCommandMap.put("/app/admin/routes/deleteRoute", new DeleteRouteCommand());
        getCommandMap.put("/app/admin/routes/editRoute", new EditRouteGetCommand());
        getCommandMap.put("/app/admin/routes/deleteBusFromRoute", new RemoveBusFromRouteCommand());


        getCommandMap.put("/app/admin/assignments", new AssignmentsCommand());

        postCommandMap.put("/app/admin/drivers/addDriver", new AddDriverPostCommand());
        postCommandMap.put("/app/admin/drivers/editDriver", new EditDriverPostCommand());
        postCommandMap.put("/app/admin/buses/addBus", new AddBusPostCommand());
        postCommandMap.put("/app/admin/buses/editBus", new EditBusPostCommand());
        postCommandMap.put("/app/admin/routes/addRoute", new AddRoutePostCommand());
        postCommandMap.put("/app/admin/routes/editRoute", new EditRoutePostCommand());
        postCommandMap.put("/app/login", new LoginPostCommand());

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
