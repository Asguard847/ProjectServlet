package web.commands;

import web.commands.assignment.ApproveAssignmentCommand;
import web.commands.assignment.AssignmentsCommand;
import web.commands.bus.*;
import web.commands.driver.*;
import web.commands.locale.LocaleEnCommand;
import web.commands.locale.LocaleRuCommand;
import web.commands.login.LoginCommand;
import web.commands.login.LogoutCommand;
import web.commands.route.*;
import web.commands.user.UserCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static Map<String, Command> commandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static{
        commandMap.put("/app/login", new LoginCommand());
        commandMap.put("/app/admin", new AdminCommand());
        commandMap.put("/app/user", new UserCommand());
        commandMap.put("/app/logout", new LogoutCommand());
        commandMap.put("/app/user/approve", new ApproveAssignmentCommand());
        commandMap.put("/app/setRu", new LocaleRuCommand());
        commandMap.put("/app/setEn", new LocaleEnCommand());

        //Drivers

        commandMap.put("/app/admin/drivers", new DriversCommand());
        commandMap.put("/app/admin/drivers/addDriver", new AddDriverCommand());
        commandMap.put("/app/admin/drivers/deleteDriver", new DeleteDriverCommand());
        commandMap.put("/app/admin/drivers/setReady", new SetReadyDriverCommand());
        commandMap.put("/app/admin/drivers/setNotReady", new SetNotReadyDriverCommand());
        commandMap.put("/app/admin/drivers/editDriver", new EditDriverCommand());

        //Buses

        commandMap.put("/app/admin/buses", new BusesCommand());
        commandMap.put("/app/admin/buses/addBus", new AddBusCommand());
        commandMap.put("/app/admin/buses/deleteBus", new DeleteBusCommand());
        commandMap.put("/app/admin/buses/editBus", new EditBusCommand());

        //Routes

        commandMap.put("/app/admin/routes", new RoutesCommand());
        commandMap.put("/app/admin/routes/addRoute", new AddRouteCommand());
        commandMap.put("/app/admin/routes/deleteRoute", new DeleteRouteCommand());
        commandMap.put("/app/admin/routes/editRoute", new EditRouteCommand());
        commandMap.put("/app/admin/routes/deleteBusFromRoute", new RemoveBusFromRouteCommand());


        commandMap.put("/app/admin/assignments", new AssignmentsCommand());

    }

    private CommandFactory(){}

    public static Command getCommand(String path) {
        return commandMap.getOrDefault(path, defaultCommand);
    }

}
